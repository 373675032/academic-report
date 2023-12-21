package world.xuewei.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import world.xuewei.constant.ReportConstant;
import world.xuewei.constant.Role;
import world.xuewei.entity.*;
import world.xuewei.vo.AppointmentVo;
import world.xuewei.vo.MeetingVo;
import world.xuewei.vo.ReportVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统页面跳转控制器
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
@Controller
@Slf4j
public class SystemController extends BaseController {

    /**
     * 前往我的资料页面
     */
    @GetMapping("/profile")
    public String profile(Map<String, Object> map) {
        if (Role.ADMIN.equals(loginUser.getRole())) {
            return "redirect:/list-students";
        } else {
            map.put("loginUser", loginUser);
            map.put("loginStudent", loginStudent);
            map.put("loginTeacher", loginTeacher);
            map.put("loginDept", loginDept);
            return "background/profile";
        }
    }

    /**
     * 前往我的报告
     */
    @GetMapping("/my-report")
    public String reports(Map<String, Object> map) {
        Report build = Report.builder()
                .reporterNo(loginUser.getNo())
                .build();
        // 查询我的全部报告
        List<Report> reports = reportService.listReports(build);
        // 过滤掉回收站状态的报告
        reports = reports.stream()
                .filter(report -> report.getStatus().intValue() != ReportConstant.TRASH)
                .collect(Collectors.toList());
        //封装接口
        map.put("reports", reports);
        return "teacher/reports";
    }

    /**
     * 前往回收站
     */
    @GetMapping("/trash")
    public String trash(Map<String, Object> map) {
        Report build = Report.builder()
                .reporterNo(loginUser.getNo())
                .status(ReportConstant.TRASH)
                .build();
        // 查询我的全部回收站状态的报告
        List<Report> reports = reportService.listReports(build);
        //封装接口
        map.put("reports", reports);
        return "teacher/trash";
    }

    /**
     * 修改学术报告
     */
    @GetMapping("/edit")
    public String edit(@RequestParam("id") Integer id, Map<String, Object> map) {
        Report report = reportService.getById(id);
        if (report.getStatus() >= ReportConstant.APPOINTMENT.intValue()) {
            return "error/402";
        }
        if (ObjectUtil.isEmpty(report)) {
            // 未找到相关学术报告
            return "error/404";
        }
        if (!report.getReporterNo().equals(loginUser.getNo())) {
            // 企图修改别人的报告
            return "error/401";
        }
        map.put("report", report);
        return "teacher/edit";
    }

    /**
     * 学院院长初审
     */
    @GetMapping("/leader-audit")
    public String leaderAudit(Map<String, Object> map) {
        // 获取当前登录用户的学院信息
        College college = loginUser.getCollege();
        // 获取此学院所有等待院长审核状态的报告
        List<Report> reports = reportService.listReportsByCollegeId(college.getId(), ReportConstant.WAIT);
        //封装接口
        map.put("reportVos", dealReports(reports));
        return "teacher/leader-audit";
    }

    /**
     * 教务部门审核
     */
    @GetMapping("/audit")
    public String audit(Map<String, Object> map) {
        // 获取当前登录用户的学院信息
        College college = loginUser.getCollege();
        // 获取此学院所有等待院长审核状态的报告
        List<Report> reports = reportService.listReportsByCollegeId(college.getId(), ReportConstant.PASS_FROM_LEADER);
        //封装接口
        map.put("reportVos", dealReports(reports));
        return "department/audit";
    }

    /**
     * 查看已通过审核的报告
     */
    @GetMapping("/pass")
    public String passReport(Map<String, Object> map) {
        // 获取当前登录用户的学院信息
        College college = loginUser.getCollege();
        // 获取等待安排会议的报告
        List<Report> waitMeeting = reportService.listReportsByCollegeId(college.getId(), ReportConstant.PASS_FROM_DEPT);
        // 获取正在预约会议的报告
        List<Report> appointment = reportService.listReportsByCollegeId(college.getId(), ReportConstant.APPOINTMENT);
        // 获取已经完成会议的学术报告
        List<Report> finish = reportService.listReportsByCollegeId(college.getId(), ReportConstant.FINISHED);

        // 封装结果集
        List<Report> result = new ArrayList<>(waitMeeting);
        result.addAll(appointment);
        result.addAll(finish);

        map.put("reportVos", dealReports(result));
        map.put("waitCount", waitMeeting.size());
        map.put("appointingCount", appointment.size());
        map.put("finishCount", finish.size());
        return "department/pass";
    }

