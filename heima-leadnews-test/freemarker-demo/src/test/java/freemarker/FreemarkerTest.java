package freemarker;

import com.heima.freemarker.FreemarkerApp;
import com.heima.freemarker.Student;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileWriter;
import java.util.*;

@SpringBootTest(classes = FreemarkerApp.class)
@RunWith(SpringRunner.class)
public class FreemarkerTest {
    @Autowired
    private Configuration configuration;
    @Test
    public void test() throws Exception{
        //获取模板对象
        Template template = configuration.getTemplate("freemarker.ftl");
        //准备数据
        Student student1 = new Student();
        Student student2 = new Student();
        Map<String,Object> map = new HashMap<>();
        map.put("name","人");
        map.put("today",new Date());
        map.put("No",23232332232323L);

        student1.setName("horizon");
        student1.setAge(18);
        student1.setMoney(22);

        student2.setName("bloodhunter");
        student2.setAge(20);
        student2.setMoney(100);

        map.put("stu",student1);

        List<Student> stuList = new ArrayList<>();
        stuList.add(student1);
        stuList.add(student2);
        map.put("stus",stuList);

        //map指令
        Map<String,Object> stuMap = new LinkedHashMap<>();
        stuMap.put("stu1",student1);
        stuMap.put("stu2",student2);

        map.put("stuMap",stuMap);

        //生成结果
        template.process(map,new FileWriter("D://index.html"));
    }

}
