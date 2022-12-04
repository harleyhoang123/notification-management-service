package vn.edu.fpt.notification.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import vn.edu.fpt.notification.entity.common.Auditor;

@Document(collection = "comments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class Comment extends Auditor {

    private static final long serialVersionUID = 3109866927282749282L;
    @Id
    @Field(name = "_id", targetType = FieldType.OBJECT_ID)
    private String commentId;
    @Field(name = "content")
    private String content;
}
