package life.majiang.community.controller;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GithubUser;
import life.majiang.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state){

        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode( code );
//        accessTokenDTO.setClient_id( "a09d1df2bea7dadbd408" );
//        accessTokenDTO.setClient_secret( "68642e6f399cc49163bf6161ab6ae198be3e8183" );
//        accessTokenDTO.setRedirect_uri( "http://localhost:8887/callback" );
        accessTokenDTO.setClient_id( ClientId);
        accessTokenDTO.setClient_secret( ClientSecret );
        accessTokenDTO.setRedirect_uri( ClientUri );
        accessTokenDTO.setState( state );
        String accessToken =  githubProvider.getAccessToken( accessTokenDTO );
        GithubUser user = githubProvider.getUser( accessToken );
        System.out.println("........");
        System.out.println(user.getName());
        return "index";
    }
}
