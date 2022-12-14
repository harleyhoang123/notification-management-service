package vn.edu.fpt.notification.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.fpt.notification.constant.ResponseStatusEnum;
import vn.edu.fpt.notification.dto.event.HandleNotifyEvent;
import vn.edu.fpt.notification.dto.request.notification.GetNotifyResponse;
import vn.edu.fpt.notification.dto.request.notification.GetNumberNotifyResponse;
import vn.edu.fpt.notification.dto.request.notification.NotifyContentResponse;
import vn.edu.fpt.notification.entity.Notification;
import vn.edu.fpt.notification.entity.NotifyContent;
import vn.edu.fpt.notification.exception.BusinessException;
import vn.edu.fpt.notification.repository.NotificationRepository;
import vn.edu.fpt.notification.service.NotificationService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 22/12/2022 - 17:57
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void addNotify(String value) {
        try {
            HandleNotifyEvent event = objectMapper.readValue(value, HandleNotifyEvent.class);
            Notification notification = notificationRepository.getNotificationByAccountId(event.getAccountId())
                    .orElse(Notification.builder()
                            .accountId(event.getAccountId())
                            .build());
            List<NotifyContent> notifyContents = notification.getContents();
            notifyContents.add(NotifyContent.builder()
                    .content(event.getContent())
                    .createdDate(event.getCreatedDate())
                    .build());
            notificationRepository.save(notification);
        } catch (Exception ex) {
            throw new BusinessException("Can't add notify: " + ex.getMessage());
        }
    }

    @Override
    public GetNotifyResponse getNotify(String accountId) {
        Notification notification = notificationRepository.getNotificationByAccountId(accountId)
                .orElse(Notification.builder()
                        .accountId(accountId)
                        .build());
        List<NotifyContent> notifyContents = notification.getContents();
        List<NotifyContent> unreadContent = notifyContents.stream().filter(v -> !v.getIsRead()).limit(20).collect(Collectors.toList());
        List<NotifyContent> notifyContentList = new ArrayList<>();
        notifyContentList.addAll(unreadContent);
        if (unreadContent.size() < 20) {
            notifyContentList.addAll(notifyContents.stream().filter(v -> v.getIsRead()).limit(20 - unreadContent.size()).collect(Collectors.toList()));
        }
        GetNotifyResponse response = GetNotifyResponse.builder()
                .notifyId(notification.getNotifyId())
                .accountId(accountId)
                .contents(notifyContentList.stream().map(this::convertNotifyResponse).collect(Collectors.toList()))
                .build();
        notifyContentList.stream().filter(v -> !v.getIsRead())
                .forEach(v -> {
                    if (unreadContent.contains(v)) {
                        v.setIsRead(true);
                    }
                });

        notification.setContents(notifyContentList);
        try {
            notificationRepository.save(notification);
        } catch (Exception ex) {
            throw new BusinessException("Can't save notification to database: " + ex.getMessage());
        }
        return response;
    }

    private NotifyContentResponse convertNotifyResponse(NotifyContent notifyContent) {
        return NotifyContentResponse.builder()
                .content(notifyContent.getContent())
                .createdDate(notifyContent.getCreatedDate())
                .read(notifyContent.getIsRead())
                .build();
    }

    @Override
    public GetNumberNotifyResponse getNumberNotifyByAccountId(String accountId) {
        Notification notification = notificationRepository.getNotificationByAccountId(accountId).orElse(Notification.builder().contents(new ArrayList<>()).build());
        List<NotifyContent> notifyContents = notification.getContents();
        Long notifies = notifyContents.stream().filter(v -> !v.getIsRead()).count();
        return GetNumberNotifyResponse.builder()
                .notifies(notifies)
                .build();
    }
}
