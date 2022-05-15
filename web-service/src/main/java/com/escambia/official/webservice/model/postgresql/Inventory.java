package com.escambia.official.webservice.model.postgresql;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Table("inventory")
public class Inventory {

    @Id
    @Column("inventory_id")
    private Integer inventoryId;

    @Column("user_id")
    private Integer userId;

    private String name;

    @Column("expiry_date")
    private LocalDate expiryDate;

    @Column("purchase_date")
    private LocalDate purchaseDate;

    @Column("store_method")
    private String storeMethod;

    /**
     * 1. 交換
     * 2. 贈送
     */
    @Column("exchange_type")
    private Integer exchangeType;

    @Column("city_dictionary_id")
    private Integer cityDictionaryId;

    @Column("town_dictionary_id")
    private Integer townDictionaryId;

    @Column("other_description")
    private String otherDescription;

    /**
     * 1. 未上架
     * 2. 已上架
     * 3. 已過期
     */
    private Integer status;

    private String[] images;

    @Column("total_amount")
    private Integer totalAmount;

    @Column("current_amount")
    private Integer currentAmount;

}
