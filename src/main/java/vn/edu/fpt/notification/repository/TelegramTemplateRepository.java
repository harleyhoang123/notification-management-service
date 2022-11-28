package vn.edu.fpt.notification.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.notification.entity.TelegramTemplate;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 14/09/2022 - 15:34
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Repository
public interface TelegramTemplateRepository extends MongoRepository<TelegramTemplate, String> {
}
