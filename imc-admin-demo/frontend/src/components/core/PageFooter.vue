<template>
  <v-footer height="auto" dark>
    <v-card
      flat
      tile
      :color="$root.themeColor"
      class="text-xs-center">
      <v-card-text>
        <v-btn
          v-for="icon in icons"
          :key="icon"
          class="mx-3 white--text"
          icon>
          <v-icon size="24px">{{ icon }}</v-icon>
        </v-btn>
      </v-card-text>

      <v-card-text class="white--text pt-0">
        IMC-ADMIN-DEMO
      </v-card-text>

      <v-divider></v-divider>

      <v-card-text class="white--text">
        &copy;2020 — <strong>KIMBS</strong>
      </v-card-text>
    </v-card>
  </v-footer>
</template>

<script>
  export default {
    name: 'page-footer',
    data() {
      return {
        icons: [
          'fab fa-facebook',
          'fab fa-twitter',
          'fab fa-google-plus',
          'fab fa-linkedin',
          'fab fa-instagram'
        ]
      }
    }
  }
</script>
