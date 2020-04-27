package com.humuson.imc.crawler.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "IMC_FT_BIZ_MSG")
public class ImcFtBizMsg {
	
	// 메시지 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// 친구톡 상품 타입
	private String ftType = "T";
	
	// 메시지 상태 (1-발송대기, 2-발송후결과대기, 3-결과완료)
	private String status = "1";
	
	// 메시지 우선순위 (S-Slow, N-Normal, F-Fast)
	private String priority = "N";
	
	// (광고) 문구와 수신거부 문구 표시여부
	private String adFlag = "Y";
	
	// 와이드 버블 사용 여부 Y/N, 기본값 N
	private String wide = "N";
	
	// 전송 예약시간
	private String reservedDate;
	
	// 발신 프로필 키
	private String senderKey;
	
	// 사용자 전화번호
	private String phoneNumber;
	
	// 앱유저아이디
	private String appUserId;
	
	// 사용자 식별키. 옐로아이디 봇을 이용해 받은 옐로아이디 사용자 식별키
	private String userKey;
	
	// 사용자에게 전달될 메시지 (공백 포함 1000자로 제한)
	private String message;
	
	// 버튼 이름
	private String buttonName;
	
	// 버튼 링크 URL (개인화 사용시에만 입력)
	private String buttonUrl;
	
	// Attachment ( Button )
	private String attachmentJson;
	
	// 과금 코드 (부서코드 등 사용자 컬럼으로 사용)
	private String billCode;
	
	// 카카오에 업로드된 이미지 url
	private String imgUrl;
	
	// 업로드할 이미지 path
	private String imgFilePath;
	
	// 이미지 클릭시 이동할 url. 미설정시 카카오톡 내 이미지 뷰어 사용
	private String imgClickUrl;
	
	// 문자 메시지 대체발송 정보 (NO-사용안함, SM-SMS, LM-LMS, MM-MMS)
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
