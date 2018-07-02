package com.bccns.umsserviceweb.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bccns.umsserviceweb.api.service.SimpleService;
import com.bccns.umsserviceweb.api.vo.SimpleVO;
import com.bccns.umsserviceweb.common.controller.DefaultAPIController;
import com.bccns.umsserviceweb.common.exception.SimpleException;

@Controller
public class SimpleAPIController extends DefaultAPIController {
    private static final Logger logger = LoggerFactory.getLogger(SimpleAPIController.class);

    @Autowired
    SimpleService               simpleService;

    @RequestMapping(value = "/api/{testId}", method = RequestMethod.GET)
    public @ResponseBody
    SimpleVO simpleAPI(@PathVariable(value = "testId") String pathParam) {

        SimpleVO simpleVO = new SimpleVO();
        /*
        try {
            simpleVO = simpleService.getSimple(pathParam);
        }
        catch ( Exception e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        */
        simpleVO.setId("12121");    
        simpleVO.setTitle("title asdfasdf");
        simpleVO.setContent("content content 2121");
        return simpleVO;
    }
}
