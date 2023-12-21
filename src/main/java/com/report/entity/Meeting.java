package com.report.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (Meeting)实体类
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
 * @since 2020-12-20 15:49:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Meeting implements Serializable {

    private static final long serialVersionUID = 877299606472764277L;

    /**
     * 会议ID
     */
    private Integer id;

    /**
     * 报告ID
     */
    private Integer reportId;

    /**
     * 报告教师id
     */
    private Integer reporterId;

    /**
     * 主持人教师ID
     */
    private Integer presenterId;

    /**
     * 报告时间
     */
    private Date reportTime;

    /**
     * 报告地点
     */
    private String address;

    /**
     * 预约截止时间
     */
    private Date appointmentEnd;

    /**
     * 最大容纳人数
     */
    private Integer capacity;

}