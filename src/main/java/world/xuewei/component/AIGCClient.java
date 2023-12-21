package world.xuewei.component;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationOutput;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.generation.models.QwenParam;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.MessageManager;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.utils.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 生成式AI客户端，对接阿里云通义千问
 *
 * @author XUEW
 * @since 2023/12/21 12:00
 */
@Component
public class AIGCClient {

    @Value("${ai-key}")
    private String apiKey;

    public String query(String queryMessage) {
        Constants.apiKey = apiKey;
        try {
            Generation gen = new Generation();
            MessageManager msgManager = new MessageManager(10);
            Message systemMsg = Message.builder().role(Role.SYSTEM.getValue())
                    .content("你是一个学术报告的专业写手，善于总结和归纳！")
                    .build();
            Message userMsg = Message.builder().role(Role.USER.getValue()).content(queryMessage).build();
            msgManager.add(systemMsg);
            msgManager.add(userMsg);
            QwenParam param = QwenParam.builder().model(Generation.Models.QWEN_TURBO).messages(msgManager.get()).resultFormat(QwenParam.ResultFormat.MESSAGE).build();
            GenerationResult result = gen.call(param);
            GenerationOutput output = result.getOutput();
            Message message = output.getChoices().get(0).getMessage();
            return message.getContent();
        } catch (Exception e) {
            return "AI 繁忙，请稍后再试～";
        }
    }
}
