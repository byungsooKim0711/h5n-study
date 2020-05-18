<template>
  <nav>
    <v-navigation-drawer v-model="drawer" dark app class="blue-grey darken-2 py-0">
      <v-card max-width="500" class="mx-auto">
        <v-list-item>
          <v-list-item-content>
            <v-list-item-title class="title">
              휴머스온
            </v-list-item-title>
            <v-list-item-subtitle>
              IMC 통합 어드민
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
        <v-divider></v-divider>

        <v-list >
          <v-list-item router to="/dashboard">
            <v-list-item-action>
              <v-icon small>fas fa-tachometer-alt</v-icon>
            </v-list-item-action>
            <v-list-item-title>Dashboard</v-list-item-title>
          </v-list-item>

          <!-- 통계 -->
          <v-list-group>
            <template v-slot:activator>
              <v-list-item-action>
                <v-icon small>fas fa-chart-line</v-icon>
              </v-list-item-action>
              <v-list-item-title>통계</v-list-item-title>
            </template>

            <v-list-group no-action sub-group>
              <template v-slot:activator>
                <v-list-item-content>
                  <v-list-item-title>알림톡</v-list-item-title>
                </v-list-item-content>
              </template>

              <v-list-item link>
                <v-list-item-title>일별 전체 통계</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>기간별 전체 통계</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>발송 통계</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>유/무효 상세 통계</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>템플릿별 통계</v-list-item-title>
              </v-list-item>
            </v-list-group>


            <v-list-group no-action sub-group >
              <template v-slot:activator>
                <v-list-item-content>
                  <v-list-item-title>친구톡</v-list-item-title>
                </v-list-item-content>
              </template>
              <v-list-item link>
                <v-list-item-title>일별 전체 통계</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>발송 통계</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>유/무효 상세</v-list-item-title>
              </v-list-item>
            </v-list-group>


            <v-list-group no-action sub-group>
              <template v-slot:activator>
                <v-list-item-content>
                  <v-list-item-title>문자</v-list-item-title>
                </v-list-item-content>
              </template>

              <v-list-item link>
                <v-list-item-title>부달 문자</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>일반 문자</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>문자 통합</v-list-item-title>
              </v-list-item>
            </v-list-group>


            <v-list-group no-action sub-group >
              <template v-slot:activator>
                <v-list-item-content>
                  <v-list-item-title>전체통계</v-list-item-title>
                </v-list-item-content>
              </template>

              <v-list-item link>
                <v-list-item-title>업체별 살송 통계</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>AGENT 발송 통계</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>부서별 발송 통계</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>문자 중계사별 통계</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>월간/일간 통계</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>부가서비스 통계</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>월간/주간 지표</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>URL 트래킹 통계</v-list-item-title>
              </v-list-item>
            </v-list-group>
          </v-list-group>

          <!-- 정산 -->
          <v-list-group no-action>
            <template v-slot:activator>
              <v-list-item-action>
                <v-icon small>fas fa-credit-card</v-icon>
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title>정산</v-list-item-title>
              </v-list-item-content>
            </template>

            <v-list-item @click="">
              <v-list-item-content>
                <v-list-item-title>알림톡</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
            <v-list-item @click="">
              <v-list-item-content>
                <v-list-item-title>친구톡</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
            <v-list-item @click="">
              <v-list-item-content>
                <v-list-item-title>문자(부달)</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
            <v-list-item @click="">
              <v-list-item-content>
                <v-list-item-title>문자(일반)</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
            <v-list-item @click="">
              <v-list-item-content>
                <v-list-item-title>통합 정산</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
            <v-list-item @click="">
              <v-list-item-content>
                <v-list-item-title>업체별 정산</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list-group>

          <!-- 관리 -->
          <v-list-group no-action>
            <template v-slot:activator>
              <v-list-item-action>
                <v-icon small>fas fa-cogs</v-icon>
              </v-list-item-action>
              <v-list-item-content>
                <v-list-item-title>관리</v-list-item-title>
              </v-list-item-content>
            </template>

            <v-list-group no-action sub-group>
              <template v-slot:activator>
                <v-list-item-content>
                  <v-list-item-title>고객 관리</v-list-item-title>
                </v-list-item-content>
              </template>

              <v-list-item link>
                <v-list-item-title>고객 목록</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>고객 등록</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>계약 조회</v-list-item-title>
              </v-list-item>
            </v-list-group>

            <v-list-group no-action sub-group>
              <template v-slot:activator>
                <v-list-item-content>
                  <v-list-item-title>단가 관리</v-list-item-title>
                </v-list-item-content>
              </template>

              <v-list-item link>
                <v-list-item-title>비즈메시지</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>문자메시지</v-list-item-title>
              </v-list-item>
            </v-list-group>

            <v-list-group no-action sub-group>
              <template v-slot:activator>
                <v-list-item-content>
                  <v-list-item-title>템플릿 관리</v-list-item-title>
                </v-list-item-content>
              </template>

              <v-list-item link>
                <v-list-item-title>템플릿 요청 목록</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>템플릿 목록</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>템플릿 등록</v-list-item-title>
              </v-list-item>
            </v-list-group>

            <v-list-group no-action sub-group>
              <template v-slot:activator>
                <v-list-item-content>
                  <v-list-item-title>발신번호 관리</v-list-item-title>
                </v-list-item-content>
              </template>

              <v-list-item link>
                <v-list-item-title>담당자 관리</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>발신번호 관리</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>발신번호 등록</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>금지리스트 관리</v-list-item-title>
              </v-list-item>
            </v-list-group>


            <v-list-group no-action sub-group>
              <template v-slot:activator>
                <v-list-item-content>
                  <v-list-item-title>AGENT 관리</v-list-item-title>
                </v-list-item-content>
              </template>

              <v-list-item link>
                <v-list-item-title>AGENT 목록</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>AGENT 등록</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>AGENT 접속 관리</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>AGENT 파일 관리</v-list-item-title>
              </v-list-item>
            </v-list-group>

            <v-list-group no-action sub-group>
              <template v-slot:activator>
                <v-list-item-content>
                  <v-list-item-title>라우팅 관리</v-list-item-title>
                </v-list-item-content>
              </template>

              <v-list-item link>
                <v-list-item-title>메시지 라우팅</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>라우팅 그룹 목록</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>전체 토픽 목록</v-list-item-title>
              </v-list-item>
            </v-list-group>

            <v-list-group no-action sub-group>
              <template v-slot:activator>
                <v-list-item-content>
                  <v-list-item-title>메시지 조회</v-list-item-title>
                </v-list-item-content>
              </template>

              <v-list-item link>
                <v-list-item-title>비즈메시지</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>문자메시지</v-list-item-title>
              </v-list-item>
            </v-list-group>


            <v-list-group no-action sub-group>
              <template v-slot:activator>
                <v-list-item-content>
                  <v-list-item-title>ADMIN 관리</v-list-item-title>
                </v-list-item-content>
              </template>

              <v-list-item link>
                <v-list-item-title>관리자 목록</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>공지사항</v-list-item-title>
              </v-list-item>
              <v-list-item link>
                <v-list-item-title>FAQ</v-list-item-title>
              </v-list-item>
            </v-list-group>


            <v-list-item link>
              <v-list-item-content>
                <v-list-item-title>장애알람 관리</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
            <v-list-item @click="">
              <v-list-item-content>
                <v-list-item-title>전화번호 복호화</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list-group>
        </v-list>
      </v-card>
    </v-navigation-drawer>
  </nav>
</template>

<script>
import { eventBus } from '@/EventBus.js';

export default {
  name: 'navbar',

  data() {
    return {
      drawer: true
    }
  },

  created() {
    eventBus.$on('drawer', (event) => {
      this.drawer = event;
    });
  }

}
</script>

<style scoped>

</style>
