<template>
  <div class="Memory"></div>
</template>

<script>
export default {
  name: "Memory",
  data() {
    return {
      myChart: null,
      ws: null,
      data: {
        totalMemory: [],
        usedMemory: [],
        availableMemory: [],
        cachedMemory: [],
        swapTotal: [],
        swapUsed: []
      },
    };
  },
  methods: {
    setChart() {
      let option = {
        title: {
          text: "内存使用量动态监控",
        },
        tooltip: {
          trigger: "axis",
          formatter: function (params) {
            params = params[0];
            var date = new Date(params.name);
            return (
              date.getHours() +
              ":" +
              ("0" + date.getMinutes()).slice(-2) +
              ":" +
              ("0" + date.getSeconds()).slice(-2) +
              " : " +
              "Total: " + params.value[0] + " MB\n" +
              "Used: " + params.value[1] + " MB\n" +
              "Available: " + params.value[2] + " MB\n" +
              "Cached: " + params.value[3] + " MB\n" +
              "Swap Total: " + params.value[4] + " MB\n" +
              "Swap Used: " + params.value[5] + " MB"
            );
          },
          axisPointer: {
            animation: false,
          },
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
          min: 0, // 设置 y 轴的最小值为 0
          max: 300, // 设置 y 轴的最大值为 300 MB
        },
        series: [
          {
            name: "内存使用量",
            type: "line",
            showSymbol: false,
            data: this.data.usedMemory,
          },
          {
            name: "可用内存",
            type: "line",
            showSymbol: false,
            data: this.data.availableMemory,
          },
          {
            name: "缓存内存",
            type: "line",
            showSymbol: false,
            data: this.data.cachedMemory,
          },
          {
            name: "Swap Total",
            type: "line",
            showSymbol: false,
            data: this.data.swapTotal,
          },
          {
            name: "Swap Used",
            type: "line",
            showSymbol: false,
            data: this.data.swapUsed,
          },
        ],
      };

      if (!this.myChart) this.myChart = this.$echarts(this.$el);
      this.myChart.clear();
      this.myChart.resize();
      this.myChart.setOption(option);
    },

    updateChart(newData) {
      // 更新图表数据
      for (let key in this.data) {
        this.data[key].shift();  // 移除旧数据
        this.data[key].push(newData[key]);  // 添加新数据
      }
      
      this.myChart.setOption({
        series: [
          { data: this.data.usedMemory },
          { data: this.data.availableMemory },
          { data: this.data.cachedMemory },
          { data: this.data.swapTotal },
          { data: this.data.swapUsed },
        ],
      });
    },

    setupWebSocket() {
      // WebSocket 服务器地址
      this.ws = new WebSocket("ws://localhost:8080/ws/url");

      this.ws.onopen = () => {
        console.log("WebSocket 连接成功");
        socket.send("Hello, Server!");
      };

      this.ws.onmessage = (event) => {
        try {
          // 解析从 WebSocket 服务器接收到的数据
          const receivedData = JSON.parse(event.data);
          const { timestamp, totalMemory, usedMemory, availableMemory, cachedMemory, swapTotal, swapUsed } = receivedData;

          // 生成新的数据点
          const newData = {
            name: new Date(timestamp).toString(),
            value: [totalMemory, usedMemory, availableMemory, cachedMemory, swapTotal, swapUsed],
          };

          this.updateChart(newData);
        } catch (error) {
          console.error("解析 WebSocket 数据失败:", error);
        }
      };

      this.ws.onclose = () => {
        console.log("WebSocket 连接关闭");
      };
      
      this.ws.onerror = (error) =>{
        console.log('WebSocket 发生错误:',error)
      }
    },
  },
  mounted() {
    this.setChart();
    this.setupWebSocket();
  },
};
</script>

<style lang="less" scoped>
.Memory {
  width: 100%;
  height: 100%;
}
</style>
