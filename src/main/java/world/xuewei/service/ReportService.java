package world.xuewei.service;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import world.xuewei.component.AIGCClient;
import world.xuewei.component.OssClient;
import world.xuewei.constant.Leader;
import world.xuewei.constant.ReportConstant;
import world.xuewei.constant.Role;
import world.xuewei.dao.ReportMapper;
import world.xuewei.dto.ResponseResult;
import world.xuewei.entity.Message;
import world.xuewei.entity.Report;
import world.xuewei.entity.Teacher;
import world.xuewei.entity.User;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 报告服务
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
public class ReportService {

    @Resource
    private AIGCClient aigcClient;

    @Resource
    private ReportMapper reportMapper;

    @Resource
    private OssClient ossClient;

    @Resource
    private TeacherService teacherService;

    @Resource
    private MessageService messageService;


    public boolean insert(Report report) {
        return reportMapper.insert(report) == 1;
    }


    public boolean deleteById(Integer id) {
        return reportMapper.deleteById(id) == 1;
    }


    public Report getById(Integer id) {
        return reportMapper.getById(id);
    }


    public List<Report> listReports() {
        return reportMapper.listReports();
    }


    public List<Report> listReports(Report report) {
        return reportMapper.listReports(report);
    }


    public List<Report> listReportsByCollegeId(Integer id, Integer status) {
        return reportMapper.listReportsByCollegeId(id, status);
    }


    public int countReports(Report report) {
        return reportMapper.countReports(report);
    }


    public boolean update(Report report) {
        return reportMapper.update(report) == 1;
    }

    /**
     * 发布学术报告
     */
    public ResponseResult publishReport(String title, String info, String reporterInfo, MultipartFile file, MultipartFile reportFile, User loginUser) {
        ResponseResult result = new ResponseResult();
        // 上传文件到阿里云对象存储
        String fileUrl = "";
        String reportFileUrl = "";

        if (ObjectUtil.isNotEmpty(file)) {
            // 检查附件的文件类型
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            if (!fileName.endsWith(".zip") && !fileName.endsWith(".rar") && !fileName.endsWith(".7z")) {
                result.setCode(302);
                result.setMessage("附件类型请上传压缩包 [ .zip|.rar|.7z ]");
                return result;
            }
            try {
                fileUrl = ossClient.upLoad(file, loginUser.getName());
            } catch (IOException e) {
                log.error(e.getMessage());
                result.setCode(301);
                result.setMessage("上传文件发生错误");
            }
        }
        // 检查学术报告文件的类型
        String reportFileName = reportFile.getOriginalFilename();
        assert reportFileName != null;
        if (!reportFileName.endsWith(".word") && !reportFileName.endsWith(".pdf") && !reportFileName.endsWith(".doc")) {
            result.setCode(302);
            result.setMessage("报告类型请上传文档 [ .word|.pdf|.doc ]");
            return result;
        }
        try {
            reportFileUrl = ossClient.upLoad(reportFile, loginUser.getName());
        } catch (IOException e) {
            log.error(e.getMessage());
            result.setCode(301);
            result.setMessage("上传文件发生错误");
        }
        // 添加到数据库
        Report report = Report.builder()
                .title(title).info(info).reporterInfo(reporterInfo).attachment(fileUrl).reportFile(reportFileUrl)
                .status(ReportConstant.WAIT).publishTime(new Date()).reporterNo(loginUser.getNo())
                .build();
        // 学院院长发布的报告，无需自己审核
        if (Objects.equals(loginUser.getRole(), Role.TEACHER) && loginUser.getIsLeader() == Leader.YES) {
            report.setStatus(ReportConstant.PASS_FROM_LEADER);
            report.setCheckInfo1("-");
        }
        if (insert(report)) {
            result.setCode(200);
            result.setMessage("发布成功, 请耐心等待审核");
        } else {
            result.setCode(500);
            result.setMessage("服务器出错了");
        }
        return result;
    }

