package life.majiang.community.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import life.majiang.community.dto.NotificationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.enums.NotificationStatusEnum;
import life.majiang.community.enums.NotificationTypeEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.NotificationMapper;
import life.majiang.community.model.Notification;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import life.majiang.community.service.NotificationService;
import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    NotificationMapper notificationMapper;
    @Override
    public List<Notification> list(Long id, Integer page, Integer size) {
        PageHelper.startPage(page,size);
        List<Notification> list = notificationMapper.selectAll(id);
        return list;
    }
    public List<NotificationDTO> listDTO(Long id, Integer page, Integer size) {
        PageHelper.startPage(page,size);
        List<Notification> notifications = notificationMapper.selectAll(id);
        List<NotificationDTO> notificationDTOS  = new ArrayList<>(  );

        BeanUtils.copyProperties( notifications,notificationDTOS );
        for(Notification notification:notifications){
            String typeName = NotificationTypeEnum.nameOfType( notification.getType() );
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties( notification,notificationDTO );
            notificationDTO.setTypeName(typeName);
            notificationDTOS.add( notificationDTO );
        }
        return notificationDTOS;
    }

    @Override
    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException( CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }
        notification.setStatus( NotificationStatusEnum.READ.getStatus());
        //更新notification
        notificationMapper.updateByPrimaryKey(notification);
        NotificationDTO notificationDTO = new NotificationDTO();

        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }

    @Override
    public Long unreadCount(Long userId) {
        return notificationMapper.countByExample(userId);
    }
}
