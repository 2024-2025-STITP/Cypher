<template>
    <div class="container">
      <div class="forget-password-box">
        <div class="logo">
          <img src="../../../assets/photos/cypher.png" alt="Logo" />
          <span>CYPHER</span>
        </div>
        <el-form @submit.prevent="resetPassword" class="form" :model="formForget" ref="formForget">
          <el-form-item label="请输入您的用户名" prop="username">
            <el-input prefix-icon="el-icon-user" v-model="username" placeholder="输入您的用户名" required></el-input>
          </el-form-item>
          <el-form-item label="请输入您的注册邮箱" prop="email">
            <el-input prefix-icon="el-icon-message" v-model="email" placeholder="输入您的邮箱" required></el-input>
          </el-form-item>
          <el-form-item label="请输入您的新密码" prop="password">
            <el-input prefix-icon="el-icon-lock" type="password" show-password v-model="password" placeholder="输入您的新密码" required></el-input>
          </el-form-item>
          
  
          <el-form-item>
            <el-button type="primary" @click="resetPassword">提交</el-button>
          </el-form-item>
          <p></p>
          <div style="display: flex; justify-content: space-between;">
            <p>记得密码？<router-link to="/login">返回登录</router-link></p>
          </div>
        </el-form>
      </div>
      
      <div class="footer">
        <div class="foot-left">
          <div class="foot-logo">
            <img src="../../../assets/photos/cypher.png" />
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
  import { Form, FormItem, Input, Button } from "element-ui";
  import axios from "axios";
  
  export default {
    name: "Forget",
    components: {
      ElForm: Form,
      ElFormItem: FormItem,
      ElInput: Input,
      ElButton: Button,
    },
    data() {
      return {
        username: '',
        email: '', 
        password: '', 
        formForget: {
          username: '',
          email: '',
          password: '',
        }
      };
    },
    methods: {
      // 发送重置密码的邮件的方法
      async resetPassword() {
        try {
            const res = await axios.post('/api/reset-password', {
              username: this.username,
              email: this.email,
              password: this.password,
            });
            if (res.data.code === 200) {
              alert('密码修改成功');
              this.$router.push('/login');
            } else {
              this.$message.error(res.data.message);
            }
        } catch (error) {
          console.log(error);
          this.$message.error('请求失败，请稍后再试');
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
  
  .forget-password-box {
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
    height: 30%;
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
    margin-bottom: 10px;
  }
  
  
  button {
    margin-top: 10px;
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
    margin-top: 10px;
    font-size: 14px;
    text-align: center;
  }
  
  .footer {
    margin-top: 2%;
    height: 280px;
    width: 100%;
    background-color: #333;
    display: flex; 
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
  