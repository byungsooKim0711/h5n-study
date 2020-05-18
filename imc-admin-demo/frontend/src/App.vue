<template>
  <v-app id="app">
    <Navbar :drawer="drawer"></Navbar>
    <v-content>
      <template>
        <!-- Toolbar -->
        <!-- <v-toolbar color="teal" dark app> -->
        <v-toolbar class="blue-grey darken-2" hide-on-scroll elevation="0">
          <v-app-bar-nav-icon @click="toggleDrawer()"></v-app-bar-nav-icon>
          <v-toolbar-title class="white--text">휴머스온 IMC 통합 어드민</v-toolbar-title>
          <v-spacer></v-spacer>

          <!-- 검색 버튼 -->
          <!-- <v-btn icon>
            <v-icon>search</v-icon>
          </v-btn> -->

          <!-- App 버튼 -->
          <v-btn icon>
            <v-icon>apps</v-icon>
          </v-btn>

          <!-- 새로고침 버튼 -->
          <v-btn icon>
            <v-icon>refresh</v-icon>
          </v-btn>

          <!-- 더보기? 버튼 -->
          <v-menu bottom left>
            <template v-slot:activator="{ on }">
              <v-btn dark icon v-on="on">
                <v-icon>more_vert</v-icon>
              </v-btn>
            </template>

            <v-list>
              <!-- <v-list-item v-for="(item, i) in items" :key="i" @click="">
                <v-list-item-title>{{ item.title }}</v-list-item-title>
              </v-list-item> -->

              <v-list-item link>
                <v-icon>mdi-account</v-icon>
                <v-list-item-title>내 정보</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-icon>mdi-logout</v-icon>
                <v-list-item-title>로그아웃</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </v-toolbar>
      </template>
    </v-content>


    <v-content>
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
