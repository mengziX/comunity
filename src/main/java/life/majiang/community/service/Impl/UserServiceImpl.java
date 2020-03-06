package life.majiang.community.service.Impl;

import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.model.UserExample;
import life.majiang.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public void creatorOrUpdate(User user) {
        User usert =userMapper.findByAcountId( user.getAccountId() );
        if(usert == null){
            user.setGmtCreate( System.currentTimeMillis() );
            user.setGmtModified( user.getGmtCreate() );
            userMapper.insert(user);
        }else {
            user.setGmtModified(System.currentTimeMillis());
            user.setAvatarUrl( user.getAvatarUrl() );
            user.setToken( user.getToken() );
            user.setName( user.getName() );
            userMapper.Update(user);
        }
    }
}
