package com.example.cypherserverside.service.collect;

import com.example.cypherserverside.entity.ProcessData;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

import java.util.LinkedList;
import java.util.List;


@Service
public class ProcessDataServer {
    public static List<ProcessData> collectProcessData() {
        // 创建一个 SystemInfo 对象来访问操作系统和硬件信息
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();

        // 获取当前系统中运行的所有进程
        List<ProcessData> processList = new LinkedList<>();
        List<OSProcess> processes = os.getProcesses();
        // 收集相关数据
        for (OSProcess process : processes) {
            //选择前台进程
            if(process.getState().equals(OSProcess.State.RUNNING)
                    && !process.getName().equalsIgnoreCase("system")
                    && !process.getUser().equalsIgnoreCase("system")
                    && !process.getUser().equalsIgnoreCase("unknown")
                    && !process.getName().toLowerCase().contains("service")
                    && !process.getName().toLowerCase().contains("host")
                    && !process.getName().toLowerCase().contains("explorer"))
                processList.add(getProcessData(process));
        }

        return processList;
    }

    public static ProcessData getProcessData(OSProcess process)
    {
        ProcessData pd = new ProcessData();
        pd.setPid(process.getProcessID());
        pd.setPName(process.getName());
        pd.setProcessCpuUsage(process.getProcessCpuLoadCumulative() * 100);
        pd.setProcessMemoryUsage(process.getResidentSetSize());

        return pd;
    }

}