    /**
     * 查看等待排期的报告
     */
    @GetMapping("/wait-arrange")
    public String waitArrange(Map<String, Object> map) {
        // 获取当前登录用户的学院信息
        College college = loginUser.getCollege();
        // 获取等待安排会议的报告
        List<Report> waitMeeting = reportService.listReportsByCollegeId(college.getId(), ReportConstant.PASS_FROM_DEPT);

        // 封装结果集
        List<Report> result = new ArrayList<>(waitMeeting);

        map.put("reportVos", dealReports(result));
        return "department/wait-arrange";
    }

    /**
     * 查看开放预约的报告
     */
    @GetMapping("/appointing")
    public String appointing(Map<String, Object> map) {
        // 获取当前登录用户的学院信息
        College college = loginUser.getCollege();
        // 获取等待安排会议的报告
        List<Report> result = reportService.listReportsByCollegeId(college.getId(), ReportConstant.APPOINTMENT);

        map.put("MeetingVos", getMeetingVosReports(result));
        return "department/appointing";
    }

    /**
     * 查看我的会议
     */
    @GetMapping("/meetings")
    public String myMeetings(Map<String, Object> map) {
        // 获取等待安排会议的报告
        List<Report> result = reportService.listReports(
                Report.builder()
                        .reporterNo(loginUser.getNo()).status(ReportConstant.APPOINTMENT)
                        .build());

        map.put("MeetingVos", getMeetingVosReports(result));
        return "teacher/meetings";
    }

    /**
     * 查看我的历史会议
     */
    @GetMapping("/pass-meetings")
    public String passMeetings(Map<String, Object> map) {
        List<Meeting> meetings = meetingService.listMyFinishMeetings(loginUser.getId());
        map.put("MeetingVos", getMeetingVosByMeetings(meetings));
        return "teacher/pass-meetings";
    }

    /**
     * 学生查看开放预约的报告
     */
    @GetMapping("/appointing-meeting")
    public String appointingMeeting(Map<String, Object> map) {
        // 获取当前登录用户的学院信息
        College college = loginUser.getCollege();
        // 获取开放预约的学术报告
        List<Report> waitMeeting = reportService.listReportsByCollegeId(college.getId(), ReportConstant.APPOINTMENT);

        // 封装结果集
        List<Report> result = new ArrayList<>(waitMeeting);

        map.put("MeetingVos", getMeetingVosReports(result));
        return "student/appointing";
    }

    /**
     * 修改会议
     */
    @GetMapping("/edit-meeting")
    public String editMeeting(@RequestParam("id") Integer id, Map<String, Object> map) {
        Meeting meeting = meetingService.getById(id);
        map.put("meetingVo", getMeetingVo(meeting));
        return "department/edit-meeting";
    }

    /**
     * 预约结束，准备会议
     */
    @GetMapping("/prepare-meeting")
    public String finishMeeting(Map<String, Object> map) {
        // 获取当前登录用户的学院信息
        College college = loginUser.getCollege();
        // 获取超过预约时间的会议，且未达到会议时间的报告
        List<Meeting> meetings = meetingService.listAppointmentEndMeetings(college.getId());
        map.put("MeetingVos", getMeetingVosByMeetings(meetings));
        return "department/prepare-meeting";
    }

    /**
     * 会议统计，统计签到结果
     */
    @GetMapping("/meeting-statistics")
    public String meetingStatistics(Map<String, Object> map) {
        // 获取当前登录用户的学院信息
        College college = loginUser.getCollege();
        List<Meeting> meetings = meetingService.listFinishMeetings(college.getId());
        map.put("MeetingVos", getMeetingVosByMeetings(meetings));
        return "department/meeting-statistics";
    }

