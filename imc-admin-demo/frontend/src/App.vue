<template>
  <v-app id="app">
    <!-- <Navbar :drawer="drawer" v-if="isLogin()"></Navbar> -->
    <axios-interceptor></axios-interceptor>
    <Navbar v-if="isLogin()"></Navbar>
    <v-content>
      <!-- 툴바 -->
      <Toolbar v-if="isLogin()"></Toolbar>
    </v-content>

    <v-content>
      <!-- 라우팅 페이지 -->
      <router-view></router-view>
    </v-content>

    <Footer v-if="isLogin()"></Footer>

  </v-app>
</template>

<script>
import Navbar from '@/components/Navbar';
import Toolbar from '@/components/Toolbar';
import Footer from '@/components/Footer';

import AxiosInterceptor from '@/components/AxiosInterceptor';

import { mapGetters } from "vuex";

export default {
  name: 'App',

  computed: {
    ...mapGetters({
      currentAccount: 'getCurrentAccount'
    })
  },

  components: {
    Navbar,
    Toolbar,
    Footer,
    AxiosInterceptor
  },

  data() {
    return {

    }
  },

  methods: {

    // this.currentAccount.user.id !== undefiend 해도 왜 에러가 나는가..
    isLogin() {
      try {
        if (this.currentAccount.user.id) {
          return true;
        } else {
          return false;
        }
      } catch (e) {
        return false;
      }

    }
  }
}
</script>

<style>
/* Loading spinner*/
.loading { display: none; z-index: 99999; }
.loading.on { display: block; position: absolute; top: 0; left: 0; width: 100%; height: 100%; margin: 0; padding: 0; background-color: #00000055; text-align: center; }
.spinner-wrap { display: inline-block; position: relative; top: 50%; }
</style>
