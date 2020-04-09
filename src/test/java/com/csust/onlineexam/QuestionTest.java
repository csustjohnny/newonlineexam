package com.csust.onlineexam;

import com.csust.onlineexam.entity.ChoiceQuestion;
import com.csust.onlineexam.service.impl.ChoiceQuestionServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author ：Lenovo
 * @date ：Created in 2020/4/4 18:22
 * @description : 问题相关测试
 * @modified By：
 */
@SpringBootTest
public class QuestionTest {
    @Autowired
    private ChoiceQuestionServiceImpl choiceQuestionService;

   @Test
   public void testOptionQuestion(){
       ChoiceQuestion choicequestion = new ChoiceQuestion();
       choicequestion.setTitle("下面属性中，是事务(Transaction)属性的有____。");
       choicequestion.setOptionA("原子性(Atomic)");
       choicequestion.setOptionB("并发性(Concurrency) ");
       choicequestion.setOptionC("一致性(Consistent) ");
       choicequestion.setOptionD("隔离性(Isolated) ");
       choicequestion.setOptionE("持久性(Durable)  ");
       choicequestion.setKnowledgePoint("");
       choicequestion.setAnalysis("这是一个解析哦！");
       choicequestion.setAnswer("A,C,D,E");
       choicequestion.setIsMultiple(true);
       choiceQuestionService.save(choicequestion);
   }
}
