package manage.tool.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

/**
 * 通用线程池
 *
 * @param <T>
 */
@Data
@Slf4j
public class CommonThreadPool<T> implements InitializingBean {

    /**
     * 核心线程数
     */
    private int coreSize;
    /**
     * 最大线程数
     */
    private int maxSize;
    /**
     * 空闲线程存活秒数
     */
    private int aliveSeconds;
    /**
     * 阻塞队列
     */
    private BlockingQueue queue;
    /**
     * 阻塞队列容量
     */
    private int capacity;
    /**
     * 线程池
     */
    private ThreadPoolExecutor threadPoolExecutor;

    public CommonThreadPool() {
    }

    public Future<T> submit(Callable<T> callableTask) {
        return threadPoolExecutor.submit(callableTask);
    }
    public void execute(Runnable command){
        threadPoolExecutor.execute(command);
    }


    @Override
    public void afterPropertiesSet() {
        ThreadFactory factory = Executors.defaultThreadFactory();
        this.queue = new LinkedBlockingQueue(capacity);
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        this.threadPoolExecutor = new ThreadPoolExecutor(coreSize, maxSize, aliveSeconds, TimeUnit.SECONDS, queue, factory, handler);
    }

    public int getCoreSize() {
      return coreSize;
    }

    public void setCoreSize(int coreSize) {
      this.coreSize = coreSize;
    }

    public int getMaxSize() {
      return maxSize;
    }

    public void setMaxSize(int maxSize) {
      this.maxSize = maxSize;
    }

    public int getAliveSeconds() {
      return aliveSeconds;
    }

    public void setAliveSeconds(int aliveSeconds) {
      this.aliveSeconds = aliveSeconds;
    }

    public BlockingQueue getQueue() {
      return queue;
    }

    public void setQueue(BlockingQueue queue) {
      this.queue = queue;
    }

    public int getCapacity() {
      return capacity;
    }

    public void setCapacity(int capacity) {
      this.capacity = capacity;
    }

    public ThreadPoolExecutor getThreadPoolExecutor() {
      return threadPoolExecutor;
    }

    public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
      this.threadPoolExecutor = threadPoolExecutor;
    }
    
}
