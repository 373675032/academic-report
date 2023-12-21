package com.report.vo;

import com.report.entity.Meeting;
import com.report.entity.Report;
import com.report.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeetingVo {
    /**
     * 报告
     */
    private Report report;
    /**
     * 报告人
     */
    private Teacher reporter;
    /**
     * 主持人
     */
    private Teacher host;
    /**
     * 会议
     */
    private Meeting meeting;

    /**
     * 预约数
     */
    private int appointmentCount;

    /**
     * 签到数
     */
    private int arriveCount;

    /**
     * 预约百分比 = 预约人数/会议容量 * 100%
     */
    private int appointmentPercent;

    /**
     * 状态【1:已预约】【0:未预约】【2:预约已满】
     */
    private int status;
}