package com.bccns.umsserviceweb.web.controller;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class SimpleController {

    private static final Logger logger = LoggerFactory.getLogger(SimpleController.class);

    @RequestMapping(value = "/srv/simpleView1", method = RequestMethod.GET)
    public String getSimpleView1(Model model) {
        return "/common/simpleView1";
    }
    
    @RequestMapping(value = "/srv/simpleView2", method = RequestMethod.GET)
    public String getSimpleView2(Model model) {
        return "/common/simpleView2";
    }
    @RequestMapping(value = "/srv/simpleView3", method = RequestMethod.GET)
    public String getSimpleView3(Model model) {
        return "/common/simpleView3";
    }
    @RequestMapping(value = "/srv/simpleView4", method = RequestMethod.GET)
    public String getSimpleView4(Model model) {
        return "/common/simpleView4";
    }

    
    
//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public String home(Locale locale, Model model) {
//        logger.info("Welcome home! The client locale is {}.", locale);
//
//        Date date = new Date();
//        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG,
//                locale);
//
//        String formattedDate = dateFormat.format(date);
//
//        model.addAttribute("message", formattedDate);
//
//        return "/common/main";
//    }
//    
//    @RequestMapping(value = "/main", method = RequestMethod.GET)
//    public String message(Locale locale, Model model) {
//        logger.info("Welcome home! The client locale is {}.", locale);
//
//        Date date = new Date();
//        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG,
//                locale);
//
//        String formattedDate = dateFormat.format(date);
//
//        model.addAttribute("message", formattedDate);
//
//        return "/common/main";
//    }

    @RequestMapping(value = "/sitemesh/1", method = RequestMethod.GET)
    public String sitemeth1(Locale locale, Model model) {
        return "/sitemesh/sitemesh1";
    }

    @RequestMapping(value = "/sitemesh/2", method = RequestMethod.GET)
    public String sitemeth2(Locale locale, Model model) {
        return "/sitemesh/sitemesh2";
    }

    @RequestMapping(value = "/sitemesh/3", method = RequestMethod.GET)
    public String sitemeth3(Locale locale, Model model) {
        return "/sitemesh/sitemesh3";
    }

    @RequestMapping(value = "/excelDownload", method = RequestMethod.GET)
    public String excelDownload(Locale locale, Map<String, Object> model) {

        List<Object> dataList = null;
        List<Object> label = null;
        List<Object> lableKey = null;

        Map<String, String> data = new HashMap();
        data.put("data-1", "test1");
        data.put("data-2", "test2");
        data.put("data-3", "test3");
        data.put("data-4", "test4");
        data.put("data-5", "test5");

        Map<String, String> data1 = new HashMap();
        data1.put("data-1", "test-1");
        data1.put("data-2", "test-2");
        data1.put("data-3", "test-3");
        data1.put("data-4", "test-4");
        data1.put("data-5", "test-5");

        dataList = new ArrayList();
        dataList.add(data);
        dataList.add(data1);

        label = new ArrayList();
        label.add("data 1");
        label.add("data 2");
        label.add("data 3");
        label.add("data 5");

        lableKey = new ArrayList();
        lableKey.add("data-1");
        lableKey.add("data-2");
        lableKey.add("data-3");
        lableKey.add("data-5");

        model.put("dataList", dataList);
        model.put("label", label);
        model.put("labelKey", lableKey);
        model.put("viewName", "testExcelview");

        logger.info("view name : " + (String) model.get("viewName"));

        // Bean View 는 / 가 없다. 구현하고 등록한 bean id name 을 리턴하면 된다.
        return "excelDownload";
    }
}
