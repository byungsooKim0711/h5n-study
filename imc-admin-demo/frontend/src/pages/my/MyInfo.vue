<template>
  <v-container
    id="my-info"
    fluid
    tag="section"
  >
    <v-row justify="center">
      <v-col cols="12" md="8">
        <v-card>
          <v-form>
            <v-container class="py-0">
              <v-row>
                <v-col cols="12" md="4">
                  <v-text-field label="Company (disabled)" disabled/>
                </v-col>

                <v-col cols="12" md="4">
                  <v-text-field label="User Name"/>
                </v-col>

                <v-col cols="12" md="4">
                  <v-text-field label="Email Address" class="purple-input"/>
                </v-col>

                <v-col cols="12" md="6">
                  <v-text-field label="First Name" class="purple-input"/>
                </v-col>

                <v-col cols="12" md="6">
                  <v-text-field label="Last Name" class="purple-input"/>
                </v-col>

                <v-col cols="12">
                  <v-text-field label="Adress" class="purple-input"/>
                </v-col>

                <v-col cols="12" md="4">
                  <v-text-field label="City" class="purple-input"/>
                </v-col>

                <v-col cols="12" md="4">
                  <v-text-field label="Country" class="purple-input"/>
                </v-col>

                <v-col cols="12" md="4">
                  <v-text-field class="purple-input" label="Postal Code" type="number"/>
                </v-col>

                <v-col cols="12">
                  <v-textarea class="purple-input" label="About Me" value="Lorem ipsum dolor sit amet, consectetur adipiscing elit."/>
                </v-col>

                <v-col cols="12" class="text-right">
                  <v-btn color="success" class="mr-0">
                    Update Profile
                  </v-btn>
                </v-col>
              </v-row>
            </v-container>
          </v-form>
        </v-card>
      </v-col>

      <v-col cols="12" md="4">
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
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "MyInfo",

  data() {
    return {

    }
  },

  created() {
  },

  methods: {

  }
}
</script>
