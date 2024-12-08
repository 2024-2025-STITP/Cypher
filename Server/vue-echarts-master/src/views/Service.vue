<template>
  <div class="service">
    <div class="title-box">
      <span>云计算监控平台</span>
    </div>
    <div class="nav">
      <div class="nav-left">
        <div class="buttonbox">
          <button @click="$router.push('/')" @mouseenter="hover1 = true" @mouseleave="hover1 = false" :class="{ 'hovered': hover1}" >首页</button>
          <button @click="$router.push('/')" @mouseenter="hover2 = true" @mouseleave="hover2 = false" :class="{ 'hovered': hover2}">xxxx</button>
        </div>
      </div>
      <div class="nav-right">
        <span class="time">{{ currentTime }}</span>
      </div>
    </div>
    <Row class="content">
      <Col span="8">
        <div class="list">
          <div class="left">
            <!-- CPU -->
            <span class="title"
              ><span class="title-gradient">CPU利用率</span></span
            >
            <span class="angle1"></span>
            <span class="angle2"></span>
            <span class="angle3"></span>
            <span class="angle4"></span>

            <!-- 图表1 -->
            <div class="chart-68">
              <chart1 ref="chart1" id="left_1"></chart1>
            </div>
          </div>

          <div class="left">
            <!-- 内存 -->
            <span class="title"><span class="title-gradient">内存</span></span>
            <span class="angle1"></span>
            <span class="angle2"></span>
            <span class="angle3"></span>
            <span class="angle4"></span>

            <!-- 图表2 -->
            <div class="chart-68">
              <chart2 ref="chart2" id="left_2"></chart2>
            </div>
          </div>
        </div>
      </Col>
      <Col span="8">
        <div class="process" id="process">
          <span class="title">
            <!-- 中间部分-进程 -->
            <span class="title-gradient">进程管理</span></span
          >
          <span class="angle1"></span>
          <span class="angle2"></span>
          <span class="angle3"></span>
          <span class="angle4"></span>
          <!-- 进程组件 -->
          <div class="table">
            <process />
          </div>
        </div>
      </Col>
      <Col span="8">
        <div class="list">
          <div class="right">
            <span class="title"
              ><span class="title-gradient">磁盘读取/写入字节</span></span
            >
            <span class="angle1"></span>
            <span class="angle2"></span>
            <span class="angle3"></span>
            <span class="angle4"></span>
            <!-- 图表3 -->
            <div class="chart-68">
              <chart3 ref="chart3" id="right_3"></chart3>
            </div>
          </div>

          <div class="right">
            <span class="title"
              ><span class="title-gradient">磁盘读取/写入操作</span></span
            >
            <span class="angle1"></span>
            <span class="angle2"></span>
            <span class="angle3"></span>
            <span class="angle4"></span>
            <!-- 图表4 -->
            <div class="chart-68">
              <chart4 ref="chart4" id="right_4"></chart4>
            </div>
          </div>
        </div>
      </Col>
    </Row>
    <Row class="bottom-list">
      <Col span="8">
        <div class="list right-bottom">
          <div class="bottom bottom1">
            <span class="title"
              ><span class="title-gradient">网络接收/发送字节</span></span
            >
            <span class="angle1"></span>
            <span class="angle2"></span>
            <span class="angle3"></span>
            <span class="angle4"></span>
            <!-- 图表5 -->
            <div class="chart-68">
              <chart5 ref="chart5" id="bottom_5"></chart5>
            </div>
          </div>
        </div>
      </Col>

      <Col span="8">
        <div class="list" style="width: 65%; margin-left: 18%">
          <div class="bottom">
            <span class="title"
              ><span class="title-gradient">GPU利用率</span></span
            >
            <span class="angle1"></span>
            <span class="angle2"></span>
            <span class="angle3"></span>
            <span class="angle4"></span>
            <!-- 图表6 -->
            <div class="chart-68">
              <chart6 ref="chart6" id="bottom_6"></chart6>
            </div>
          </div>
        </div>
      </Col>
      <Col span="8">
        <div class="list right-bottom">
          <div class="bottom bottom1">
            <span class="title"
              ><span class="title-gradient">模块八分析</span></span
            >
            <span class="angle1"></span>
            <span class="angle2"></span>
            <span class="angle3"></span>
            <span class="angle4"></span>
            <!-- 图表7  还没想好做什么图 -->
            <div class="chart-68">
              <chart5 ref="chart7" id="bottom_7"></chart5>
            </div>
          </div>
        </div>
      </Col>
    </Row>
  </div>
