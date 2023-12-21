package world.xuewei.service;

import org.springframework.stereotype.Service;
import world.xuewei.dao.MessageMapper;
import world.xuewei.entity.Message;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Message)表业务接口实现类
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
@Service
public class MessageService {

    @Resource
    private MessageMapper messageMapper;

    public boolean insert(Message message) {
        return messageMapper.insert(message) == 1;
    }


    public boolean deleteById(Integer id) {
        return messageMapper.deleteById(id) == 1;
    }


    public boolean deleteAllByTeacherId(Integer id) {
        return messageMapper.deleteAllByTeacherId(id);
    }


    public Message getById(Integer id) {
        return messageMapper.getById(id);
    }


    public List<Message> listMessages() {
        return messageMapper.listMessages();
    }


    public List<Message> listMessages(Message message) {
        return messageMapper.listMessages(message);
    }


    public int countMessages(Message message) {
        return messageMapper.countMessages(message);
    }


    public boolean update(Message message) {
        return messageMapper.update(message) == 1;
    }

}