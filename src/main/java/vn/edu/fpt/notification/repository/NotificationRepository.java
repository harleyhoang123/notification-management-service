package vn.edu.fpt.notification.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import vn.edu.fpt.notification.entity.Notification;

import java.util.List;
import java.util.Optional;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 22/12/2022 - 15:40
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

    Optional<Notification> getNotificationByAccountId(String accountId);
}
