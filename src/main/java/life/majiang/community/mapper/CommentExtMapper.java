package life.majiang.community.mapper;

import life.majiang.community.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface CommentExtMapper {

    @Update( "update COMMENT set COMMENT_COUNT = COMMENT_COUNT + #{commentCount} where id = #{id}" )
    int incCommentCount(Comment comment);
}