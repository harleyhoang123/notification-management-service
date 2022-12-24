package vn.edu.fpt.notification.service;

import vn.edu.fpt.notification.dto.request.notification.GetNotifyResponse;
import vn.edu.fpt.notification.dto.request.notification.GetNumberNotifyResponse;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 22/12/2022 - 17:57
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public interface NotificationService {

    void addNotify(String value);

    GetNotifyResponse getNotify(String accountId);

    GetNumberNotifyResponse getNumberNotifyByAccountId(String accountId);

}
