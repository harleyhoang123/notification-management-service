package vn.edu.fpt.notification.dto.response.news;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.notification.dto.common.AuditableResponse;
import vn.edu.fpt.notification.entity.Comment;
import vn.edu.fpt.notification.entity._Attachment;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
public class GetNewsDetailResponse extends AuditableResponse implements Serializable {

    private static final long serialVersionUID = 8651582105311396196L;
    private String newsId;
    private String title;
    private String author;
    private String content;
    private _Attachment thumbnail;
    private List<Comment> comments;
    private Integer views;
}
