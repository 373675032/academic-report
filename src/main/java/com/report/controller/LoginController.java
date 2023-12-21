package com.report.controller;

import com.report.constant.Leader;
import com.report.constant.Role;
import com.report.dto.ResponseResult;
import com.report.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
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
 * @ClassName: LoginController
 * 登录控制器
 * @date 2020/11/27 8:55
 * @Version: 1.0
 */
@Controller
@Slf4j
public class LoginController extends BaseController {

    /**
     * 登录
     */
    @PostMapping("/login")
    @ResponseBody
    public ResponseResult login(String no, String password, String role) {
        log.info("请求登录的用户信息 : {}", "no = " + no + ", password = " + password + ", role = " + role);
        User user = null;
        if (Role.ADMIN.equals(role)) {
            // 管理员登录
            Admin build = Admin.builder()
                    .no(no).password(password)
                    .build();
            List<Admin> admins = adminService.listAdmins(build);
            if (admins.size() > 0) {
                // 找到登录的用户并放入session
                Admin admin = admins.get(0);
                user = User.builder()
                        .id(admin.getId())
                        .no(no)
                        .name("管理员[" + admin.getId() + "]")
                        .role(Role.ADMIN)
                        .img(admin.getImg())
                        .build();
                result.setCode(200);
                result.setMessage("管理员登录成功");
            } else {
                // 判断账号是否存在
                build.setPassword(null);
                List<Admin> existAdmin = adminService.listAdmins(build);
                if (existAdmin.size() > 0) {
                    // 密码错误
                    result.setCode(100);
                    result.setMessage("密码错误");
                } else {
                    // 用户不存在
                    result.setCode(101);
                    result.setMessage("用户不存在");
                }
            }
        }
        if (Role.STUDENT.equals(role)) {
            // 学生登录
            Student build = Student.builder()
                    .no(no).password(password)
                    .build();
            List<Student> students = studentService.listStudents(build);
            if (students.size() > 0) {
                // 找到登录的用户并放入session
                Student student = students.get(0);
                user = User.builder()
                        .id(student.getId())
                        .no(no)
                        .name(student.getName())
                        .role(Role.STUDENT)
                        .img(student.getImg())
                        .build();
                result.setCode(200);
                result.setMessage("学生登录成功");
            } else {
                // 判断账号是否存在
                build.setPassword(null);
                List<Student> existStudent = studentService.listStudents(build);
                if (existStudent.size() > 0) {
                    // 密码错误
                    result.setCode(100);
                    result.setMessage("密码错误");
                } else {
                    // 用户不存在
                    result.setCode(101);
                    result.setMessage("用户不存在");
                }
            }
        }
        if (Role.TEACHER.equals(role)) {
            // 教师登录
            Teacher build = Teacher.builder()
                    .no(no).password(password)
                    .build();
            List<Teacher> teachers = teacherService.listTeachers(build);
            if (teachers.size() > 0) {
                // 找到登录的用户并放入session
                Teacher teacher = teachers.get(0);
                user = User.builder()
                        .id(teacher.getId())
                        .no(no)
                        .name(teacher.getName())
                        .role(Role.TEACHER)
                        .img(teacher.getImg())
                        .build();
                result.setCode(200);
                result.setMessage("教师登录成功");
                if (teacher.getIsCollegeLeader().intValue() == Leader.YES) {
                    // 学院领导
                    user.setIsLeader(Leader.YES);
                }
            } else {
                // 判断账号是否存在
                build.setPassword(null);
                List<Teacher> existTeacher = teacherService.listTeachers(build);
                if (existTeacher.size() > 0) {
                    // 密码错误
                    result.setCode(100);
                    result.setMessage("密码错误");
                } else {
                    // 用户不存在
                    result.setCode(101);
                    result.setMessage("用户不存在");
                }
            }
        }
        if (Role.DEPARTMENT.equals(role)) {
            // 部门登录
            Department build = Department.builder()
                    .no(no).password(password)
                    .build();
            List<Department> departments = departmentService.listDepartments(build);
            if (departments.size() > 0) {
                // 找到登录的用户并放入session
                Department department = departments.get(0);
                user = User.builder()
                        .id(department.getId())
                        .no(no)
                        .name(department.getName())
                        .role(Role.DEPARTMENT)
                        .img(department.getImg())
                        .build();
                result.setCode(200);
                result.setMessage("部门登录成功");
            } else {
                // 判断账号是否存在
                build.setPassword(null);
                List<Department> existDepartment = departmentService.listDepartments(build);
                if (existDepartment.size() > 0) {
                    // 密码错误
                    result.setCode(100);
                    result.setMessage("密码错误");
                } else {
                    // 用户不存在
                    result.setCode(101);
                    result.setMessage("用户不存在");
                }
            }
        }
        session.setAttribute("loginUser", user);
        return result;
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public String logout() {
        // 清空session
        session.invalidate();
        return "redirect:/login.html";
    }

    /**
     * 修改密码
     */
    @PostMapping("/reset")
    @ResponseBody
    public ResponseResult reset(String oldPassword, String newPassword) {
        String role = loginUser.getRole();
        if (Role.ADMIN.equals(role)) {
            // 管理员登录
            if (loginAdmin.getPassword().equals(oldPassword)) {
                // 旧密码正确
                loginAdmin.setPassword(newPassword);
                if (adminService.update(loginAdmin)) {
                    result.setCode(200);
                    result.setMessage("修改密码成功");
                }
            } else {
                result.setCode(501);
                result.setMessage("原密码错误");
            }
        }
        if (Role.STUDENT.equals(role)) {
            // 学生登录
            if (loginStudent.getPassword().equals(oldPassword)) {
                // 旧密码正确
                loginStudent.setPassword(newPassword);
                if (studentService.update(loginStudent)) {
                    result.setCode(200);
                    result.setMessage("修改密码成功");
                }
            } else {
                result.setCode(501);
                result.setMessage("原密码错误");
            }
        }
        if (Role.TEACHER.equals(role)) {
            // 教师登录
            if (loginTeacher.getPassword().equals(oldPassword)) {
                // 旧密码正确
                loginTeacher.setPassword(newPassword);
                if (teacherService.update(loginTeacher)) {
                    result.setCode(200);
                    result.setMessage("修改密码成功");
                }
            } else {
                result.setCode(501);
                result.setMessage("原密码错误");
            }
        }
        if (Role.DEPARTMENT.equals(role)) {
            // 部门登录
            if (loginDept.getPassword().equals(oldPassword)) {
                // 旧密码正确
                loginDept.setPassword(newPassword);
                if (departmentService.update(loginDept)) {
                    result.setCode(200);
                    result.setMessage("修改密码成功");
                }
            } else {
                result.setCode(501);
                result.setMessage("原密码错误");
            }
        }
        // 修改成功清空session
        if (result.getCode() == 200) {
            session.invalidate();
        }
        return result;
    }
}
