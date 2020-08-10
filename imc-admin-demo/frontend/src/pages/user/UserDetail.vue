<template>
  <v-container>
    {{webUserInfo}}

    <div class="row">
			<div class="col-md-12 col-sm-12 col-xs-12">
				<div class="panel panel-default">
					<table class="table table-bordered table-responsive table_detail">
						<colgroup>
							<col width="5%">
							<col width="10%">
							<col width="*">
						</colgroup>
						<tbody>
							<tr>
								<th colspan="2">회사명</th>
								<td colspan="5">{{webUserInfo.company}}</td>
							</tr>
							<tr>
								<th colspan="2">아이디</th>
								<td colspan="5">{{webUserInfo.userLogin}}</td>
							</tr>
							<tr>
								<th colspan="2">사업자 번호</th>
								<td colspan="5">{{webUserInfo.bizNum}}</td>
							</tr>
							<!-- <tr>
								<th colspan="2">비고</th>
								<td colspan="5">
									<textarea style="width:100%; resize:vertical" class="form-control" rows="8" readonly="readonly">${user.remark}</textarea>
								</td>
							</tr> -->
						</tbody>
					</table>
				</div>
			</div>
		</div>
  </v-container>
</template>

<script>
export default {
  props: {
    id: Number
  },

  watch: {
    id: {
      handler() {
        axios.get("/user/" + this.id, {})
        .then(response => {
          this.webUserInfo = response.data;
        })
        .catch(error => {
          console.error(error);
        })
      },
      immediate: true
    }
  },

  created() {

  },

  data() {
    return {
      webUserInfo: {},
    }
  },

  methods: {

  }
}
</script>
