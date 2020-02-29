package life.majiang.community.controller;

import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Test {
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/test")
    public String test(){
//        userMapper.test();
        User user =new User();
        user.setName( "2" );
        user.setGmtModified( 2l );
        user.setGmtCreate( 2L );
        user.setToken( "2" );

        userMapper.insert( user );
        return "index";
    }
}