    public ResponseResult editReport(Integer id, String title, String info, String reporterInfo, MultipartFile file, MultipartFile reportFile, User loginUser) {
        ResponseResult result = new ResponseResult();
        // 获取要修改的报告
        Report report = getById(id);
        if (ObjectUtil.isNotEmpty(file)) {
            // 检查附件的文件类型
            String fileName = file.getOriginalFilename();
            if (StrUtil.isNotEmpty(fileName)) {
                if (!fileName.endsWith(".zip") && !fileName.endsWith(".rar") && !fileName.endsWith(".7z")) {
                    result.setCode(302);
                    result.setMessage("附件类型请上传压缩包 [ .zip|.rar|.7z ]");
                    return result;
                }
                try {
                    String fileUrl = ossClient.upLoad(file, loginUser.getName());
                    report.setAttachment(fileUrl);
                } catch (IOException e) {
                    log.error(e.getMessage());
                    result.setCode(301);
                    result.setMessage("上传文件发生错误");
                    return result;
                }
            }
        }
        // 检查学术报告文件的类型
        if (ObjectUtil.isNotEmpty(reportFile)) {
            String reportFileName = reportFile.getOriginalFilename();
            assert reportFileName != null;
            if (!reportFileName.endsWith(".word") && !reportFileName.endsWith(".pdf") && !reportFileName.endsWith(".doc")) {
                result.setCode(302);
                result.setMessage("报告类型请上传文档 [ .word|.pdf|.doc ]");
                return result;
            }
            try {
                String fileUrl = ossClient.upLoad(reportFile, loginUser.getName());
                report.setReportFile(fileUrl);
            } catch (IOException e) {
                log.error(e.getMessage());
                result.setCode(301);
                result.setMessage("上传文件发生错误");
            }
        }

        // 修改
        report.setTitle(title);
        report.setInfo(info);
        report.setReporterInfo(reporterInfo);
        report.setStatus(ReportConstant.WAIT);
        report.setPublishTime(new Date());
        // 更新报告
        if (update(report)) {
            result.setCode(200);
            result.setMessage("修改成功, 请耐心等待审核");
        } else {
            result.setCode(500);
            result.setMessage("服务器出错了");
        }
        return result;
    }

    /**
     * 处理报告
     */
    public ResponseResult dealReport(Integer id, int status, String checkInfo) {
        ResponseResult result = new ResponseResult();
        // 获取学术报告
        Report report = getById(id);
        report.setStatus(status);
        // 获取作者
        Teacher teacher = teacherService.getByNo(report.getReporterNo());
        Message message = Message.builder()
                .toTeacherId(teacher.getId())
                .sendTime(new Date())
                .build();
        if (status == ReportConstant.PASS_FROM_LEADER || status == ReportConstant.NO_PASS_FROM_LEADER) {
            // 判断是否为院领导审核
            report.setCheckInfo1(checkInfo);
        } else {
            // 教务部门审核
            report.setCheckInfo2(checkInfo);
        }
        if (update(report)) {
            result.setCode(200);
            result.setMessage("处理成功");
            if (status == ReportConstant.PASS_FROM_LEADER) {
                message.setMessage("您的学术报告《" + report.getTitle() + "》：已通过 学院院长 审核。审核意见：" + checkInfo);
            } else if (status == ReportConstant.PASS_FROM_DEPT) {
                message.setMessage("您的学术报告《" + report.getTitle() + "》：已通过 教务部门 审核。审核意见：" + checkInfo);
            } else if (status == ReportConstant.NO_PASS_FROM_LEADER) {
                message.setMessage("您的学术报告《" + report.getTitle() + "》：未通过 学院院长 审核。审核意见：" + checkInfo);
            } else if (status == ReportConstant.NO_PASS_FROM_DEPT) {
                message.setMessage("您的学术报告《" + report.getTitle() + "》：未通过 教务部门 审核。审核意见：" + checkInfo);
            }
            messageService.insert(message);
        } else {
            result.setCode(500);
            result.setMessage("服务器错误");
        }
        return result;
    }

    /**
     * 润色标题
     */
    public String generateTitle(String title) {
        title = "帮我润色一下标题，要求吸引人，我只需要一个结果，并直接返回即可：" + title;
        return aigcClient.query(title).replace("\"", "");
    }

    /**
     * 润色报告简介
     */
    public String generateReport(String info) {
        info = "我想基于以下内容做一个学术报告会议，请帮我生成大概800字的报告会议简介，要求用词专业、吸引人，请直接返回结果：" + info;
        return aigcClient.query(info).replace("\"", "");
    }

    /**
     * 润色报告人简介
     */
    public String generateReporter(String info) {
        info = "帮我润色一下我的简介，大概300字即可，作为学术报告的宣传材料，要求用词专业、吸引人，请直接返回结果：" + info;
        return aigcClient.query(info).replace("\"", "");
    }
}