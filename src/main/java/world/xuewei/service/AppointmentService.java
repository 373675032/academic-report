package world.xuewei.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import world.xuewei.dto.ResponseResult;
import world.xuewei.entity.*;
import world.xuewei.excel.AppointmentTable;
import world.xuewei.mapper.AppointmentMapper;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预约签到服务
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
public class AppointmentService {

    @Resource
    private AppointmentMapper appointmentMapper;

    @Resource
    private MeetingService meetingService;

    @Resource
    private ReportService reportService;

    @Resource
    private StudentService studentService;

    @Resource
    private CollegeService collegeService;

    public boolean insert(Appointment appointment) {
        return appointmentMapper.insert(appointment) == 1;
    }

    public boolean deleteById(Integer id) {
        return appointmentMapper.deleteById(id) == 1;
    }

    public Appointment getById(Integer id) {
        return appointmentMapper.getById(id);
    }

    public List<Appointment> listAppointments() {
        return appointmentMapper.listAppointments();
    }

    public List<Appointment> listAppointments(Appointment appointment) {
        return appointmentMapper.listAppointments(appointment);
    }

    public int countAppointments(Appointment appointment) {
        return appointmentMapper.countAppointments(appointment);
    }

    public boolean update(Appointment appointment) {
        return appointmentMapper.update(appointment) == 1;
    }

    public boolean updatePresent(Appointment appointment) {
        return appointmentMapper.updatePresent(appointment);
    }

    /**
     * 导出会议的签到表
     */
    public void exportAppointment(Integer id, HttpServletResponse response) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        // 根据ID获取会议
        Meeting meeting = meetingService.getById(id);
        // 获取学术报告
        Report report = reportService.getById(meeting.getReportId());
        // 获取预约会议的全体学生
        List<Appointment> appointments = listAppointments(
                Appointment.builder().meetingId(id)
                        .build());
        // 保存excel表格的每一行
        List<AppointmentTable> appointmentStudents = new ArrayList<>();
        for (Appointment appointment : appointments) {
            Student student = studentService.getById(appointment.getStudentId());
            AppointmentTable table = convertStudent(student, appointment);
            appointmentStudents.add(table);
        }
        response.setHeader("content-disposition",
                "attachment;fileName=" + URLEncoder.encode("《" + report.getTitle() + "》-" + "会议签到表.xls", "UTF-8"));
        // 生成文件的信息
        ExportParams params = new ExportParams();
        params.setTitle("XX大学 《" + report.getTitle() + "》学术报告会议-签到表");
        params.setSheetName("签到表");
        Workbook workbook = ExcelExportUtil.exportExcel(params, AppointmentTable.class, appointmentStudents);
        // 输出
        workbook.write(outputStream);
        // 关闭资源
        outputStream.close();
        workbook.close();
    }

    /**
     * 转换Student对象
     */
    private AppointmentTable convertStudent(Student student, Appointment appointment) {
        AppointmentTable table = new AppointmentTable();
        table.setNo(student.getNo());
        table.setName(student.getName());
        table.setClassInfo(student.getClassInfo());
        table.setGrade(student.getGrade());
        table.setSex(student.getSex());
        table.setMajor(student.getMajor());
        table.setPhone(student.getPhone());
        Integer collegeId = student.getCollegeId();
        College college = collegeService.getById(collegeId);
        table.setCollegeName(college.getName());
        if (ObjectUtil.isNotEmpty(appointment.getPresent())) {
            table.setPresent(appointment.getPresent() == 1 ? "是" : "否");
        }
        table.setTime(appointment.getAppointmentTime());
        return table;
    }

    /**
     * 导入学生签到信息
     */
    public ResponseResult importAppointments(MultipartFile file) {
        ResponseResult result = new ResponseResult();
        String fileName = file.getOriginalFilename();
        // 获取报告名称
        assert fileName != null;
        String reportTitle = fileName.split("《")[1].split("》")[0];
        // 查找学术报告
        List<Report> reports = reportService.listReports(Report.builder().title(reportTitle).build());
        if (reports.size() == 0) {
            result.setCode(404);
            result.setMessage("未找到名称为：" + reportTitle + "的学术报告");
            return result;
        }
        Report report = reports.get(0);
        // 获取会议
        Meeting meeting = meetingService.getByReportId(report.getId());
        // 参数1：文件流
        InputStream stream = null;
        try {
            stream = file.getInputStream();
        } catch (IOException e) {
            log.error(e.getMessage());
            result.setCode(500);
            result.setMessage("读取文件失败");
            return result;
        }
        // 参数2：导入类型
        ImportParams params = new ImportParams();
        // 标题占用多少行
        params.setTitleRows(1);
        // 头部属性占用多少行
        params.setHeadRows(1);
        // 对Excel进行合法参数校验
        String[] array = new String[]{"学号", "姓名", "性别", "手机号码", "年级", "专业", "班级", "院系", "预约时间", "是否到场（是/否）"};
        params.setImportFields(array);
        List<AppointmentTable> tables = null;
        try {
            tables = ExcelImportUtil.importExcel(stream, AppointmentTable.class, params);
        } catch (Exception e) {
            log.error(e.getMessage());
            result.setCode(302);
            result.setMessage("请检查上传的Excel文件是否严格遵守模板规定");
            return result;
        }
        Map<String, String> error = new HashMap<>();
        int emptyNo = 0;
        for (AppointmentTable table : tables) {
            if (StrUtil.isEmpty(table.getNo())) {
                emptyNo++;
            } else if (StrUtil.isEmpty(table.getPresent())) {
                error.put(table.getNo(), "签到情况为空");
            } else {
                Student student = studentService.getByNo(table.getNo());
                if (ObjectUtil.isEmpty(student)) {
                    // 学生学号不存在
                    error.put(table.getNo(), "学号不存在");
                } else if (!"是".equals(table.getPresent()) && !"否".equals(table.getPresent())) {
                    error.put(table.getNo(), "签到情况只允许填写（ 是 / 否 ）");
                } else {
                    int status = 0;
                    if ("是".equals(table.getPresent())) {
                        status = 1;
                    }
                    // 记录签到情况
                    Appointment build = Appointment.builder()
                            .meetingId(meeting.getId())
                            .studentId(student.getId())
                            .present(status)
                            .build();
                    updatePresent(build);
                }
            }
        }

        // 成功导入的记录数
        int success = tables.size() - error.size() - emptyNo;
        if (success == tables.size()) {
            // 全部成功
            result.setCode(200);
            result.setMessage("成功导入" + tables.size() + "条记录");
        } else {
            result.setCode(303);
            result.setMessage("成功导入" + success + "条记录，共 " + tables.size() + "条");
            if (emptyNo > 0) {
                error.put("编号为空", emptyNo + "条");
            }
            // 生成错误信息
            StringBuilder errorHtml = new StringBuilder();
            for (String s : error.keySet()) {
                errorHtml.append(s).append("：").append(error.get(s)).append("、");
            }
            errorHtml = new StringBuilder(errorHtml.substring(0, errorHtml.length() - 1));
            result.setData(errorHtml.toString());
        }
        return result;
    }
}