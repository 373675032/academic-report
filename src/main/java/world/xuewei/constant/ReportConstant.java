package world.xuewei.constant;

/**
 * 学术报告常量
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
public class ReportConstant {

    /**
     * 未通过教务部门审核
     */
    public static final Integer NO_PASS_FROM_DEPT = -2;

    /**
     * 未通过院长审核
     */
    public static final Integer NO_PASS_FROM_LEADER = -1;

    /**
     * 等待审核
     */
    public static final Integer WAIT = 0;

    /**
     * 通过院长审核
     */
    public static final Integer PASS_FROM_LEADER = 1;

    /**
     * 通过教务部门审核
     */
    public static final Integer PASS_FROM_DEPT = 2;

    /**
     * 正在预约会议
     */
    public static final Integer APPOINTMENT = 3;

    /**
     * 已完成
     */
    public static final Integer FINISHED = 4;

    /**
     * 回收站
     */
    public static final Integer TRASH = 5;
}
