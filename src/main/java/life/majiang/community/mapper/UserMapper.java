package life.majiang.community.mapper;

import life.majiang.community.model.User;
import life.majiang.community.model.UserExample;
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
    @Insert( "INSERT INTO USER (NAME,ACCOUNT_ID,TOKEN,GMT_CREATE,GMT_MODIFED,avatarUrl) VALUES (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})" )
    public void insert(User user);
    @Select( "select * from user" )
    public List<User> findAll();

    @Select( "select * from user where token = #{token}" )
    User findByToken(@Param( "token" ) String token);

    @Select( "select * from user where id = #{id}" )
    User findByID(Long creator);

    @Select( "update user set name = #{name},token=#{token},GMT_MODIFED=#{gmtModified},avatarUrl=#{avatarUrl} where ACCOUNT_ID=#{accountId}" )
    void Update(User user);

    @Select( "select * from user where ACCOUNT_ID=#{accountId}" )
    User findByAcountId(String acountId);


    @Select({
            "<script>" +
                    "select * from user where ID in " +
                    "<foreach item = 'item' index = 'index' collection = 'ids' open='(' separator=',' close=')' >" +
                    "#{item}" +
                    "</foreach>"+
                    "</script>"})
    List<User> selectByExample(@Param( value = "ids")List<Long> ids);
}
