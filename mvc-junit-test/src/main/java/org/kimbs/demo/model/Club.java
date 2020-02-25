package org.kimbs.demo.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "NAME")
    @NotEmpty(message = "Name field is required")
    @Length(max = 36, message = "Should be name field length less than 36")
    private String name;

    @Column(name = "OFFICE")
    @Length(max = 20, message = "should be office field length less than 20")
    private String office;

    @Length(max = 20, message = "Should be telephoneNumber field length less than 20")
    @Column(name = "TELEPHONE_NUMBER")
    private String telephoneNumber;
}
