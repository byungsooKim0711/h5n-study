import Vue from 'vue'
import Router from 'vue-router'

import Login from '@/pages/Login'
import Dashboard from '@/pages/Dashboard'
import Admin from '@/pages/operation/Admin'

import VueRouter from 'vue-router'
import axios from 'axios'

import { store } from '@/store/store.js';

Vue.use(Router)

// const checkAuth = (...roles) => (from, to, next) => {
//   let id = store.state.currentAccount.user.id;
//   let role = store.state.currentAccount.authorities;

//   console.log(id);
//   console.log(role);
//   console.log(roles);

//   if (!id) {
//     let find = roles.find(checkRole => checkRole == role);
//     if (find) {
//       return next();
//     }
//   }
//   next('/login');
// }

const isLogin = () => (from, to, next) => {
  axios.get("/login/check", {

  })
  .then(response => {
    console.log(response);
  })
  .catch(error => {
    console.log(error);
    // next("/login");
  });
}


const router = new VueRouter({
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login,
      // beforeEnter: isLogin()
    },
    {
      path: '/login',
      name: 'Login',
      component: Login,
      // beforeEnter: isLogin()
    },
    {
      path: '/dashboard',
      name: 'Dashboard',
      component: Dashboard,
      // beforeEnter: isLogin()
    },
    {
      path: '/operation/admin',
      name: 'Admin',
      component: Admin
    }
  ]
});

export default router;
