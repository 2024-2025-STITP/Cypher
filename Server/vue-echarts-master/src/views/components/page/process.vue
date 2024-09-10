<template>
    <div class="table">
      <el-table :data="processes" border >
        <el-table-column prop="pid" label="进程号" width="100">
        </el-table-column>
        <el-table-column prop="pname" label="进程名" width="100">
        </el-table-column>
        <el-table-column prop="cpuUsage" label="CPU占用率 (%)" width="150">
          <template #default="scope">
            {{ scope.row.cpuUsage.toFixed(2) }}
          </template>
        </el-table-column>
        <el-table-column prop="memoryUsage" label="内存占用 (MB)" width="150">
          <template #default="scope">
            {{ (scope.row.memoryUsage / 1024).toFixed(2) }} <!-- Assuming memoryUsage is in KB -->
          </template>
        </el-table-column>
      </el-table>
  
    </div>
  </template>
  
  <script setup>
import { ref } from 'vue';

const processes = ref([]);

const ws = new WebSocket('ws://localhost:8080/ws/url'); 

ws.onopen = () => {
  console.log('WebSocket 连接成功');
  socket.send("Hello, Server!");
};

ws.onmessage = (event) => {
  const data = JSON.parse(event.data);
  // 处理后端数据
  processes.value = data.map(process => ({
    pid: process.pid,
    pname: process.pName,
    cpuUsage: process.processCpuUsage,
    memoryUsage: process.processMemoryUsage
  }));
};

ws.onclose = () => {
  console.log('WebSocket 连接关闭');
};



ws.onerror = (error) => {
  console.error('WebSocket 发生错误:', error);
};


</script>
  
  <style scoped>
  .table {
    padding-top: 5%;
  }
  
  </style>
  