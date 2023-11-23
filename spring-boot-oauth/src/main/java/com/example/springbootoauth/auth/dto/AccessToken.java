package com.example.springbootoauth.auth.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

/* 공식문서 AccessToken 
Accept: application/json
{
  "access_token":"gho_16C7e42F292c6912E7710c838347Ae178B4a",
  "scope":"repo,gist",
  "token_type":"bearer"
}

- final 키워드 : 초기화된 후에 변경되지 않을 것을 나타냄
- @ToString : 클래스명(필드1명 = 필드1값, 필드2명 = 필드2값,...) 식으로 출력
- @JsonCreator, @JsonProperty : JSON 파싱을 위함

 */

@Getter
@ToString
public class AccessToken {
    private final String accessToken;
    private final String tokenType;

    @JsonCreator
    public AccessToken(
            @JsonProperty("access_token") String accessToken,
            @JsonProperty("token_type") String tokenType) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
    }
}
