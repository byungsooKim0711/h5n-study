package org.kimbs.batch.domain.member;

import lombok.Getter;

@Getter
public enum ImcMemberStatus {
    ACTIVE,
    DORMANT,
    BLOCK,
    WITHDRAW
}
