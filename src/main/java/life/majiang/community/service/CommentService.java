package life.majiang.community.service;

import life.majiang.community.dto.CommentDTO;
import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.model.Comment;
import life.majiang.community.model.User;

import java.util.List;

public interface CommentService {
    public void insert(Comment comment, User commentator);

//    List<CommentDTO> listByTargetId(Long questionId, CommentTypeEnum type);
    List<CommentDTO> listByTargetId(Long questionId, Integer type);
}
