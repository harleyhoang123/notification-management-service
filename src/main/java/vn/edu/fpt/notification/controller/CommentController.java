package vn.edu.fpt.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.notification.dto.common.GeneralResponse;
import vn.edu.fpt.notification.dto.request.comment._UpdateCommentRequest;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 04/12/2022 - 16:57
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/

@RequestMapping("${app.application-context}/public/api/v1/comments")
public interface CommentController {

    @PutMapping("/{comment-id}")
    ResponseEntity<GeneralResponse<Object>> updateComment(@PathVariable("comment-id") String commentId, @RequestBody _UpdateCommentRequest request);

    @DeleteMapping("/{comment-id}")
    ResponseEntity<GeneralResponse<Object>> deleteComment(@PathVariable("comment-id") String commentId);
}
