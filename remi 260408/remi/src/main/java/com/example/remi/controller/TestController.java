package com.example.remi.controller;

import com.example.remi.dto.TestDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestController {

    @GetMapping("/test/chuga") // localhost:8083/test/chuga
    public String chuga() {
        return "test/chuga"; // test/chuga.html을 화면에 띄워라.
    }

    @PostMapping("/test/chugaProc")
    public void chugaProc(
            TestDTO testDTO
    ) {
        System.out.println("name : " + testDTO.getName());
        System.out.println("kor : " + testDTO.getKor());
        System.out.println("eng : " + testDTO.getEng());
        System.out.println("mat : " + testDTO.getMat());
        //System.out.println("sci : " + testDTO.getSci());
        //System.out.println("his : " + testDTO.getHis());
        //return "redirect:/test/chuga";
    }

    @GetMapping("/test/sujung") // localhost:8083/test/chuga
    public String sujung() {
        return "test/sujung"; // test/chuga.html을 화면에 띄워라.
    }

    @PostMapping("/test/sujungProc")
    public void sujungProc(
            TestDTO testDTO
    ) {
        System.out.println("id : " + testDTO.getId());
        System.out.println("name : " + testDTO.getName());
        System.out.println("kor : " + testDTO.getKor());
        System.out.println("eng : " + testDTO.getEng());
        System.out.println("mat : " + testDTO.getMat());
        //System.out.println("sci : " + testDTO.getSci());
        //System.out.println("his : " + testDTO.getHis());
        //return "redirect:/test/chuga";
    }

}
