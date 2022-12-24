package vn.edu.fpt.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.notification.dto.common.GeneralResponse;
import vn.edu.fpt.notification.dto.request.notification.GetNotifyResponse;
import vn.edu.fpt.notification.dto.request.notification.GetNumberNotifyResponse;
import vn.edu.fpt.notification.entity.Notification;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 22/12/2022 - 15:34
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RequestMapping("${app.application-context}/private/api/v1/notifies")
public interface NotificationController {

    @PostMapping
    ResponseEntity<GeneralResponse<Notification>> createNotification(@RequestBody CreateNotifyRequest request);

    @PostMapping("/{notify-id}")
    ResponseEntity<GeneralResponse<Notification>> createContent(@PathVariable(name = "notify-id") String notifyId, @RequestBody CreateNotifyContentRequest request);

    @GetMapping("/{account-id}/count")
    ResponseEntity<GeneralResponse<GetNumberNotifyResponse>> getNumberNotify(@PathVariable(name = "account-id") String accountId);

    @GetMapping("/{account-id}")
    ResponseEntity<GeneralResponse<GetNotifyResponse>> getNotify(@PathVariable(name = "account-id")String accountId);
}
