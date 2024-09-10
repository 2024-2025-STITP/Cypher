<template>
    <div class="GPU"></div>
</template>

<script>
export default {
    name: 'GPU',
    data() {
        return {
            myChart:null,
            ws:null,
            data:{
              gpuUsage: 0,
              gpuMemoryUsed: 0,
            }
        }
    },
    methods: {
        setChart() {
            let option = {
        title: { text: "GPU利用率" },

        tooltip: {},
        series: [
          {
            type: "pie",
            // 修改饼形图大小，(内圆半径，外圆半径)
            radius: ['50%', '80%'],
            center:['51%','55%'],
            data: [
              {
                name: "GPU已使用",
                // 后端传来的数据
                // value: this.data.gpuUsage
                value: 1200,  //测试数据
                label: {
                  show: true,
                  position: "center",
                  // {d}百分比 {big|内容} 使用样式
                  formatter: "{big|{d}}{small|%}\n{b}",
                  // 定义样式（富文本）
                  rich: {
                    big: {
                      color: "#f70",
                      fontSize: "20px",
                      fontWeight: 900,
                    },
                    small: {
                        fontSize:'10px',
                      color: "#f70",
                    },
                  },
                },
              },
              {
                name: "GPU未使用",
                // 后端传来的数据（或者直接计算得到）
                // value: totalGpu - this.data.gpuUsage
                value: 360, //测试数据
                // 样式灰色
                itemStyle: { color: "#ccc" },
                // 图形上的文字不显示
                label: { show: false },
                labelLine:{
                    show:true,
                },
                // 提示不显示
                tooltip: { show: true },
              },
            ],
          },
        ],
      };
            if (!this.myChart) this.myChart = this.$echarts(this.$el);

            this.myChart.clear();
            this.myChart.resize()
            this.myChart.setOption(option);
        },

        // 设置 WebSocket 连接
    setupWebSocket() {
      // 连接
      this.ws = new WebSocket("ws://localhost:8080/ws/url");
      this.ws.onopen = () => {
        console.log("WebSocket 连接成功");
        socket.send("Hello, Server!");
      };
      this.ws.onmessage = (event) => {
        try {
          // 解析从服务器接收到的数据
          const data = JSON.parse(event.data);
          this.chartData = {
            gpuUsage: data.gpuUsage || 0,
          };
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
}
</script>

<style lang="less" scoped>
.GPU {
    height: 100%;
    width: 100%;
}
</style>