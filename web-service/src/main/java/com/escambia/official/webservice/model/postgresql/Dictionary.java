package com.escambia.official.webservice.model.postgresql;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("dictionary")
public class Dictionary {

    @Id
    @Column("dictionary_id")
    private Integer dictionaryId;

    /**
     * 1. 國家
     * 2. 縣市
     * 3. 鄉鎮市區
     */
    private Integer type;

    private String name;

    private String code;

    @Column("related_id")
    private Integer relatedId;

}
