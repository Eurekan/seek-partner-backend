package com.yupi.yupao.service;

import com.yupi.yupao.model.domain.Team;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yupao.model.domain.User;
import com.yupi.yupao.model.dto.TeamQuery;
import com.yupi.yupao.model.vo.TeamUserVO;

import java.util.List;

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

    List<TeamUserVO> listTeams(TeamQuery teamQuery, boolean isAdmin);

}
