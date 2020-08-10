<template>
  <v-container>
    <v-data-table
      :headers="headers"
      :items="adminListWithIndex"
      class="elevation-1"
    >
      <template v-slot:[`item.activeYn`]="{item}">
        <span>{{item.activeYn | activeYnFilter}}</span>
      </template>
      <template v-slot:[`item.createAt`]="{item}">
        <span>{{item.createAt | moment('YYYY-MM-DD HH:mm:ss')}}</span>
      </template>
      <template v-slot:[`item.modifiedAt`]="{item}">
        <span>{{item.modifiedAt | moment('YYYY-MM-DD HH:mm:ss')}}</span>
      </template>

      <template v-slot:top>
        <v-toolbar flat color="white">
          <v-toolbar-title>IMC 관리자 관리</v-toolbar-title>
          <v-divider
            class="mx-4"
            inset
            vertical
          ></v-divider>
          <v-spacer></v-spacer>
          <v-dialog v-model="dialog" max-width="800px" ref="form">
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                color="primary"
                dark
                class="mb-2"
                v-bind="attrs"
                v-on="on"
              >관리자 추가</v-btn>
            </template>
            <v-card>
              <v-card-title>
                <span class="headline">{{ formTitle }}</span>
              </v-card-title>

              <v-card-text>
                <v-container>
                  <v-row>
                    <v-col cols="6" sm="6" md="4">
                      <v-select
                        v-model="selectAuthor"
                        :items="webUserAuthors"
                        :hint="`${editedItem.authId || ''} ${editedItem.authLevel || ''} ${editedItem.authName || ''}`"
                        item-text="id"
                        item-value="authLevel"
                        label="어드민 권한"
                        persistent-hint
                        return-object
                        single-line
                      ></v-select>
                    </v-col>
                    <v-col cols="6" sm="6" md="4">
                      <v-text-field v-model="editedItem.userLogin" label="아이디" counter="45" :disabled="editedIndex !== -1"
                        ref="userLogin"
                        :rules="[
                          () => !!editedItem.userLogin || 'This field is required.',
                          () => !!editedItem.userLogin && editedItem.userLogin.length <= 45 || 'Login id must be less than 45 characters.'
                        ]"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="6" sm="6" md="4">
                      <v-text-field v-model="editedItem.kakaoBizCenterId" label="카카오아이디" counter="128" :disabled="editedIndex !== -1" type="email"
                        ref="kakaoBizCenterId"
                        :rules="[
                          () => !!editedItem.kakaoBizCenterId || 'This field is required.',
                          () => validateEmailFormat(editedItem.kakaoBizCenterId) || 'Malformed email address',
                          () => !!editedItem.kakaoBizCenterId && editedItem.kakaoBizCenterId.length <= 128 || 'Kakao biz center id must be less than 128 characters.'
                        ]"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="6" sm="6" md="4">
                      <v-text-field v-model="editedItem.infoNa" label="이름" counter="64" :disabled="editedIndex !== -1"
                        ref="infoNa"
                        :rules="[
                          () => !!editedItem.infoNa || 'This field is required.',
                          () => !!editedItem.infoNa && editedItem.infoNa.length <= 64 || 'Name must be less than 64 characters.'
                        ]"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="6" sm="6" md="4">
                      <v-text-field v-model="editedItem.infoCp" label="전화번호" counter="64" :disabled="editedIndex !== -1"
                        ref="infoCp"
                        :rules="[
                          () => !!editedItem.infoCp || 'This field is required.',
                          () => !!editedItem.infoCp && editedItem.infoCp.length <= 64 || 'Phone number must be less than 64 characters.'
                        ]"
                      ></v-text-field>
                    </v-col>
                    <v-col cols="6" sm="6" md="4">
                      <v-text-field v-model="editedItem.infoEm" label="이메일" counter="64" :disabled="editedIndex !== -1" type="email"
                        ref="infoEm"
                        :rules="[
                          () => !!editedItem.infoEm || 'This field is required.',
                          () => validateEmailFormat(editedItem.infoEm) || 'Malformed email address',
                          () => !!editedItem.infoEm && editedItem.infoEm.length <= 64 || 'Email must be less than 64 characters.'
                        ]"
                      ></v-text-field>
                    </v-col>
                  </v-row>
                </v-container>
              </v-card-text>

              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="close()">취소</v-btn>
                <v-btn color="blue darken-1" text @click="save()">저장</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-toolbar>
      </template>
      <template v-slot:[`item.actions`]="{ item }">
        <v-icon
          small
          class="mr-2"
          @click="editItem(item)"
        >
          mdi-pencil
        </v-icon>
        <v-icon
          small
          @click="deleteItem(item)"
        >
          mdi-delete
        </v-icon>
      </template>
    </v-data-table>
  </v-container>
</template>

