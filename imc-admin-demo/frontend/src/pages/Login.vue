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
import axios from 'axios';

export default {
  data: () => ({
    drawer: null,
    username: '',
    password: '',
    account: {}
  }),

  created() {
    // window.history.pushState("", "", "/#/");
  },

  methods: {
    login: function() {
      let form = new FormData();
      form.append("username", this.username);
      form.append("password", this.password);

      let me = this;
      axios.post('/login', form)
      .then(response => {
        console.log(response);
      })
      .catch(error => {
        if (error.response.status === 401) {
          alert("가입하지 않은 아이디이거나, 잘못된 비밀번호입니다.");
        }
        if (error.response.status >= 500 && error.response.status < 600) {
          alert("일시적인 문제로 다시 시도바랍니다. 문제가 지속될 경우 관리자에게 문의하세요.");
        }
     })
    }
  },

  props: {
    source: String
  }
}
</script>
<style>
</style>
