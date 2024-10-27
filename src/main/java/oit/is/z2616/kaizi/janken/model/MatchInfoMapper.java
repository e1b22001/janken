package oit.is.z2616.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper

public interface MatchInfoMapper {
  @Select("select * from matchInfo")
  ArrayList<MatchInfo> selectAllMatchInfos();

  @Select("select * from matchinfo where isActive = true and user2 = #{user2}")
  MatchInfo selectActiveMatchInfo(int user2);

  @Insert("insert into matchinfo (user1, user2, user1Hand, isActive) VALUES (#{user1}, #{user2}, #{user1Hand}, #{isActive})")
  void insertMatchInfo(MatchInfo matchInfo);

  @Update("update matchinfo set isActive = false where id = #{id}")
  void deactivateMatchInfo(int id);
}
