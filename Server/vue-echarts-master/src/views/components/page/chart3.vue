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


      // 自动生成测试实例

      // function generateRandomDiskUsage() {
      //   now = new Date(+now + oneSecond);
      //   readBytes = Math.max(0, readBytes + Math.random() * 10000 - 5000);
      //   writeBytes = Math.max(0, writeBytes + Math.random() * 10000 - 5000);
      //   return {
      //     name: now.toString(),
      //     value: [now, Math.round(readBytes), Math.round(writeBytes)],
      //   };
      // }

      // let data = [];
      // let now = new Date();
      // let oneSecond = 1000;
      // let readBytes = Math.random() * 100000;
      // let writeBytes = Math.random() * 100000;

      // // 时间轴长度设置为三分钟
      // for (var i = 0; i < 180; i++) {
      //   data.push(generateRandomDiskUsage());
      // }

     let option = {
        title: {
          text: "磁盘读取和写入动态监控",
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
              "  读取: " +
              params[0].value[1] +
              " Bytes" +
              " | 写入: " +
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

      this.myChart.clear();
      this.myChart.resize();
      this.myChart.setOption(option);

      // 动态更新数据(自动生成时使用)
      // setInterval(() => {
      //   for (var i = 0; i < 5; i++) {
      //     data.shift();
      //     data.push(generateRandomDiskUsage());
      //   }
      //   this.myChart.setOption({
      //     series: [
      //       {
      //         name: "磁盘读取字节数",
      //         data: data.map((item) => [item.value[0], item.value[1]]),
      //       },
      //       {
      //         name: "磁盘写入字节数",
      //         data: data.map((item) => [item.value[0], item.value[2]]),
      //       },
      //     ],
      //   });
      // }, 1000);

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
          const{ timestamp, diskReadBytes, diskWriteBytes} = receiveData;

          // 生成新的数据点
          const newData = {
            name: new Date(timestamp).toString(),
            value: [diskReadBytes,diskWriteBytes]
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


    updateChart(newData) {
       // 更新图表数据
       for (let key in this.data) {
        this.data[key].shift();  // 移除旧数据
        this.data[key].push(newData[key]);  // 添加新数据
      }
      
      this.myChart.setOption({
        series: [
          { data: this.data.diskReadBytes },
          { data: this.data.diskWriteBytes },
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
.DiskBytes {
  width: 100%;
  height: 100%;
}
</style>
