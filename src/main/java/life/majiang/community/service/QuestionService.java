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
    List<QuestionDTO> questionListDTO(Integer page,Integer size);
    List<Question> questionList(Integer page,Integer size);
    List<Question> questionListByUser(Long userId,Integer page,Integer size);
    public List<QuestionDTO> questionListDTOByUser(Long userId,Integer page, Integer size) ;
    public Question getById(Long id);
    public void createOrUpdate(Question question);
    public void incView(Long id);
}
