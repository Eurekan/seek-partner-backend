package com.yupi.yupao.service;

import com.yupi.yupao.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户服务
 *
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @param planetCode    星球编号
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request 请求
     * @return 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     *
     * @param originUser 原始用户信息
     * @return User
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销
     *
     * @param request 请求
     * @return 结果
     */
    int userLogout(HttpServletRequest request);

    /**
     * 根据标签搜索用户
     * @param tagNameList 标签名
     * @return List<User>
     */
    List<User> searchUserByTags(List<String> tagNameList);

    /**
     * 获取当前登录用户
     * @param request 请求
     * @return User
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 更新用户信息
     * @param user 修改的用户信息
     * @param loginUser 当前登录用户
     * @return 结果
     */
    int updateUser(User user, User loginUser);

    /**
     * 是否为管理员
     * @param loginUser 当前登录用户
     * @return 结果
     */
    boolean isAdmin(User loginUser);

    /**
     * 是否为管理员
     *
     * @param request http 请求对象
     * @return 结果
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 匹配用户
     * @param num num
     * @param loginUser loginUser
     * @return userList
     */
    List<User> matchUser(long num, User loginUser);
}
