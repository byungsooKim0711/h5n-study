import Vue from 'vue'
import VueRouter from 'vue-router'

import Login from '@/pages/Login'
import Dashboard from '@/pages/Dashboard'
import UserList from '@/pages/user/UserList'
import ApiList from '@/pages/user/ApiList'
import Admin from '@/pages/operation/Admin'
import MyInfo from '@/pages/my/MyInfo'

import axios from 'axios'

import { store } from '../store/store'

Vue.use(VueRouter)


// 권한 여부 확인
const checkAuth = (...roles) => (to, from, next) => {

  let id = store.state.account.currentAccount.user.id;
  let authorities = store.state.account.currentAccount.authorities;

  if (id !== undefined) {
    let find = roles.find(role => authorities.find(auth => auth.authority === role));
    if (find) {
      return next();
    } else {
      alert("접근권한이 없습니다.");
      return ;
    }
  }
  return next("/login");
}

// 로그인 여부 확인
const isLogin = () => (to, from, next) => {
  if (store.state.account.currentAccount.user.id != undefined) {
    return next("/dashboard");
  }
  next();
}

const notfoundRoute = () => (to, from, next) => {
  next(from.path);
}

const router = new VueRouter({
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login,
      beforeEnter: isLogin()
    },
    {
      path: '/login',
      name: 'Login',
      component: Login,
      beforeEnter: isLogin()
    },
    {
      path: '/myinfo',
      name: 'MyInfo',
      component: MyInfo,
      beforeEnter: checkAuth("ROLE_USER")
    },
    {
      path: '/dashboard',
      name: 'Dashboard',
      component: Dashboard,
      beforeEnter: checkAuth("ROLE_USER")
    },
    {
      path: '/user/list',
      name: 'UserList',
      component: UserList,
      beforeEnter: checkAuth("ROLE_USER")
    },
    {
      path: '/user/api',
      name: 'ApiList',
      component: ApiList,
      beforeEnter: checkAuth("ROLE_USER")
    },
    {
      path: '/operation/admin',
      name: 'Admin',
      component: Admin,
      beforeEnter: checkAuth("ROLE_MANAGE")
    },
    {
      path: '*',
      name: 'Dashboard',
      component: Dashboard,
      beforeEnter: notfoundRoute()
    }
  ]
});

router.beforeEach((to, from, next) => {
  axios.get('/login/check')
  .then(response => {
    if (response.data.status === 700) {
      store.commit("LOGOUT");
      store.commit("SET_CURRENT_ROUTE", "/");
      next("/");
    }
    store.commit("LOGIN", response.data);
    store.commit("SET_CURRENT_ROUTE", to.path);
    next();
  })
  .catch(error => {
    console.error(error);
    store.commit("LOGOUT");
    store.commit("SET_CURRENT_ROUTE", "/");
    next();
    // window.history.pushState("", "", "/#/");
  });
});

export default router;
