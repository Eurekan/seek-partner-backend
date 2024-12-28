package com.yupi.yupao.service;

import com.yupi.yupao.constant.RedisConstant;
import com.yupi.yupao.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class RedisGEOTest {

    @Resource
    private UserService userService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private static List<User> getUserList() {
        User userA = new User();
        userA.setId(18947150182L);
        userA.setUsername("A");
        userA.setLongitude(116); // 北京的经度
        userA.setLatitude(39);   // 北京的纬度

        User userB = new User();
        userB.setId(19274716411L);
        userB.setUsername("B");
        userB.setLongitude(121); // 上海的经度
        userB.setLatitude(31);   // 上海的纬度

        User userC = new User();
        userC.setId(15848123453L);
        userC.setUsername("C");
        userC.setLongitude(104); // 四川的经度
        userC.setLatitude(30);   // 四川的纬度

        List<User> userList = new ArrayList<>();
        userList.add(userA);
        userList.add(userB);
        userList.add(userC);
        return userList;
    }

    @Test
    public void importUserGEOByRedis() {
        List<User> userList = getUserList();

        //List<User> userList = userService.list();
        String key = RedisConstant.USER_GEO_KEY; // Redis的key

        List<RedisGeoCommands.GeoLocation<String>> locationList = new ArrayList<>(userList.size()); // 初始化地址（经纬度）List
        for (User user : userList) {
            locationList.add(new RedisGeoCommands.GeoLocation<>(String.valueOf(user.getId()),
                    new Point(user.getLongitude(), user.getLatitude()))); // 往locationList添加每个用户的经纬度数据
        }
        stringRedisTemplate.opsForGeo().add(key, locationList); // 将每个用户的经纬度信息写入Redis中
    }

    @Test
    public void getUserGeo() {
        // 创建两个用户实例，并设置合法的经纬度值
        List<User> userList = getUserList();
        String key = RedisConstant.USER_GEO_KEY;
        // 计算每个用户与登录用户的距离
        for (User user : userList) {
            Distance distance = stringRedisTemplate
                    .opsForGeo()
                    .distance(key, "15848123453", String.valueOf(user.getId()), RedisGeoCommands.DistanceUnit.KILOMETERS);

            if (distance != null) {
                System.out.println("User: " + user.getId() + ", Distance: " +
                        distance.getValue() + " " + distance.getUnit());
            } else {
                System.out.println("User: " + user.getId() + ", Distance: Unable to calculate");
            }
        }
    }


    @Test
    public void searchUserByGeo() {
//        User loginUser = userService.getById(1);
        List<User> userList = getUserList();
        User userA = userList.get(0);
        User userB = userList.get(1);
        User userC = userList.get(2);
        Distance geoRadius = new Distance(1500, RedisGeoCommands.DistanceUnit.KILOMETERS);
        Circle circle = new Circle(new Point(userC.getLongitude(), userC.getLatitude()), geoRadius);
        RedisGeoCommands.GeoRadiusCommandArgs geoRadiusCommandArgs = RedisGeoCommands.GeoRadiusCommandArgs
                .newGeoRadiusArgs().includeCoordinates();
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = stringRedisTemplate.opsForGeo()
                .radius(RedisConstant.USER_GEO_KEY, circle, geoRadiusCommandArgs);
        if (results != null) {
            for (GeoResult<RedisGeoCommands.GeoLocation<String>> result : results) {
                if (!result.getContent().getName().equals("15848123453")) {
                    System.out.println(result.getContent().getName()); // // 打印1500km内的用户id
                }
            }
        }
    }

}
