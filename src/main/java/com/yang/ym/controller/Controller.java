package com.yang.ym.controller;

import com.yang.ym.dao.StudentDao;
import com.yang.ym.entity.Student;
import com.yang.ym.service.StudentService;
import com.yang.ym.service.TestRedis;
import com.yang.ym.service.TestService;
import com.yang.ym.zk.ZkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.FileNotFoundException;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qcy
 * @create 2021/08/07 12:31:05
 */
@RestController
public class Controller {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/test")
    public String test() {
        String result = restTemplate.getForObject("http://demo/test", String.class);
        System.out.println(result);
        return result;
    }

    @RequestMapping("/handle")
    public String handle() {

        //.....

        ExecutorService pool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            pool.execute(() -> {
                //业务处理
            });
        }

        //.....

        return "success";
    }

    @RequestMapping("/handle1")
    public String handle1() {

        //.....

        ExecutorService pool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 2; i++) {
            pool.execute(() -> {
                try {
                    //模拟业务耗时
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        //.....

        return "success";
    }

    @RequestMapping("/execute")
    public String execute() {

        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                10,
                10,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                new MyThreadFactory());

        pool.allowCoreThreadTimeOut(true);

        for (int i = 0; i < 20; i++) {
            pool.execute(() -> {
                try {
                    //模拟业务耗时
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        return "success";
    }

    static class MyThreadFactory implements ThreadFactory {
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            String name = "MyThreadFactory-" + threadNumber.getAndIncrement();
            return new Thread(r, name);
        }
    }


//    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        ExecutorService pool = Executors.newFixedThreadPool(10);
//        Future<Integer> future = pool.submit(new Task());
//        Integer result = future.get();
//        System.out.println(result);
//        pool.shutdown();
//    }
//
//    static class Task implements Callable<Integer> {
//
//        @Override
//        public Integer call() throws Exception {
//            Thread.sleep(2000);
//            return 1;
//        }
//    }
//
//    public static void main(String[] args) {
//        ThreadLocal<String> threadLocal = new ThreadLocal<>();
//
//        Thread t1 = new Thread(() -> {
//            threadLocal.set("t1");
//            System.out.println("Thread 1:" + threadLocal.get());
//        });
//
//        Thread t2 = new Thread(() -> {
//            threadLocal.set("t2");
//            System.out.println("Thread 2:" + threadLocal.get());
//        });
//
//        t1.start();
//        t2.start();
//
//        threadLocal.set("main");
//        System.out.println("Thread main:" + threadLocal.get());
//    }

//    @Autowired
//    LRUService lruService;
//
//    @RequestMapping("put")
//    private String put(@RequestParam Integer key, @RequestParam Integer value) {
//        lruService.put(key, value);
//        return "ok";
//    }
//
//    @RequestMapping("get")
//    private Integer get(@RequestParam Integer key) {
//        return lruService.get(key);
//    }
//
//    @RequestMapping("print")
//    private String print() {
//        return lruService.print();
//    }
//
//    public static void main(String[] args) {
//        ServiceLoader<Log> logServiceLoader = ServiceLoader.load(Log.class);
//        for (Log log : logServiceLoader) {
//            log.print();
//        }
//    }


    @GetMapping("/t")
    public String t() {
        System.out.println("进入Controller");
        throw new RuntimeException("123");
    }


    @Resource
    StudentService studentService;
    @Resource
    StudentDao studentDao;
    @Resource
    TestService testService;

//    @Transactional
//    private void save() {
//        Student student = Student.builder()
//                .name("a")
//                .age(18)
//                .build();
//
//        studentDao.insert(student);
//        throw new NullPointerException();
//    }

    @GetMapping("/save")
    public String saveStudent() throws FileNotFoundException {
        testService.saveA();
        return "success";
    }

    @Autowired
    TestRedis testRedis;

    @GetMapping("/testRedis")
    public String testRedis() throws InterruptedException {
//        testRedis.lock();
        return "success";
    }

    @Resource
    ZkService zkService;

    @GetMapping("/testCurator")
    public String testCurator() throws Exception {
        zkService.testCurator();
        return "success";
    }
}
