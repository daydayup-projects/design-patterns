package tech.alexchen.daydayup.designpattern.behavioural.chain.filter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author alexchen
 * @date 2023/3/5
 */
public class Client {

    public static void main(String[] args) {
        concurrencyTest();
    }

    public static void test() {
        FilterChain filterChain = FilterChainFactory.createFilterChain();
        Request request = new Request();
        Response response = new Response();
        filterChain.doFilter(request, response);
    }


    public static void concurrencyTest() {
        int threadCount = 10; // 线程池中的线程数量
        CountDownLatch startLatch = new CountDownLatch(1); // 用于控制线程同时开始执行的门闩
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                try {
                    startLatch.await(); // 等待门闩打开
                    test();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        // 让所有线程同时开始执行任务
        startLatch.countDown();
        // 等待所有线程执行完成
        executorService.shutdown();
    }
}
