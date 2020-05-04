<template>
  <v-app id="app">
    <div class="app-container">
      <NavigationDrawer :toggle="drawer"/>
      <v-content>
        <router-view/>
      </v-content>
    </div>
  </v-app>
</template>

<script>
import NavigationDrawer from '@/components/NavigationDrawer';

  export default {
    name: 'App',

    components: {
      NavigationDrawer
    },

    data() {
      return {
        drawer: false
      }
    }
  }
</script>