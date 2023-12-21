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
 * (Student)实体类
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
 * @since 2020-12-20 15:52:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ExcelTarget("Student")
public class Student implements Serializable {

    private static final long serialVersionUID = 972593633686891663L;

    /**
     * 主键ID
     */
    private Integer id;

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
     * 登录密码
     */
    private String password;

    /**
     * 邮箱
     */
    @Excel(name = "邮箱", width = 20)
    private String email;

    /**
     * 手机号码
     */
    @Excel(name = "手机号码", width = 20)
    private String phone;

    /**
     * 性别
     */
    @Excel(name = "性别")
    private String sex;

    /**
     * 出生日期【1999-09-09】
     */
    @Excel(name = "出生日期", format = "yyyy年MM月dd日", width = 20)
    private Date birthday;

    /**
     * 出生日期字符串
     */
    private String birthdayStr;

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
     * 头像
     */
    private String img = Photo.student;

}