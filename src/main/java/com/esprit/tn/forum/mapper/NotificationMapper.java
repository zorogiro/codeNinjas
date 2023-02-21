//package com.esprit.tn.forum.mapper;
//
//import com.esprit.tn.forum.dto.NotificationDto;
//import com.esprit.tn.forum.model.Notification;
//import com.esprit.tn.forum.model.User;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Mapper(componentModel = "spring")
//public interface NotificationMapper {
//
//    @Mapping(target = "id", source = "id")
//    @Mapping(target = "message", source = "message")
//   // @Mapping(target = "read", source = "read")
//    @Mapping(target = "createdDate", source = "createdDate")
//    NotificationDto mapToDto(Notification notification);
//
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "user", source = "userId")
//    @Mapping(target = "message", source = "message")
//  //  @Mapping(target = "read", source = "notificationDto.read")
//    Notification map(NotificationDto notificationDto, User recipient);
//
//    default List<NotificationDto> mapToDtoList(List<Notification> notifications) {
//        return notifications.stream()
//                .map(this::mapToDto)
//                .collect(Collectors.toList());
//    }
//}
