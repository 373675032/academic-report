package com.report.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.report.constant.Leader;
import com.report.entity.College;
import com.report.entity.Department;
import com.report.entity.Teacher;
import com.report.mapper.DepartmentMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部门服务
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
public class DepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

    @Resource
    private CollegeService collegeService;

    @Resource
    private TeacherService teacherService;


    public boolean insert(Department department) {
        return departmentMapper.insert(department) == 1;
    }


    public boolean deleteById(Integer id) {
        return departmentMapper.deleteById(id) == 1;
    }


    public Department getById(Integer id) {
        return departmentMapper.getById(id);
    }


    public Department getByNo(String no) {
        return departmentMapper.getByNo(no);
    }


    public List<Department> listDepartments() {
        return departmentMapper.listDepartments();
    }


    public List<Department> listDepartments(Department department) {
        return departmentMapper.listDepartments(department);
    }


    public int countDepartments(Department department) {
        return departmentMapper.countDepartments(department);
    }


    public boolean update(Department department) {
        return departmentMapper.update(department) == 1;
    }

    /**
     * 分页查询部门
     */
    public Map<String, Object> pageAllDepartments(Integer page, Integer rows, String searchField, String searchString) {
        Map<String, Object> map = new HashMap<>();
        List<Department> departments = new ArrayList<>();
        if (StrUtil.isNotEmpty(searchString)) {
            // 搜索
            Department search = null;
            if ("no".equals(searchField)) {
                search = Department.builder().no(searchString).build();
            }
            if ("name".equals(searchField)) {
                search = Department.builder().name(searchString).build();
            }
            if ("college.name".equals(searchField)) {
                College college = College.builder().name(searchString).build();
                List<College> colleges = collegeService.listColleges(college);
                if (colleges.size() > 0) {
                    search = Department.builder().collegeId(colleges.get(0).getId()).build();
                } else {
                    search = Department.builder().collegeId(-1).build();
                }
            }
            departments = listDepartments(search);
        } else {
            // 分页查询
            PageHelper.startPage(page, rows);
            departments = listDepartments();
        }
        PageInfo<Department> pageInfo = new PageInfo<>(departments);
        // 将查询结果放入map
        map.put("rows", dealDepartment(departments));
        // 总页数
        map.put("total", pageInfo.getPages());
        // 总条数
        map.put("records", pageInfo.getTotal());
        return map;
    }

    /**
     * 处理完善部门对象
     */
    private List<Department> dealDepartment(List<Department> list) {
        list.forEach(department -> {
            // 设置学院
            Integer collegeId = department.getCollegeId();
            College college = collegeService.getById(collegeId);
            // 设置部长
            Integer leaderId = department.getLeaderId();
            Teacher teacher = teacherService.getById(leaderId);
            department.setCollege(college);
            department.setLeader(teacher);
        });
        return list;
    }

    /**
     * 编辑部门
     */
    public void editDepartment(Department department, String action) {
        if ("edit".equals(action)) {
            // 编辑
            update(department);
            // 获取原来部门的信息
            Department target = getById(department.getId());
            // 获取编辑部门的领导
            Teacher teacher = teacherService.getByNo(department.getLeader().getNo());
            if (ObjectUtil.isNotEmpty(teacher)) {
                if (target.getLeaderId().intValue() != teacher.getId()) {
                    // 更换领导
                    Integer oldLeaderId = target.getLeaderId();
                    Teacher oldLeader = Teacher.builder().id(oldLeaderId).isDepartmentLeader(Leader.NO).build();
                    teacherService.update(oldLeader);
                    // 设置新领导
                    teacher.setIsDepartmentLeader(Leader.YES);
                    teacherService.update(teacher);
                    Department build = Department.builder().id(department.getId()).leaderId(teacher.getId()).build();
                    update(build);
                    log.info("部门 {} 设置新领导 -> {}", target.getNo(), teacher.getNo());
                }
            }
        }
        if ("del".equals(action)) {
            // 删除
            deleteById(department.getId());
        }
    }
}