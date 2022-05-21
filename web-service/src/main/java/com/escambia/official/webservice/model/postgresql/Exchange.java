package com.escambia.official.webservice.model.postgresql;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.sql.Timestamp;

@Data
@Table("exchange")
public class Exchange {

    @Id
    @Column("exchange_id")
    private Integer exchangeId;

    @Column("inventory_id")
    private Integer inventoryId;

    @Column("exchanger_user_id")
    private Integer exchangerUserId;

    /**
     * 1. 請求者提出請求，交換者尚未接受<br/>
     * 2. 交換者已接受，聊天室已開啓<br/>
     * 3. 同意交換/贈送<br/>
     * 4. 確認交換/贈送<br/>
     * 5. 交換/贈送成功<br/>
     * ----------------<br/>
     * 51. 不同意交換/贈送<br/>
     * 52. 交換/贈送失敗
     */
    private Integer status;

    @Column("start_date")
    private Timestamp startDate;

    @Column("accept_date")
    private Timestamp acceptDate;

    @Column("complete_date")
    private Timestamp completeDate;

}
