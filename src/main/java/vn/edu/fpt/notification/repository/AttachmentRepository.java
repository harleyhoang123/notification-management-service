package vn.edu.fpt.notification.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.notification.entity._Attachment;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 04/12/2022 - 14:34
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Repository
public interface AttachmentRepository extends MongoRepository<_Attachment, String> {
}
