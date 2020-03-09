package life.majiang.community.mapper;

import life.majiang.community.dto.QuestionQueryDTO;
import life.majiang.community.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Mapper
public interface QuestionExtMapper {
    @Update( "update QUESTION set VIEW_COUNT = VIEW_COUNT + #{view_Count} where id = #{id}" )
    int incView(Question record);

    @Update( "update QUESTION set COMMENT_COUNT = COMMENT_COUNT + #{comment_Count} where id = #{id}" )
    int incCommentCount(Question record);

    List<Question> selectRelated(Question question);

    Integer countBySearch(QuestionQueryDTO questionQueryDTO);

    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}