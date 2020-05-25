package com.csust.onlineexam;

import com.csust.onlineexam.entity.ChoiceQuestion;
import com.csust.onlineexam.entity.CodeQuestion;
import com.csust.onlineexam.service.impl.ChoiceQuestionServiceImpl;
import com.csust.onlineexam.service.impl.CodeQuestionServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

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
    @Autowired
    private CodeQuestionServiceImpl codeQuestionService;

   @Test
   public void testOptionQuestion(){
       ChoiceQuestion choicequestion = new ChoiceQuestion();
       choicequestion.setId(5);
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
       choiceQuestionService.updateById(choicequestion);
   }
   @Test
   public void testXML() throws ParserConfigurationException, IOException, SAXException {
       DocumentBuilder builder= DocumentBuilderFactory.newInstance().newDocumentBuilder();
       File f = new File("D:/BaiduYunDownload/fps-201616080307-1000.xml");
       Document document = builder.parse(f);
       Element root = document.getDocumentElement();
       //获取元素的子元素
       NodeList children = root.getChildNodes();
       CodeQuestion codeQuestion;
       for (int i = 0; i < children.getLength(); i++) {
           Node child = children.item(i);
           if(child.getNodeName().equals("item")){
               /*NodeList dataList = child.getChildNodes();*/
               codeQuestion = new CodeQuestion();
               codeQuestion.setTitle(((Element) child).getElementsByTagName("title").item(0).getTextContent());
               codeQuestion.setDescription(((Element) child).getElementsByTagName("description").item(0).getTextContent());
               codeQuestion.setInput(((Element) child).getElementsByTagName("input").item(0).getTextContent());
               codeQuestion.setOutput(((Element) child).getElementsByTagName("output").item(0).getTextContent());
               codeQuestion.setTimeLimit(Integer.valueOf(((Element) child).getElementsByTagName("time_limit").item(0).getTextContent()));
               codeQuestion.setMemoryLimit(Integer.valueOf(((Element) child).getElementsByTagName("memory_limit").item(0).getTextContent()));
               System.out.println(codeQuestion);
                codeQuestionService.save(codeQuestion);
               /*NodeList data = ((Element) child).getElementsByTagName("title");
               Node content = data.item(0);
               System.out.println(content.getNodeName() + ": " + content.getTextContent());*/
               /*for (int j = 0; j < dataList.getLength(); j++) {
                   Node data = dataList.item(j);
                   if(data instanceof Element) {
                       System.out.println(data.getNodeName() + ": " + data.getTextContent());
                   }
               }*/
           }
       }
   }

}
