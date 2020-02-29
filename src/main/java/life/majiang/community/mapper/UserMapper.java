package life.majiang.community.mapper;

import life.majiang.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@Mapper
public interface UserMapper {
    @Insert( "INSERT INTO USER (NAME,ACCOUNT_ID,TOKEN,GMT_CREATE,GMT_MODIFED) VALUES (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})" )
    public void insert(User user);
    @Select( "select * from user" )
    public List<User> findAll();
    @Select( "select * from test" )
    public void test();

    @Select( "select * from user where token = #{token}" )
    User findByToken(@Param( "token" ) String token);
}
