import Vue from 'vue';
import Router from 'vue-router';
import Home from '../views/Home.vue';
import About from '../views/About.vue';
import Service from '../views/Service.vue';
import Contact from '../views/Contact.vue';
import Login from '../views/Login.vue';
import Register from '../views/Register.vue';
import User from '../views/User.vue';
import Agreement from '../views/components/register/agreement.vue';
import Privacy from '../views/components/register/privacy.vue';
import Forget from '../views/components/login/forget.vue';

 
Vue.use(Router);

export default new Router({
  routes: [
    { path: '/', component: Home },
    { path: '/about', component: About },
    { path: '/service', component: Service },
    { path: '/contact', component: Contact },
    { path: '/login', component: Login },
    { path: '/register', component: Register },
    { path: '/agreement', component: Agreement },
    { path: '/privacy', component: Privacy },
    { path: '/forget', component: Forget },
    { path: '/user', component: User },
  ],
});
