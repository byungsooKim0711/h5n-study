// import axios from 'axios'

import axios from "axios";
import router from "../../router";

const state = {
  currentAccount: {
    authorities: [],
    user: {}
  },
}

const getters = {
  getCurrentAccount: function() {
    return state.currentAccount;
  }
}

const mutations = {

  // 권한 정보와 유저 정보 저장
  LOGIN(state, authenticate) {
    state.currentAccount.authorities = authenticate.authorities;
    state.currentAccount.user = authenticate.user;
  },

  // 로그아웃 시 권한정보와 유저 정보를 초기화 한다.
  LOGOUT(state) {
    state.currentAccount.authorities = [];
    state.currentAccount.user = {};
  }
}

const actions = {
  LOGIN ( {commit}, form) {
    return axios.post('/login', form).then(response => {
        commit('LOGIN', response.data);
        router.push("/dashboard");
      })
      .catch(error => {
        if (error.response.status === 401) {
          alert("가입하지 않은 아이디이거나, 잘못된 비밀번호입니다.");
        }
        else {
          alert("일시적인 문제로 다시 시도바랍니다. 문제가 지속될 경우 관리자에게 문의하세요.");
        }
      });
  }
}

export default {
  state,
  getters,
  mutations,
  actions
}
