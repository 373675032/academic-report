package com.report.controller;

import cn.hutool.core.util.ObjectUtil;
import com.report.client.OssClient;
import com.report.constant.ReportConstant;
import com.report.constant.Role;
import com.report.dto.ResponseResult;
import com.report.entity.*;
import com.report.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
 * @ClassName: BaseController
 * 控制器的基类，所有控制器必须继承此类
 * @date 2020/11/27 8:55
 * @Version: 1.0
 */
public class BaseController {

    @Autowired
    protected AdminService adminService;
    @Autowired
    protected AppointmentService appointmentService;
    @Autowired
    protected CollegeService collegeService;
    @Autowired
    protected DepartmentService departmentService;
    @Autowired
    protected MeetingService meetingService;
    @Autowired
    protected ReportService reportService;
    @Autowired
    protected TeacherService teacherService;
    @Autowired
    protected StudentService studentService;
    @Autowired
    protected MessageService messageService;
    @Autowired
    protected OssClient ossClient;

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    /**
     * 登录的用户
     */
    protected User loginUser;
    protected Admin loginAdmin;
    protected Student loginStudent;
    protected Teacher loginTeacher;
    protected Department loginDept;

    protected ResponseResult result;

    /**
     * 在每个子类方法调用之前先调用，设置request,response,session这三个对象
     */
    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession(true);
        result = new ResponseResult();
        // 赋值当前登录的对象
        loginUser = (User) session.getAttribute("loginUser");
        if (ObjectUtil.isNotEmpty(loginUser)) {
            if (Role.ADMIN.equals(loginUser.getRole())) {
                loginAdmin = adminService.getById(loginUser.getId());
            }
            if (Role.TEACHER.equals(loginUser.getRole())) {
                loginTeacher = teacherService.getById(loginUser.getId());
                Integer collegeId = loginTeacher.getCollegeId();
                College college = collegeService.getById(collegeId);
                loginTeacher.setCollege(college);
                loginTeacher.setCollegeName(college.getName());
                loginUser.setCollege(college);
                if (loginTeacher.getIsCollegeLeader() == 1) {
                    // 等待学院院长审核的会议
                    List<Report> reports = reportService.listReportsByCollegeId(college.getId(), ReportConstant.WAIT);
                    session.setAttribute("waitFirstAudit", reports.size());
                }
                // 获取我的会议安排
                List<Report> result = reportService.listReports(
                        Report.builder()
                                .reporterNo(loginUser.getNo()).status(ReportConstant.APPOINTMENT)
                                .build());
                session.setAttribute("arrangeCount", result.size());
                // 获取最新的消息条数
                List<Message> messages = messageService.listMessages(Message.builder().toTeacherId(loginUser.getId()).build());
                session.setAttribute("messagesCount", messages.size());
            }
            if (Role.STUDENT.equals(loginUser.getRole())) {
                loginStudent = studentService.getById(loginUser.getId());
                Integer collegeId = loginStudent.getCollegeId();
                College college = collegeService.getById(collegeId);
                loginStudent.setCollege(college);
                loginStudent.setCollegeName(college.getName());
                loginUser.setCollege(college);
            }
            if (Role.DEPARTMENT.equals(loginUser.getRole())) {
                loginDept = departmentService.getById(loginUser.getId());
                Integer collegeId = loginDept.getCollegeId();
                College college = collegeService.getById(collegeId);
                loginDept.setCollege(college);
                Integer leaderId = loginDept.getLeaderId();
                Teacher teacher = teacherService.getById(leaderId);
                loginDept.setLeader(teacher);
                loginUser.setCollege(college);
                if (loginUser.getName().contains("教务")) {
                    // 等待教务部门审核的会议
                    List<Report> reports = reportService.listReportsByCollegeId(college.getId(), ReportConstant.PASS_FROM_LEADER);
                    session.setAttribute("waitFinalAudit", reports.size());
                }
                if (loginUser.getName().contains("宣传")) {
                    // 等待安排会议
                    List<Report> reports = reportService.listReportsByCollegeId(college.getId(), ReportConstant.PASS_FROM_DEPT);
                    session.setAttribute("waitArrange", reports.size());
                    // 等待准备的会议
                    List<Meeting> meetings = meetingService.listAppointmentEndMeetings(college.getId());
                    session.setAttribute("waitPrepare", meetings.size());
                }
            }
        }
    }
}
