package org.kimbs.uracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UrackerTarget {

    private String tinyId;

    // agent id
    private String agentId;

    // channel type
    private String type;

    // serialNumber
    private String sn;

    // original url
    private String url;

    // user's id
    private String userId;

    // phone number
    private String phone;

    // email
    private String email;

    // uuid
    private String uuid;

    // ttl
    private long ttl;

    // callback url
    private String callback;

    // created date
    private String created;

    // click date
    private String click;

}
