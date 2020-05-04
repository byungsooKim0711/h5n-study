import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/pages/core/login.vue'
import DashBoard from '@/pages/DashBoard.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/dashboard',
      name: 'DashBoard',
      component: DashBoard
    }
  ]
})
