import Vue from 'vue'
import App from './App'
import router from './router'
import iView from 'iview';
import ElementUI from 'element-ui'; // 引入 ElementUI
import 'element-ui/lib/theme-chalk/index.css'; // 引入 ElementUI 的样式
import './assets/less/index.less';
import * as echarts from 'echarts';
import utils from "./lib/utils";

Vue.prototype.$echarts = function (el) {
    return echarts.init(el, null, {renderer: 'svg'})
}
Vue.config.productionTip = false;
Vue.use(iView);
Vue.use(ElementUI); // 注册 ElementUI
Vue.use(utils);
Vue.prototype.$user = {}; // 用于存储用户信息的全局属性

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
