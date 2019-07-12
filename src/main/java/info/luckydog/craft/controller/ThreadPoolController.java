package info.luckydog.craft.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolController
 *
 * @author eric
 * @since 2019/7/11
 */
@Slf4j
@RestController
@RequestMapping("/thread-pool")
public class ThreadPoolController {

    @Resource(name = "consumerQueueThreadPool")
    private ExecutorService consumerQueueThreadPool;

    @GetMapping("/test/{n}")
    public void test(@PathVariable("n") int n) {
        long start = System.currentTimeMillis();
        //消费队列
        for (int i = 0; i < n; i++) {
            consumerQueueThreadPool.execute(new MyThread("t" + (i + 1)));
        }

        consumerQueueThreadPool.shutdown();

        try {
            while (!consumerQueueThreadPool.awaitTermination(1, TimeUnit.SECONDS)) {
                log.info("线程还在执行。。。");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error("exception occurs: [{}] ", e.getMessage());
        }

        long end = System.currentTimeMillis();
        log.info("一共处理了【{} ms】", (end - start));
    }
}

class MyThread extends Thread {

    private String name;

    public MyThread(String name) {
        super(name);
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println("线程：" + this.name + " 启动！");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
