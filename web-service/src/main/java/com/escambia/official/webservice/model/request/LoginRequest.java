package com.escambia.official.webservice.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginRequest {

    @Schema(description = "登入方式\n1: Google\n2: Apple\n3: Facebook")
    private Integer loginType;

    @Schema(description = "Google登入Token")
    private String googleToken;

    @Schema(description = "Apple登入Token")
    private String appleToken;

    @Schema(description = "Facebook登入Token")
    private String facebookToken;

    @Schema(description = "Apple Push Notification Token")
    private String apnToken;

}
