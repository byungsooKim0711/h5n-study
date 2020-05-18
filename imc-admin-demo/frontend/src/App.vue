<template>
  <v-app id="app">
    <Navbar></Navbar>
    <v-content>
      <template>
        <!-- Toolbar -->
        <!-- <v-toolbar color="teal" dark app> -->
        <v-toolbar class="blue-grey darken-2" hide-on-scroll>
          <v-app-bar-nav-icon></v-app-bar-nav-icon>
          <v-toolbar-title class="white--text">휴머스온 IMC 통합 어드민</v-toolbar-title>
          <v-spacer></v-spacer>
          <!-- <v-btn icon>
            <v-icon>search</v-icon>
          </v-btn> -->
          <v-btn icon>
            <v-icon>apps</v-icon>
          </v-btn>
          <v-btn icon>
            <v-icon>refresh</v-icon>
          </v-btn>
          <v-btn icon>
            <v-icon>more_vert</v-icon>
          </v-btn>
        </v-toolbar>
      </template>

      <!-- 라우팅 페이지 -->
      <router-view></router-view>
    </v-content>
  </v-app>
</template>

<script>
import Navbar from '@/components/Navbar';

export default {
  name: 'App',

  components: {
    Navbar,
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
