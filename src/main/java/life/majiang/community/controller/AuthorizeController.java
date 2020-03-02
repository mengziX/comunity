package life.majiang.community.controller;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUser;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Value( "${github.client.id}" )
    private String ClientId;

    @Value( "${github.client.secret}" )
    private String ClientSecret;

    @Value( "${github.client.uri}" )
    private String ClientUri;

    @Autowired
    private UserMapper userMapper;
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletRequest request,
                           HttpServletResponse response){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode( code );
        accessTokenDTO.setClient_id( ClientId);
        accessTokenDTO.setClient_secret( ClientSecret );
        accessTokenDTO.setRedirect_uri( ClientUri );
        accessTokenDTO.setState( state );
        String accessToken =  githubProvider.getAccessToken( accessTokenDTO );
        GithubUser githubUser = githubProvider.getUser( accessToken );
        if(githubUser!=null){
            User user = new User(  );
            String token = UUID.randomUUID().toString();
            user.setToken( token);
            user.setName( githubUser.getName() );
            user.setAccountId( String.valueOf( githubUser.getId() ) );
            user.setGmtCreate( System.currentTimeMillis() );
            user.setGmtModified( user.getGmtModified() );
            user.setAvatarUrl( githubUser.getAvatar_url() );
            userMapper.insert( user );
            response.addCookie( new Cookie( "token",token ) );
            request.getSession().setAttribute( "user",user );
            return "redirect:/";
        }else {
            return "redirect:/";
        }

    }
}
