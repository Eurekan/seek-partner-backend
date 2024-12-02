package com.yupi.yupao.service;

import com.yupi.yupao.model.domain.Team;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yupao.model.domain.User;

/**
 * <p>
 * 队伍 服务类
 * </p>
 *
 * @author Eureka
 * @since 2024-12-01
 */
public interface TeamService extends IService<Team> {

    /**
     *   添加队伍
     * @param team  队伍信息
     * @param loginUser  登录用户
     * @return teamId 队伍id
     */
    long addTeam(Team team, User loginUser);

}
