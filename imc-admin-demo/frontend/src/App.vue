<template>
  <v-app id="app">
    <Navbar></Navbar>
    <v-content>
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
