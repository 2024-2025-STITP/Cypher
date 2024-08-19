package com.example.cypherserverside.controller;

import com.example.cypherserverside.entity.SysData;
import com.example.cypherserverside.service.collect.SysDataServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataCollectConroller {

    @Autowired
    private SysDataServer sysDataServer;

    @GetMapping("/data/collect")
    public SysData GetSysData() {
        return sysDataServer.collectData();
    }
}
