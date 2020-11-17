package com.example.communityweb.domain;

import com.example.communityweb.domain.enums.SocialType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;

    private String password;

    private String email;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    private String principal;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

}
