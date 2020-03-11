package life.majiang.community.mapper;

import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.model.Comment;
import life.majiang.community.model.CommentExample;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface CommentMapper {

    long countByExample(CommentExample example);

    @Delete( "delete from COMMENT" )
    int deleteByExample(CommentExample example);

    @Delete( "delete from COMMENT where ID = #{id}" )
    int deleteByPrimaryKey(Long id);

    @Insert( "insert into COMMENT (ID, PARENT_ID, TYPE,COMMENTATOR, GMT_CREATE, GMT_MODIFIED,  LIKE_COUNT, CONTENT, COMMENT_COUNT)" +
            " values (#{id}, #{parent_Id}, #{type}, " +
            "#{commentator}, #{gmt_Create}, #{gmt_Modified}," +
            "#{like_Count}, #{content}, #{comment_Count})" )
    int insert(Comment record);

    int insertSelective(Comment record);

    List<Comment> selectByExampleWithRowbounds(CommentExample example, RowBounds rowBounds);

//    @Select( "select * from COMMENT order by #{orderByClause}" )
//    List<Comment> selectByExample(CommentExample example);
    @Select( "select * from COMMENT where parent_id = #{idt} and type = #{type} order by gmt_create desc" )
    List<Comment> selectByExample(@Param( value = "idt")Integer idt,@Param( value = "type")Integer type);

    @Select( "select * from COMMENT where ID = #{id}" )
    Comment selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByExample(@Param("record") Comment record, @Param("example") CommentExample example);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);
}
