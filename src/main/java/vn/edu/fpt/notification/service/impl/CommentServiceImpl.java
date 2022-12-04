package vn.edu.fpt.notification.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.edu.fpt.notification.constant.ResponseStatusEnum;
import vn.edu.fpt.notification.dto.request.comment._CreateCommentRequest;
import vn.edu.fpt.notification.dto.request.comment._UpdateCommentRequest;
import vn.edu.fpt.notification.dto.response.comment._CreateCommentResponse;
import vn.edu.fpt.notification.entity.Comment;
import vn.edu.fpt.notification.entity.News;
import vn.edu.fpt.notification.exception.BusinessException;
import vn.edu.fpt.notification.repository.CommentRepository;
import vn.edu.fpt.notification.repository.NewsRepository;
import vn.edu.fpt.notification.service.CommentService;

import java.util.List;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 04/12/2022 - 17:07
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;

    @Override
    public _CreateCommentResponse addCommentToNews(String newsId, _CreateCommentRequest request) {
        News news = newsRepository.findById(newsId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, ""));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .build();
        try {
            comment = commentRepository.save(comment);
        }catch (Exception ex){
            throw new BusinessException("Can't save comment to database: "+ ex.getMessage());
        }

        List<Comment> comments = news.getComments();
        comments.add(comment);
        news.setComments(comments);
        try {
            newsRepository.save(news);
        }catch (Exception ex){
            throw new BusinessException("Can't update news to database");
        }

        return _CreateCommentResponse.builder()
                .commentId(comment.getCommentId())
                .build();
    }

    @Override
    public void updateComment(String newsId, _UpdateCommentRequest request) {

    }

    @Override
    public void deleteComment(String commentId) {

    }
}
