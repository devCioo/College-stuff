package com.tss.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    
    @Autowired
    BuildProperties buildProperties;
    
    @Value("${myparams.jdkversion}")
    String myJDKVersion;
    
    @Value("${myparams.springbootversion}")
    String springBootVersion;
    
    @Value("${application.name}")
    String applicationName;
    
    @Value("${build.version}")
    String buildVersion;
    
    @Value("${build.timestamp}")
    String buildTimestamp;
    
    @RequestMapping("/")
    public String mainView(Model model) {
        
        String artifactApp = buildProperties.getArtifact();
        String versionApp = buildProperties.getVersion();
        String springVersion = SpringVersion.getVersion();
        model.addAttribute("myJDKVersion", myJDKVersion);
        model.addAttribute("springBootVersion", springBootVersion);
        model.addAttribute("springVersion", springVersion);
        model.addAttribute("applicationName", applicationName);
        model.addAttribute("buildVersion", buildVersion);
        model.addAttribute("buildTimestamp", buildTimestamp);
        return "index.html";
    }
}