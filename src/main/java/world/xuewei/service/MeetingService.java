package world.xuewei.service;

import cn.hutool.core.util.ObjectUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import world.xuewei.constant.ReportConstant;
import world.xuewei.dao.MeetingMapper;
import world.xuewei.dto.ResponseResult;
import world.xuewei.entity.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 会议服务
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
@Service
public class MeetingService {

    @Resource
    private MeetingMapper meetingMapper;

    @Resource
    private TeacherService teacherService;

    @Resource
    private ReportService reportService;

    @Resource
    private MessageService messageService;

    @Resource
    @Lazy
    private AppointmentService appointmentService;


    public boolean insert(Meeting meeting) {
        return meetingMapper.insert(meeting) == 1;
    }


    public boolean deleteById(Integer id) {
        return meetingMapper.deleteById(id) == 1;
    }


    public Meeting getById(Integer id) {
        return meetingMapper.getById(id);
    }


    public Meeting getByReportId(Integer id) {
        return meetingMapper.getByReportId(id);
    }


    public List<Meeting> listMeetings() {
        return meetingMapper.listMeetings();
    }


    public List<Meeting> listAppointmentEndMeetings(Integer collegeId) {
        return meetingMapper.listAppointmentEndMeetings(collegeId);
    }


    public List<Meeting> listFinishMeetings(Integer collegeId) {
        return meetingMapper.listFinishMeetings(collegeId);
    }


    public List<Meeting> listMyFinishMeetings(Integer id) {
        return meetingMapper.listMyFinishMeetings(id);
    }


    public List<Meeting> searchMeetings(String year, String semester, Integer collegeId) {
        return meetingMapper.searchMeetings(year, semester, collegeId);
    }


    public List<Meeting> listMeetings(Meeting meeting) {
        return meetingMapper.listMeetings(meeting);
    }


    public int countMeetings(Meeting meeting) {
        return meetingMapper.countMeetings(meeting);
    }


    public boolean update(Meeting meeting) {
        return meetingMapper.update(meeting) == 1;
    }


    public List<Meeting> listAppointingMeeting(Integer studentId) {
        return meetingMapper.listAppointingMeeting(studentId);
    }


    public List<Meeting> listAppointingFinishMeeting(Integer studentId) {
        return meetingMapper.listAppointingFinishMeeting(studentId);
    }


    public List<String> getAllYears(Integer collegeId) {
        return meetingMapper.getAllYears(collegeId);
    }

    /**
     * 安排会议
     */
    public ResponseResult arrangeMeeting(Integer id, String hostNo, String reportTime, String reportAddress, String appointEnd, Integer capacity) {
        ResponseResult result = new ResponseResult();
        // 获取主持人
        Teacher host = teacherService.getByNo(hostNo);
        if (ObjectUtil.isEmpty(host)) {
            result.setCode(404);
            result.setMessage("未找到编号为" + hostNo + "的教师职工");
            return result;
        }
        // 检查日期是否合法
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date reportDateTime = null;
        Date appointEndTime = null;
        try {
            reportDateTime = format.parse(reportTime);
        } catch (ParseException e) {
            result.setCode(500);
            result.setMessage("报告时间格式错误！格式为：2020-01-06 12:20");
            return result;
        }
        try {
            appointEndTime = format.parse(appointEnd);
        } catch (ParseException e) {
            result.setCode(500);
            result.setMessage("截止时间格式错误！格式为：2020-01-06 12:20");
            return result;
        }
        // 检查容纳人数
        if (capacity <= 0) {
            result.setCode(500);
            result.setMessage("会议容纳人数必须大于0");
            return result;
        }
        // 获取报告
        Report report = reportService.getById(id);
        // 获取报告人
        Teacher teacher = teacherService.getByNo(report.getReporterNo());
        // 创建会议对象
        Meeting meeting = Meeting.builder()
                .reportId(id).reporterId(teacher.getId())
                .address(reportAddress).reportTime(reportDateTime).appointmentEnd(appointEndTime)
                .presenterId(host.getId()).capacity(capacity)
                .build();
        // 添加会议
        if (insert(meeting)) {
            // 更新报告状态
            report.setStatus(ReportConstant.APPOINTMENT);
            reportService.update(report);
            result.setCode(200);
            result.setMessage("添加会议成功");
            Message message = Message.builder()
                    .toTeacherId(teacher.getId()).sendTime(new Date())
                    .message("您的学术报告《" + report.getTitle() + "》：已经安排了学术报告会议。详情见 会议安排。")
                    .build();
            messageService.insert(message);
            return result;
        }
        return result;
    }

    /**
     * 更新报告会议
     */
    public ResponseResult updateMeeting(Integer meetingId, String hostNo, String reportTime, String reportAddress, String appointEnd, Integer capacity) {
        ResponseResult result = new ResponseResult();
        // 获取主持人
        Teacher host = teacherService.getByNo(hostNo);
        if (ObjectUtil.isEmpty(host)) {
            result.setCode(404);
            result.setMessage("未找到编号为" + hostNo + "的教师职工");
            return result;
        }
        // 检查日期是否合法
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date reportDateTime = null;
        Date appointEndTime = null;
        try {
            reportDateTime = format.parse(reportTime);
        } catch (ParseException e) {
            result.setCode(500);
            result.setMessage("报告时间格式错误！格式为：2020-01-06 12:20");
            return result;
        }
        try {
            appointEndTime = format.parse(appointEnd);
        } catch (ParseException e) {
            result.setCode(500);
            result.setMessage("截止时间格式错误！格式为：2020-01-06 12:20");
            return result;
        }
        // 检查容纳人数
        if (capacity <= 0) {
            result.setCode(500);
            result.setMessage("会议容纳人数必须大于0");
            return result;
        }
        // 创建会议对象
        Meeting meeting = Meeting.builder().id(meetingId)
                .address(reportAddress).reportTime(reportDateTime).appointmentEnd(appointEndTime)
                .presenterId(host.getId()).capacity(capacity)
                .build();
        // 添加会议
        if (update(meeting)) {
            result.setCode(200);
            result.setMessage("修改会议成功");
            return result;
        }
        return result;
    }

    /**
     * 学生预约会议
     */
    public ResponseResult applyMeeting(Integer id, User loginUser) {
        ResponseResult result = new ResponseResult();
        // 获取会议
        Meeting meeting = getById(id);
        // 预约会议
        Appointment build = Appointment.builder()
                .meetingId(meeting.getId()).studentId(loginUser.getId())
                .build();
        List<Appointment> appointments = appointmentService.listAppointments(build);
        if (appointments.size() > 0) {
            // 已经预约过
            result.setCode(500);
            result.setMessage("您已预约，不可重复预约");
        } else {
            build.setStudentId(null);
            appointments = appointmentService.listAppointments(build);
            if (appointments.size() == meeting.getCapacity()) {
                // 判断预约是否已满
                result.setCode(500);
                result.setMessage("会议火爆，预约已满");
            } else {
                build.setStudentId(loginUser.getId());
                // 添加预约信息
                build.setAppointmentTime(new Date());
                if (appointmentService.insert(build)) {
                    result.setCode(200);
                    result.setMessage("预约成功");
                } else {
                    result.setCode(500);
                    result.setMessage("服务器出错了");
                }
            }
        }
        return result;
    }
}