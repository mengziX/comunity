package life.majiang.community.mapper;

import com.github.pagehelper.Page;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface QuestionMapper {
    @Insert( "insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmt_Create},#{gmt_Modified},#{creator},#{tag})" )
    void create(Question question);

    @Select( "select * from question limit #{offset},#{size}" )
    List<Question> list(@Param( value = "offset")Integer offset,@Param( value = "size") Integer size,Integer a,Integer b);

    @Select( "select * from question limit #{offset},#{size}" )
    List<Question> lists(@Param( value = "offset")Integer offset,@Param( value = "size") Integer size);

    @Select( "select count(1) from question" )
    Integer count();

    @Select("SELECT * FROM question")
    Page<Question> getQuestionList();

    @Select("SELECT * FROM question where creator=#{userId}")
    Page<Question> getQuestionListByUser(@Param( value = "userId")Long userId);

    @Select("SELECT * FROM question where id=#{id}")
    Question getById(Long id);

    @Update( "update question set title=#{title},description=#{description},gmt_modified=#{gmt_Modified},tag=#{tag}" )
    void Update(Question question);
}
