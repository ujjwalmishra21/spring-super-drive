package com.udacity.jwdnd.course1.cloudstorage.mappings;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USERS WHERE USERNAME=#{username}")
    User getUser(String username);

    @Insert("INSERT INTO USERS(username, password, salt, firstname, lastname) VALUES(#{username},#{password}, #{salt}, #{firstname}, #{lastname})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    Integer insertUser(User user);

}
