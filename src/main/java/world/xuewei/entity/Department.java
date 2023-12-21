package world.xuewei.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import world.xuewei.constant.Photo;

import java.io.Serializable;

/**
 * (Department)实体类
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
 * @since 2020-12-20 15:53:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department implements Serializable {

    private static final long serialVersionUID = 719806364595412025L;

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 部门编号
     */
    private String no;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门部长ID
     */
    private Integer leaderId;

    /**
     * 学院ID
     */
    private Integer collegeId;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 头像
     */
    private String img = Photo.department;

    /**
     * 部长
     */
    private Teacher leader;

    /**
     * 学院
     */
    private College college;

}