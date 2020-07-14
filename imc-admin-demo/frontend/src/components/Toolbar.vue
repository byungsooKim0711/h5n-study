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
    <v-menu bottom right>
      <template v-slot:activator="{ on }">
        <v-btn dark icon v-on="on">
          <v-icon>more_vert</v-icon>
          <!-- {{currentAccount.user.infoNa}} -->
        </v-btn>
      </template>


      <v-list>
        <!-- <v-list-item v-for="(item, i) in items" :key="i" @click="">
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item> -->

        <v-list-item route to="/myinfo">
          <v-icon>mdi-account</v-icon>
          <v-list-item-title>내정보</v-list-item-title>
        </v-list-item>
        <v-list-item @click="logout()">
          <v-icon>mdi-logout</v-icon>
          <v-list-item-title>로그아웃</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
  </v-toolbar>
</template>

<script>
import { eventBus } from '@/EventBus.js';
import { mapGetters } from 'vuex';

export default {
  name: 'toolbar',

  computed: {
    ...mapGetters({
      currentAccount: 'getCurrentAccount'
    })
  },

  data() {
    return {
      drawer: true
    }
  },

  methods: {
    toggleDrawer() {
      this.drawer = !this.drawer;

      this.$emit('drawer', this.drawer);
      eventBus.$emit('drawer', this.drawer);
    },

    logout() {
      axios.get("/logout", {})
      .then(response => {
        console.log(response);
        this.$store.commit("LOGOUT");
        this.$router.replace("/");
        alert("로그아웃되었습니다.");
      })
      .catch(error => {
        console.log(error);
      })
    }
  }
}
</script>
