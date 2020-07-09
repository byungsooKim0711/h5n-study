package org.kimbs.imc.admin.domain;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.kimbs.imc.admin.config.BaseTimeEntity;

import javax.persistence.*;

@Data
@Entity
@Table(name = "TB_API_INFO")
public class ApiInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "API_KEY", length = 32)
    private String apiKey;

    private Long webUserId;

    @Column(name = "USE_YN", length = 1)
    @ColumnDefault("'Y'")
    private String useYn = "Y";

}
