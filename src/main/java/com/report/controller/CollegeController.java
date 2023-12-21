package com.report.controller;

import com.alibaba.fastjson.JSONObject;
import com.report.entity.College;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 学院控制器
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
@Slf4j
public class CollegeController extends BaseController {

    /**
     * 查询所有学院
     */
    @GetMapping("/allColleges")
    public String allColleges(Integer page, Integer rows) {
        return JSONObject.toJSONString(collegeService.pageAllColleges(page, rows));
    }

    /**
     * 编辑、删除学院
     */
    @PostMapping("/editCollege")
    public void editCollege(College college, String action) {
        collegeService.editCollege(college, action);
    }
}