package org.kimbs.demo.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    @NotEmpty(message = "Name field is required")
    @Length(max = 36, message = "Should be name field length less than 36")
    private String name;

    @Column(name = "EMAIL")
    @Email(message = "Wrong email format")
    private String email;

    @Column(name = "SCORE")
    @Min(value = 0, message = "Should be score field value more than 0")
    @Max(value = 100, message = "Should be score field value less than 100")
    private int score;

    // 동아리 그룹 ID
    @Column(name = "CLUB_ID")
    private Long clubId;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "CLUB_ID",
            referencedColumnName ="id",
            updatable = false,
            insertable = false,
            foreignKey = @ForeignKey(name = "fk_member_club"))
    private Club club;
}
