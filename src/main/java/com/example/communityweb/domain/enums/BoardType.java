package com.example.communityweb.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BoardType {
    notice("공지사항"),
    none("아직 타입이 정해지지 않음"),
    free("자유게시판");

    private String value;

    BoardType(String value){
        this.value = value;
    }
}
