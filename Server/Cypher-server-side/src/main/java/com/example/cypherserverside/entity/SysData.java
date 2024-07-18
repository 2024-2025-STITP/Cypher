package com.example.cypherserverside.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Data
@Getter
@Setter
public class SysData {
    // CPU data
    private double cpuUsage;
    private double[] perCoreUsage;

    // Memory data
    private long totalMemory;
    private long usedMemory;
    private long availableMemory;
    private long cachedMemory;
    private long swapTotal;
    private long swapUsed;

    // Disk I/O data
    private long diskReadBytes;
    private long diskWriteBytes;
    private long diskReadOps;
    private long diskWriteOps;

    // Network data
    private long networkReceivedBytes;
    private long networkSentBytes;
    private Map<String, Long> interfaceUsage = new HashMap<>();

    // Processes
    private List<ProcessData> processes = new LinkedList<>();

    // GPU data (if applicable)
    private double gpuUsage;
    private long gpuMemoryUsed;

    // System logs (example, can be more detailed)
    private List<String> systemLogs;

    // System uptime
    private long uptime; // in seconds

}
