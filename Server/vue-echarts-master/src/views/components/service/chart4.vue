<template>
  <div class="DiskOps"></div>
</template>

<script>
export default {
  name: "DiskOPS",
  data() {
    return {
      updateTimeout: null,
      myChart: null,
      ws: null,
      data:{
        diskReadOps:[],
        diskWriteOps:[],
      }
    };
  },
  methods: {
    setChart() {
      let option = {
        animation: false,
        tooltip: {
          trigger: "axis",
          formatter: function (params) {
            let tooltipHtml = "";
            params.forEach((param) => {
              let value = param.value[1].toFixed(2);
              tooltipHtml += param.seriesName + " : " + value + "次\n";
          });
          return tooltipHtml;
          },
          axisPointer: {
            animation: false,
          },
        },
        legend: { 
          data: ["磁盘读取操作数", "磁盘写入操作数"], 
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
          name:'操作数/次',
          nameTextStyle: {
            color: "#6EDDF1",
          },
          boundaryGap: [0, "100%"],
          splitLine: {
            show: false,
          },
          min: 0,
          max: 2000000,
        },
        series: [
          {
            name: "磁盘读取操作数",
            type: "line",
            showSymbol: false,
            data: this.data.diskReadOps,
          },
          {
            name: "磁盘写入操作数",
            type: "line",
            showSymbol: false,
            data: this.data.diskWriteOps,
          },
        ],
      };

      if (!this.myChart) this.myChart = this.$echarts(this.$el);

      this.myChart.setOption(option);

      
    },
  
    updateChart(newData) {
       // 移除之前的延迟调用
       if (this.updateTimeout) {
        clearTimeout(this.updateTimeout);
      }
      // 设置延迟调用
      this.updateTimeout = setTimeout(() => {
      // 确保数据已定义，然后将其添加到对应数组
      if(newData){
        const currentTime = new Date().getTime();
        const { diskReadOps, diskWriteOps } = newData;
        // 更新数据
        this.data.diskReadOps.push([currentTime, diskReadOps]);
        this.data.diskWriteOps.push([currentTime, diskWriteOps]);

         // 选择最近 4 个数据点进行展示
         const displayDataCount = 4;
         const diskReadOpsToDisplay = this.data.diskReadOps.slice(-displayDataCount);
         const diskWriteOpsToDisplay = this.data.diskWriteOps.slice(-displayDataCount);
        
        //  计算新的min和max值
        const allData = [...diskReadOpsToDisplay,...diskWriteOpsToDisplay];
        const values = allData.map((d) => d[1]);
        const maxValue = Math.max(...values);

        
      // 更新图表数据
      this.myChart.setOption({
        series: [
          { data: diskReadOpsToDisplay },
          { data: diskWriteOpsToDisplay },
        ],
        xAxis: {
          min: diskReadOpsToDisplay.length > 0 ? diskReadOpsToDisplay[0][0] : currentTime,
          max: currentTime,
        },
        yAxis: {
          min: 0,
          max: maxValue,
        },
      });
      }
    },100);
    },

  setupWebSocket() {
      // 连接到 WebSocket 服务器
      this.ws = new WebSocket("ws://localhost:8081/data/collect");

      this.ws.onopen = () => {
        console.log("WebSocket 连接成功");
        this.ws.send("Hello, Server!");
      };

      // 监听 WebSocket 消息
      this.ws.onmessage = (event) => {
        try {
          // 解析从 WebSocket 服务器接收到的数据
          const receiveData = JSON.parse(event.data);
          const { diskReadOps, diskWriteOps } = receiveData;

          // 生成新的数据点
          const newData = {
            diskReadOps,
            diskWriteOps,
          };
          this.updateChart(newData);
        } catch (error) {
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
.DiskOps {
  height: 100%;
  width: 100%;
}
</style>
