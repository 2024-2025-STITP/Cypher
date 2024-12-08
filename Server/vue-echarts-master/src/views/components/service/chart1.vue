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
    setChart() {
      // 定义新的数据
      const newData = [
        {
          name: "CPU已使用",
          value: this.data.cpuUsage,
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
          value: 16 - this.data.cpuUsage,
          itemStyle: { color: "#ccc" },
          label: { show: false },
          labelLine: { show: true },
          tooltip: { show: true },
        },
      ];

      // 如果图表不存在，则初始化图表并设置选项
      if (!this.myChart) {
        this.myChart = this.$echarts(this.$el);
        let option = {
          tooltip: {},
          series: [
            {
              type: "pie",
              radius: ["50%", "80%"],
              center: ["51%", "55%"],
              data: newData,
            },
          ],
        };
        this.myChart.setOption(option);
      } else {
        // 图表已存在则更新图表数据
        this.myChart.setOption(
          {
            series: [
              {
                data: newData,
              },
            ],
          },
          false
        ); // 不合并，保持其他选项不变
      }

      this.myChart.resize(); // 调整图表尺寸
    },

    // 设置 WebSocket 连接
    setupWebSocket() {
      // 连接
      this.ws = new WebSocket("ws://localhost:8081/data/collect");

      this.ws.onopen = () => {
        console.log("WebSocket 连接成功");
        this.ws.send("Hello, Server!");
      };

      this.ws.onmessage = (event) => {
        try {
          // 解析从服务器接收到的数据
          const data = JSON.parse(event.data);
          this.data.cpuUsage = data.cpuUsage || 0;
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
      this.setChart();
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
