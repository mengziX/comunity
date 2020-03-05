package life.majiang.community.controller;

import com.github.pagehelper.PageInfo;
import life.majiang.community.dto.QuestionDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class TestController {
    @GetMapping(value = "/testController")
    @ResponseBody
    public ModelAndView ajaxBlog(@RequestParam(name = "pageNum", defaultValue = "1")Integer pageNum,
                                 @RequestParam(name = "pageSize", defaultValue = "3")Integer pageSize){
        ModelAndView modelAndView = new ModelAndView( "testController" );
//        PageInfo<>
//        modelAndView.addObject("pageInfo", pageInfo );
        return modelAndView;
    }
}
