package com.report.controller;

import com.report.dto.ResponseResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * (Message)表控制层
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
 * @since 2021-01-24 22:16:43
 */
@RestController
public class MessageController extends BaseController {

    /**
     * 全部删除
     */
    @PostMapping("/deleteAllMessages")
    public ResponseResult deleteAllMessages() {
        if (messageService.deleteAllByTeacherId(loginUser.getId())) {
            result.setCode(200);
            result.setMessage("清空成功");
        } else {
            result.setCode(500);
            result.setMessage("没有消息被清空");
        }
        return result;
    }

}