<script>
import Vue from 'vue';
export default {
  data: () => ({
    dialog: false,

    // 관리자 리스트
    adminList: [],

    // 권한 리스트
    webUserAuthors: [],

    // 테이블 헤더
    headers: [
      { text: '순번', align: 'start', sortable: false, value: 'index' },
      { text: '권한 레벨', value: 'authName' },
      { text: '아이디', value: 'userLogin' },
      { text: '카카오 ID', value: 'kakaoBizCenterId' },
      { text: '사용여부', value: 'activeYn' },
      { text: '이름', value: 'infoNa' },
      { text: '전화번호', value: 'infoCp' },
      { text: '이메일', value: 'infoEm' },
      { text: '등록일', value: 'createAt' },
      { text: '수정일', value: 'modifiedAt' },
      { text: '오류 횟수', value: 'failCount' },
      { text: '수정', value: 'actions', sortable: false },
    ],

    // 수정인지, 추가인지
    editedIndex: -1,
    editedItem: {
      // 권한 정보
      "authId": "",
      "authLevel": "",
      "authName": "",

      // 유저 정보
      "userLogin": "",
      "kakaoBizCenterId": "",
      "infoNa": "",
      "infoCp": "",
      "infoEm": ""
    },
    defaultItem: {
      // 권한 정보
      "authId": "",
      "authLevel": "",
      "authName": "",

      // 유저 정보
      "userLogin": "",
      "kakaoBizCenterId": "",
      "infoNa": "",
      "infoCp": "",
      "infoEm": ""
    },

    selectAuthor: {}
  }),

  computed: {
    formTitle () {
      return this.editedIndex === -1 ? '관리자 추가' : '관리자 정보 수정'
    },

    adminListWithIndex() {
      return this.adminList.map(
        (adminList, index) => ({
          ...adminList,
          index: index + 1
        }))
    },

    form() {
      return {
        userLogin: this.editedItem.userLogin,
        kakaoBizCenterId: this.editedItem.kakaoBizCenterId,
        infoNa: this.editedItem.infoNa,
        infoCp: this.editedItem.infoCp,
        infoEm: this.editedItem.infoEm
      }
    }
  },

  filters: {
    activeYnFilter(x) {
      if (x === true) {
        return "사용";
      } else if (x === false) {
        return "미사용";
      } else {
        return x;
      }
    }
  },

  watch: {
    dialog (val) {
      val || this.close()
    },
    selectAuthor (author) {
      this.editedItem.authId = author.id;
      this.editedItem.authName = author.authName;
      this.editedItem.authLevel = author.authLevel;
    }
  },

  mounted () {
    this.getAdminUserList();
    this.getUserAuthor();
  },

  methods: {
    editItem (item) {
      this.editedIndex = this.adminList.findIndex(a => a.id === item.id);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },

    deleteItem (item) {
      let admin = this.adminList[this.adminList.findIndex(a => a.id === item.id)];
      if (admin.activeYn === true) {
        if (confirm('관리자를 미사용처리 하시겠습니까?')) {
          admin.activeYn = false;
          this.updateAdminUserActiveYn(admin);
        }
      } else {
        if (confirm('관리자를 사용처리 하시겠습니까?')) {
          admin.activeYn = true;
          this.updateAdminUserActiveYn(admin);
        }
      }
    },

    close () {
      this.dialog = false
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
        this.selectAuthor = {};

        // validation message off
        Object.keys(this.form).forEach(f => {
          this.$refs[f].reset();
        })
      })
    },

    save () {
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


      if (this.editedIndex > -1) {
        axios.put("/admin/" + this.adminList[this.editedIndex].id, this.editedItem, {})
        .then(response => {
          Vue.set(this.adminList, this.adminList.findIndex(a => a.id === response.data.id), response.data);
        })
        .catch(error => {
          console.error(error);
          // alert("관리자 권한을 수정하는데 실파였습니다.");
        });
      } else {
        axios.post('/admin', this.editedItem, {})
          .then(response => {
            this.adminList.push(response.data);
          })
          .catch(error => {
            console.error(error);
            // alert("관리자를 등록하는데 오류가 발생하였습니다.");
          });
      }
      this.close()
    },

    getUserAuthor () {
      axios.get("/admin/author", {

      })
      .then(response => {
        this.webUserAuthors = response.data;
      })
      .catch(error => {
        console.log(error);
        // alert("권한 정보를 가져오는데 실패하였습니다.");
      });
    },

    getAdminUserList () {
      axios.get("/admin", {})
      .then(response => {
        this.adminList = response.data;
      })
      .catch(error => {
        console.log(error);
        // alert("관리자 정보를 가져오는데 실패하였습니다.");
      });
    },

    updateAdminUserActiveYn(admin) {
      axios.put("/admin/" + admin.id, admin, {})
      .then(response => {
        Vue.set(this.adminList, this.adminList.findIndex(a => a.id === response.data.id), response.data);
      })

      .catch(error => {
        console.error(error);
        // alert("관리자 권한을 수정하는데 실패하였습니다.");
      });
    },


    // validate function
    validateEmailFormat(email) {
      // return /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(email)
      return /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,}$/.test(email)
        ? true
        : false;
    }
  }
}
</script>
