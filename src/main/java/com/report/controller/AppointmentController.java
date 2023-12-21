package com.report.controller;

import com.report.dto.ResponseResult;
import com.report.entity.Appointment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 * 预约会议控制器
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
@RestController
public class AppointmentController extends BaseController {

    /**
     * 导出会议的签到表
     */
    @GetMapping("/exportAppointment")
    public void exportAppointment(@RequestParam("id") Integer id, HttpServletResponse response) throws IOException {
        appointmentService.exportAppointment(id, response);
    }

    /**
     * 导入学生签到信息
     */
    @PostMapping("/importAppointments")
    public ResponseResult importAppointments(MultipartFile file) {
        ResponseResult result = new ResponseResult();
        String fileName = file.getOriginalFilename();
        log.info("接收到文件：{}", fileName);
        String pattern = "^《[^》]+》-会议签到表\\.xls$";
        // 验证文件名是否合法，格式：《会议名称》-会议签到表.xls
        if (!Pattern.matches(pattern, file.getOriginalFilename())) {
            result.setCode(301);
            result.setMessage("上传文件的格式（与导出文件名相同）必须为：《报告名称》-会议签到表.xls");
            return result;
        }
        return appointmentService.importAppointments(file);
    }

    /**
     * 更新签到状态
     */
    @PostMapping("/updateStatus")
    public ResponseResult updateStatus(@RequestParam("id") Integer id, @RequestParam("status") Integer status) {
        // 构建预约对象
        Appointment appointment = Appointment.builder()
                .id(id).present(status)
                .build();
        if (appointmentService.update(appointment)) {
            result.setCode(200);
            result.setMessage("更新签到状态成功");
        } else {
            result.setCode(500);
            result.setMessage("服务器错误");
        }
        return result;
    }
}
