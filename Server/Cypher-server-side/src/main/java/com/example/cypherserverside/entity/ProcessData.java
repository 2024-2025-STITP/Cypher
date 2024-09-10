package com.example.cypherserverside.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Data
@Setter
@Getter
public class ProcessData {
    // Processes data
    private int pid;
    private String pName;
    private double processCpuUsage;
    private double processMemoryUsage;
}
