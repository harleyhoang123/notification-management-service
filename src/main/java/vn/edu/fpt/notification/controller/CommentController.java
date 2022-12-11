package vn.edu.fpt.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.notification.dto.common.GeneralResponse;
import vn.edu.fpt.notification.dto.request.comment._CreateCommentRequest;
import vn.edu.fpt.notification.dto.request.comment._UpdateCommentRequest;
import vn.edu.fpt.notification.dto.response.comment._CreateCommentResponse;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 04/12/2022 - 16:57
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/

@RequestMapping("${app.application-context}/public/api/v1/comments")
public interface CommentController {

    @PostMapping("/{comment-id}/comment")
    ResponseEntity<GeneralResponse<_CreateCommentResponse>> addCommentToNews(@PathVariable("comment-id") String commentId, @RequestBody _CreateCommentRequest request);

    @PutMapping("/{comment-id}")
    ResponseEntity<GeneralResponse<Object>> updateComment(@PathVariable("comment-id") String commentId, @RequestBody _UpdateCommentRequest request);

    @DeleteMapping("/{comment-id}/{subcomment-id}")
    ResponseEntity<GeneralResponse<Object>> deleteSubCommentFromComment(@PathVariable("comment-id") String commentId, @PathVariable("subcomment-id") String subCommentId);

}


