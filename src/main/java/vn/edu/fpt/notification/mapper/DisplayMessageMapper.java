package vn.edu.fpt.notification.mapper;

import org.mapstruct.*;
import vn.edu.fpt.notification.dto.response.display_message.GetDisplayMessageResponse;
import vn.edu.fpt.notification.entity.DisplayMessage;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 31/08/2022 - 23:57
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL
)
public interface DisplayMessageMapper {

    @Mapping(target = "displayMessageId", source = "displayMessage.displayMessageId")
    @Mapping(target = "code", source = "displayMessage.code")
    @Mapping(target = "language", source = "displayMessage.language")
    @Mapping(target = "message", source = "displayMessage.message")
    @Mapping(target = "createdBy", source = "displayMessage.createdBy")
    @Mapping(target = "createdDate", source = "displayMessage.createdDate")
    @Mapping(target = "lastModifiedBy", source = "displayMessage.lastModifiedBy")
    @Mapping(target = "lastModifiedDate", source = "displayMessage.lastModifiedDate")
    GetDisplayMessageResponse mapToGetDisplayMessageResponse(DisplayMessage displayMessage);
}
