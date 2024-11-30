package com.yupi.yupao.service;

import com.yupi.yupao.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@SpringBootTest
public class InsertUserTest {

    @Resource
    private UserService userService;

    // 线程池设置
    private final ExecutorService executorService = new ThreadPoolExecutor(16, 1000, 10000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(10000));

    // for循环插入（15s）
    @Test
    public void doInsertUser() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 1000;
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < INSERT_NUM; i++) {
            User user = new User();
            user.setUsername("假Eureka");
            user.setUserAccount("Eureka");
            user.setAvatarUrl("https://img1.baidu.com/it/u=467212011,1034521901&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=500");
            user.setProfile("你好");
            user.setGender(0);
            user.setUserPassword("12345678");
            user.setPhone("18947150182");
            user.setEmail("xxx@qq.com");
            user.setUserStatus(0);
            user.setUserRole(0);
            user.setPlanetCode("A567");
            user.setTags("[]");
            userList.add(user);
        }
        userService.saveBatch(userList,100);
        stopWatch.stop();
        System.out.println(stopWatch.getLastTaskTimeMillis());

    }

    // 并发插入（26s）
    @Test
    public void doConcurrencyInsertUser() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final int INSERT_NUM = 100000;
        // 分十组
        int j = 0;
        // 批量插入数据的大小
        int batchSize = 5000;
        // 创建一个列表，用于存储CompletableFuture<Void>类型的对象
        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        // i 要根据数据量和插入批量来计算需要循环的次数
        for (int i = 0; i < INSERT_NUM/batchSize; i++) {
            List<User> userList = new ArrayList<>();
            while (true){
                j++;
                User user = new User();
                user.setUsername("假Eureka");
                user.setUserAccount("Eureka");
                user.setAvatarUrl("https://img1.baidu.com/it/u=3738863420,1873922533&fm=253&fmt=auto&app=138&f=JPEG?w=1048&h=800");
                user.setProfile("你好");
                user.setGender(0);
                user.setUserPassword("12345678");
                user.setPhone("18947150182");
                user.setEmail("xxx@qq.com");
                user.setUserStatus(0);
                user.setUserRole(0);
                user.setPlanetCode("A567");
                user.setTags("[]");
                userList.add(user);
                if (j % batchSize == 0 ){
                    break;
                }
            }
            // 异步执行
            CompletableFuture<Void> future = CompletableFuture.runAsync(() ->{
                System.out.println("ThreadName：" + Thread.currentThread().getName());
                userService.saveBatch(userList,batchSize);
            },executorService);
            // 添加到任务列表
            futureList.add(future);
        }
        // 等待所有任务执行完成
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();

        stopWatch.stop();
        System.out.println( stopWatch.getLastTaskTimeMillis());

    }
}

