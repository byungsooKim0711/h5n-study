<template>
  <v-app id="app">
    <Navbar :drawer="drawer" v-if="currentRoute != '/' && currentRoute != 'Login'"></Navbar>
    <v-content>
      <!-- 툴바 -->
      <Toolbar v-if="currentRoute != '/' && currentRoute != 'Login'"></Toolbar>
    </v-content>

    <v-content>
      <!-- 라우팅 페이지 -->
      <router-view></router-view>
    </v-content>

    <Footer v-if="currentRoute != '/' && currentRoute != 'Login'"></Footer>

  </v-app>
</template>

<script>
import Navbar from '@/components/Navbar';
import Toolbar from '@/components/Toolbar';
import Footer from '@/components/Footer';

import { mapGetters } from "vuex";

export default {
  name: 'App',

  computed: {
    ...mapGetters({
      currentRoute: 'getCurrentRoute'
    })
  },

  components: {
    Navbar,
    Toolbar,
    Footer
  },

  data() {
    return {
      drawer: true
    }
  },

  methods: {
    toggleDrawer() {
      this.drawer = !this.drawer;
    }
  }
}
</script>

<style>
/* html,
body {
  font-weight: 500;
  font-size: 13px;
} */
</style>