    /**
     * 学术报告统计
     */
    @GetMapping("/report-statistics")
    public String reportStatistics(String year, String semester, Map<String, Object> map) {
        // 处理入参
        year = StrUtil.isEmpty(year) ? "all" : year;
        semester = StrUtil.isEmpty(semester) ? "all" : semester;
        // 获取本学院开展过报告会议的学年
        List<String> years = meetingService.getAllYears(loginUser.getCollege().getId());
        List<Meeting> meetings = meetingService.searchMeetings(year, semester, loginUser.getCollege().getId());
        map.put("MeetingVos", getMeetingVosByMeetings(meetings));
        map.put("years", years);
        map.put("curYear", year);
        map.put("curSemester", semester);
        return "department/report-statistics";
    }


    /**
     * 查看预约
     */
    @GetMapping("/appointments")
    public String appointments(@RequestParam("id") Integer id, Map<String, Object> map) {
        // 根据ID获取会议
        Meeting meeting = meetingService.getById(id);
        // 定义会议状态
        String status = "wait";
        // 判断当前会议是否结束
        if (System.currentTimeMillis() - meeting.getReportTime().getTime() > 0) {
            // 会议已经结束
            status = "finish";
        }
        // 获取学术报告
        Report report = reportService.getById(meeting.getReportId());
        // 获取预约会议的全体学生
        List<Appointment> appointments = appointmentService.listAppointments(
                Appointment.builder().meetingId(id)
                        .build());
        map.put("AppointmentVos", dealAppointments(appointments, meeting));
        map.put("meeting", meeting);
        map.put("report", report);
        map.put("status", status);
        return "department/appointments";
    }

    /**
     * 查看我的预约
     */
    @GetMapping("/my-appointment")
    public String myAppointment(Map<String, Object> map) {
        // 获取我预约的会议
        List<Meeting> meetings = meetingService.listAppointingMeeting(loginUser.getId());
        map.put("MeetingVos", getMeetingVosByMeetings(meetings));
        return "student/my-appointment";
    }

    /**
     * 查看我的参会记录
     */
    @GetMapping("/my-present")
    public String myPresent(Map<String, Object> map) {
        // 获取我的参会记录
        List<Meeting> meetings = meetingService.listAppointingFinishMeeting(loginUser.getId());
        List<AppointmentVo> appointmentVos = new ArrayList<>();
        // 处理预约信息
        for (Meeting meeting : meetings) {
            // 获取我的预约信息
            Appointment build = Appointment.builder()
                    .meetingId(meeting.getId()).studentId(loginUser.getId())
                    .build();

            List<Appointment> appointments = appointmentService.listAppointments(build);
            AppointmentVo appointmentVo = AppointmentVo.builder()
                    .meetingVo(getMeetingVo(meeting)).student(loginStudent).appointment(appointments.get(0))
                    .build();
            appointmentVos.add(appointmentVo);
        }
        map.put("AppointmentVos", appointmentVos);
        return "student/my-present";
    }

    /**
     * 前往消息中心
     */
    @GetMapping("/messages")
    public String messages(Map<String, Object> map) {
        List<Message> messages = messageService.listMessages(Message.builder().toTeacherId(loginUser.getId()).build());
        map.put("messages", messages);
        return "teacher/messages";
    }

    /**
     * 处理学术报告的集合
     */
    private List<ReportVo> dealReports(List<Report> reports) {
        List<ReportVo> reportVos = new ArrayList<>();
        for (Report report : reports) {
            // 获取报告作者
            Teacher teacher = teacherService.getByNo(report.getReporterNo());
            ReportVo build = ReportVo.builder().report(report).teacher(teacher).build();
            reportVos.add(build);
        }
        return reportVos;
    }

