package org.kimbs.batch.domain.member;

import lombok.Data;
import org.kimbs.batch.domain.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class ImcMember extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private ImcMemberStatus status;

    @Column
    private LocalDateTime lastAccessTime;
}
