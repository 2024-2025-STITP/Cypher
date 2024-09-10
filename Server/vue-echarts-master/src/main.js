import Vue from 'vue'
import App from './App'
import router from './router'
import iView from 'iview';
import {Table,TableColumn} from 'element-ui';
import './assets/less/index.less';
import * as echarts from 'echarts';
import utils from "./lib/utils";

Vue.prototype.$echarts = function (el) {
    return echarts.init(el, null, {renderer: 'svg'})
}
Vue.config.productionTip = false;
Vue.use(iView);
Vue.use(Table)
Vue.use(TableColumn)
Vue.use(utils)
new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
