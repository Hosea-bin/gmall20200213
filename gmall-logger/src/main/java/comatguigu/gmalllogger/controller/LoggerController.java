package comatguigu.gmalllogger.controller;

import com.alibaba.fastjson.JSONObject;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController //=@Controller+方法上@ResponseBody
@Slf4j
public class LoggerController {
    @Autowired//自动注入，不用new对象
    KafkaTemplate<String,String> kafkaTemplate;

    @RequestMapping("t1")
    @ResponseBody
    public String test1(){
        System.out.println("我是第一个测试方法");
        return "success";
    }

    @RequestMapping("t2")
    //@ResponseBody
    public String test2(@RequestParam("name") String nn,@RequestParam("age") int age){
        System.out.println("我是第二个测试方法");
        return nn+":"+age+":"+"success";
    }

    @RequestMapping("log")
    public String getLogger(@RequestParam("logString")String logString){
        //将传输的数据转换成json对象
        JSONObject jsonObject = JSONObject.parseObject(logString);
        //添加时间戳字段
        jsonObject.put("ts",System.currentTimeMillis());
        //日志打印
        log.info(jsonObject.toString());

        //事件日志
        if ("startup".equals(jsonObject.getString("type"))) {
            //启动日志
            kafkaTemplate.send(GmallConstants.GMALL_START, jsonObject.toString());
        } else kafkaTemplate.send(GmallConstants.GMALL_EVENT, jsonObject.toString());

        log.info(logString);
        return "success";
    }
}