    /**
     * 处理会议的集合
     */
    private List<MeetingVo> getMeetingVosReports(List<Report> reports) {
        List<MeetingVo> meetingVos = new ArrayList<>();
        for (Report report : reports) {
            Meeting meeting = meetingService.getByReportId(report.getId());
            // 获取报告人和主持人
            Teacher reporter = teacherService.getById(meeting.getReporterId());
            Teacher host = teacherService.getById(meeting.getPresenterId());
            // 获取预约人数
            int countAppointments = appointmentService.countAppointments(
                    Appointment.builder()
                            .meetingId(meeting.getId())
                            .build());
            // 获取签到数
            int arriveCounts = appointmentService.countAppointments(Appointment.builder().meetingId(meeting.getId()).present(1).build());

            int appointmentPercent = countAppointments * 100 / meeting.getCapacity();

            MeetingVo build = MeetingVo.builder()
                    .meeting(meeting).reporter(reporter).host(host).report(report)
                    .appointmentCount(countAppointments).arriveCount(arriveCounts).appointmentPercent(appointmentPercent)
                    .build();
            // 设置会议状态
            Appointment appointment = Appointment.builder()
                    .studentId(loginUser.getId()).meetingId(meeting.getId())
                    .build();
            if (appointmentService.listAppointments(appointment).size() > 0) {
                // 已预约
                build.setStatus(1);
            } else {
                if (countAppointments == meeting.getCapacity()) {
                    // 预约已满
                    build.setStatus(2);
                } else {
                    // 未预约
                    build.setStatus(0);
                }
            }
            meetingVos.add(build);
        }
        return meetingVos;
    }

    /**
     * 处理会议的集合
     */
    private List<MeetingVo> getMeetingVosByMeetings(List<Meeting> meetings) {
        List<MeetingVo> meetingVos = new ArrayList<>();
        for (Meeting meeting : meetings) {
            Report report = reportService.getById(meeting.getReportId());
            // 获取报告人和主持人
            Teacher reporter = teacherService.getById(meeting.getReporterId());
            Teacher host = teacherService.getById(meeting.getPresenterId());
            // 获取预约人数
            int countAppointments = appointmentService.countAppointments(
                    Appointment.builder()
                            .meetingId(meeting.getId())
                            .build());
            // 获取签到数
            int arriveCounts = appointmentService.countAppointments(Appointment.builder().meetingId(meeting.getId()).present(1).build());

            MeetingVo build = MeetingVo.builder()
                    .meeting(meeting).reporter(reporter).host(host).report(report).appointmentCount(countAppointments).arriveCount(arriveCounts)
                    .build();
            meetingVos.add(build);
        }
        return meetingVos;
    }

    /**
     * 处理会议
     */
    private MeetingVo getMeetingVo(Meeting meeting) {
        Report report = reportService.getById(meeting.getReportId());
        // 获取报告人和主持人
        Teacher reporter = teacherService.getById(meeting.getReporterId());
        Teacher host = teacherService.getById(meeting.getPresenterId());
        // 获取预约人数
        int countAppointments = appointmentService.countAppointments(
                Appointment.builder()
                        .meetingId(meeting.getId())
                        .build());
        // 获取签到数
        int arriveCounts = appointmentService.countAppointments(Appointment.builder().meetingId(meeting.getId()).present(1).build());

        MeetingVo build = MeetingVo.builder()
                .meeting(meeting).reporter(reporter).host(host).report(report).appointmentCount(countAppointments).arriveCount(arriveCounts)
                .build();
        return build;
    }

    /**
     * 处理预约信息
     */
    private List<AppointmentVo> dealAppointments(List<Appointment> appointments, Meeting meeting) {
        List<AppointmentVo> appointmentVos = new ArrayList<>();
        for (Appointment appointment : appointments) {
            // 获取预约会议的学生
            Student student = studentService.getById(appointment.getStudentId());
            Integer collegeId = student.getCollegeId();
            // 获取学生的院系
            College college = collegeService.getById(collegeId);
            student.setCollege(college);
            // 创建预约对象
            AppointmentVo appointmentVo = AppointmentVo.builder()
                    .appointment(appointment).student(student).meetingVo(getMeetingVo(meeting))
                    .build();
            appointmentVos.add(appointmentVo);
        }
        return appointmentVos;
    }

}
