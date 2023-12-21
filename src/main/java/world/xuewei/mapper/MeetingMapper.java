package world.xuewei.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import world.xuewei.entity.Meeting;

import java.util.List;

/**
 * (Meeting)表数据库访问层
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
public interface MeetingMapper {

    /**
     * 添加Meeting
     */
    int insert(Meeting meeting);

    /**
     * 删除Meeting
     */
    int deleteById(Integer id);

    /**
     * 查询单条数据
     */
    Meeting getById(Integer id);

    /**
     * 查询单条数据
     */
    Meeting getByReportId(Integer id);

    /**
     * 查询全部数据
     * 分页使用MyBatis的插件实现
     */
    List<Meeting> listMeetings();

    /**
     * 查询预约结束且会议时间未达到的会议
     * 分页使用MyBatis的插件实现
     */
    List<Meeting> listAppointmentEndMeetings(Integer collegeId);

    /**
     * 查询已完成的会议
     * 分页使用MyBatis的插件实现
     */
    List<Meeting> listFinishMeetings(Integer collegeId);

    /**
     * 查询我已完成的会议
     * 分页使用MyBatis的插件实现
     */
    List<Meeting> listMyFinishMeetings(Integer id);

    /**
     * 通过学年和学期搜索指定学院的会议
     */
    List<Meeting> searchMeetings(@Param("year") String year, @Param("semester") String semester, @Param("collegeId") Integer collegeId);

    /**
     * 实体作为筛选条件查询数据
     */
    List<Meeting> listMeetings(Meeting meeting);

    /**
     * 实体作为筛选条件获取结果数量
     */
    int countMeetings(Meeting meeting);

    /**
     * 修改Meeting, 根据 meeting 的主键修改数据
     */
    int update(Meeting meeting);

    /**
     * 获取指定学生预约的全部会议
     */
    List<Meeting> listAppointingMeeting(Integer studentId);

    /**
     * 获取指定学生的参会记录
     */
    List<Meeting> listAppointingFinishMeeting(Integer studentId);

    /**
     * 获取指定学院的开展过报告会议的学年
     */
    List<String> getAllYears(Integer collegeId);

}