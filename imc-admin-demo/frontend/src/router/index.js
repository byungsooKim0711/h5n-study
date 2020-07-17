import Vue from 'vue'
import VueRouter from 'vue-router'

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
  if (store.state.account.currentAccount.user.id !== undefined) {
    return next("/dashboard");
  }
  next();
}

const notfoundRoute = () => (to, from, next) => {
  next(from.path);
}

const router = new VueRouter({
  base: process.env.BASE_URL,
  // mode: 'history',
  // scrollBehavior: () => ({ y: 0 }),
  routes: [
    {
      path: '/',
      name: 'Login',
      component: () => import('@/pages/Login'),
      beforeEnter: isLogin()
    },
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/pages/Login'),
      beforeEnter: isLogin()
    },
    {
      path: '/myinfo',
      name: 'MyInfo',
      component: () => import('@/pages/my/MyInfo'),
      beforeEnter: checkAuth("ROLE_USER")
    },
    {
      path: '/dashboard',
      name: 'Dashboard',
      component: () => import('@/pages/Dashboard'),
      beforeEnter: checkAuth("ROLE_USER")
    },
    {
      path: '/user/list',
      name: 'UserList',
      component: () => import('@/pages/user/UserList'),
      beforeEnter: checkAuth("ROLE_USER")
    },
    {
      path: '/user/api',
      name: 'ApiList',
      component: () => import('@/pages/user/ApiList'),
      beforeEnter: checkAuth("ROLE_USER")
    },
    {
      path: '/operation/admin',
      name: 'Admin',
      component: () => import('@/pages/operation/Admin'),
      beforeEnter: checkAuth("ROLE_MANAGE")
    },
    {
      path: '*',
      name: 'Dashboard',
      component: () => import('@/pages/Dashboard'),
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
      return next("/login");
    }
    store.commit("LOGIN", response.data);
    store.commit("SET_CURRENT_ROUTE", to.path);
    return next();
  })
  .catch(error => {
    console.error(error);
    store.commit("LOGOUT");
    store.commit("SET_CURRENT_ROUTE", "/");
    return next();
    // window.history.pushState("", "", "/#/");
  });
});

export default router;
