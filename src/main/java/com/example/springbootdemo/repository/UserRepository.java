package com.example.springbootdemo.repository;

import com.example.springbootdemo.dto.UserDTO;
import com.example.springbootdemo.model.UserModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserRepository {
    @Select("SELECT id, userName, accessToken, password FROM Users WHERE userName = #{username}")
    UserModel findByName(String username);

    @Insert("INSERT INTO Users(userName, accessToken, password, isValid) VALUES(#{ut.username}, null, #{ut.encryptedPassword}, true)")
    void insert(@Param("ut") UserDTO userDTO);

    @Update("UPDATE Users SET valid=false WHERE id=#{id}")
    void updateValid(Integer id);

}
