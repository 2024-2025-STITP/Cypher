package com.example.cypherserverside.service.collect;

import com.example.cypherserverside.pojo.SysData;

public interface SysDataServer {
    public SysData collectData();
    public String toJSONString(SysData sysData);
}
