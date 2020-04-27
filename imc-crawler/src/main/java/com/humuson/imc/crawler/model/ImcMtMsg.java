package com.humuson.imc.crawler.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "IMC_MT_MSG")
public class ImcMtMsg {
	
	// 메시지 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// MT 상품 타입 (SM-SMS, LM-LMS, MM-MMS)
	private String mtType;
	
	// 메시지 상태 (1-발송대기, 2-발송후결과대기, 3-결과완료)
	private String status = "1";
	
	// 메시지 우선순위 (S-Slow, N-Normal, F-Fast)
	private String priority = "N";
	
	// 광고 flag
	private String adFlag = "Y";
	
	// 전송 예약시간
	private String reservedDate;
	
	// 수신자 전화번호
	private String phoneNumber;
	
	// 발신자 전화번호
	private String callback;
	
	// 메시지 타이틀 (LMS일 경우 사용)
	private String title;
	
	// 메시지 내용
	private String message;
	
	// 첨부파일
	private String attachFile01;
	private String attachFile02;
	private String attachFile03;
	
	// 과금 코드 (부서코드 등 사용자 컬럼으로 사용)
	private String billCode;
	
	// 발송 구분 코드
	private String senderCode;
}
