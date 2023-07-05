package com.tss.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebSocketController {
    
    @RequestMapping("/webSocketJSON")
    public String webSocketJson(Model model) {
        return "webSocketJSON.html";
    }
}
