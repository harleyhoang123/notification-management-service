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
import java.util.Objects;

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
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "News ID not exist"));

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
    public _CreateCommentResponse addCommentToComment(String commentId, _CreateCommentRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Comment ID not exist"));

        Comment subComment = Comment.builder()
                .content(request.getContent())
                .build();
        try {
            subComment = commentRepository.save(subComment);
        }catch (Exception ex){
            throw new BusinessException("Can't save sub comment to database: "+ ex.getMessage());
        }

        List<Comment> subComments = comment.getComments();
        subComments.add(subComment);
        comment.setComments(subComments);
        try {
            commentRepository.save(comment);
        }catch (Exception ex){
            throw new BusinessException("Can't update sub comment to comment to database");
        }

        return _CreateCommentResponse.builder()
                .commentId(subComment.getCommentId())
                .build();
    }

    @Override
    public void updateComment(String commentId, _UpdateCommentRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Comment ID not exist"));

        if (Objects.nonNull(request.getContent())){
            comment.setContent(request.getContent());
            try {
                commentRepository.save(comment);
                log.info("Update comment success");
            }catch (Exception ex){
                throw new BusinessException("Can't update comment in database: "+ ex.getMessage());
            }
        }

    }

    @Override
    public void deleteSubCommentFromComment(String commentId, String subCommentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Comment ID not exist"));
        Comment subComment = commentRepository.findById(subCommentId).orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Sub Comment ID not exist"));
        List<Comment> comments = comment.getComments();
        comments.removeIf(m->m.getCommentId().equals(subCommentId));
        comment.setComments(comments);
        List<Comment> subComments = subComment.getComments();
        if (!subComments.isEmpty()){
            for (Comment c: subComments) {
                this.deleteSubCommentFromComment(subCommentId, c.getCommentId());
            }
        }

        try {
            commentRepository.deleteById(subCommentId);
            log.info("Delete sub comment success");
        }catch (Exception ex){
            throw new BusinessException("Can't delete sub comment in database: "+ ex.getMessage());
        }
        try {
            commentRepository.save(comment);
            log.info("Save comment success");
        }catch (Exception ex){
            throw new BusinessException("Can't save comment in database: "+ ex.getMessage());
        }
    }

}
