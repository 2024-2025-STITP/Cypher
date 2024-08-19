package com.example.cypherserverside.service.collect;

import com.example.cypherserverside.entity.SysData;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.NetworkIF;
import oshi.software.os.OperatingSystem;

import java.sql.Time;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

@Service
public class SysDataServer {
    public static SysData collectData() {
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

        // 遍历所有网络接口并汇总接收和发送的字节数
        for (NetworkIF net : networkIFs) {
            networkReceivedBytes += net.getBytesRecv();
            networkSentBytes += net.getBytesSent();
            sysData.getInterfaceUsage().put(net.getName(), net.getSpeed());
        }

        // 将汇总的网络数据设置到 SysData 对象中
        sysData.setNetworkReceivedBytes(networkReceivedBytes);
        sysData.setNetworkSentBytes(networkSentBytes);

        // 进程数据采集
        sysData.setProcesses(ProcessDataServer.collectProcessData());
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
}
