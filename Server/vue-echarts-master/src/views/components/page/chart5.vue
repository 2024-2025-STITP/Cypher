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
      // // 初始化变量
      // let now = new Date();
      // const oneSecond = 1000;
      // let readBytes = Math.random() * 100000;
      // let writeBytes = Math.random() * 100000;

      // // 生成随机磁盘使用数据
      // function generateRandomDiskUsage() {
      //   now = new Date(+now + oneSecond);
      //   readBytes = Math.max(0, readBytes + Math.random() * 10000 - 5000);
      //   writeBytes = Math.max(0, writeBytes + Math.random() * 10000 - 5000);
      //   return {
      //     name: now.toString(),
      //     value: [now, Math.round(readBytes), Math.round(writeBytes)],
      //   };
      // }

      // // 初始化数据
      // let data = [];
      // for (let i = 0; i < 180; i++) {
      //   data.push(generateRandomDiskUsage());
      // }

      // 配置图表选项
      let option = {
        title: {
          text: "网络接收/发送动态监控",
        },
        tooltip: {
          trigger: "axis",
          formatter: function (params) {
            var date = new Date(params[0].name);
            return (
              date.getHours() +
              ":"+
              ("0" + date.getMinutes()).slice(-2) +
              ":" +
              ("0" + date.getSeconds()).slice(-2) +
              " : " +
              "接收: " +
              params[0].value[1] +
              " Bytes" +
              " | 发送: " +
              params[0].value[2] +
              " Bytes"
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
          min: 0,
          max: 200000,
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

      this.myChart.clear();
      this.myChart.resize();
      this.myChart.setOption(option);

      // // 动态更新数据
      // setInterval(() => {
      //   // 生成新的数据
      //   const newData = generateRandomDiskUsage();

      //   // 删除最旧的数据
      //   data.shift();

      //   // 添加新数据
      //   data.push(newData);

      //   // 更新图表
      //   this.myChart.setOption({
      //     series: [
      //       {
      //         name: "网络接受字节数",
      //         data: data.map((item) => [item.value[0], item.value[1]]),
      //       },
      //       {
      //         name: "网络发送字节数",
      //         data: data.map((item) => [item.value[0], item.value[2]]),
      //       },
      //     ],
      //   });
      // }, oneSecond);


    },

    setupWebSocket() {
      // 连接到 WebSocket 服务器
      this.ws = new WebSocket('ws://localhost:8080/ws/url');

      this.ws.onopen = () => {
        console.log("WebSocket 连接成功");
        socket.send("Hello, Server!");
      };

      // 监听 WebSocket 消息
      this.ws.onmessage = (event) => {
        try{
          // 解析从 WebSocket 服务器接收到的数据
          const receiveData = JSON.parse(event.data);
          const{ timestamp, networkReceivedBytes, networkSentBytes} = receiveData;

          // 生成新的数据点
          const newData = {
            name: new Date(timestamp).toString(),
            value: [networkReceivedBytes,networkSentBytes]
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


    updateChart(newData) {
       // 更新图表数据
       for (let key in this.data) {
        this.data[key].shift();  // 移除旧数据
        this.data[key].push(newData[key]);  // 添加新数据
      }
      
      this.myChart.setOption({
        series: [
          { data: this.data.networkReceivedBytes },
          { data: this.data.networkSentBytes },
        ],
      });
    }



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
