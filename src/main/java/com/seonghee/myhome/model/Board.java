package com.seonghee.myhome.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity //db 접근하는 모델이다! 라고 알려주는 어노테이션
@Data   //getter, setter를 만들지 않아도 된다.
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //자동 증가 관련 설정(mariadb:auto increment)
    private Long id;

    @NotNull
    @Size(min=2, max=30)
    private String title;
    private String content;
}
