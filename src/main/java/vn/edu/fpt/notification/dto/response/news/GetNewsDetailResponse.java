package vn.edu.fpt.notification.dto.response.news;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.notification.dto.common.AuditableResponse;
import vn.edu.fpt.notification.dto.response.comment.GetCommentDetailResponse;
import vn.edu.fpt.notification.entity.Comment;
import vn.edu.fpt.notification.entity._Attachment;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@JsonPropertyOrder({"newsId", "title", "content", "comments", "views"})
public class GetNewsDetailResponse extends AuditableResponse implements Serializable {

    private static final long serialVersionUID = 8651582105311396196L;
    private String newsId;
    private String title;
    private String content;
    private List<GetCommentDetailResponse> comments;
    private Integer views;
}
