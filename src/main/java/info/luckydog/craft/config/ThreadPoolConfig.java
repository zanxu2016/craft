package info.luckydog.craft.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * ThreadPoolConfig
 *
 * @author eric
 * @since 2019/7/11
 */
@Configuration
public class ThreadPoolConfig {

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 5;
    private static final long KEEP_ALIVE_TIME = 0L;
    private static final int QUEUE_CAPACITY = 5;

    /**
     * 消费队列线程
     *
     * @return ExecutorService
     */
    @Bean(value = "consumerQueueThreadPool")
    public ExecutorService buildConsumerQueueThreadPool() {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("consumer-queue-thread-%d").build();

        ExecutorService pool = new MyThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        return pool;
    }
}

@Slf4j
class MyThreadPoolExecutor extends ThreadPoolExecutor {

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        log.info("thread name: {}, state: {}, isAlive: {}, isDaemon: {}", t.getName(), t.getState().name(), t.isAlive(), t.isDaemon());
        log.info("thread name: {}, active count: {}, completed task count: {}, queue size: {}, task count: {}",
                t.getName(), this.getActiveCount(), this.getCompletedTaskCount(), this.getQueue().size(), this.getTaskCount());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        log.info("runnable: {}", r.getClass());
        log.info("active count: {}, completed task count: {}, queue size: {}, task count: {}",
                this.getActiveCount(), this.getCompletedTaskCount(), this.getQueue().size(), this.getTaskCount());
    }

    @Override
    protected void terminated() {
        log.info("active count: {}, completed task count: {}, task count: {}, queue size: {}",
                this.getActiveCount(), this.getCompletedTaskCount(), this.getTaskCount(), this.getQueue().size());
    }

}
