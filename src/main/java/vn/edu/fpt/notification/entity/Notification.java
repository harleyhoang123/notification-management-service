package vn.edu.fpt.notification.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 22/12/2022 - 15:10
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Document(collection = "notifications")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Notification {
    @Id
    @Field(name = "_id", targetType = FieldType.OBJECT_ID)
    private String notifyId;
    @Field(name = "account_id")
    private String accountId;
    @Field(name = "contents")
    @Builder.Default
    private List<NotifyContent> contents = new ArrayList<>();
}
