<template>
    <div id="app">
        <div v-if = "!hiddenRoutes.includes($route.path)" class="header">
        <div class="header-left">
          <a href="#" class="logo">
            <img src="./assets/photos/cypher.png" class="logo-img" alt="Logo" />
            <span>CYPHER</span>
          </a>
          <ul>
            <li><router-link to="/">首页</router-link></li>
            <li><router-link to="/about">关于我们</router-link></li>
            <li><router-link to="/service">服务</router-link></li> <!-- 修改为router-link -->
            <li><router-link to="/contact">联系我们</router-link></li>
          </ul>
        </div>
  
        <div class="header-right">
          <ul>
            <li><router-link to="/login">登录</router-link></li> <!-- 修改为router-link -->
            <li><router-link to="/register">注册</router-link></li> <!-- 修改为router-link -->
          </ul>
          <router-link to="/user">
            <img src="./assets/photos/用户.png">
          </router-link>
        </div>
      </div>
        <router-view/>
    </div>
</template>

<script>
export default {
    name: 'App',
    data() {
        return {
            resizeFn: null,
            hiddenRoutes: ['/service','/agreement','/privacy' ]
        }
    },
    mounted() {
        const documentWidth = document.body.offsetWidth;
        const ratio = documentWidth / 1920;
        if (documentWidth > 1920) {
            document.body.style.transform = `scale(${ratio}, ${ratio})`
        }
        this.resizeFn = this.$debounce(function () {
            const documentWidth = document.body.offsetWidth;
            const ratio = documentWidth / 1920;
            if (documentWidth > 1920) {
                document.body.style.transform = `scale(${ratio}, ${ratio})`
            }
        }, 200)
        window.addEventListener('resize',this.resizeFn);
    },
    beforeDestroy() {
        window.removeEventListener('resize', this.resizeFn);
    }
}
</script>

<style lang="less">
* {
  margin: 0;
  padding: 0;
}

a {
  text-decoration: none;
  color: inherit;
}

.logo-img {
  height: 45px;
  margin: 0 20px;
}

.header {
  height: 55px;
  width: 100%;
  background: #333;
  display: flex;
  align-items: center;
  justify-content: space-between; /* 使左右部分处于两端 */
}

.header-left {
  display: flex;
  align-items: center;

  .logo {
    display: flex;
    align-items: center;

    span {
      margin-top: 5px;
      font-size: 20px;
      font-weight: bold;
      font-family: "Dark Falcon Demo";
      color: #fff;
      justify-content: space-between;
      margin-right: 40px;
    }
  }
}

ul {
    display: flex;
    list-style: none;
    margin-right: 10px;
    

    li {
      margin-left: 25px;
      font-size: 16px;

      a {
        color: #aaa;
      }
    }
  }

.header-right {
  display: flex;
  align-items: center;
  margin-right: 20px;

  img {
    height: 30px;
    margin-left: 20px;
    margin-top: 5px;
  }
}

.body {
  height: 100vh;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: black;
}

.footer {
  height: 50px;
  width: 100%;
  background: #333;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
  font-size: 14px;
}

html,body {
    height: 100%;
    width: 100%;
    padding:0;
    margin:0;
}
html {
    font-size: 20px;
}
body {
    transform-origin: left top;
    background-size: 100% 100%;
}
#app {
    height: 100%;
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
}
</style>
