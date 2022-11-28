package vn.edu.fpt.notification.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.persistence.EntityListeners;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 07/09/2022 - 21:53
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Document(collection = "sms_history")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
@EntityListeners(AuditingEntityListener.class)
public class SmsHistory implements Serializable {

    private static final long serialVersionUID = 8977023913333501476L;
    @Id
    @Field(name = "_id", targetType = FieldType.OBJECT_ID)
    private String smsHistoryId;
    @Field(name = "template")
    private SmsTemplate template;
    @Field(name = "send_to")
    private String sendTo;
    @Field(name = "message")
    private String message;
    @Field(name = "status")
    private String status;
    @Field(name = "params")
    private Map<String, String> params;
    @Field(name = "created_by")
    @CreatedBy
    private String createdBy;
    @Field(name = "created_date")
    @CreatedDate
    private LocalDateTime createdDate;
}
