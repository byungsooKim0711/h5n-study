package com.humuson.imc.crawler.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "IMC_AT_BIZ_MSG")
public class ImcAtBizMsg {

    // 메시지 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 메시지 상태 (1-발송대기, 2-발송후결과대기, 3-결과완료)
    private String status = "1";

    // 메시지 우선순위 (S-Slow, N-Normal, F-Fast)
    private String priority = "N";

    // 전송 예약시간
    private String reservedDate;

    // 발신 프로필 키
    private String senderKey;

    // 사용자 전화번호
    private String phoneNumber;

    // 앱유저아이디
    private String appUserId;

    // 템플릿코드
    private String templateCode;

    // 사용자에게 전달될 메시지 (공백 포함 1000자로 제한)
    private String message;

    // 버튼 링크 URL (개인화 사용시에만 입력)
    private String buttonUrl;

    // Attachment ( Button )
    private String attachmentJson;

    // 과금 코드 (부서코드 등 사용자 컬럼으로 사용)
    private String billCode;

    // 문자 메시지 대체발송 정보 (NO-사용안함, SM-SMS, LM-LMS)
    private String resendMtType = "NO";

    // 문제 메시지 대체발송 정보 - 발신자 전화번호
    private String resendMtFrom;

    // 문자 메시지 대체발송 정보 - 수신자 전화번호
    private String resendMtTo;

    // 문자 메시지 대체발송 정보 - 메시지 타이틀 (LMS일 경우 사용)
    private String resendMtTitle;

    // 문자 메시지 대체발송 정보 - 알림톡 메시지 내용 사용 여부
    private String resendMtMessageReuse = "N";

    // 문자 메시지 대체발송 정보 - 메시지 내용
    private String resendMtMessage;

    // 문자 메시지 대체발송 결과 코드
    private String resendReportCode;

    // 문자 메시지 대체발송 수신 시간
    private String resendArrivalDate;

    // 발송 구분 코드
    private String senderCode;
}

