package com.report.service;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.report.constant.Leader;
import com.report.entity.College;
import com.report.entity.Teacher;
import com.report.mapper.CollegeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学院服务
 *
 * <p>
 * ==========================================================================
 * 郑重说明：本项目免费开源！原创作者为：薛伟同学，严禁私自出售。
 * ==========================================================================
 * B站账号：薛伟同学
 * 微信公众号：薛伟同学
 * 作者博客：http://xuewei.world
 * ==========================================================================
 * 陆陆续续总会收到粉丝的提醒，总会有些人为了赚取利益倒卖我的开源项目。
 * 不乏有粉丝朋友出现钱付过去，那边只把代码发给他就跑路的，最后还是根据线索找到我。。
 * 希望各位朋友擦亮慧眼，谨防上当受骗！
 * ==========================================================================
 *
 * @author <a href="http://xuewei.world/about">XUEW</a>
 */
@Slf4j
@Service
public class CollegeService {

    @Resource
    private CollegeMapper collegeMapper;

    @Resource
    private TeacherService teacherService;

    public boolean insert(College college) {
        return collegeMapper.insert(college) == 1;
    }

    public boolean deleteById(Integer id) {
        return collegeMapper.deleteById(id) == 1;
    }

    public College getById(Integer id) {
        return collegeMapper.getById(id);
    }

    public List<College> listColleges() {
        return collegeMapper.listColleges();
    }

    public List<College> listColleges(College college) {
        return collegeMapper.listColleges(college);
    }

    public int countColleges(College college) {
        return collegeMapper.countColleges(college);
    }

    public boolean update(College college) {
        return collegeMapper.update(college) == 1;
    }

    /**
     * 分页查询所有学院
     */
    public Map<String, Object> pageAllColleges(Integer page, Integer rows) {
        Map<String, Object> map = new HashMap<>();
        // 分页查询
        PageHelper.startPage(page, rows);
        List<College> colleges = listColleges();
        PageInfo<College> pageInfo = new PageInfo<>(colleges);
        // 将查询结果放入map
        map.put("rows", dealCollege(colleges));
        // 总页数
        map.put("total", pageInfo.getPages());
        // 总条数
        map.put("records", pageInfo.getTotal());
        return map;
    }


    /**
     * 处理完善学院对象
     */
    private List<College> dealCollege(List<College> list) {
        list.forEach(college -> {
            // 设置院长
            Integer leaderId = college.getLeaderId();
            Teacher teacher = teacherService.getById(leaderId);
            college.setLeader(teacher);
        });
        return list;
    }

    /**
     * 编辑、删除学院
     */
    public void editCollege(College college, String action) {
        if ("edit".equals(action)) {
            // 编辑
            update(college);
            // 获取原来学院的信息
            College target = getById(college.getId());
            // 获取编辑学院的领导
            Teacher teacher = teacherService.getByNo(college.getLeader().getNo());
            if (ObjectUtil.isNotEmpty(teacher)) {
                if (target.getLeaderId().intValue() != teacher.getId()) {
                    // 更换领导
                    Integer oldLeaderId = target.getLeaderId();
                    Teacher oldLeader = Teacher.builder().id(oldLeaderId).isCollegeLeader(Leader.NO).build();
                    teacherService.update(oldLeader);
                    // 设置新领导
                    teacher.setIsCollegeLeader(Leader.YES);
                    teacherService.update(teacher);
                    College build = College.builder().id(college.getId()).leaderId(teacher.getId()).build();
                    update(build);
                    log.info("学院 {} 设置新领导 -> {}", target.getId(), teacher.getNo());
                }
            }
        }
        if ("del".equals(action)) {
            // 删除
            deleteById(college.getId());
        }
    }
}