package life.majiang.community.controller;

import jdk.nashorn.internal.ir.RuntimeNode;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request){
        Cookie [] cookies = request.getCookies();
        for(Cookie cookie:cookies){
            if(cookie.getName().equals( "token" )){
                String token = cookie.getValue();
                System.out.println("......");
                System.out.println(token);
                User user = userMapper.findByToken(token);
                System.out.println(user);
                if(user !=null){
                    System.out.println("test2.....");
                    request.getSession().setAttribute( "user",user );
                }
                break;
            }
        }
        return "index";
    }
}
