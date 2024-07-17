package com.example.cypherserverside.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class ProcessData {
    // Processes data
    private Integer pid;
    private String pname;
    private Map<String, Double> processCpuUsage = new HashMap<>();
    private Map<String, Long> processMemoryUsage = new HashMap<>();
    private Boolean status; //open and close
}
