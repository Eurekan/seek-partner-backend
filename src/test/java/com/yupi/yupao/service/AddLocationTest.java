package com.yupi.yupao.service;

import com.yupi.yupao.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class AddLocationTest {

    @Resource
    private UserService userService;

    @Test
    public void testAdd() {
        List<User> userList = userService.list();
        userList.forEach(user -> {
            user.setLongitude(116.);
            user.setLatitude(39.);
            userService.updateById(user);
        });
        System.out.println("添加位置完成");
    }
}
