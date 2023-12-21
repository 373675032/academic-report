package com.report.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.report.constant.Photo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (Teacher)实体类
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
 * @since 2020-12-20 15:49:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ExcelTarget("Teacher")
public class Teacher implements Serializable {

    private static final long serialVersionUID = 459028036953009293L;

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 职工号
     */
    @Excel(name = "工号", width = 20)
    private String no;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String name;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 性别
     */
    @Excel(name = "性别")
    private String sex;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码", width = 20)
    private String phone;

    /**
     * 出生年月
     */
    @Excel(name = "出生日期", format = "yyyy年MM月dd日", width = 20)
    private Date birthday;

    /**
     * 出生日期字符串
     */
    private String birthdayStr;

    /**
     * 职称【助教、讲师、副教授、教授】
     */
    @Excel(name = "职称")
    private String position;

    /**
     * 是否是院长【1是】【0否】
     */
    private Integer isCollegeLeader;

    /**
     * 学院ID
     */
    private Integer collegeId;

    /**
     * 院系
     */
    @Excel(name = "院系", width = 30)
    private String collegeName;

    /**
     * 院系
     */
    private College college;

    /**
     * 是否是部门部长【1是】【0否】
     */
    private Integer isDepartmentLeader;

    /**
     * 所属部门ID
     */
    private String departmentId;

    /**
     * 头像
     */
    private String img = Photo.teacher;

}