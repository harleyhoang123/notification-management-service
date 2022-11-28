package vn.edu.fpt.notification.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.Map;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 07/09/2022 - 21:55
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Document(collection = "telegram_history")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
public class TelegramHistory {

    @Id
    @Field(name = "_id", targetType = FieldType.OBJECT_ID)
    private String historyId;
    @Field(name = "template")
    private TelegramTemplate template;
    @Field(name = "params")
    private Map<String, String> params;
    @Field(name = "message")
    private String message;
    @Field(name = "status")
    private String status;
    @Field(name = "created_by")
    private String createdBy;
    @Field(name = "created_date")
    private String createdDate;
}
