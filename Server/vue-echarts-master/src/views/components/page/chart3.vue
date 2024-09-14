<template>
  <div class="DiskBytes"></div>
</template>

<script>
export default {
  name: "DiskBytes",
  data() {
    return {
      myChart: null,
      ws: null, // WebSocket 连接对象
      data: {
        diskReadBytes:[],
        diskWriteBytes:[],
      }
    };
  },
 
  methods: {
    setChart() {


     let option = {
        tooltip: {
          trigger: "axis",
          formatter: function (params) {
            let tooltipHtml = "";
            params.forEach((param) => {
              let value = param.value[1].toFixed(2);
              tooltipHtml += param.seriesName + " : " + value + "MB\n";
          });
          return tooltipHtml;
          },
          axisPointer: {
            animation: false,
          },
        },
        legend: { 
          data: ["磁盘读取字节数", "磁盘写入字节数"], 
          textStyle: {
            color: "#aaa",
          }
        },
        xAxis: {
          type: "time",
          splitLine: {
            show: false,
          },
        },
        yAxis: {
          type: "value",
          boundaryGap: [0, "100%"],
          splitLine: {
            show: false,
          },
          min: 0,
          max: 1000,
        },
        series: [
          {
            name: "磁盘读取字节数",
            type: "line",
            showSymbol: false,
            data: this.data.diskReadBytes,
          },
          {
            name: "磁盘写入字节数",
            type: "line",
            showSymbol: false,
            data: this.data.diskWriteBytes,
          },
        ],
      };

      

      if (!this.myChart) this.myChart = this.$echarts(this.$el);


      this.myChart.setOption(option);

      

    },
      


    updateChart(newData) {
      // 确保数据已定义，然后将其添加到对应数组
      if(newData){
        const currentTime = new Date().getTime();

        // 将数据转换为MB单位
        const diskReadBytes = newData.diskReadBytes /(1024*1024);
        const diskWriteBytes = newData.diskReadBytes /(1024*1024);
        // 更新数据
        this.data.diskReadBytes.push([currentTime, diskReadBytes]);
        this.data.diskWriteBytes.push([currentTime, diskWriteBytes]);

        // 限制数据长度为 100 条
        if (this.data.diskReadBytes.length > 100) {
          this.data.diskReadBytes.shift();
          this.data.diskWriteBytes.shift();
        }

        // 计算新的min和max值
        const allData = [
        ...this.data.diskReadBytes,
         ...this.data.diskWriteBytes,
        ];
        const values = allData.map((d) => d[1]);
        const maxValue = Math.max(...values); 
      // 更新图表数据
      this.myChart.setOption({
        series: [
          { data: this.data.diskReadBytes },
          { data: this.data.diskWriteBytes },
        ],
        yAxis: {
          min: 0,
          max: maxValue,
        },
      });
      }
    },
    setupWebSocket() {
      // 连接到 WebSocket 服务器
      this.ws = new WebSocket('ws://localhost:8081/ws/url');

      this.ws.onopen = () => {
        console.log("WebSocket 连接成功");
        this.ws.send("Hello, Server!");
      };

      // 监听 WebSocket 消息
      this.ws.onmessage = (event) => {
        try{
          // 解析从 WebSocket 服务器接收到的数据
          const receiveData = JSON.parse(event.data);
          const{ diskReadBytes, diskWriteBytes} = receiveData;

          // 生成新的数据点
          const newData = {
            diskReadBytes,
            diskWriteBytes, 
          }
        this.updateChart(newData);
        }catch(error){
          console.error("解析 WebSocket 数据失败:", error);
        }
      };

      this.ws.onclose = () => {
        console.log("WebSocket 连接关闭");
      };

      this.ws.onerror = (error) => {
        console.error("WebSocket 发生错误:", error);
      };

    },
  },
  mounted() {
    this.setChart();
    this.setupWebSocket();
  },
};
</script>

<style lang="less" scoped>
.DiskBytes {
  width: 100%;
  height: 100%;
}
</style>
