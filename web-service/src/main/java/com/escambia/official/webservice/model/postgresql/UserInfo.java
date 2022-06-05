package com.escambia.official.webservice.model.postgresql;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("user_info")
public class UserInfo {

    @Id
    @Column("user_id")
    private Integer userId;

    /**
     * 1. Google
     * 2. Apple
     * 3. Facebook
     */
    @Column("login_type")
    private Integer loginType;

    @Column("google_token")
    private String googleToken;

    @Column("apple_token")
    private String appleToken;

    @Column("facebook_token")
    private String facebookToken;

    @Column("user_name")
    private String userName;

    @Column("real_name")
    private String realName;

    private String email;

    @Column("city_dictionary_id")
    private Integer cityDictionaryId;

    @Column("town_dictionary_id")
    private Integer townDictionaryId;

    private String address;

    @Column("accept_expired")
    private Boolean acceptExpired;

    /**
     * 0: 停用
     * 1: 未驗證
     * 2: 啓用
     */
    private Integer status;

    @Column("creation_date")
    private LocalDateTime creationDate;

    @Column("update_date")
    private LocalDateTime updateDate;

    @Column("apn_token")
    private String apnToken;

}
