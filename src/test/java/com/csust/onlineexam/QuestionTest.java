package com.csust.onlineexam;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csust.onlineexam.entity.ChoiceQuestion;
import com.csust.onlineexam.entity.CodeQuestion;
import com.csust.onlineexam.entity.Judgement;
import com.csust.onlineexam.mapper.ChoiceQuestionMapper;
import com.csust.onlineexam.mapper.JudgementMapper;
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
import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    ChoiceQuestionMapper choiceQuestionMapper;
    @Autowired
    JudgementMapper judgementMapper;
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
   public void testQuestionSearch(){
       List<Integer> idList = new ArrayList<>(1);
       idList.add(3);
       choiceQuestionService.listByIds(idList).forEach(System.out::println);
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
   @Test
   public void testGetChoiceQuestion(){
       choiceQuestionMapper.getStudentChoiceQuestionsByExamId(1)
               .forEach(System.out::println);
   }
   @Test
   public void testRandomQuestion(){
       /*QueryWrapper<Judgement> queryWrapper = new QueryWrapper<>();
       queryWrapper.eq("level",2);
       judgementMapper.getRandomQuestion(5,queryWrapper).forEach(System.out::println);*/
       choiceQuestionMapper.getRandomQuestion(1,new QueryWrapper<>()).forEach(System.out::println);


   }

}
