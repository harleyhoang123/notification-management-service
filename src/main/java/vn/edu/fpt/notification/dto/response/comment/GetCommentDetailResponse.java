package vn.edu.fpt.notification.dto.response.comment;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.notification.dto.common.AuditableResponse;
import vn.edu.fpt.notification.entity.Comment;

import java.util.List;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 05/12/2022 - 07:23
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@JsonPropertyOrder({"commentId", "content"})
public class GetCommentDetailResponse extends AuditableResponse {

    private static final long serialVersionUID = -5838501817641283869L;
    private String commentId;
    private String content;
    private List<Comment> comments;
}
