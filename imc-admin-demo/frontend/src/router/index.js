import Vue from 'vue'
import Router from 'vue-router'

import Home from '@/pages/Home'
import Stats from '@/pages/Stats'

Vue.use(Router)

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
