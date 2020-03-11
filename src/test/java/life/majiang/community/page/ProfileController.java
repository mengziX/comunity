package life.majiang.community.page;

import com.github.pagehelper.PageInfo;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/profile")
    public String profile(Model model,
                          @RequestParam(name = "pageNum", defaultValue = "1")Integer pageNum,
                          @RequestParam(name = "pageSize", defaultValue = "3")Integer pageSize){
        return "profile2";
    }

    @GetMapping(value = "/questionPage")
    @ResponseBody
    public ModelAndView ajaxBlog(HttpServletRequest request,
                                 @RequestParam(name = "pageNum", defaultValue = "1")Integer pageNum,
                                 @RequestParam(name = "pageSize", defaultValue = "3")Integer pageSize){
        ModelAndView modelAndView = new ModelAndView( "profile2" );

        User user = (User) request.getSession().getAttribute("user");
        if(user==null) modelAndView.setViewName( "redirect:/" );
        Long userId=user.getId();

        List<Question>questions = questionService.questionListByUser(userId,pageNum,pageSize );
        PageInfo pageInfo = new PageInfo( questions );
        List<QuestionDTO>questionsDTO = questionService.questionListDTOByUser(userId,pageNum,pageSize );
        PageInfo pageInfoDTO = new PageInfo(questionsDTO);

        BeanUtils.copyProperties( pageInfo,pageInfoDTO );
        pageInfoDTO.setList( questionsDTO );
        modelAndView.addObject("pageInfo", pageInfoDTO );
        return modelAndView;
    }
    @GetMapping("/profile2/{action}")
    public String profile(HttpServletRequest request,@PathVariable( name ="action" ) String action, Model model){
        User user = (User) request.getSession().getAttribute("user");
        if(user==null) return "redirect:/";
        Long userId=user.getId();

        List<Question>questions = questionService.questionListByUser(userId,1,5 );
        PageInfo pageInfo = new PageInfo( questions );
        List<QuestionDTO>questionsDTO = questionService.questionListDTOByUser(userId,1,5 );
        PageInfo pageInfoDTO = new PageInfo(questionsDTO);
        BeanUtils.copyProperties( pageInfo,pageInfoDTO );
        pageInfoDTO.setList( questionsDTO );

        model.addAttribute( "pageInfo" ,pageInfoDTO);
        if("questions".equals( action )){
            model.addAttribute( "section","questions" );
            model.addAttribute( "sectiorName","我的问题" );
        }else  if("replies".equals( action )){
            model.addAttribute( "section","replies" );
            model.addAttribute( "sectiorName","我的回复" );
        }
        return "profile2";
    }

}

