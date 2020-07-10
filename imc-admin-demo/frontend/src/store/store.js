import Vue from 'vue';
import Vuex from 'vuex';

import account from './modules/account';

Vue.use(Vuex);

export const store = new Vuex.Store({
  modules: {
    account,
  },

  state: {
    currentRoute: '',
  },

  getters: {
    getCurrentRoute(state) {
      return state.currentRoute;
    }
  },

  mutations: {
    SET_CURRENT_ROUTE(state, to) {
      state.currentRoute = to;
    }
  },

  actions: {}
});
