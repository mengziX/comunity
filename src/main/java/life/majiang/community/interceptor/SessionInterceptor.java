package life.majiang.community.interceptor;

import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Service
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private NotificationService notificationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null && cookies.length!=0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals( "token" )) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken( token );
                    if (user != null) {
                        HttpSession session = request.getSession();
//                        request.getSession().setAttribute( "user",user );
                        session.setAttribute("user", user);
                        Long unreadCount = notificationService.unreadCount(user.getId());
                        session.setAttribute("unreadCount", unreadCount);
                    }
                    break;
                }
            }
        }
        return true;
    }
}
