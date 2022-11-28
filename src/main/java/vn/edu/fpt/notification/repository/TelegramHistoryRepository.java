package vn.edu.fpt.notification.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.notification.entity.TelegramHistory;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 25/10/2022 - 20:52
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Repository
public interface TelegramHistoryRepository extends MongoRepository<TelegramHistory, String> {

}
