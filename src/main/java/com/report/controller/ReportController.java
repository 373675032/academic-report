package com.report.controller;

import com.report.constant.ReportConstant;
import com.report.dto.ResponseResult;
import com.report.entity.Report;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 报告控制器
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
@RestController
@Slf4j
public class ReportController extends BaseController {

    /**
     * AI 润色标题
     */
    @PostMapping("/generateTitle")
    public ResponseResult generateTitle(@RequestParam("title") String title) {
        String generateTitle = reportService.generateTitle(title);
        return ResponseResult.builder().code(200).message(generateTitle).build();
    }

    /**
     * AI 润色报告简介
     */
    @PostMapping("/generateReport")
    public ResponseResult generateReport(@RequestParam("info") String info) {
        String generateInfo = reportService.generateReport(info);
        return ResponseResult.builder().code(200).message(generateInfo).build();
    }

    /**
     * AI 润色报告人简介
     */
    @PostMapping("/generateReporter")
    public ResponseResult generateReporter(@RequestParam("reporterInfo") String reporterInfo) {
        String generateInfo = reportService.generateReporter(reporterInfo);
        return ResponseResult.builder().code(200).message(generateInfo).build();
    }

    /**
     * 发布学术报告
     */
    @PostMapping("/publishReport")
    public ResponseResult publishReport(@RequestParam("title") String title, @RequestParam("info") String info,
                                        @RequestParam("reporterInfo") String reporterInfo, MultipartFile file, @RequestParam("reportFile") MultipartFile reportFile) {
        return reportService.publishReport(title, info, reporterInfo, file, reportFile, loginUser);
    }

    /**
     * 编辑学术报告
     */
    @PostMapping("/editReport")
    public ResponseResult editReport(@RequestParam("id") Integer id, @RequestParam("title") String title, @RequestParam("info") String info,
                                     @RequestParam("reporterInfo") String reporterInfo, MultipartFile file, MultipartFile reportFile) {
        return reportService.editReport(id, title, info, reporterInfo, file, reportFile, loginUser);
    }

    /**
     * 复原
     */
    @PostMapping("/restore")
    public ResponseResult restore(@RequestParam("id") Integer id) {
        Report report = reportService.getById(id);
        report.setStatus(ReportConstant.WAIT);
        // 更新状态为待审核
        if (reportService.update(report)) {
            result.setCode(200);
            result.setMessage("移出回收站成功");
        } else {
            result.setCode(500);
            result.setMessage("服务器错误");
        }
        return result;
    }

    /**
     * 移入回收站
     */
    @PostMapping("/intoTrash")
    public ResponseResult intoTrash(@RequestParam("id") Integer id) {
        Report report = reportService.getById(id);
        if (report.getStatus() > ReportConstant.WAIT) {
            // 改报告已经安排会议，不可删除
            result.setCode(500);
            result.setMessage("报告已经开始进入审核阶段，不可删除");
            return result;
        }
        report.setStatus(ReportConstant.TRASH);
        // 更新状态为回收站
        if (reportService.update(report)) {
            result.setCode(200);
            result.setMessage("移入回收站成功");
        } else {
            result.setCode(500);
            result.setMessage("服务器错误");
        }
        return result;
    }

    /**
     * 永久删除
     */
    @PostMapping("/deleteReport")
    public ResponseResult deleteReport(@RequestParam("id") Integer id) {
        // 删除
        if (reportService.deleteById(id)) {
            result.setCode(200);
            result.setMessage("删除成功");
        } else {
            result.setCode(500);
            result.setMessage("服务器错误");
        }
        return result;
    }

    /**
     * 清空回收站
     */
    @PostMapping("/deleteAll")
    public ResponseResult deleteAll() {
        // 获取全部的回收站文章
        Report build = Report.builder()
                .reporterNo(loginUser.getNo())
                .status(ReportConstant.TRASH)
                .build();
        List<Report> reports = reportService.listReports(build);
        for (Report report : reports) {
            // 遍历删除
            Integer id = report.getId();
            reportService.deleteById(id);
        }
        result.setCode(200);
        result.setMessage("清空成功");
        return result;
    }

    /**
     * 全部复原
     */
    @PostMapping("/restoreAll")
    public ResponseResult restoreAll() {
        // 获取全部的回收站文章
        Report build = Report.builder()
                .reporterNo(loginUser.getNo())
                .status(ReportConstant.TRASH)
                .build();
        List<Report> reports = reportService.listReports(build);
        for (Report report : reports) {
            // 更新状态为等待审核
            report.setStatus(ReportConstant.WAIT);
            reportService.update(report);
        }
        result.setCode(200);
        result.setMessage("全部移出成功");
        return result;
    }

    /**
     * 处理报告
     */
    @PostMapping("/dealReport")
    public ResponseResult dealReport(@RequestParam("id") Integer id, @RequestParam("status") int status, String checkInfo) {
        return reportService.dealReport(id, status, checkInfo);
    }

}
