package oit.is.z2616.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper

public interface UserMapper {
  @Select("select * from users")
  ArrayList<User> selectAllUsers();

  @Select("SELECT * FROM users WHERE id = #{id}")
  User selectById(int id);

  @Select("SELECT * FROM users WHERE name = #{name}")
  User selectByName(String name);
}
