package life.majiang.community.mapper;

import life.majiang.community.model.Notification;

import life.majiang.community.model.NotificationExample;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.LongFunction;

@Component
@Mapper
public interface NotificationMapper {

    @Select( "select * from notification where receiver=#{receiver}" )
    List<Notification> selectAll(@Param( value = "receiver")Long receiver);

//    long countByExample(NotificationExample example);
    @Select( "select count(*) from notification where receiver=#{receiver} and status=0" )
    Long countByExample(Long userId);

    int deleteByExample(NotificationExample example);

    int deleteByPrimaryKey(Long id);

    @Insert( "    insert into NOTIFICATION (ID, NOTIFIER, RECEIVER,OUTERID, TYPE, GMT_CREATE, STATUS, NOTIFIER_NAME, OUTER_TITLE)\n" +
            "    values (#{id}, #{notifier}, #{receiver}, \n" +
            "      #{outerid}, #{type}, #{gmt_Create}, \n" +
            "      #{status}, #{notifier_Name}, #{outer_Title}\n" +
            "      )" )
    int insert(Notification record);

    int insertSelective(Notification record);

    List<Notification> selectByExampleWithRowbounds(NotificationExample example, RowBounds rowBounds);

    List<Notification> selectByExample(NotificationExample example);

    @Select( "select * from NOTIFICATION where ID = #{id}" )
    Notification selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param( "record" ) Notification record, @Param( "example" ) NotificationExample example);

    int updateByExample(@Param( "record" ) Notification record, @Param( "example" ) NotificationExample example);

    int updateByPrimaryKeySelective(Notification record);

    @Update( "update NOTIFICATION set NOTIFIER = #{notifier},RECEIVER = #{receiver},OUTERID = #{outerid}," +
            "TYPE = #{type},GMT_CREATE = #{gmt_Create},STATUS = #{status},NOTIFIER_NAME = #{notifier_Name}," +
            "OUTER_TITLE = #{outer_Title} where ID = #{id}" )
    int updateByPrimaryKey(Notification record);
}