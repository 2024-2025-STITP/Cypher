<template>
  <div class="Network"></div>
</template>

<script>
export default {
  name: "Network",
  data() {
    return {
      updateTimeout: null,
      myChart: null,
      ws: null,
      data: {
        networkReceivedBytes: [],
        networkSentBytes: [],
      },
    };
  },
  methods: {
    setChart() {
      // 配置图表选项
      let option = {
        animation: false,
        tooltip: {
          trigger: "axis",
          formatter: function (params) {
            let tooltipHtml = "";
            params.forEach((param) => {
              let value = param.value[1].toFixed(2);
              tooltipHtml += param.seriesName + " : " + value + "KB\n";
            });
            return tooltipHtml;
          },
          axisPointer: {
            animation: false,
          },
        },
        legend: {
          data: ["网络接收字节数", "网络发送字节数"],
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
          name:'字节数/KB',
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
            name: "网络接收字节数",
            type: "line",
            showSymbol: false,
            data: this.data.networkReceivedBytes,
          },
          {
            name: "网络发送字节数",
            type: "line",
            showSymbol: false,
            data: this.data.networkSentBytes,
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
        const { networkReceivedBytes, networkSentBytes } = newData;
        // 更新数据
        this.data.networkReceivedBytes.push([
          currentTime,
          networkReceivedBytes,
        ]);
        this.data.networkSentBytes.push([currentTime, networkSentBytes]);

        // 选择最近 4 个数据点进行展示
        const displayDataCount = 4;
        const networkReceivedBytesToDisplay = this.data.networkReceivedBytes.slice(
          -displayDataCount
        );
        const networkSentBytesToDisplay = this.data.networkSentBytes.slice(
          -displayDataCount
        );
        // 计算新的min和max值
        const allData = [...networkReceivedBytesToDisplay,...networkSentBytesToDisplay];
        const values = allData.map((d) => d[1]);
        const maxValue = Math.max(...values);

        // 更新图表数据
        this.myChart.setOption({
          series: [
            { data: networkReceivedBytesToDisplay },
            { data: networkSentBytesToDisplay },
          ],
          xAxis: {
            min: networkReceivedBytesToDisplay.length > 0 ? networkReceivedBytesToDisplay[0][0] : currentTime ,
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
          const { networkReceivedBytes, networkSentBytes } = receiveData;

          // 生成新的数据点
          const newData = {
            networkReceivedBytes,
            networkSentBytes,
          };
          this.updateChart(newData);
        } catch (error) {
          console.error("解析 WebSocket 数据失败:", error);
        }
      };

      this.ws.onerror = (error) => {
        console.error("WebSocket 发生错误:", error);
      };

      this.ws.onclose = () => {
        console.log("WebSocket 连接关闭");
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
.Network {
  width: 100%;
  height: 100%;
}
</style>