</template>

<script>
// 导入组件
import process from "./components/service/process.vue";
const chart1 = () => import("./components/service/chart1");
const chart2 = () => import("./components/service/chart2");
const chart3 = () => import("./components/service/chart3");
const chart4 = () => import("./components/service/chart4");
const chart5 = () => import("./components/service/chart5");
const chart6 = () => import("./components/service/chart6");

export default {
  name: "service",
  components: {
    chart1,
    chart2,
    chart3,
    chart4,
    chart5,
    chart6,
    process,
  },
  data() {
    return {
      currentTime: this.getCurrentTime(),
      // btn_bk: "url(../assets/photos/bnt.png)",
      hover1: false,
      hover2: false,
    };
  },
  methods: {
    getCurrentTime() {
      const now = new Date();
      return now.toLocaleString();
    },
    updateCurrentTime() {
      this.currentTime = this.getCurrentTime();
    },
    
  },
    mounted() {
      setInterval(this.updateCurrentTime, 1000);
    },
};
</script>

<style lang="less" scoped>
.service {
  height: 100%;
  width: 100%;
  padding: 14px 20px 20px;
  // background: #03044a;
  background-image: url("../assets/photos/bg.jpg");
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  overflow: hidden;

  .nav {
    display: flex;
    justify-content: space-between;
    align-items: center;
    position: absolute;
    top: 3%;
    left: 5%;
    float: left;
    width: 90%;

    .nav-left {
      width: 33%;
      .buttonbox {
        display: flex;
        margin-left: -60px;
        button {
          border: none;
          background: none;
          padding: 0;
          width: 100px;
          height: 30px;
          border: none;
          border-radius: 5px;
          margin: 0 25px;
          background-image: url("../assets/photos/bnt.png");
          color: #6eddf1;
          font-size: 14px;
          cursor: pointer;
          text-align: center;
          text-decoration: none;
          position: relative;
          z-index: 1;
        }
        button.hovered {
          background-image: url('../assets/photos/bntactive.png');
        }
      }
    }

    .nav-right {
      .time {
        font-size: 35px;
        font-family: "字魂板刷黑(商用需授权)";
        color: #6eddf1;
      }
    }
  }

  .title-box {
    position: absolute;
    display: inline-block;
    font-size: 40px;
    margin-top: 1.5%;
    left: 50%;
    transform: translate(-50%, -50%);
    color: #6eddf1;

    font-family: "字魂板刷黑(商用需授权)";
  }
  @font-face {
    font-family: "字魂板刷黑(商用需授权)";
    src: url("../assets/fonts/字魂板刷黑(商用需授权).ttf") format("truetype");
  }

  .content {
    height: 65%;

    .ivu-col {
      height: 100%;
    }

    .process {
      height: 87.5%;
      margin-left: 2%;
      margin-right: 2%;
      margin-bottom: 5%;
      padding: 0 0 40px;
      text-align: center;
      position: relative;
      background-image: url("../assets/photos/bk.png");
      background-size: cover;
      background-position: center;
      background-repeat: no-repeat;

      .table {
        height: 80%;
        margin-top: 18%;
      }
    }

    .list {
      height: 48%;
      margin-top: 15%;

      .left,
      .right {
        // background: #0d1341;
        background-image: url("../assets/photos/bk.png");
        background-size: cover;
        background-position: center;
        background-repeat: no-repeat;
        margin-bottom: 4%;
      }

      .left,
      .right,
      .center {
        width: 100%;
        height: 90%;
        border: 1px solid #0d2451;
        position: relative;
        // background: rgba(1, 202, 217, .2);

        #left1,
        #left2,
        #left3,
        #right1,
        #right2,
        #right3,
        #center2 {
          height: 100%;
        }
      }
    }
  }
  .chart-68 {
    width: 95%;
    height: 95%;
    margin-left: 2.5%;
    margin-top: 1.25%;
    float: left;

    background: rgba(12, 118, 153, 0.2);
  }

  .bottom-list {
    height: 31%;
    margin-top: 2.7%;
    .ivu-col {
      height: 100%;

      .list {
        height: 100%;
        width: 33.3333%;
        float: left;
        background-image: url("../assets/photos/bk.png");
        background-size: cover;
        background-position: center;
        background-repeat: no-repeat;

        .bottom {
          width: 100%;
          height: 100%;
          border: 1px solid #0d2451;
          position: relative;
        }
      }

      .right-bottom {
        width: 100%;
        padding-right: 0;

        .bottom1 {
          width: 100%;
        }
      }
    }
  }
}
</style>