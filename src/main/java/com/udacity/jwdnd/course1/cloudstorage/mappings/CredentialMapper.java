package com.udacity.jwdnd.course1.cloudstorage.mappings;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE USERID=#{userId}")
    List<Credential> getCredentials(Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE CREDENTIALID=#{credentialId}")
    Credential getCredential(Integer credentialId);

    @Insert("INSERT INTO CREDENTIALS(url, username, key, password, userId) VALUES (#{url}, #{username}, #{key},#{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer insertCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET URL=#{url}, USERNAME=#{username}, PASSWORD=#{password}, KEY=#{key} WHERE CREDENTIALID=#{credentialId}")
    Integer updateCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE CREDENTIALID=#{credentialId}")
    Integer deleteCredential(Integer credentialId);

}
