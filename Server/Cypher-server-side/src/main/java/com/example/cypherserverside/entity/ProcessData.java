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
    private Integer pid;
    private String pname;
    private double processCpuUsage;
    private long processMemoryUsage;
    private boolean status; //open and close
}
