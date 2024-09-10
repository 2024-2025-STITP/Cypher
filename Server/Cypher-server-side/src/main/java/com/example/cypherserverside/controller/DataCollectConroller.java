package com.example.cypherserverside.controller;

import com.example.cypherserverside.pojo.SysData;
import com.example.cypherserverside.service.impl.SysDataServerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class DataCollectConroller {

    @Autowired
    private SysDataServerImpl sysDataServer;

    @GetMapping("/data/collect")
    public SysData GetSysDataByCode(String ip) {
        return sysDataServer.collectData();
    }
}
