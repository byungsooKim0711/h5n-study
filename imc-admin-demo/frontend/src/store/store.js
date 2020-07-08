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
  mutations: {
    SET_CURRENT_ROUTE(state, name) {
      state.currentRoute = name;
    },
    SET_DATETIMEOPEN (state, TF){
      state.isDatetimeOpen = TF
    },
    RESET(state) {
      Object.keys(state).forEach(key => {
        const temp = state[key];
        if(key == 'currentRoute') {

        } else if (key == 'isDatetimeOpen') {

        } else {
          Object.keys(temp).forEach(key2 => {
            if(typeof(temp[key2]) === 'object') {
              if(temp[key2] instanceof Array) {
                temp[key2] = [];
              } else {
                temp[key2] = {};
              }
            } else if(typeof(temp[key2]) === 'boolean'){
              temp[key2] = false
            } else if(key2 == 'selecteGwIndex') {
              temp[key2] = -1;
            } else if(key2 == 'adminOrgPage' || key2 == 'adminFaqPage') {
              temp[key2] = 1
            } else {
              temp[key2] = ''
            }
          })
        }
      })
    }
  },
  getters: {
    getCurrentRoute(state) {
      return state.currentRoute;
    }
  },
  actions: {

  }
});
