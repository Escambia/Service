package com.escambia.official.webservice.model.postgresql;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Table("admin_info")
public class AdminInfo {

    @Id
    @Column("admin_id")
    private Integer adminId;

    private String account;

    private String password;

    private String name;

    private Integer role;

    private Boolean status;

    @Column("creation_date")
    private LocalDateTime creationDate;

    @Column("created_by")
    private Integer createdBy;

    @Column("update_date")
    private LocalDateTime updateDate;

    @Column("updated_by")
    private Integer updatedBy;

}
