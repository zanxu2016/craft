package info.luckydog.craft.util;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * IdWorker
 * 雪花算法 Snowflake
 * 来自 Twitter
 *
 * @author eric
 * @since 2019/6/25
 */
public class IdWorker {

    private final long workerId;
    private final static long TWEPOCH = 1288834974657L;
    private long sequence = 0L;
    private final static long WORKER_ID_BITS = 4L;
    private final static long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);// 15
    private final static long SEQUENCE_BITS = 10L;
    private final static long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private final static long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;// 14
    private final static long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);// 1023
    private long lastTimestamp = -1L;

    public IdWorker(final long workerId) {
        super();
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format(
                    "worker Id can't be greater than %d or less than 0",
                    MAX_WORKER_ID));
        }
        this.workerId = workerId;
    }

    public /*synchronized*/ long nextId() {
        long timestamp = this.timeGen();
        if (this.lastTimestamp == timestamp) {
            this.sequence = (this.sequence + 1) & SEQUENCE_MASK;
            if (this.sequence == 0) {
                System.out.println("###########" + SEQUENCE_MASK);
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0;
        }
        if (timestamp < this.lastTimestamp) {
            try {
                throw new Exception(
                        String.format(
                                "Clock moved backwards. Refusing to generate id for %d milliseconds",
                                this.lastTimestamp - timestamp));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        this.lastTimestamp = timestamp;
        long nextId = ((timestamp - TWEPOCH << TIMESTAMP_LEFT_SHIFT)) | (this.workerId << WORKER_ID_SHIFT) | (this.sequence);
//        System.out.println("timestamp:" + timestamp + ", TIMESTAMP_LEFT_SHIFT:"
//                + TIMESTAMP_LEFT_SHIFT + ",nextId:" + nextId + ", workerId:"
//                + workerId + ", sequence:" + sequence);
        return nextId;
    }

    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }


    public static void main(String[] args) {
        IdWorker worker2 = new IdWorker(2);
//        System.out.println(worker2.nextId());

        // 测试并发情况
        Set<Long> nextIds = new HashSet<>();
        int threadCount = 100000;

        long start = System.currentTimeMillis();
        System.out.println("start generate ids...");
        for (int i = 0; i < threadCount; i++) {
            MyThread thread = new MyThread(worker2);
            FutureTask<Long> result = new FutureTask<>(thread);
            new Thread(result).start();

            try {
                Long nextId = result.get();
                nextIds.add(nextId);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("end generate ids...");
        System.out.println("thread count : " + threadCount + ", cost time " + (end - start) + " ms.");// 10万线程，不到43秒
        System.out.println("Does set's size equals to thread count? " + (nextIds.size() == threadCount));// set长度与线程数量一致
    }

}

class MyThread implements Callable<Long> {

    private IdWorker idWorker;

    public MyThread(IdWorker idWorker) {
        this.idWorker = idWorker;
    }

    @Override
    public Long call() {
        return idWorker.nextId();
    }
}

//class IdWorkerTest {
//
//    @Test
//    public void multiThreadNextIdTest() {
//        IdWorker worker2 = new IdWorker(2);
//        for (int i = 0; i < 100; i++) {
//            Thread thread = new MyThread("thread-" + i, worker2);
//            thread.start();
//        }
//    }
//}

