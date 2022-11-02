package com.shuaib.controller;

import com.shuaib.service.IssuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/issue")
public class IssuesController {

    @Autowired
    private IssuesService issuesService;
}
