// import axios from 'axios'

const state = {
  currentAccount: {},
}

const getters = {
  getCurrentAccount(state) {
    return state.currentAccount;
  },
  getCurrentAccountConfig(state) {
    return state.currentAccountConfig;
  },
  isPersonalUser(state) {
    if(state.currentAccount.role == 'USER') {
      return true;
    } else {
      return false;
    }
  }
}

const mutations = {
  SET_CURRENT_ACCOUNT(state, account){
    state.currentAccount = account;
  },
  SET_CURRENT_ACCOUNT_CONFIG(state, config) {
    state.currentAccountConfig = config;
  },
}

const actions = {
  SET_CURRENT_ACCOUNT( {commit}, account) {
    commit('SET_CURRENT_ACCOUNT', account);
  },

  SET_CURRENT_ACCOUNT_CONFIG({commit}, config) {
    commit('SET_CURRENT_ACCOUNT_CONFIG', config);
  },
}

export default {
  state,
  getters,
  mutations,
  actions
}
