<template>
  <v-container id="my-info" fluid tag="section">
    <v-row>내 정보</v-row>

    <v-row justify="center">
      <v-col cols="12" md="12">
        <v-card>
          <v-form ref="form">
            <v-container class="py-0">
              <v-row>
                <v-col cols="12" dense>
                  <v-text-field label="로그인 아이디" v-model="myInfo.userLogin" disabled/>
                </v-col>

                <v-col cols="12" dense>
                  <v-text-field label="이름" v-model="myInfo.infoNa" counter="64" required
                    ref="infoNa"
                    :rules="[
                      () => !!myInfo.infoNa || 'This field is required.',
                      () => !!myInfo.infoNa && myInfo.infoNa.length <= 64 || 'Name must be less than 64 characters.'
                    ]"
                  />
                </v-col>

                <v-col cols="12" dense>
                  <v-text-field label="연락처" v-model="myInfo.infoCp" class="purple-input" counter="64" required
                    ref="infoCp"
                    :rules="[
                      () => !!myInfo.infoCp || 'This field is required.',
                      () => !!myInfo.infoCp && myInfo.infoCp.length <= 64 || 'Phone number must be less than 64 characters.'
                    ]"
                  />
                </v-col>

                <v-col cols="12" dense>
                  <v-text-field label="이메일" v-model="myInfo.infoEm" class="purple-input" counter="64" required type="email"
                    ref="infoEm"
                    :rules="[
                      () => !!myInfo.infoEm || 'This field is required.',
                      () => validateEmailFormat(myInfo.infoEm) || 'Malformed email address',
                      () => !!myInfo.infoEm && myInfo.infoEm.length <= 64 || 'Email must be less than 64 characters.'
                    ]"
                  />
                </v-col>

                <!-- <v-col cols="12" md="6"> -->
                <v-col cols="12" dense>
                  <v-text-field label="카카오 비즈센터 아이디" v-model="myInfo.kakaoBizCenterId" class="purple-input"  counter="128" required
                    ref="kakaoBizCenterId"
                    :rules="[
                      () => !!myInfo.kakaoBizCenterId || 'This field is required.',
                      () => validateEmailFormat(myInfo.kakaoBizCenterId) || 'Malformed email address',
                      () => !!myInfo.kakaoBizCenterId && myInfo.kakaoBizCenterId.length <= 128 || 'Kakao biz center id must be less than 45 characters.'
                    ]"
                  />
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
    }),

    form() {
      return {
        kakaoBizCenterId: this.myInfo.kakaoBizCenterId,
        infoNa: this.myInfo.infoNa,
        infoCp: this.myInfo.infoCp,
        infoEm: this.myInfo.infoEm
      }
    }
  },

  mounted() {
    this.getMyInfo();
  },

  methods: {
    getMyInfo() {
      axios.get("/myinfo/" + this.currentAccount.user.id, {

      })
      .then(response => {
        this.myInfo = response.data;
      })
      .catch(error => {
        console.error(error);
      });
    },

    updateMyInfo() {
      let isValid = true;

      // Check Validation
      Object.keys(this.form).forEach(f => {
        isValid = (this.form[f] && this.$refs[f].valid) ? isValid : false;
        this.$refs[f].validate(true);
      })

      if (!isValid) {
        alert("필수항목 및 유효성검사를 확인하세요.");
        return;
      }

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

    },

    // validate function
    validateEmailFormat(email) {
      // return /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(email)
      return /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,}$/.test(email)
        ? true
        : false;
    },

    // 패스워드는 공백 없이 영문, 숫자, 특수문자를 모두 포함하여 8자 이상 작성
    validatePasswordFormat(password) {
      return /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/.test(password)
        ? true
        : false;
    }
  }
}
</script>
