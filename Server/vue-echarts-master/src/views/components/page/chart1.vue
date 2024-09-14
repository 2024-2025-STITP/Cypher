<template>
  <div class="CPU"></div>
</template>

<script>
export default {
  name: "CPU",
  data() {
    return {
      myChart: null,
      ws: null,
      data: {
        cpuUsage: 0,
      },
    };
  },
  methods: {
    // 初始化图表
    setChart() {
     
      let option = {
        title: { text: "CPU利用率" },
        tooltip: {},
        series: [
          {
            type: "pie",
            // 修改饼形图大小，(内圆半径，外圆半径)
            radius: ["50%", "80%"],
            center: ["51%", "55%"],
            data: [
              {
                name: "CPU已使用",
                // 后端传来的数据
                value: this.data.cpuUsage,
                // value: 60,  //测试数据
                label: {
                  show: true,
                  position: "center",
                  formatter: "{big|{d}}{small|%}\n{b}",
                  rich: {
                    big: {
                      color: "#f70",
                      fontSize: "20px",
                      fontWeight: 900,
                    },
                    small: {
                      fontSize: "10px",
                      color: "#f70",
                    },
                  },
                },
              },
              {
                name: "CPU未使用",
                // 后端传来的数据(或者直接计算得来)
                value: 16 - this.data.cpuUsage,
                // value: 40,
                itemStyle: { color: "#ccc" },
                label: { show: false },
                labelLine: { show: true },
                tooltip: { show: true },
              },
            ],
          },
        ],
      };
      if (!this.myChart) this.myChart = this.$echarts(this.$el);

      this.myChart.clear();
      this.myChart.resize();
      this.myChart.setOption(option);
    },
    // 设置 WebSocket 连接
    setupWebSocket() {
      // 连接
      this.ws = new WebSocket("ws://localhost:8081/ws/url");

      this.ws.onopen = () => {
        console.log("WebSocket 连接成功");
        this.ws.send("Hello, Server!");
      };

      this.ws.onmessage = (event) => {
        try {
          // 解析从服务器接收到的数据
          const data = JSON.parse(event.data);
          this.data.cpuUsage = data.cpuUsage || 0 ;
          // 更新图表
          this.setChart();
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
    this.setChart(),
    this.setupWebSocket();
  },
};
</script>

<style lang="less" scoped>
.CPU {
  width: 100%;
  height: 100%;
}
</style>
