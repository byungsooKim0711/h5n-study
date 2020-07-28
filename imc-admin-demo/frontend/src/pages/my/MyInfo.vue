<template>
  <v-container id="my-info" fluid tag="section">
    <v-row>내 정보</v-row>

    <v-row justify="center">
      <v-col cols="12" md="12">
        <v-card>
          <v-form>
            <v-container class="py-0">
              <v-row>
                <v-col cols="12" dense>
                  <v-text-field label="로그인 아이디" v-model="myInfo.userLogin" disabled/>
                </v-col>

                <v-col cols="12" dense>
                  <v-text-field label="이름" v-model="myInfo.infoNa" counter="64" required/>
                  <!-- <v-text-field label="이름" v-model="myInfo.infoNa" counter="64"
                  :rules="[() => !!myInfo.infoNa && myInfo.infoNa.length <= 64]"
                  /> -->
                </v-col>

                <v-col cols="12" dense>
                  <v-text-field label="연락처" v-model="myInfo.infoCp" class="purple-input" counter="64" required/>
                </v-col>

                <v-col cols="12" dense>
                  <v-text-field label="이메일" v-model="myInfo.infoEm" class="purple-input" counter="64" required/>
                </v-col>

                <!-- <v-col cols="12" md="6"> -->
                <v-col cols="12" dense>
                  <v-text-field label="카카오 비즈센터 아이디" v-model="myInfo.kakaoBizCenterId" class="purple-input"  counter="128" required/>
                </v-col>

                <v-col cols="12" class="text-right">
                  <v-btn color="error" class="mr-0">
                    비밀번호 변경
                  </v-btn>
                  <v-btn color="success" class="mr-0" @click="updateMyInfo()">
                    프로필 수정
                  </v-btn>
                </v-col>
              </v-row>
            </v-container>
          </v-form>
        </v-card>
      </v-col>

      <!-- <v-col cols="12" md="4">
        <v-card>
          <v-card-text class="text-center">


            <h4 class="display-2 font-weight-light mb-3 black--text">
              내정보 화면은 테스트중 입니다.
            </h4>

            <p class="font-weight-light grey--text">
              무시하세요.
            </p>

            <v-btn color="success" rounded class="mr-0">
              버튼입니다.
            </v-btn>
          </v-card-text>
        </v-card>
      </v-col> -->
    </v-row>
  </v-container>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  name: "MyInfo",

  data() {
    return {
      // 내 정보
      myInfo: {},

      // 비밀번호 변경
      passwordChangeRequest: {
        oldPassword: "",
        newPassword: "",
        checkNewPassword: ""
      }
    }
  },

  computed: {
    ...mapGetters({
      currentAccount: 'getCurrentAccount'
    })
  },

  mounted() {
    this.getMyInfo();
  },

  methods: {
    getMyInfo() {
      axios.get("/myinfo/" + this.currentAccount.user.id, {

      })
      .then(response => {
        console.log(response);
        this.myInfo = response.data;
      })
      .catch(error => {
        console.error(error);
      });
    },

    updateMyInfo() {
      axios.put("/myinfo/" + this.myInfo.id, this.myInfo, {

      })
      .then(response => {
        console.info(response);
      })
      .catch(error => {
        console.error(error);
      })
    },

    changePassword() {

    }
  }
}
</script>
