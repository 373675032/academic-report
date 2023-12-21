package world.xuewei.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (Appointment)实体类
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
 * @since 2021-01-11 20:41:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment implements Serializable {

    private static final long serialVersionUID = 827721937429118051L;

    /**
     * 预约ID
     */
    private Integer id;

    /**
     * 会议ID
     */
    private Integer meetingId;

    /**
     * 学生ID
     */
    private Integer studentId;

    /**
     * 预约时间
     */
    private Date appointmentTime;

    /**
     * 是否到场【1：是】【0：否】
     */
    private Integer present;

}