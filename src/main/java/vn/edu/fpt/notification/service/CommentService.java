package vn.edu.fpt.notification.service;

import vn.edu.fpt.notification.dto.request.comment._CreateCommentRequest;
import vn.edu.fpt.notification.dto.request.comment._UpdateCommentRequest;
import vn.edu.fpt.notification.dto.response.comment._CreateCommentResponse;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 04/12/2022 - 17:07
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public interface CommentService {

    _CreateCommentResponse addCommentToNews(String newsId, _CreateCommentRequest request);

    void updateComment(String commentId, _UpdateCommentRequest request);

    void deleteComment(String commentId);
}
