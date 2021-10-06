package com.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PagesController {

    @RequestMapping(value = {"login", "not-found"})
    public String common() {
        return "/index.html";
    }

    @RequestMapping(value = {"charge", "change-pass"})
    public String simple() {
        return "/index.html";
    }

    @RequestMapping(value = {"management/**"})
    public String crud() {
        return "/index.html";
    }

}
