package world.xuewei.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import world.xuewei.dto.ResponseResult;

/**
 * 会议控制器
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
@RestController
public class MeetingController extends BaseController {

    /**
     * 安排报告会议
     */
    @PostMapping("/arrangeMeeting")
    public ResponseResult arrangeMeeting(@RequestParam("id") Integer id, @RequestParam("hostNo") String hostNo,
                                         @RequestParam("reportTime") String reportTime, @RequestParam("reportAddress") String reportAddress,
                                         @RequestParam("appointEnd") String appointEnd, @RequestParam("capacity") Integer capacity) {
        return meetingService.arrangeMeeting(id, hostNo, reportTime, reportAddress, appointEnd, capacity);
    }

    /**
     * 更新报告会议
     */
    @PostMapping("/updateMeeting")
    public ResponseResult updateMeeting(@RequestParam("meetingId") Integer meetingId, @RequestParam("hostNo") String hostNo,
                                        @RequestParam("reportTime") String reportTime, @RequestParam("reportAddress") String reportAddress,
                                        @RequestParam("appointEnd") String appointEnd, @RequestParam("capacity") Integer capacity) {
        return meetingService.updateMeeting(meetingId, hostNo, reportTime, reportAddress, appointEnd, capacity);
    }

    /**
     * 学生预约会议
     */
    @PostMapping("/applyMeeting")
    public ResponseResult applyMeeting(@RequestParam("id") Integer id) {
        return meetingService.applyMeeting(id, loginUser);
    }
}
