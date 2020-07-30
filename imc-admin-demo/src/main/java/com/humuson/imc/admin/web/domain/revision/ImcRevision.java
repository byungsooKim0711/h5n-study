package com.humuson.imc.admin.web.domain.revision;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;
import java.io.Serializable;


// 테이블 이름 revinfo -> tb_imc_rev_info 로 변경
// pk int -> long 으로 변경

// [REV_TYPE] 0 : INSERT
// [REV_TYPE] 1 : UPDATE
// [REV_TYPE] 2 : DELETE

@Getter
@NoArgsConstructor
@RevisionEntity
@Entity
@Table(name = "TB_IMC_REV_INFO")
public class ImcRevision implements Serializable {
//public class ImcRevision extends DefaultRevisionEntity {

    @Id
    @RevisionNumber
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @RevisionTimestamp
    @Column(name = "TIMESTAMP")
    private Long timestamp;
}
