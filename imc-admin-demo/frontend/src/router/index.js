import Vue from 'vue'
import VueRouter from 'vue-router'

import Login from '@/pages/Login'
import Dashboard from '@/pages/Dashboard'
import UserList from '@/pages/user/UserList'
import ApiList from '@/pages/user/ApiList'
import Admin from '@/pages/operation/Admin'

import axios from 'axios'

import { store } from '../store/store'

Vue.use(VueRouter)

const checkAuth = (...roles) => (from, to, next) => {

  let id = store.state.account.currentAccount.user.id;
  let authorities = store.state.account.currentAccount.authorities;

  if (id !== undefined) {
    let find = roles.find(role => authorities.find(auth => auth.authority === role));
    if (find) {
      return next();
    }
  }
  next("/login");
}

const isLogin = () => (from, to, next) => {
  alert("is login");
  if (store.state.account.currentAccount.user.id !== undefined) {
    return next(to.path);
  }
  next();
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
      // beforeEnter: checkAuth("ROLE_USER")
    },
    {
      path: '/user/list',
      name: 'UserList',
      component: UserList
    },
    {
      path: '/user/api',
      name: 'ApiList',
      component: ApiList
    },
    {
      path: '/operation/admin',
      name: 'Admin',
      component: Admin,
      // beforeEnter: checkAuth("ROLE_OPERATION")
    }
  ]
});

// router.beforeEach((to, from, next) => {
//   console.log("beforeEach");
//   store.commit('SET_CURRENT_ROUTE', to.name);
//   axios.get('/login/check')
//   .then(response => {
//     console.log(response);
//     next();
//     store.dispatch("LOGIN", response.data);
//   })
//   .catch(error => {
//     console.error(error);
//     // next("/login");
//     if (error.response.status === 401) {
//       // next("/login");
//     }
//   });
// });

export default router;
