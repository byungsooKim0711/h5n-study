package com.humuson.demo.domain.send;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "TB_SEND_PROFILE")
public class SendProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "USER_ID")
    private long userId;

    @Column(name = "AGENT_ID")
    private String agentId;

    @Column(name = "SENDER_KEY")
    private String senderKey;

    @Column(name = "TOP_SENDER_KEY")
    private String topSenderKey;

    @Column(name = "TOP_SENDER_KEY_YN")
    private String topSenderKeyYn;

    @Column(name = "SENDER_KEY_TYPE")
    private String senderKeyType;

    @Column(name = "UUID")
    private String uuid;

    @Column(name = "NAME")
    private String name;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "PROFILE_STATUS")
    private String profileStatus;

    @Column(name = "CREATE_AT")
    private String createAt;

    @Column(name = "MODIFIED_AT")
    private String modifiedAt;
}