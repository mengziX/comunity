package life.majiang.community.service;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface QuestionService {
    void create(Question question);

//    List<QuestionDTO> list();

    PaginationDTO list(Integer page,Integer size);
}
