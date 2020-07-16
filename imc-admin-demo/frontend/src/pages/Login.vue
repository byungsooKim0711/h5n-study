<template>
  <v-content>
    <v-container fluid fill-height>
      <v-layout align-center justify-center>
        <v-flex xs12 sm8 md4>
          <v-card class="elevation-12">
            <v-toolbar dark color="primary">
              <v-toolbar-title>IMC 통합 어드민</v-toolbar-title>
              <v-spacer></v-spacer>
            </v-toolbar>
            <v-card-text>
              <v-form v-on:submit.prevent="login()">
                <v-text-field v-model="username" prepend-icon="person" name="username" label="username" type="text"></v-text-field>
                <v-text-field v-model="password" prepend-icon="lock" name="password" label="password" id="password" type="password"></v-text-field>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="primary" type="submit">Login</v-btn>
                  <!-- <v-btn color="primary" @click="findPassword()">비밀번호 찾기</v-btn> -->
                </v-card-actions>
              </v-form>
            </v-card-text>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>
  </v-content>
</template>

<script>
import { mapGetters } from "vuex";

export default {
  name: "Login",

  data: () => ({
    drawer: null,
    username: '',
    password: '',
    account: {}
  }),

  created() {
    window.history.pushState("", "", "/#/");
  },

  methods: {
    login: function() {
      if (this.username && this.password) {
        let form = new FormData();
        form.append("username", this.username);
        form.append("password", this.password);

        this.$store.dispatch("LOGIN", form);

      } else {
        alert("아이디와 비밀번호를 입력해주세요.");
      }
    },

    findPassword: function() {
      alert("준비 중입니다.");
    }
  },

  props: {
    source: String
  }
}
</script>

<style>
</style>
