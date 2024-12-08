<template>
  <div class="container">
    <div class="user-header">
      <div class="user-info">
        <div class="userInfo-left">
          <img :src='userphotos'>
        </div>
        <div class="userInfo-right">
          <div class="user-name">
            <span>{{ username }}</span>
          </div>
          <div class="user-email">
            <span>{{ email }}</span>
          </div>
          <div class="introduce">
            <span>个性签名：{{ introduce }}</span>
          </div>
        </div>
      </div>
      <div class="user-title">
        <span>账户设置</span>
      </div>
    </div>
    <div class="content-box">
      <div class="content">
        <el-tabs tab-position="left">
          <el-tab-pane label="个人信息">
            <div class="tab-box">
              <el-form label-position="left" label-width="100px">
                <el-form-item label="用户名">
                  <el-input v-model="tempUsername"></el-input>
                </el-form-item>
                <el-form-item label="个性签名">
                  <el-input type="textarea" v-model="tempIntroduce"></el-input>
                </el-form-item>
                <el-form-item label="头像">
                  <div class="avatar-box" @mouseenter="showOverlay = true" @mouseleave="showOverlay = false">
                    <el-avatar :src="userphotos" :size='100'></el-avatar>
                    <div class="avatar-overlay" v-if="showOverlay" @click="dialogVisible = true">
                      修改头像
                    </div>
                  </div>
                </el-form-item>
                <el-dialog :visible.sync="dialogVisible" top="20vh">
                  <SwiperDemo @select="handleSelect" />
                  <span slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="confirmSelection">确定</el-button>
                    <el-button @click="dialogVisible = false">取消</el-button>
                  </span>
                </el-dialog>
                <el-form-item label="性别">
                  <el-radio-group v-model="tempRadio">
                    <el-radio :label="3">男</el-radio>
                    <el-radio :label="6">女</el-radio>
                    <el-radio :label="9">保密</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="出生日期">
                  <el-date-picker v-model="tempValue" type="date" placeholder="选择日期"></el-date-picker>
                </el-form-item>
                <el-form-item>
                  <el-button type="primary" @click="saveInfo">保存</el-button>
                </el-form-item>
              </el-form>
            </div>
          </el-tab-pane>
          <el-tab-pane label="账户管理">
            <div class="tab-box">
              <el-form label-position="left" label-width="100px">
                <el-form-item label="邮箱">
                  <el-input v-model="email" :disabled="true"></el-input>
                </el-form-item>
                <el-form-item label="账户密码">
                  <el-input :disabled="true" placeholder="已设置，可通过账户密码更改"></el-input>
                </el-form-item>
                <el-form-item>
                  <el-button @click="changePassword">修改密码</el-button>
                </el-form-item>
              </el-form>
            </div>
          </el-tab-pane>
          <el-tab-pane label="设备管理">
            <div class="tab-box">
              <el-form label-position="left" label-width="100px">
                <el-form-item label="主机名" prop="hostName">
                  <el-input v-if="!isDeviceAdded" v-model="hostName"></el-input>
                  <span v-else>{{ hostName }}</span>
                </el-form-item>
                <el-form-item label="主机地址" prop="hostIp">
                  <el-input v-if="!isDeviceAdded" v-model="hostIp"></el-input>
                  <span v-else>{{ hostIp }}</span>
                </el-form-item>
                <p style="margin-left: 350px;">不清楚？点击<a href="#" @click.prevent="getHost">获取</a></p>
                <el-form-item label="开启监控">
                  <el-switch v-model="hostStatus"></el-switch>
                </el-form-item>
                <el-form-item>
                  <el-button @click="toggleDeviceMode" :disabled="!canAddDevice">{{ isDeviceAdded ? '更换设备' : '添加设备' }}</el-button>
                </el-form-item>
              </el-form>
            </div>
          </el-tab-pane>
        </el-tabs>
      </div>
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
import SwiperDemo from './components/user/SwiperDemo.vue'
import axios from 'axios'
export default {
  name: 'User',
  data() {
    return {
      userphotos: require('../assets/user-photos/user1.jpg'),
      username: '',
      email: '',
      userId: null,
      introduce: '',
      radio: 3,
      value: '',
      hostName: '',
      hostIp: '',
      hostStatus: false,
      // 临时存储用户输入信息
      tempUsername: '',
      tempIntroduce: '',
      tempRadio: 3,
      tempValue: '',
      showOverlay: false, // 显示头像修改
      dialogVisible: false, // 显示头像修改对话框
      selectImage: null,  // 选择的图片
      isDeviceAdded: false, // 标记是否已添加设备
      canAddDevice: true, // 标记是否可以添加设备
    }
  },
  components: {
    SwiperDemo
  },
  created() {
    // 从本地存储中恢复用户信息
    this.userId = this.$user.userId; // 假设您有一个全局的用户对象，包含用户ID
    const userInfo = JSON.parse(localStorage.getItem(`userInfo_${this.userId}`));
    if (userInfo) {
      this.username = userInfo.username;
      this.email = userInfo.email;
      this.introduce = userInfo.introduce;
      this.userphotos = userInfo.userphotos; // 恢复头像URL
      this.sex = userInfo.sex;
      this.birthDate = userInfo.birthDate;
    }
    // 这里从全局属性获取用户数据并填充到数据中
    // this.username = this.$user.username; // 从全局属性获取用户名
    // this.email = this.$user.email; // 从全局属性获取邮箱
    this.username = 'derder'; // 测试用，后续需要从全局属性获取用户名
    this.email = '2310550020@qq.com'; // 测试用，后续需要从全局属性获取邮箱
    this.introduce = '这是一个测试用户，欢迎来到CYPHER！'; // 测试用，后续需要从全局属性获取介绍
    this.tempUsername = this.username; // 初始化临时数据
    this.tempIntroduce = this.introduce; // 初始化临时数据
    this.tempRadio = this.radio; // 初始化临时数据
    this.tempValue = this.value; // 初始化临时数据
  },
  methods: {
    changePassword() {
      this.$router.push('/forget');
    },
    // 使用用户ID作为键来保存用户信息
    async saveInfo() {
      // 将临时数据保存到实际数据
      this.username = this.tempUsername;
      this.introduce = this.tempIntroduce;
      this.radio = this.tempRadio;
      this.value = this.tempValue;
      alert("用户信息已保存");
      const userInfo = {
        username: this.username,
        email: this.email,
        introduce: this.introduce,
        userphotos: this.userphotos, 
        sex: this.radio,
        birthDate: this.value,
        // 其他需要保存的信息
      };
      console.log("用户信息已保存:", {
        username: this.username,
        introduce: this.introduce,
        sex: this.radio,
        birthDate: this.value
      });
      localStorage.setItem(`userInfo_${this.userId}`, JSON.stringify(userInfo));
      try {
        const response = await axios.post(`http://localhost:8081/user/update/${userId}`, userInfo);  //根据userId更新后端用户信息
        if (response.data.code === 200) {
          alert("个人信息更新成功！");
        } else {
          alert(`更新失败：${response.data.msg}`);
        }
      } catch (error) {
        alert("更新个人信息过程中发生错误，请稍后再试");
        console.error(error);
      }
    },
    
    handleSelect(image) {
      this.selectedImage = image; // 当选择一个图片时，更新 selectedImage
    },
    confirmSelection() {
      if (this.selectedImage) {
        this.userphotos = this.selectedImage; // 更新头像
        this.dialogVisible = false; // 关闭对话框
      }
    },
    getHost() {
      // 从后端获取主机信息
      axios.get('http://localhost:8081/host')
        .then(response => {
          if (response.data.code === 200) {
            this.hostName = response.data.hostName;
            this.hostIp = response.data.hostIp;
          } else {
            alert(`获取主机信息失败：${response.data.msg}`);
          }
        })
        .catch(error => {
          alert('获取主机信息过程中发生错误，请稍后再试');
          console.error(error);
        });
    },
    toggleDeviceMode() {
      if (this.isDeviceAdded) {
        // 切换到添加设备模式
        this.isDeviceAdded = false;
        this.canAddDevice = true;
      } else {
        // 验证主机名和主机地址是否已填入
        if (!this.hostName || !this.hostIp) {
          alert('请填写主机名和主机地址');
          return;
        }
        // 向后端发送数据
        this.addDevice();
      }
    },
    addDevice() {
      const deviceInfo = {
        hostName: this.hostName,
        hostIp: this.hostIp,
        hostStatus: this.hostStatus,
      };
      axios.post('http://localhost:8081/device/add', deviceInfo)
        .then(response => {
          if (response.data.code === 200) {
            alert('设备添加成功');
            // 切换到编辑设备模式
            this.isDeviceAdded = true;
            this.canAddDevice = false;
          } else {
            alert(`添加设备失败：${response.data.msg}`);
          }
        })
        .catch(error => {
          alert('添加设备过程中发生错误，请稍后再试');
          console.error(error);
        });
    },
  }
}
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  height: calc(100vh - 55px);
}
.user-header {
  display: flex;
  width: 100%;
  height: 150px;
  padding: 0 15%;
  background-color: #333;
  justify-content: space-between;
  align-items: center;
}
.user-info {
  display: flex;
  align-items: center;
}
.userInfo-left {
  width: 100px;
  height: 100px;
  overflow: hidden;
  border-radius: 50%;
  border: 2px solid #fff;
  display: flex;
}
.userInfo-left img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
.userInfo-right {
  display: block;
  margin-left: 40px;
}
.user-name {
  font-size: 36px;
  color: #fff;
}
.user-email, .introduce {
  color: #aaa;
}
.user-title {
  font-size: 36px;
  color: #fff;
}
.content-box {
  height: 600px;
  padding: 0 25%;
  background-color: #fafafb;
}
.content {
  margin-top: 30px;
}
.tab-box {
  margin-left: 50px;
  width: 500px;
  height: 470px;
  padding: 25px;
  background-color: #fff;
  border-radius: 12px;
  box-shadow: 0  2px 10px rgba(0, 0, 0, 0.1);
}
.avatar-box {
  margin-left: 50px;
  display: inline-block;
  transition: transform 0.3s ease;
  position: relative;
  width: 100px;
  height: 100px;
  overflow: hidden;
  border-radius: 50%;
}
.avatar-box:hover {
  transform: scale(1.2);
}
.avatar-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(38, 38, 38, 0.5); 
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;  
  font-size: 16px;
  opacity: 0;  
  transition: opacity 0.3s ease; 
}
.avatar-box:hover .avatar-overlay {
  opacity: 1;
}
.dialog-footer {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
  margin-right: 60px;
}
button {
  width: 50%;
  margin-left: 12%;
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
.footer {
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
