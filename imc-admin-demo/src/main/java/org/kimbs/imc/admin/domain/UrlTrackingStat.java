package org.kimbs.imc.admin.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.kimbs.imc.admin.config.BaseTimeEntity;

@Entity
@Table(name = "TB_URL_TRACKING_STAT", indexes = {
		@Index(name = "idx_url_tracking_stat_01", columnList = "REQ_DATE, AGENT_ID, TYPE") })
@Data
public class UrlTrackingStat extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "REQ_DATE", nullable = false, length = 8)
	private String reqDate;
	
	@Column(name = "AGENT_ID", nullable = false, length = 45)
	private String agentId;
	
	@Column(name = "TYPE", nullable = false, length = 5)
	private String type;
	
	@Column(name = "CLICK_COUNT", nullable = false)
	@ColumnDefault("0")
	private int clickCount = 0;

	public int incrementClickCount() {
		this.clickCount = this.clickCount + 1;
		return this.clickCount;
	}
	
	public int incrementClickCount(int count) {
		this.clickCount = this.clickCount + count;
		return this.clickCount;
	}

}
