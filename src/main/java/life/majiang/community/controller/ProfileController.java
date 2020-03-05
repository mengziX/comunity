package life.majiang.community.controller;

import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Mod;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
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

        Long userId=0l;
        userId=getUerId( request );

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
        Long userId=0l;
        userId=getUerId( request );

        List<Question>questions = questionService.questionListByUser(userId,1,3 );
        PageInfo pageInfo = new PageInfo( questions );
        List<QuestionDTO>questionsDTO = questionService.questionListDTOByUser(userId,1,3 );
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
    private Long getUerId(HttpServletRequest request){
        Long userId=0l;
        Cookie[] cookies = request.getCookies();
        if(cookies!=null && cookies.length!=0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals( "token" )) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken( token );
                    if (user != null) {
                        userId=user.getId();
                    }
                    break;
                }
            }
        }
        return userId;
    }
}

