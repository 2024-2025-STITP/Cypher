<template>
  <div class="Memory"></div>
</template>

<script>
export default {
  name: "Memory",
  data() {
    return {
      updateTimeout: null,
      myChart: null,
      ws: null,
      data: {
        totalMemory: [],
        usedMemory: [],
        availableMemory: [],
        cachedMemory: [],
      },
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
              let value = param.value[1].toFixed(2); // 保留两位小数
              tooltipHtml += param.seriesName + ": " + value + " MB\n";
            });
            return tooltipHtml;
          },
          axisPointer: {
            animation: false,
          },
        },

        legend: {
          textStyle: {
            color: "#aaa",
          },
          data: ["内存总量", "内存使用量", "可用内存", "缓存内存"],
        },
        
        xAxis: {
          type: "time",
          splitLine: {
            show: false,
          },
          
        },
        yAxis: {
          type: "value",
          name:'内存/MB',
          nameTextStyle: {
            color: "#6EDDF1",
          },
          boundaryGap: [0, "100%"],
          splitLine: {
            show: false,
          },
          min: 0, // 初始设置为 0
          max: 15000, // 初始设置为一个大值
        },
        series: [
          {
            name: "内存总量",
            type: "line",
            showSymbol: false,
            data: this.data.totalMemory,
          },
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
        if (newData) {
          const currentTime = new Date().getTime();

          // 将数据转换为 MB 单位
          const totalMemoryMB = newData.totalMemory / (1024 * 1024);
          const usedMemoryMB = newData.usedMemory / (1024 * 1024);
          const availableMemoryMB = newData.availableMemory / (1024 * 1024);
          const cachedMemoryMB = newData.cachedMemory / (1024 * 1024);

          this.data.totalMemory.push([currentTime, totalMemoryMB]);
          this.data.usedMemory.push([currentTime, usedMemoryMB]);
          this.data.availableMemory.push([currentTime, availableMemoryMB]);
          this.data.cachedMemory.push([currentTime, cachedMemoryMB]);

          // 选择最近 4 个数据点进行展示
          const displayDataCount = 4;
          const totalMemoryToDisplay = this.data.totalMemory.slice(
            -displayDataCount
          );
          const usedMemoryToDisplay = this.data.usedMemory.slice(
            -displayDataCount
          );
          const availableMemoryToDisplay = this.data.availableMemory.slice(
            -displayDataCount
          );
          const cachedMemoryToDisplay = this.data.cachedMemory.slice(
            -displayDataCount
          );

          // 打印更新后的图表数据
          console.log("Updated chart data:", this.data);

          // 计算新的 min 和 max 值
          const allData = [
            ...this.data.totalMemory,
            ...this.data.usedMemory,
            ...this.data.availableMemory,
            ...this.data.cachedMemory,
          ];
          const values = allData.map((d) => d[1]);
          const maxValue = Math.max(...values);

          // 更新图表数据
          this.myChart.setOption({
            series: [
              { data: totalMemoryToDisplay },
              { data: usedMemoryToDisplay },
              { data: availableMemoryToDisplay },
              { data: cachedMemoryToDisplay },
            ],
            xAxis: {
              min:
                totalMemoryToDisplay.length > 0
                  ? totalMemoryToDisplay[0][0]
                  : currentTime,
              max: currentTime,
            },
            yAxis: {
              min: 0,
              max: maxValue,
            },
          });
        } else {
          console.error("New data is not defined or has invalid structure.");
        }
      }, 200);
    },

    setupWebSocket() {
      // WebSocket 服务器地址
      this.ws = new WebSocket("ws://localhost:8081/data/collect");

      this.ws.onopen = () => {
        console.log("WebSocket 连接成功");
        this.ws.send("Hello, Server!");
      };

      this.ws.onmessage = (event) => {
        try {
          const receivedData = JSON.parse(event.data);
          console.log("Received data:", receivedData); // 添加此行查看接收到的数据

          const { totalMemory, usedMemory, availableMemory, cachedMemory } =
            receivedData;

          // 生成新的数据点
          const newData = {
            totalMemory,
            usedMemory,
            availableMemory,
            cachedMemory,
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
        console.log("WebSocket 发生错误:", error);
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
.Memory {
  width: 100%;
  height: 100%;
}
</style>
