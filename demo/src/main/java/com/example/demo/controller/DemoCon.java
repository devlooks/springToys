package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class DemoCon {

    @GetMapping("demo-mvc")
    public String demoMvc(@RequestParam("name") String name, Model model) {
        log.info("name={}", name);
        model.addAttribute("name", name);
        return "demo-tmp";
    }

    @GetMapping("demo-string")
    public @ResponseBody
    String demoString(@RequestParam("name") String name) {
        return "demo " + name;
    }

    @GetMapping("demo-returnObject")
    public @ResponseBody ReturnObject returnObj(@RequestParam("name") String name, Model model) {
        
        ReturnObject ro = new ReturnObject();
        ro.setStrTest(name);

        return ro;
    }


    static class ReturnObject {

        private String strTest;

        public String getStrTest() {
            return strTest;
        }

        public void setStrTest(String strTest) {
            this.strTest = strTest;
        }
    }

}
