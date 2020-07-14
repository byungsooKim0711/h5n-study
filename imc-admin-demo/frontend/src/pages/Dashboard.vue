<template>
  <v-container id="dashboard" fluid>
    "Dashboard"
    <!-- <v-row dense>
      <v-col cols="2">
        <v-card color="#F7FFF7">
          <v-card-title class="headline">금월 과금발송</v-card-title>
          <v-card-subtitle class="headline">7,912,189</v-card-subtitle>
          &lt;!&ndash; <v-card-actions>
            <v-btn text>Listen Now</v-btn>
          </v-card-actions> &ndash;&gt;
        </v-card>
      </v-col>
      <v-col cols="2">
        <v-card color="#1A535C" dark>
          <v-card-title class="headline">금월 과금액</v-card-title>
          <v-card-subtitle class="headline">142,953,246</v-card-subtitle>
        </v-card>
      </v-col>
      <v-col cols="2">
        <v-card color="#4ECDC4" dark>
          <v-card-title class="headline">금월 GP</v-card-title>
          <v-card-subtitle class="headline">24,051,087</v-card-subtitle>
        </v-card>
      </v-col>
      <v-col cols="2">
        <v-card color="#FF6B6B" dark>
          <v-card-title class="headline">금일 과금발송</v-card-title>
          <v-card-subtitle class="headline">830,151건</v-card-subtitle>
        </v-card>
      </v-col>
      <v-col cols="2">
        <v-card color="#FFE66D" dark>
          <v-card-title class="headline">금일 과금액</v-card-title>
          <v-card-subtitle class="headline">18,923,535원</v-card-subtitle>
        </v-card>
      </v-col>
      <v-col cols="2">
        <v-card color="#6ec2de" dark>
          <v-card-title class="headline">AGENT 수</v-card-title>
          <v-card-subtitle class="headline">9912</v-card-subtitle>
        </v-card>
      </v-col>

      &lt;!&ndash; TODO: 스타일 안쓰고, 맨 왼쪽으로 어떻게...?? &ndash;&gt;
      &lt;!&ndash; <v-row style="text-align: right">
        <v-col>
          <v-btn>금월 실발송 업체 수 : 346</v-btn>
        </v-col>
      </v-row> &ndash;&gt;
    </v-row>
    <v-row dense>
      <v-col justify-self="end">
        <v-dialog v-model="dialog" scrollable max-width="300px">
          <template v-slot:activator="{ on }">
            <v-btn text color="success" dark v-on="on">금월 실발송 업체 수</v-btn>
          </template>
          <v-card>
            <v-card-title>금월 실발송 업체</v-card-title>
            <v-divider></v-divider>
            <v-card-text style="height: 300px;">

            </v-card-text>
            <v-divider></v-divider>
            <v-card-actions>
              <v-btn color="blue darken-1" text @click="closeDialog(false)">닫기</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-col>
    </v-row>

    <v-row>
      <v-col>
        <v-timeline align-top>
          <v-timeline-item
            v-for="(timeline, i) in timelines"
            :key="i"
            :color="timeline.color"
            :icon="timeline.icon"
            fill-dot
            large
          >
            <v-card class="pa-6">
              <v-chip
                :color="timeline.color"
                class="overline mb-3"
                small
              >
                {{ timeline.chip }}
              </v-chip>

              <p
                class="subtitle-1 font-weight-light"
                v-text="timeline.text"
              />

              <div
                class="text-uppercase body-2"
                v-text="timeline.subtext"
              />

              <template v-if="timeline.action">
                <v-divider class="mb-3" />

                <v-menu
                  v-model="menu"
                  bottom
                  offset-y
                  origin="top left"
                  right
                  transition="scale-transition"
                >
                  <template v-slot:activator="{ attrs, on }">
                    <v-btn
                      v-bind="attrs"
                      :color="timeline.action"
                      large
                      rounded
                      v-on="on"
                    >
                      <v-icon
                        left
                        v-text="timeline.actionIcon"
                      />
                      <v-icon right>
                        {{ menu ? 'mdi-menu-up' : 'mdi-menu-down' }}
                      </v-icon>
                    </v-btn>
                  </template>

                  <v-sheet>
                    <v-list>
                      <v-list-item
                        v-for="a in timeline.actions"
                        :key="a"
                        link
                      >
                        <v-list-item-title v-text="a" />
                      </v-list-item>
                    </v-list>
                  </v-sheet>
                </v-menu>
              </template>
            </v-card>
          </v-timeline-item>
        </v-timeline>
      </v-col>
    </v-row> -->
  </v-container>
</template>

<script>
  export default {
    name: 'DashboardPagesTimeline',
    methods: {
      closeDialog(close) {
        this.dialog = close;
      }
    },
    data: () => ({
      menu: false,
      dialog: false,
      timelines: [
        {
          chip: 'Some title',
          color: 'error',
          icon: 'mdi-briefcase',
          text: 'Wifey made the best Father\'s Day meal ever. So thankful so happy so blessed. Thank you for making my family We just had fun with the “future” theme !!! It was a fun night all together ... The always rude Kanye Show at 2am Sold Out Famous viewing @ Figueroa and 12th in downtown.',
          subtext: '11 hours ago via twitter',
        },
        {
          chip: 'Another one',
          color: 'success',
          icon: 'mdi-puzzle',
          text: 'Thank God for the support of my wife and real friends. I also wanted to point out that it’s the first album to go number 1 off of streaming!!! I love you Ellen and also my number one design rule of anything I do from shoes to music to homes is that Kim has to like it....',
        },
        {
          chip: 'Another title',
          color: 'info',
          icon: 'mdi-fingerprint',
          text: 'Called I Miss the Old Kanye That’s all it was Kanye And I love you like Kanye loves Kanye Famous viewing @ Figueroa and 12th in downtown LA 11:10PM. What if Kanye made a song about Kanye Royère doesn\'t make a Polar bear bed but the Polar bear couch is my favorite piece of furniture we own It wasn’t any Kanyes Set on his goals Kanye',
          action: 'info',
          actionIcon: 'mdi-wrench',
          actions: [
            'Action',
            'Another Action',
            'Something else here',
          ],
        },
        {
          chip: 'Another one',
          color: 'warning',
          icon: 'mdi-airplane-landing',
          text: 'Tune into Big Boy\'s 92.3 I\'m about to play the first single from Cruel Winter also to Kim’s hair and makeup Lorraine jewelry and the whole style squad at Balmain and the Yeezy team. Thank you Anna for the invite thank you to the whole Vogue team',
        },
      ],
    }),
  }
</script>
