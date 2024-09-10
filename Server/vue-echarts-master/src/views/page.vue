<template>
  <div class="page2">
    <Row class="content">
      <Col span="8">
        <div class="list">
          <div class="left">
            <!-- CPU -->
            <span class="title"><span class="title-gradient">CPU</span></span>
            <span class="angle1"></span>
            <span class="angle2"></span>
            <span class="angle3"></span>
            <span class="angle4"></span>

            <!-- 图表1 -->
            <div class="chart-68">
              <chart1 ref="chart1" id="left_1"></chart1>
            </div>

          </div>
        </div>

        <div class="list">
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
          <span class="title-gradient">进程管理</span></span>
          <span class="angle1"></span>
          <span class="angle2"></span>
          <span class="angle3"></span>
          <span class="angle4"></span>
          <!-- 进程组件 -->
          <div class="table">
            <process/>
          </div>
          
        </div>
      </Col>
      <Col span="8">
        <div class="list">
          <div class="right">
            <span class="title"><span class="title-gradient">磁盘读取/写入字节</span></span>
            <span class="angle1"></span>
            <span class="angle2"></span>
            <span class="angle3"></span>
            <span class="angle4"></span>
            <!-- 图表3 -->
            <div class="chart-68">
              <chart3 ref="chart3" id="right_3"></chart3>
            </div>
          </div>
        </div>
        <div class="list">
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
            <chart5 ref="chart5" id="bottom_5"></chart5>
          </div>
        </div>
      </Col>

      <Col span="8">
        <div class="list" style="width: 65%; margin-left: 18%">
          <div class="bottom">
            <span class="title"><span class="title-gradient">GPU</span></span>
            <span class="angle1"></span>
            <span class="angle2"></span>
            <span class="angle3"></span>
            <span class="angle4"></span>
            <!-- 图表6 -->
            <chart6 ref="chart6" id="bottom_6"></chart6>
          </div>
        </div>
      </Col>
      <Col span="8">
        <div class="list right-bottom">
          <div class="bottom bottom1">
            <span class="title"><span class="title-gradient">模块八分析</span></span>
            <span class="angle1"></span>
            <span class="angle2"></span>
            <span class="angle3"></span>
            <span class="angle4"></span>
            <!-- 图表7  还没想好做什么图 -->
            <chart5 ref="chart7" id="bottom_7"></chart5>
          </div>
        </div>
      </Col>
    </Row>
  </div>
</template>

<script>
// 导入组件
import process from './components/page/process.vue'
const chart1 = () => import("./components/page/chart1");
const chart2 = () => import("./components/page/chart2");
const chart3 = () => import("./components/page/chart3");
const chart4 = () => import("./components/page/chart4");
const chart5 = () => import("./components/page/chart5");
const chart6 = () => import("./components/page/chart6");

export default {
  name: "page",
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
        
    };
  },
  methods: {},
};
</script>

<style lang="less" scoped>
.page2 {
  height: 100%;
  width: 100%;
  padding: 14px 20px 20px;
  background: #03044a;
  overflow: hidden;

  .content {
    height: 65%;

    .ivu-col {
      height: 100%;
    }

    .process {
      height: 96%;
      margin-left: 2%;
      margin-right: 2%;
      margin-bottom: 5%;
      padding: 0 0 40px;
      text-align: center;
      position: relative;
      background: #0d1341;

      .table{
        height: 80%;
      }
    }

    .list {
      height: 48%;

      .left,
      .right {
        background: #0d1341;
      }

      .left,
      .right,
      .center {
        width: 100%;
        height: 90%;
        border: 1px solid #0d2451;
        position: relative;

        #left1,
        #left2,
        #left3,
        #right1,
        #right2,
        #right3,
        #center2 {
          height: 100%;
        }

        .chart-68 {
          width: 100%;
          height: 100%;
          float: left;
        }
      }
    }
  }

  .bottom-list {
    height: 35%;

    .ivu-col {
      height: 100%;

      .list {
        height: 100%;
        width: 33.3333%;
        padding-right: 1.5%;
        float: left;

        #bottom_4 {
          padding: 0 20px;
        }

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
