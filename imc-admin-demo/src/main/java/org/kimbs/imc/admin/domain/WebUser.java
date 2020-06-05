package org.kimbs.imc.admin.domain;

import lombok.Data;
import org.kimbs.imc.admin.config.BaseTimeEntity;

import javax.persistence.*;

@Data
@Entity
@Table(name = "TB_WEB_USER", indexes = {
		@Index(name = "idx_web_user_01", columnList = "USER_LOGIN", unique = true),
		@Index(name = "idx_web_user_02", columnList = "COMPANY"),
		@Index(name = "idx_web_user_03", columnList = "AUTH_ID")})
public class WebUser extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "USER_LOGIN", unique = true, nullable = false, length = 45)
	private String userLogin;

	@Column(name = "COMPANY", nullable = false, length = 128)
	private String company;

	@Column(name = "BIZ_NUM", nullable = false, length = 20)
	private String bizNum;

	@Column(name = "INFO_NA", nullable = false, length = 64)
	private String infoNa;

	@Column(name = "INFO_CP", nullable = false, length = 64)
	private String infoCp;

	@Column(name = "INFO_EM", nullable = false, length = 64)
	private String infoEm;

	@Column(name = "ENC_2_PA", nullable = false, length = 128)
	private String enc2Pa;

	@Column(name = "AUTH_ID", nullable = false)
	private long authId;

	@Column(name = "ACTIVE_YN", columnDefinition = "VARCHAR(1) DEFAULT 'Y'", nullable = false)
	private String activeYn = "Y";

	@Column(name = "CONTRACT_AT", nullable = false, length = 8)
	private String contractAt;

	@Column(name = "SEC_10", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double sec10;

	@Column(name = "SEC_11", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double sec11;

	@Column(name = "SEC_12", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double sec12;

	@Column(name = "SEC_13", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double sec13;

	@Column(name = "SEC_14", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double sec14;

	@Column(name = "SEC_15", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double sec15;

	@Column(name = "SEC_20", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double sec20;

	@Column(name = "SEC_30", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double sec30;

	@Column(name = "SEC_40", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double sec40;

	@Column(name = "SEC_50", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double sec50;

	@Column(name = "STATUS", columnDefinition = "CHAR(1) DEFAULT '0'", nullable = false)
	private String status = "0";

	@Column(name = "PARENT_ID", nullable = false)
	private long parentId;

	@Column(name = "USE_AT", columnDefinition = "CHAR(1) DEFAULT 'N'", nullable = false)
	private String useAt = "N";

	@Column(name = "USE_FT", columnDefinition = "CHAR(1) DEFAULT 'N'", nullable = false)
	private String useFt = "N";

	@Column(name = "USE_RMT", columnDefinition = "CHAR(1) DEFAULT 'N'", nullable = false)
	private String useRmt = "N";

	@Column(name = "USE_NMT", columnDefinition = "CHAR(1) DEFAULT 'N'", nullable = false)
	private String useNmt = "N";

	@Column(name = "MONTHLY_BASE_FEE", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double monthlyBaseFee;

	@Column(name = "AT_SECTION1", nullable = false, length = 20)
	private String atSection1;

	@Column(name = "AT_SECTION2", nullable = false, length = 20)
	private String atSection2;

	@Column(name = "AT_SECTION3", nullable = false, length = 20)
	private String atSection3;

	@Column(name = "AT_SECTION4", nullable = false, length = 20)
	private String atSection4;

	@Column(name = "AT_SECTION5", nullable = false, length = 20)
	private String atSection5;

	@Column(name = "AT_SECTION6", nullable = false, length = 20)
	private String atSection6;

	@Column(name = "AT_SECTION7", nullable = false, length = 20)
	private String atSection7;

	@Column(name = "AT_SECTION8", nullable = false, length = 20)
	private String atSection8;

	@Column(name = "AT_SECTION9", nullable = false, length = 20)
	private String atSection9;

	@Column(name = "AT_SECTION10", nullable = false, length = 20)
	private String atSection10;

	@Column(name = "AT_FEE1", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double atFee1;

	@Column(name = "AT_FEE2", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double atFee2;

	@Column(name = "AT_FEE3", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double atFee3;

	@Column(name = "AT_FEE4", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double atFee4;

	@Column(name = "AT_FEE5", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double atFee5;

	@Column(name = "AT_FEE6", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double atFee6;

	@Column(name = "AT_FEE7", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double atFee7;

	@Column(name = "AT_FEE8", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double atFee8;

	@Column(name = "AT_FEE9", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double atFee9;

	@Column(name = "AT_FEE10", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double atFee10;

	@Column(name = "FT_TEXT_FEE", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double ftTextFee;

	@Column(name = "FT_IMG_FEE", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double ftImgFee;

	@Column(name = "RMT_SMS_FEE", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double rmtSmsFee;

	@Column(name = "RMT_LMS_FEE", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double rmtLmsFee;

	@Column(name = "RMT_MMS_FEE", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double rmtMmsFee;

	@Column(name = "NMT_SMS_FEE", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double nmtSmsFee;

	@Column(name = "NMT_LMS_FEE", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double nmtLmsFee;

	@Column(name = "NMT_MMS_FEE", columnDefinition = "DECIMAL(10, 2) DEFAULT 0.0", nullable = false)
	private double nmtMmsFee;
}
