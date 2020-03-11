package life.majiang.community.service;

import life.majiang.community.dto.NotificationDTO;
import life.majiang.community.model.Notification;
import life.majiang.community.model.User;

import java.util.List;

public interface NotificationService {
    List<Notification> list(Long id,Integer page,Integer size);

    public List<NotificationDTO> listDTO(Long id, Integer page, Integer size);

    public NotificationDTO read(Long id, User user);

    public Long unreadCount(Long userId);
}
