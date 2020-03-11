package life.majiang.community.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.mapper.QuestionExtMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import life.majiang.community.service.QuestionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService{
    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionExtMapper questionExtMapper;
    @Override
    public void create(Question question) {
        questionMapper.create( question );
    }
    @Override
    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination( totalCount,page ,size);
        if(page<1){
            page =1;
        }
        if(page>paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }
        Integer offfset  = size* (page - 1);
//        List<Question> questions = questionMapper.list(offfset,size,2,2);
        List<Question> questions = questionMapper.lists(offfset,size);
        List<QuestionDTO> questionDTOList  = new ArrayList<>(  );
        for(Question question:questions){
            User user =userMapper.findByID(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties( question,questionDTO );
            questionDTO.setUser( user );
            questionDTOList.add( questionDTO );
        }
        paginationDTO.setQuestions( questionDTOList );
        return paginationDTO;
    }

    @Override
    public List<QuestionDTO> questionListDTO(Integer page, Integer size) {
        PageHelper.startPage(page,size,true);
        List<Question> questions = questionMapper.getQuestionList();

        List<QuestionDTO> questionDTOList  = new ArrayList<>(  );
        for(Question question:questions){
            User user =userMapper.findByID(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties( question,questionDTO );
            questionDTO.setUser( user );
            questionDTOList.add( questionDTO );
        }
        return questionDTOList;
    }

    public List<Question> questionList(Integer page, Integer size) {
        PageHelper.startPage(page,size,true);
        List<Question> questions = questionMapper.getQuestionList();
        return questions;
    }

    public List<QuestionDTO> questionListDTOByUser(Long userId,Integer page, Integer size) {
        PageHelper.startPage(page,size,true);
        List<Question> questions = questionMapper.getQuestionListByUser(userId);

        List<QuestionDTO> questionDTOList  = new ArrayList<>(  );
        for(Question question:questions){
            User user =userMapper.findByID(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties( question,questionDTO );
            questionDTO.setUser( user );
            questionDTOList.add( questionDTO );
        }
        return questionDTOList;
    }

    @Override
    public Question getById(Long id) {
        return questionMapper.getById(id);
    }

    public List<Question> questionListByUser(Long userId,Integer page, Integer size) {
        PageHelper.startPage(page,size);
        List<Question> questions = questionMapper.getQuestionListByUser(userId );
        PageInfo pageInfo = new PageInfo( questions );
        return questions;
    }

    @Override
    public void createOrUpdate(Question question) {
        Question question1 = questionMapper.getById( question.getId() );
        if(question1==null){
            question.setGmt_Create( System.currentTimeMillis() );
            question.setGmt_Modified( question.getGmt_Create() );
            questionMapper.create( question );
        }else{
            question.setGmt_Modified( question.getGmt_Create() );
            questionMapper.Update(question);
        }
    }
    public void incView(Long id){
        Question record= new Question();
        record.setId( id );
        record.setView_Count( 1 );
        questionExtMapper.incView( record );
    }

    @Override
    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if (StringUtils.isBlank(queryDTO.getTag())) {
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTO.getTag(), ",");
        String regexpTag = Arrays
                .stream(tags)
                .filter(StringUtils::isNotBlank)
                .map(t -> t.replace("+", "").replace("*", "").replace("?", ""))
                .filter(StringUtils::isNotBlank)
                .collect( Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }

}
