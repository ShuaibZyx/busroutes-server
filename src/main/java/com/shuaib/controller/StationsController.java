package com.shuaib.controller;

import com.shuaib.service.StationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/station")
public class StationsController {

    @Autowired
    private StationsService stationsService;
}
