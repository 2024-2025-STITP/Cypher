<template>
  <div class="DiskBytes"></div>
</template>

<script>
export default {
  name: "DiskBytes",
  data() {
    return {
      updateTimeout: null,
      myChart: null,
      ws: null, // WebSocket 连接对象
      data: {
        diskReadBytes: [],
        diskWriteBytes: [],
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
          name:'字节数/MB',
          nameTextStyle: {
            color: "#6EDDF1",
          },
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
      // 移除之前的延迟调用
      if (this.updateTimeout) {
        clearTimeout(this.updateTimeout);
      }
      // 设置延迟调用
      this.updateTimeout = setTimeout(() => {
        // 确保数据已定义，然后将其添加到对应数组
        if (newData) {
          const currentTime = new Date().getTime();

          // 将数据转换为MB单位
          const diskReadBytes = newData.diskReadBytes / (1024 * 1024);
          const diskWriteBytes = newData.diskWriteBytes / (1024 * 1024);
          // 更新数据
          this.data.diskReadBytes.push([currentTime, diskReadBytes]);
          this.data.diskWriteBytes.push([currentTime, diskWriteBytes]);

          // 选择最近 4 个数据点进行展示
          const displayDataCount = 4;
          const diskReadBytesToDisplay = this.data.diskReadBytes.slice(
            -displayDataCount
          );
          const diskWriteBytesToDisplay = this.data.diskWriteBytes.slice(
            -displayDataCount
          );

          // 打印调试信息
          console.log("Displaying Read Bytes:", diskReadBytesToDisplay);
          console.log("Displaying Write Bytes:", diskWriteBytesToDisplay);

          // 计算新的 min 和 max 值
          const allData = [
            ...diskReadBytesToDisplay,
            ...diskWriteBytesToDisplay,
          ];
          const values = allData.map((d) => d[1]);
          const maxValue = Math.max(...values);

          // 更新图表数据
          this.myChart.setOption({
            series: [
              { data: diskReadBytesToDisplay },
              { data: diskWriteBytesToDisplay },
            ],
            xAxis: {
              min:
                diskReadBytesToDisplay.length > 0
                  ? diskReadBytesToDisplay[0][0]
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
      }, 100);
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
          const { diskReadBytes, diskWriteBytes } = receiveData;

          // 生成新的数据点
          const newData = {
            diskReadBytes,
            diskWriteBytes,
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
.DiskBytes {
  width: 100%;
  height: 100%;
}
</style>
