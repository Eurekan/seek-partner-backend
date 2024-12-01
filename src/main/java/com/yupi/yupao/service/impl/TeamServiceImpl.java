package com.yupi.yupao.service.impl;

import com.yupi.yupao.model.domain.Team;
import com.yupi.yupao.mapper.TeamMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.yupao.service.TeamService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 队伍 服务实现类
 * </p>
 *
 * @author Eureka
 * @since 2024-12-01
 */
@Service
public class TeamServiceImpl extends ServiceImpl<TeamMapper, Team> implements TeamService {

}
