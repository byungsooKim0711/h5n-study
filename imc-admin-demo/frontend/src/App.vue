<template>
  <v-app id="app">
    <Navbar :drawer="drawer"></Navbar>
    <v-content>
      <!-- 툴바 -->
      <Toolbar></Toolbar>
    </v-content>

    <v-content>
      <!-- 라우팅 페이지 -->
      <router-view></router-view>
    </v-content>

    <Footer></Footer>

  </v-app>
</template>

<script>
import Navbar from '@/components/Navbar';
import Toolbar from '@/components/Toolbar';
import Footer from '@/components/Footer';

export default {
  name: 'App',

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
html,
body {
  font-weight: 500;
  font-size: 13px;
}
</style>
