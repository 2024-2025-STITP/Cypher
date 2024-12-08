<template>
  <div class="container">
    <div class="register-box">
      <div class="logo">
        <img src="../assets/photos/cypher.png" alt="Logo" />
        <span>CYPHER</span>
      </div>
      <el-form
        label-position="left"
        label-width="80px"
        @submit.prevent="register"
        class="form"
      >
        <el-form-item label="用户名" prop="username">
          <el-input
            prefix-icon="el-icon-user"
            v-model="username"
            placeholder="请输入用户名"
            required
          ></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input
            prefix-icon="el-icon-message"
            v-model="email"
            placeholder="请输入邮箱"
            required
          ></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input
            prefix-icon="el-icon-lock"
            type="password"
            show-password
            v-model="password"
            placeholder="请输入密码"
            required
          ></el-input>
        </el-form-item>
        <el-form-item label="确认密码">
          <el-input
            prefix-icon="el-icon-lock"
            type="password"
            show-password
            v-model="passwordRepeat"
            placeholder="请再次输入密码"
            required
          ></el-input>
        </el-form-item>
        <div style="display: flex; align-items: center; margin-top: 15px">
          <input
            type="checkbox"
            id="checkbox"
            v-model="checkbox"
            required
            style="margin-right: 5px"
          />
          <label for="checkbox">
            我已阅读并同意<router-link to="/agreement">用户协议</router-link>和<router-link to="/privacy">隐私条款</router-link>
          </label>
        </div>
        <el-button type="primary" @click="register">注册</el-button>
        <p>已有账户？<router-link to="/login">登录</router-link></p>
      </el-form>
    </div>
    <div class="footer">
      <div class="foot-left">
        <div class="foot-logo">
          <img src="../assets/photos/cypher.png" />
          <span>CYPHER</span>
        </div>
        <div class="foot-content">
          CYPHER
          是一个基于分布式架构的网络安全监控系统，旨在实现跨设备的监控和优化，结合分布式技术、云计算，解决高并发问题，提升系统性能和效率。
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
import { Form, FormItem, Input, Button } from "element-ui";
export default {
  name: "Register",
  components: {
    ElForm: Form,
    ElFormItem: FormItem,
    ElInput: Input,
    ElButton: Button,
  },
  data() {
    return {
      username: "",
      email: "",
      password: "",
      passwordRepeat: "",
      checkbox: false,
    };
  },
  methods: {
    async register() {
      // 检查复选框是否已勾选
      if (!this.checkbox) {
        alert("请先阅读并同意用户协议和隐私条款。");
        return; // 如果未勾选，则终止注册请求
      }
      // 检查密码和确认密码是否一致
      if (this.password !== this.passwordRepeat) {
        alert("密码和确认密码不一致！");
        return;
      }

      const userData = {
        username: this.username,
        email: this.email,
        password: this.password,
      };
      alert("注册成功，请登录！");
      // 存储用户属性到全局属性中
      this.$user.username = this.username; 
      this.$user.password = this.password;
      this.$user.email = this.email;
      this.$router.push("/login"); // 注册成功后跳转到登录页面

      try {
        const response = await axios.post(
          "http://localhost:8081/user/register",
          userData
        );
        // 登录并获取后端的userId
        if (response.data.code === 200 && response.data.userId) {
          alert("注册成功，请登录！");
          // 存储用户属性到全局属性中
          this.$user.userId = response.data.userId;
          this.$user.username = this.username; 
          this.$user.password = this.password;
          this.$user.email = this.email;
          this.$router.push("/login"); // 注册成功后跳转到登录页面
        } else {
          alert(`注册失败：${response.data.msg}`);
        }
      } catch (error) {
        alert("注册过程中发生错误，请稍后再试");
        console.error(error);
      }
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
.register-box {
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
.form .el-form-item {
  margin-bottom: 20px;
}
button {
  width: 100%;
  margin-top: 15px;
  padding: 10px;
  background-image: linear-gradient(120deg, #89f7fe 0%, #66a6ff 100%);
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
button:hover {
  background-image: radial-gradient(
    circle 248px at center,
    #16d9e3 0%,
    #30c7ec 47%,
    #46aef7 100%
  );
}
p {
  margin-top: 30px;
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
  color: #aaa;
}
</style>
