package org.kimbs.imc.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.kimbs.imc.admin.config.BaseTimeEntity;

import javax.persistence.*;

@Data
@Entity
@Table(name = "TB_SEND_PROFILE", indexes = {
		@Index(name = "idx_send_profile_01", columnList = "USER_ID, SENDER_KEY"),
		@Index(name = "idx_send_profile_02", columnList = "AGENT_ID, SENDER_KEY") })
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendProfile extends BaseTimeEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	// TB_WEB_USER
	@Column(name = "USER_ID", nullable = false)
	private long userId;
	
	@Column(name = "AGENT_ID", length = 45)
	private String agentId;
	
	@Column(name = "SENDER_KEY", nullable = false, length = 40)
	private String senderKey;
	
	@Column(name = "TOP_SENDER_KEY", length = 40)
	private String topSenderKey;
	
	@Column(name = "TOP_SENDER_KEY_YN", length = 1, nullable = false)
	@ColumnDefault("'N'")
	private String topSenderKeyYn = "N";
	
	@Column(name = "SENDER_KEY_TYPE", length = 1, nullable = false)
	@ColumnDefault("'S'")
	private String senderKeyType = "S";
	
	@Column(name = "UUID", nullable = false, length = 40)
	private String uuid;
	
	@Column(name = "NAME", nullable = false, length = 128)
	private String name;
	
	@Column(name = "STATUS", length = 1, nullable = false)
	@ColumnDefault("'A'")
	private String status = "A";
	
	@Column(name = "PROFILE_STATUS", length = 1, nullable = false)
	@ColumnDefault("'A'")
	private String profileStatus = "A";
}
