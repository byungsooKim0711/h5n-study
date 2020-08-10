<template>
  <v-container>
    <v-card-title>
      <v-spacer></v-spacer>
      <v-text-field
        v-model="search"
        append-icon="mdi-magnify"
        label="Search"
        single-line
      ></v-text-field>
    </v-card-title>
    <v-data-table
      :headers="headers"
      :items="userListWithIndex"
      :page.sync="page"
      :items-per-page="itemsPerPage"
      hide-default-footer
      class="elevation-1"
      @page-count="pageCount = $event"
    >
      <template v-slot:[`item.createAt`]="{item}">
        <span>{{item.createAt | moment('YYYY-MM-DD HH:mm:ss')}}</span>
      </template>
      <template v-slot:[`item.modifiedAt`]="{item}">
        <span>{{item.modifiedAt | moment('YYYY-MM-DD HH:mm:ss')}}</span>
      </template>

      <template v-slot:[`item.actions`]="{ item }">
        <v-icon
          small
          @click="showDetails(item)"
        >
          mdi-pencil
        </v-icon>
      </template>

    </v-data-table>
    <div class="text-center pt-2">
      <v-pagination v-model="page" :length="pageCount"></v-pagination>
      <!--<v-text-field
        :value="itemsPerPage"
        label="Items per page"
        type="number"
        @input="itemsPerPage = parseInt($event, 10)"
      ></v-text-field>-->
    </div>
  </v-container>
</template>

<script>
  export default {
    name: "UserList",

    computed: {
      userListWithIndex() {
        return this.userList.map(
          (userList, index) => ({
            ...userList,
            index: index + 1
          }))
      }
    },

    data () {
      return {
        page: 1,
        pageCount: 0,
        itemsPerPage: 10,
        search: "",

        headers: [
          { text: '순번', value: 'index', sortable: false, align: 'start' },
          { text: '회사명', value: 'company' },
          { text: '사업자번호', value: 'bizNum' },
          { text: '담당자', value: 'infoNa' },
          { text: '연락처', value: 'infoCp' },
          { text: '이메일', value: 'infoEm' },
          { text: '생성일', value: 'createAt' },
          { text: '수정일', value: 'modifiedAt' },
          { text: '상세보기', value: 'actions', sortable: false, align: 'center' },
        ],
        userList: [],
      }
    },

    mounted() {
      axios.get("/user", {})
      .then(response => {
        this.userList = response.data;
      })
      .catch(error => {
        console.error(error);
      });
    },

    methods: {
      showDetails(item) {
        // this.$router.push({name: "UserDetail", params: {id : item.id}});
        this.$router.push("/user/" + item.id + "/detail");
      }
    }
  }
</script>

<style scoped>

</style>
