<template>
  <div class="container">
    <div class="login-box">
      <div class="logo">
        <img src="../assets/photos/cypher.png" alt="Logo" />
        <span>CYPHER</span>
      </div>
      <el-form @submit.prevent="login" class="form" :model="formLogin" ref="formLogin">
        <el-form-item label="用户名" prop="username">
          <el-input prefix-icon="el-icon-user" v-model="username" placeholder="请输入用户名" required></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input prefix-icon="el-icon-lock" type="password" show-password v-model="password" placeholder="请输入密码" required></el-input>
        </el-form-item>
        <el-form-item>
          <div style="display: flex; justify-content: space-between;">
            <el-input prefix-icon="el-icon-circle-check" v-model="formLogin.code" placeholder="请输入验证码" style="width: 150px;"></el-input>
            <div @click="refreshCode">
              <!-- 验证码组件 -->
              <dentify :identifyCode="identifyCode"></dentify>
            </div>
          </div>
        </el-form-item>
        <!-- 记住密码选项 -->
        <div style="display: flex; align-items: center; margin-top:15px; margin-bottom: 10px;">
          <input type="checkbox" id="checkbox" v-model="rememberMe" style="margin-right: 5px;"/>
          <label for="checkbox">记住密码</label>
        </div>

        <el-form-item>
          <el-button type="primary" @click="login">登录</el-button>
        </el-form-item>
        <p></p>
        <div style="display: flex; justify-content: space-between;">
          <p>还没有账户？<router-link to="/register">注册</router-link></p>
          <p><router-link to="/forget"> 忘记密码？</router-link></p>
        </div>
      </el-form>
    </div>
    <div class="footer">
      <div class="foot-left">
        <div class="foot-logo">
          <img src="../assets/photos/cypher.png" />
          <span>CYPHER</span>
        </div>
        <div class="foot-content">
          CYPHER 是一个基于分布式架构的网络安全监控系统，旨在实现跨设备的监控和优化，结合分布式技术、云计算，解决高并发问题，提升系统性能和效率。
          <br />
          可实现远程监控电脑性能状态，包括CPU、GPU、内存、显存以及运行进程的信息。
        </div>
        <div class="foot-copy">© 2024 CYPHER</div>
      </div>
      <div class="foot-right">
        <div class="foot-right-title">产品特色</div>
        <div class="foot-content">
          跨设备监控
          <br />
          渲染进度远程查看
          <br />
          训练进度实时获取
          <br />
          远程进程管理
        </div>
        <div class="foot-copy">
          转载内容版权归作者及来源网站所有，本站原创内容转载请注明来源，商业媒体及纸媒请先联系：cypher@dasabi.com
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import dentify from "./components/login/dentify.vue";
import { Form, FormItem, Input, Button, Checkbox } from "element-ui";

export default {
  name: "Login",
  components: {
    dentify,
    ElForm: Form,
    ElFormItem: FormItem,
    ElInput: Input,
    ElButton: Button,
  },
  data() {
    return {
      username: "",
      password: "",
      rememberMe: false, // 记住密码状态
      formLogin: {
        code: "", // 验证码输入框
      },
      identifyCodes: "1234567890abcdefjhijklinopqrsduvwxyz", // 随机串内容
      identifyCode: "", // 验证码图片内容
    };
  },
  mounted() {
    // 初始化验证码
    this.identifyCode = "";
    this.makeCode(this.identifyCodes, 4);

    // 从本地存储中获取记住的用户名和密码
    const rememberedUser = JSON.parse(localStorage.getItem("rememberedUser"));
    if (rememberedUser) {
      this.username = rememberedUser.username;
      this.password = rememberedUser.password;
      this.rememberMe = true; // 记住我复选框选中
    }
  },
  methods: {
    async login() {
      if (this.formLogin.code === this.identifyCode) {
        alert("登录成功！");
        // 跳转到首页
        this.$router.push("/home");
        try {
          const res = await axios.post("http://localhost:8081/user/login", {
            username: this.username,
            password: this.password,
          })
          if (res.data.code === 200) {
            this.$user.userId = res.data.userId; 
            this.$user.username = this.username;
            this.$user.password = this.password;
            if (this.rememberMe) {
              // 记住用户名和密码
              localStorage.setItem("rememberedUser", JSON.stringify({ username: this.username, password: this.password }));
            } else {
              // 不记住则清除
              localStorage.removeItem("rememberedUser");
            }
            alert("登录成功！");
            // 跳转到首页
            this.$router.push("/home");
          } else {
            alert(`登录失败：${res.data.msg}`);
          }
        } catch (error) {
          alert("登录过程中发生错误，请稍后再试");
          console.error(error);
        }
      } else {
        alert("验证码错误！请重新输入");
        this.refreshCode();
      }
    },
    // 重置验证码
    refreshCode() {
      this.identifyCode = "";
      this.makeCode(this.identifyCodes, 4);
      this.formLogin.code = "";
    },
    // 获取验证码的值
    makeCode(o, l) {
      this.identifyCode = "";
      for (let i = 0; i < l; i++) {
        this.identifyCode += this.identifyCodes[this.randomNum(0, this.identifyCodes.length)];
      }
    },
    // 随机数字
    randomNum(min, max) {
      return Math.floor(Math.random() * (max - min) + min);
    },
  },
};
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: calc(100vh - 55px);
}
.login-box {
  margin-top: 2%;
  height: 600px;
  width: 450px;
  background-color: #fff;
  border-radius: 12px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}
.logo {
  background-image: linear-gradient(180deg, #404040 0, #1e1e1e 100%),
    linear-gradient(126deg, #201b4f 15%, #0b0930 100%);
  height: 28%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-radius: 12px 12px 0 0;
  box-shadow: 0 2px 7px 0 #211b4f;
}
.logo img {
  margin-top: 0;
}
.logo span {
  font-size: 24px;
  color: #fff;
  font-weight: bold;
  font-family: "Dark Falcon Demo";
}

.form {
  padding: 40px 80px;
}
.form .el-form-item:first-child {
  margin-bottom: 0px;
}
.form .el-form-item {
  margin-bottom: 20px;
}



button {
  width: 100%;
  padding: 10px;
  background-image: linear-gradient(120deg, #89f7fe 0%, #66a6ff 100%);
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
button:hover {
  background-image: radial-gradient(circle 248px at center, #16d9e3 0%, #30c7ec 47%, #46aef7 100%);
}
p {
  margin-top: 15px;
  font-size: 14px;
  text-align: center;
}
.footer {
  margin-top: 2%;
  height: 280px;
  width: 100%;
  background-color: #333;
  justify-content: space-between;
}
.foot-left {
  margin-top: 2%;
  margin-left: 15%;
  width: 35%;
}
.foot-logo {
  display: flex;
  align-items: center;
}
.foot-logo img {
  height: 40px;
}
.foot-logo span {
  font-size: 12px;
  font-weight: bold;
  font-family: "Dark Falcon Demo";
  color: #fff;
  justify-content: space-between;
  margin-left: 5px;
}
.foot-content {
  font-size: 14px;
  color: #aaa;
  margin-top: 20px;
  line-height: 24px;
}
.foot-copy {
  font-size: 12px;
  color: #aaa;
  margin-top: 8%;
}
.foot-right {
  width: 35%;
}
.foot-right-title {
  margin-top: 8%;
  font-size: 18px;
  color:#aaa;
}
</style>
