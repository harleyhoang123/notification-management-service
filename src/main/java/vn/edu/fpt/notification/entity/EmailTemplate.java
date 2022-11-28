package vn.edu.fpt.notification.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import vn.edu.fpt.notification.entity.common.Approval;
import vn.edu.fpt.notification.entity.common.Auditor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 04/09/2022 - 14:39
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Document(collection = "email_template")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class EmailTemplate extends Auditor implements Serializable {

    private static final long serialVersionUID = -7820372902470409854L;
    @Id
    @Field(name = "_id", targetType = FieldType.OBJECT_ID)
    private String emailTemplateId;
    @Field(name = "template_name")
    private String templateName;
    @Field(name = "send_from")
    private String sendFrom;
    @Field(name = "subject")
    private String subject;
    @Field(name = "message")
    private String message;
    @Field(name = "params")
    private Map<String, String> params;
    @Field(name = "attach_file")
    private List<AttachFile> attachFiles;
}
