package vn.edu.fpt.notification.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.notification.entity.Comment;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 04/12/2022 - 17:07
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Repository
public interface CommentRepository extends MongoRepository<Comment, String> {
}
