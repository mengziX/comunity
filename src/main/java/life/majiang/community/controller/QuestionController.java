package life.majiang.community.controller;

import life.majiang.community.dto.CommentDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.service.CommentService;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(
            @PathVariable(name = "id")String id,
            Model model){
        Long questionId = null;
        try {
            questionId = Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new CustomizeException( CustomizeErrorCode.INVALID_INPUT);
        }
        Question question= questionService.getById(questionId);
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties( question,questionDTO );
        questionDTO.setUser( userMapper.findByID( question.getCreator() ) );

        //查询该问题下的所有帖子
        Integer questionType = 1;
        List<CommentDTO> comments = commentService.listByTargetId(questionId, questionType);

        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);

        questionService.incView(questionId);
        model.addAttribute( "question", questionDTO);
        model.addAttribute("comments", comments);
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";
    }
}
