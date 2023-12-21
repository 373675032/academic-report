package world.xuewei.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import world.xuewei.entity.Report;

import java.util.List;

/**
 * (Report)表数据库访问层
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
@Mapper
public interface ReportMapper {

    /**
     * 添加Report
     */
    int insert(Report report);

    /**
     * 删除Report
     */
    int deleteById(Integer id);

    /**
     * 查询单条数据
     */
    Report getById(Integer id);

    /**
     * 查询全部数据
     * 分页使用MyBatis的插件实现
     */
    List<Report> listReports();

    /**
     * 实体作为筛选条件查询数据
     */
    List<Report> listReports(Report report);

    /**
     * 根据学院ID获取报告
     */
    List<Report> listReportsByCollegeId(@Param("id") Integer id, @Param("status") Integer status);

    /**
     * 实体作为筛选条件获取结果数量
     */
    int countReports(Report report);

    /**
     * 修改Report, 根据 report 的主键修改数据
     */
    int update(Report report);

}