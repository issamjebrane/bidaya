package com.bidaya.bidaya.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AminController {

    @GetMapping("")
    public String demo(){
        return "hello from admin";
    }
}
