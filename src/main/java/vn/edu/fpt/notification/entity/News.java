package vn.edu.fpt.notification.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import vn.edu.fpt.notification.entity.common.Auditor;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "news")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
public class News extends Auditor {

    private static final long serialVersionUID = 4679567046768536554L;
    @Id
    @Field(name = "_id", targetType = FieldType.OBJECT_ID)
    private String newsId;
    @Field(name = "title")
    private String title;
    @Field(name = "views")
    @Builder.Default
    private Integer views = 0;
    @Field(name = "content")
    private String content;
    @Field(name = "thumbnail")
    @DBRef(lazy = true)
    private _Attachment thumbnail;
    @Field(name = "comments")
    @DBRef(lazy = true)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();
}
