<template>
  <v-container>
    <v-data-table
      :headers="headers"
      :items="adminListWithIndex"
      class="elevation-1"
    >
      <template v-slot:top>
        <v-toolbar flat color="white">
          <v-toolbar-title>IMC 관리자 관리</v-toolbar-title>
          <v-divider
            class="mx-4"
            inset
            vertical
          ></v-divider>
          <v-spacer></v-spacer>
          <v-dialog v-model="dialog" >
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
                        v-model="editedItem.webUserAuthor"
                        :items="webUserAuthor"
                        :hint="`${editedItem.webUserAuthor.id} ${editedItem.webUserAuthor.authLevel} ${editedItem.webUserAuthor.authName}`"
                        item-text="id"
                        item-value="authLevel"
                        label="어드민 권한"
                        persistent-hint
                        return-object
                        single-line
                      ></v-select>
                    </v-col>
                    <v-col cols="6" sm="6" md="4">
                      <v-text-field v-model="editedItem.userLogin" label="아이디" :disabled="editedIndex != -1"></v-text-field>
                    </v-col>
                    <v-col cols="6" sm="6" md="4">
                      <v-text-field v-model="editedItem.kakaoBizCenterId" label="카카오아이디" :disabled="editedIndex != -1"></v-text-field>
                    </v-col>
                    <v-col cols="6" sm="6" md="4">
                      <v-text-field v-model="editedItem.infoNa" label="이름" :disabled="editedIndex != -1"></v-text-field>
                    </v-col>
                    <v-col cols="6" sm="6" md="4">
                      <v-text-field v-model="editedItem.infoCp" label="전화번호" :disabled="editedIndex != -1"></v-text-field>
                    </v-col>
                    <v-col cols="6" sm="6" md="4">
                      <v-text-field v-model="editedItem.infoEm" label="이메일" :disabled="editedIndex != -1"></v-text-field>
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
      <template v-slot:item.actions="{ item }">
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
export default {
  data: () => ({
    dialog: false,

    // 관리자 리스트
    adminList: [],

    // 권한 리스트
    webUserAuthor: [],

    // 테이블 헤더
    headers: [
      { text: '순번', align: 'start', sortable: false, value: 'index' },
      { text: '권한 레벨', value: 'webUserAuthor.authName' },
      { text: '아이디', value: 'userLogin' },
      { text: '카카오 ID', value: 'kakaoBizCenterId' },
      { text: '사용여부', value: 'activeYn' },
      { text: '이름', value: 'infoNa' },
      { text: '전화번호', value: 'infoCp' },
      { text: '이메일', value: 'infoEm' },
      { text: '등록일', value: 'createAt' },
      { text: '수정일', value: 'modifiedAt' },
      { text: '비밀번호 오류 횟수', value: 'failCount' },
      { text: '수정', value: 'actions', sortable: false },
    ],

    // 수정인지, 추가인지
    editedIndex: -1,
    editedItem: {
      "webUserAuthor": {

      },
      "userLogin": "",
      "kakaoBizCenterId": "",
      "infoNa": "",
      "infoCp": "",
      "infoEm": ""
    },
    defaultItem: {
      "webUserAuthor": {

      },
      "userLogin": "",
      "kakaoBizCenterId": "",
      "infoNa": "",
      "infoCp": "",
      "infoEm": ""
    },
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
    }
  },

  watch: {
    dialog (val) {
      val || this.close()
    },
  },

  mounted () {
    axios.get("/admin", {
    })
    .then(response => {
      this.adminList = response.data;
    })
    .catch(error => {
      console.log(error);
      alert("관리자 정보를 가져오는데 실패하였습니다.");
    });

    this.getUserAuthor();
  },

  methods: {
    editItem (item) {
      this.editedIndex = this.adminList.findIndex(a => a.id == item.id);
      this.editedItem = Object.assign({}, item);
      this.dialog = true;
    },

    deleteItem (item) {
      const index = this.adminList.indexOf(item)
      confirm('관리자를 미사용처리 하시겠습니까?') && this.adminList.splice(index, 1);
    },

    close () {
      this.dialog = false
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
        this.selectAuthor = {};
      })
    },

    save () {
      if (this.editedIndex > -1) {
        Object.assign(this.adminList[this.editedIndex], this.editedItem)
      } else {
        this.adminList.push(this.editedItem)
      }
      this.close()
    },

    getUserAuthor () {
      axios.get("/admin/author", {

      })
      .then(response => {
        this.webUserAuthor = response.data;
        // this.webUserAuthor.findIndex(author => author.id)

        // this.defaultItem.webUserAuthor =
      })
      .catch(error => {
        console.log(error);
        alert("권한 정보를 가져오는데 실패하였습니다.");
      });
    }
  },
}
</script>
