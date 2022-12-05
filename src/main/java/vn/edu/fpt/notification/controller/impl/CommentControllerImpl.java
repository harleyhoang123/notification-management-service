package vn.edu.fpt.notification.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.notification.constant.ResponseStatusEnum;
import vn.edu.fpt.notification.controller.CommentController;
import vn.edu.fpt.notification.dto.common.GeneralResponse;
import vn.edu.fpt.notification.dto.request.comment._UpdateCommentRequest;
import vn.edu.fpt.notification.factory.ResponseFactory;
import vn.edu.fpt.notification.service.CommentService;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 04/12/2022 - 16:57
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RequiredArgsConstructor
@RestController
public class CommentControllerImpl implements CommentController {

    private final CommentService commentService;
    private final ResponseFactory responseFactory;

    @Override
    public ResponseEntity<GeneralResponse<Object>> updateComment(String commentId, _UpdateCommentRequest request) {
        commentService.updateComment(commentId, request);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> deleteComment(String commentId) {
        commentService.deleteComment(commentId);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }
}
