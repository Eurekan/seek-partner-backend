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
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void importUserGEOByRedis() {
        // 获取用户列表
        List<User> userList = getUserList();
        // Redis中存储用户地理位置信息的key
        String key = RedisConstant.USER_GEO_KEY;

        // 初始化与用户列表大小相同的地址（经纬度）List
        List<RedisGeoCommands.GeoLocation<String>> locationList = new ArrayList<>(userList.size());
        // 遍历用户列表，为每个用户创建地理位置信息，并添加到locationList中
        for (User user : userList) {
            // 创建并添加每个用户的地理位置信息（经度和纬度）
            locationList.add(new RedisGeoCommands.GeoLocation<>(String.valueOf(user.getId()),
                    new Point(user.getLongitude(), user.getLatitude())));
        }
        // 将所有用户的地理位置信息添加到Redis中指定的key下
        stringRedisTemplate.opsForGeo().add(key, locationList);
    }

    /**
     * 获取用户地理位置信息测试方法
     * 本方法主要用于测试用户地理位置信息的获取，通过Redis存储的地理位置数据来计算用户之间的距离
     */
    @Test
    public void getUserGeo() {
        // 获取用户列表
        List<User> userList = getUserList();
        // 设置Redis中存储用户地理位置信息的键
        String key = RedisConstant.USER_GEO_KEY;
        // 遍历用户列表
        for (User user : userList) {
            // 计算指定用户与列表中每个用户之间的距离
            Distance distance = stringRedisTemplate
                    .opsForGeo()
                    .distance(key, "15848123453", String.valueOf(user.getId()), RedisGeoCommands.DistanceUnit.KILOMETERS);

            // 根据是否能计算出距离，输出不同的信息
            if (distance != null) {
                // 如果能计算出距离，输出用户ID、距离值和单位
                System.out.println("User: " + user.getId() + ", Distance: " +
                        distance.getValue() + " " + distance.getUnit());
            } else {
                // 如果无法计算距离，输出用户ID和无法计算的提示信息
                System.out.println("User: " + user.getId() + ", Distance: Unable to calculate");
            }
        }
    }

    /**
     * 根据地理位置信息搜索用户
     * 此方法演示了如何使用Redis的地理空间功能来查找特定位置附近的用户
     */
    @Test
    public void searchUserByGeo() {
        // 获取用户列表
        List<User> userList = getUserList();
        // 提取用户C作为示例用户
        User userC = userList.get(2);

        // 创建一个最大距离为1500公里的地理半径对象
        Distance geoRadius = new Distance(1500, RedisGeoCommands.DistanceUnit.KILOMETERS);

        // 以用户C的经纬度为中心，创建一个圆形搜索区域
        Circle circle = new Circle(new Point(userC.getLongitude(), userC.getLatitude()), geoRadius);

        // 设置地理半径命令参数，包括获取坐标信息
        RedisGeoCommands.GeoRadiusCommandArgs geoRadiusCommandArgs = RedisGeoCommands.GeoRadiusCommandArgs
                .newGeoRadiusArgs().includeCoordinates();

        // 在Redis中执行地理半径搜索，查找指定区域内的用户
        GeoResults<RedisGeoCommands.GeoLocation<String>> results = stringRedisTemplate.opsForGeo()
                .radius(RedisConstant.USER_GEO_KEY, circle, geoRadiusCommandArgs);

        // 如果搜索结果不为空，则遍历结果
        if (results != null) {
            for (GeoResult<RedisGeoCommands.GeoLocation<String>> result : results) {
                // 忽略特定用户ID为"15848123453"的结果
                if (!result.getContent().getName().equals("15848123453")) {
                    // 打印1500km内的用户id
                    System.out.println(result.getContent().getName());
                }
            }
        }
    }

    private static List<User> getUserList() {
        User userA = new User();
        userA.setId(18947150182L);
        userA.setUsername("A");
        userA.setLongitude(116.); // 北京的经度
        userA.setLatitude(39.);   // 北京的纬度

        User userB = new User();
        userB.setId(19274716411L);
        userB.setUsername("B");
        userB.setLongitude(121.); // 上海的经度
        userB.setLatitude(31.);   // 上海的纬度

        User userC = new User();
        userC.setId(15848123453L);
        userC.setUsername("C");
        userC.setLongitude(104.); // 四川的经度
        userC.setLatitude(30.);   // 四川的纬度

        List<User> userList = new ArrayList<>();
        userList.add(userA);
        userList.add(userB);
        userList.add(userC);
        return userList;
    }
}
