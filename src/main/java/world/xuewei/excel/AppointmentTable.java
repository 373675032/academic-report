package world.xuewei.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 签到表
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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentTable {

    /**
     * 学号【201724114111】
     */
    @Excel(name = "学号", width = 20)
    private String no;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String name;

    /**
     * 性别
     */
    @Excel(name = "性别")
    private String sex;

    /**
     * 年级【2017】
     */
    @Excel(name = "年级")
    private Integer grade;

    /**
     * 专业
     */
    @Excel(name = "专业", width = 30)
    private String major;

    /**
     * 班级信息【1班】
     */
    @Excel(name = "班级")
    private String classInfo;

    /**
     * 院系
     */
    @Excel(name = "院系", width = 30)
    private String collegeName;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码", width = 20)
    private String phone;

    /**
     * 预约时间
     */
    @Excel(name = "预约时间", format = "yyyy-MM-dd HH:mm", width = 30)
    private Date time;

    /**
     * 是否到场
     */
    @Excel(name = "是否到场（是/否）", width = 30)
    private String present = "";

}
