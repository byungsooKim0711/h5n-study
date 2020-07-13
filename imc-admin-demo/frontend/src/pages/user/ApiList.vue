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
      :items="apiList"
      :page.sync="page"
      :items-per-page="itemsPerPage"
      hide-default-footer
      class="elevation-1"
      @page-count="pageCount = $event"
    >
      <template v-slot:item.createAt="{item}">
        <span>{{item.createAt | moment('YYYY-MM-DD HH:mm:ss')}}</span>
      </template>
      <template v-slot:item.modifiedAt="{item}">
        <span>{{item.modifiedAt | moment('YYYY-MM-DD HH:mm:ss')}}</span>
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
    name: "ApiList",

    data () {
      return {
        page: 1,
        pageCount: 0,
        itemsPerPage: 10,
        search: "",

        headers: [
          { text: '순번', value: '$index', sortable: false, align: 'start' },
          { text: '회사명', value: 'webUserId' },
          { text: 'API KEY', value: 'apiKey' },
          { text: '생성일', value: 'createAt' },
          { text: '수정일', value: 'modifiedAt' },
        ],

        apiList: [],
      }
    },

    mounted() {
      axios.get("/user/api", {})
      .then(response => {
        console.log(response);
        this.apiList = response.data;
      })
      .catch(error => {
        console.error(error);
      });
    }
  }
</script>

<style scoped>

</style>
