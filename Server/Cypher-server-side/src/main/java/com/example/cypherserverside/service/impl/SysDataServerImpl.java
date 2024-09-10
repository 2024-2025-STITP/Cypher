package com.example.cypherserverside.service.impl;

import com.example.cypherserverside.pojo.ProcessData;
import com.example.cypherserverside.pojo.SysData;
import com.example.cypherserverside.service.collect.SysDataServer;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.NetworkIF;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class SysDataServerImpl implements SysDataServer {
    public SysData collectData() {
        SysData sysData = new SysData();

        SystemInfo si = new SystemInfo();
        CentralProcessor processor = si.getHardware().getProcessor();
        GlobalMemory memory = si.getHardware().getMemory();
        HWDiskStore[] diskStores = si.getHardware().getDiskStores().toArray(new HWDiskStore[0]);
        NetworkIF[] networkIFs = si.getHardware().getNetworkIFs().toArray(new NetworkIF[0]);
        OperatingSystem os = si.getOperatingSystem();

        // CPU 数据采集
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        sysData.setCpuUsage(processor.getSystemCpuLoad(1000) * 100);

        // 内存数据采集
        sysData.setTotalMemory(memory.getTotal());
        sysData.setUsedMemory(memory.getTotal() - memory.getAvailable());
        sysData.setAvailableMemory(memory.getAvailable());
        sysData.setCachedMemory(memory.getVirtualMemory().getSwapUsed());
        sysData.setSwapTotal(memory.getVirtualMemory().getSwapTotal());
        sysData.setSwapUsed(memory.getVirtualMemory().getSwapUsed());

        long diskReadBytes = 0;
        long diskWriteBytes = 0;
        long diskReadOps = 0;
        long diskWriteOps = 0;

        //磁盘存储设备
        for (HWDiskStore disk : diskStores) {
            diskReadBytes += disk.getReadBytes();
            diskWriteBytes += disk.getWriteBytes();
            diskReadOps += disk.getReads();
            diskWriteOps += disk.getWrites();
        }

        // 将汇总的磁盘I/O数据设置到 SysData 对象中
        sysData.setDiskReadBytes(diskReadBytes);
        sysData.setDiskWriteBytes(diskWriteBytes);
        sysData.setDiskReadOps(diskReadOps);
        sysData.setDiskWriteOps(diskWriteOps);

        // 网络数据采集
        long networkReceivedBytes = 0;
        long networkSentBytes = 0;

//        // 遍历所有网络接口并汇总接收和发送的字节数
//        for (NetworkIF net : networkIFs) {
//            networkReceivedBytes += net.getBytesRecv();
//            networkSentBytes += net.getBytesSent();
//            sysData.getInterfaceUsage().put(net.getName(), net.getSpeed());
//        }

        // 将汇总的网络数据设置到 SysData 对象中
        sysData.setNetworkReceivedBytes(networkReceivedBytes);
        sysData.setNetworkSentBytes(networkSentBytes);

        // 进程数据采集
        sysData.setProcesses(collectProcessData());
        sysData.setUptime(LocalTime.now());

        // GPU 数据采集
        sysData.setGpuUsage(0);
        sysData.setGpuMemoryUsed(0);

        List<String> logs = new LinkedList<>();
        logs.add("系统启动");
        logs.add("系统检查通过");
        sysData.setSystemLogs(logs);

        return sysData;
    }


    public String toJSONString(SysData sysData) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cpuUsage", sysData.getCpuUsage());
        jsonObject.put("totalMemory", sysData.getTotalMemory());
        jsonObject.put("usedMemory", sysData.getUsedMemory());
        jsonObject.put("availableMemory", sysData.getAvailableMemory());
        jsonObject.put("cachedMemory", sysData.getCachedMemory());
        jsonObject.put("diskReadBytes", sysData.getDiskReadBytes());
        jsonObject.put("diskWriteBytes", sysData.getDiskWriteBytes());
        jsonObject.put("diskReadOps", sysData.getDiskReadOps());
        jsonObject.put("diskWriteOps", sysData.getDiskWriteOps());
        jsonObject.put("networkReceivedBytes", sysData.getNetworkReceivedBytes());
        jsonObject.put("networkSentBytes", sysData.getNetworkSentBytes());
        jsonObject.put("uptime", LocalTime.now());
        jsonObject.put("gpuUsage", sysData.getGpuUsage());
        jsonObject.put("gpuMemoryUsed", sysData.getGpuMemoryUsed());
        jsonObject.put("processes",sysData.getProcesses());
        return jsonObject.toString();
    }

    private static List<ProcessData> collectProcessData() {
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
                    && !process.getName().toLowerCase().contains("system")
                    && !process.getUser().toLowerCase().contains("system")
                    && !process.getUser().toLowerCase().contains("unknown")
                    && !process.getName().toLowerCase().contains("service")
                    && !process.getName().toLowerCase().contains("host")
                    && !process.getName().toLowerCase().contains("explorer"))
                processList.add(getProcessData(process));
        }

        return processList;
    }

    private static ProcessData getProcessData(OSProcess process)
    {
        ProcessData pd = new ProcessData();
        pd.setPid(process.getProcessID());
        pd.setPName(process.getName());
        pd.setProcessCpuUsage(process.getProcessCpuLoadCumulative() * 100);
        pd.setProcessMemoryUsage(process.getResidentSetSize());

        return pd;
    }
}
