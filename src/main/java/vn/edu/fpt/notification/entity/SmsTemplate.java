package vn.edu.fpt.notification.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import vn.edu.fpt.notification.entity.common.Auditor;

import java.util.Map;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 07/09/2022 - 21:53
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Document(collection = "sms_template")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@SuperBuilder
public class SmsTemplate extends Auditor {

    private static final long serialVersionUID = -829039124868157003L;
    @Id
    @Field(name = "_id", targetType = FieldType.OBJECT_ID)
    private String smsTemplateId;
    @Field(name = "template_name")
    private String templateName;
    @Field(name = "message")
    private String message;
    @Field(name = "params")
    private Map<String, String> params;

}
