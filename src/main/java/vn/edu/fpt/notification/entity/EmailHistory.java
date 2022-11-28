package vn.edu.fpt.notification.entity;

import lombok.*;
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
 * @created : 07/09/2022 - 21:55
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/

@Document(collection = "email_history")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class EmailHistory implements Serializable {

    private static final long serialVersionUID = 1036659010329528896L;
    @Id
    @Field(name = "_id", targetType = FieldType.OBJECT_ID)
    private String emailHistoryId;
    @Field(name = "template")
    private EmailTemplate template;
    @Field(name = "send_to")
    private String sendTo;
    @Field(name = "cc")
    private String cc;
    @Field(name = "bcc")
    private String bcc;
    @Field(name = "message")
    private String message;
    @Field(name = "params")
    private Map<String, String> params;
    @Field(name = "status")
    private String status;
    @Field(name = "created_by")
    @CreatedBy
    private String createdBy;
    @Field(name = "created_date")
    @CreatedDate
    private LocalDateTime createdDate;
}
