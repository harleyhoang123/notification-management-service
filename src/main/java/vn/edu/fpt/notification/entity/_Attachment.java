package vn.edu.fpt.notification.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.io.Serializable;

@Document(collection = "attachments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class _Attachment implements Serializable {

    private static final long serialVersionUID = 4865835205392571227L;
    @Id
    @Field(name = "_id", targetType = FieldType.OBJECT_ID)
    private String attachmentId;
    @Field(name = "file_key")
    private String fileKey;
}
