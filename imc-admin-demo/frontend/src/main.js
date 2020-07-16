// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue';
import App from './App';
import router from './router';
import vuetify from '@/plugins/vuetify';
import { store } from './store/store';
import vueMoment from 'vue-moment';

Vue.config.productionTip = false;

/* axios global configuration */
window.axios = require('axios');

/* axios request interceptors */
// axios.interceptors.request.use(function (request) {
//   return request;
// }, function (error) {
//   return Promise.reject(error);
// });

/* axios response interceptors */
// axios.interceptors.response.use(function (response) {
//   return response;
// }, function (error) {
//   return Promise.reject(error);
// });

/* Moment 설정 */
Vue.use(vueMoment);


/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  vuetify,
  store,
  components: { App },
  template: '<App/>'
})
