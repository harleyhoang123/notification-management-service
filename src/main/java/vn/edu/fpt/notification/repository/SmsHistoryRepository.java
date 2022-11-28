package vn.edu.fpt.notification.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.notification.entity.SmsHistory;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 27/10/2022 - 03:31
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Repository
public interface SmsHistoryRepository extends MongoRepository<SmsHistory, String> {
}
