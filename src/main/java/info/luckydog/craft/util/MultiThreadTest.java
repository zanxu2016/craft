package info.luckydog.craft.util;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * MultiThreadTest
 *
 * @author eric
 * @since 2019/6/25
 */
public class MultiThreadTest {
}

// 与 闭锁 结构一致
class LatchTest {

    public static void main(String[] args) throws InterruptedException {

        Runnable taskTemp = new Runnable() {

            private int iCounter;

            @Override
            public void run() {
                // 发起请求
//              HttpClientOp.doGet("https://www.baidu.com/");
                iCounter++;
                System.out.println(System.nanoTime() + " [" + Thread.currentThread().getName() + "] iCounter = " + iCounter);
            }
        };

        int threadNum = 10;

        LatchTest latchTest = new LatchTest();
        latchTest.startTaskAllInOnce(threadNum, taskTemp);
//        latchTest.startNThreadsByBarrier(threadNum, taskTemp);
    }

    // CyclicBarrier 栅栏，实现集中线程，再放开线程执行任务，模拟真正的并发
    public void startNThreadsByBarrier(int threadNums, Runnable finishTask) throws InterruptedException {
        // 设置栅栏解除时的动作，比如初始化某些值
        CyclicBarrier barrier = new CyclicBarrier(threadNums, finishTask);
        // 启动 n 个线程，与栅栏阀值一致，即当线程准备数达到要求时，栅栏刚好开启，从而达到统一控制效果
        for (int i = 0; i < threadNums; i++) {
            Thread.sleep(100);
            new Thread(new CounterTask(barrier)).start();
        }
        System.out.println(Thread.currentThread().getName() + " out over...");
    }

    // CountDownLatch 闭锁，实现集中线程，再放开线程执行任务，模拟真正的并发
    public void startTaskAllInOnce(int threadNums, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(threadNums);
        for (int i = 0; i < threadNums; i++) {
            Thread t = new Thread(() -> {
                try {
                    // 使线程在此等待，当开始门打开时，一起涌入门中
                    startGate.await();
                    try {
                        task.run();
                    } finally {
                        // 将结束门减1，减到0时，就可以开启结束门了
                        endGate.countDown();
                    }
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            });
            t.start();
        }
        long startTime = System.nanoTime();
        System.out.println(startTime + " [" + Thread.currentThread() + "] All thread is ready, concurrent going...");
        // 因开启门只需一个开关，所以立马就开启开始门
        startGate.countDown();
        // 等等结束门开启
        endGate.await();
        long endTime = System.nanoTime();
        System.out.println(endTime + " [" + Thread.currentThread() + "] All thread is completed.");
        System.out.println("cost time " + (endTime - startTime)  + " ns");
    }
}


class CounterTask implements Runnable {

    // 传入栅栏，一般考虑更优雅方式
    private CyclicBarrier barrier;

    public CounterTask(final CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    public void run() {
        System.out.println(Thread.currentThread().getName() + " - " + System.currentTimeMillis() + " is ready...");
        try {
            // 设置栅栏，使在此等待，到达位置的线程达到要求即可开启大门
            System.out.println(Thread.currentThread().getName() + " - " + System.currentTimeMillis() + " is waiting...");
            barrier.await();
            System.out.println(Thread.currentThread().getName() + " - " + System.currentTimeMillis() + " started...");
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " - " + System.currentTimeMillis() + " is running...");
    }
}
