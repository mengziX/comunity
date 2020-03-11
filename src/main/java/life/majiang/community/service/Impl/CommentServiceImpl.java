package life.majiang.community.service.Impl;

import life.majiang.community.dto.CommentDTO;
import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.enums.NotificationStatusEnum;
import life.majiang.community.enums.NotificationTypeEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.*;
import life.majiang.community.model.*;
import life.majiang.community.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static life.majiang.community.enums.CommentTypeEnum.QUESTION;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentMapper commentMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    CommentExtMapper commentExtMapper;
    @Autowired
    NotificationMapper notificationMapper;
    @Autowired
    QuestionExtMapper questionExtMapper;
    @Autowired
    UserMapper userMapper;
    @Transactional
    public void insert(Comment comment, User commentator) {
        if (comment.getParent_Id() == null || comment.getParent_Id() == 0) {
            throw new CustomizeException( CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            // 回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParent_Id());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }

            // 回复问题
            Question question =null;
            if(dbComment.getParent_Id()!=null){
                 questionMapper.selectByPrimaryKey(dbComment.getParent_Id());
                if (question == null) {
                    throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
                }
            }


            commentMapper.insert(comment);

            // 增加评论数
            Comment parentComment = new Comment();
            parentComment.setId(comment.getParent_Id());
            parentComment.setComment_Count(1);
            // 创建通知
            createNotify(comment, dbComment.getCommentator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_COMMENT, question.getId());
        } else {
            // 回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParent_Id());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            comment.setComment_Count(0);
            commentMapper.insert(comment);
            question.setComment_Count(1);
            questionExtMapper.incCommentCount(question);
            // 创建通知
            createNotify(comment, question.getCreator(), commentator.getName(), question.getTitle(), NotificationTypeEnum.REPLY_QUESTION, question.getId());
        }
    }
    private void createNotify(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationTypeEnum notificationType, Long outerId) {
//        if (receiver == comment.getCommentator()) {
//            return;
//        }
        Notification notification = new Notification();
        notification.setGmt_Create(System.currentTimeMillis());
        notification.setType(notificationType.getType());
        notification.setOuterid(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus( NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifier_Name(notifierName);
        notification.setOuter_Title(outerTitle);
        notificationMapper.insert(notification);
    }
    public List<CommentDTO>  listByTargetId(Long id, Integer type){
//        CommentExample commentExample = new CommentExample();
//        commentExample.createCriteria()
//                .andParentIdEqualTo(id)
//                .andTypeEqualTo(type.getType());
//        commentExample.setOrderByClause("gmt_create desc");
        Integer idt= Math.toIntExact( id );
        List<Comment> comments = commentMapper.selectByExample(idt,type);
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        // 获取去重的评论人
        Set<Long> commentators = comments.stream().map( comment -> comment.getCommentator()).collect( Collectors.toSet());
        List<Long> userIds = new ArrayList();
        userIds.addAll(commentators);
        // 获取评论人并转换为 Map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userIds);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap( user -> user.getId(), user -> user));

        // 转换 comment 为 commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());
        return commentDTOS;
    }
}
