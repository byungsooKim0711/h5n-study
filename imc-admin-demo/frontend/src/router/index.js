import Vue from 'vue'
import Router from 'vue-router'

import Home from '@/pages/Home'
import Stats from '@/pages/Stats'
import Login from '@/pages/Login'
import Dashboard from '@/pages/Dashboard'

Vue.use(Router)

// const checkAuth = (...roles) => (from, to, next) => {
//   let id = store.state.account.currentAccount.id;
//   let role = store.state.account.currentAccount.roles;
//
//   if (!id) {
//     let find = roles.find(checkRole => checkRole == role);
//     if (find) {
//       return next();
//     }
//   }
//   next('/login');
// }



export default new Router({
  // mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/dashboard',
      name: 'Dashboard',
      component: Dashboard
    },
    {
      path: '/about',
      name: 'About',
      component: () => import('@/pages/About.vue')
    },
    {
      path: '/stats',
      name: 'Stats',
      component: Stats
    }
  ]
})
