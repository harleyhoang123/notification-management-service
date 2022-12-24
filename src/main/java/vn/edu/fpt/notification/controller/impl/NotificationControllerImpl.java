package vn.edu.fpt.notification.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.notification.controller.CreateNotifyContentRequest;
import vn.edu.fpt.notification.controller.CreateNotifyRequest;
import vn.edu.fpt.notification.controller.NotificationController;
import vn.edu.fpt.notification.dto.common.GeneralResponse;
import vn.edu.fpt.notification.dto.request.notification.GetNotifyResponse;
import vn.edu.fpt.notification.dto.request.notification.GetNumberNotifyResponse;
import vn.edu.fpt.notification.entity.Notification;
import vn.edu.fpt.notification.entity.NotifyContent;
import vn.edu.fpt.notification.factory.ResponseFactory;
import vn.edu.fpt.notification.repository.NotificationRepository;
import vn.edu.fpt.notification.service.NotificationService;

import java.util.List;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 22/12/2022 - 15:39
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RestController
@RequiredArgsConstructor
@Slf4j
public class NotificationControllerImpl implements NotificationController {

    private final NotificationRepository notificationRepository;
    private final NotificationService notificationService;
    private final ResponseFactory responseFactory;

    @Override
    public ResponseEntity<GeneralResponse<Notification>> createNotification(CreateNotifyRequest request) {
        Notification notification = Notification.builder()
                .accountId(request.getAccountId())
                .build();
        notification = notificationRepository.save(notification);

        return responseFactory.response(notification);
    }

    @Override
    public ResponseEntity<GeneralResponse<Notification>> createContent(String notifyId, CreateNotifyContentRequest request) {
        Notification notification = notificationRepository.findById(notifyId).orElseThrow();
        NotifyContent notifyContent = NotifyContent.builder()
                .content(request.getContent())
                .build();
        List<NotifyContent> notifyContents = notification.getContents();
        notifyContents.add(notifyContent);
        notification.setContents(notifyContents);
        notification = notificationRepository.save(notification);
        return responseFactory.response(notification);
    }

    @Override
    public ResponseEntity<GeneralResponse<GetNumberNotifyResponse>> getNumberNotify(String accountId) {
        return responseFactory.response(notificationService.getNumberNotifyByAccountId(accountId));
    }

    @Override
    public ResponseEntity<GeneralResponse<GetNotifyResponse>> getNotify(String accountId) {
        return responseFactory.response(notificationService.getNotify(accountId));
    }
}
