package com.example.cypherserverside.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
