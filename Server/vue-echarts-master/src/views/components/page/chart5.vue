<template>
  <div class="Network"></div>
</template>

<script>
export default {
  name: "Network",
  data() {
    return {
      myChart: null,
      ws:null,
      data:{
        networkReceivedBytes:[],
        networkSentBytes:[],
      }
    };
  },
  methods: {
    setChart() {
      

      // 配置图表选项
      let option = {
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
          data: ["网络接收字节数", "网络发送字节数"],
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
      // 确保数据已定义，然后将其添加到对应数组
      if(newData){
        const currentTime = new Date().getTime();
        const { networkReceivedBytes, networkSentBytes } = newData;
        // 更新数据
        this.data.networkReceivedBytes.push([currentTime, networkReceivedBytes]);
        this.data.networkSentBytes.push([currentTime, networkSentBytes]);

        // 限制数据长度为 100 条
        if (this.data.networkReceivedBytes.length > 100) {
          this.data.networkReceivedBytes.shift();
          this.data.networkSentBytes.shift();
        }

        // 计算新的min和max值
        const allData = [
        ...this.data.networkReceivedBytes,
         ...this.data.networkSentBytes,
        ];
        const values = allData.map((d) => d[1]);
        const maxValue = Math.max(...values); 
      // 更新图表数据
      this.myChart.setOption({
        series: [
          { data: this.data.networkReceivedBytes },
          { data: this.data.networkSentBytes },
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
          const{ networkReceivedBytes, networkSentBytes} = receiveData;

          // 生成新的数据点
          const newData = {
            networkReceivedBytes,
            networkSentBytes,
          }
        this.updateChart(newData);
        }catch(error){
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
