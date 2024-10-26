package oit.is.z2616.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper

public interface MatchInfoMapper {
  @Select("select * from matchInfo")
  ArrayList<MatchInfo> selectAllMatchInfos();

  @Insert("insert into matchinfo (user1, user2, user1Hand, isActive) VALUES (#{user1}, #{user2}, #{user1Hand}, #{isActive})")
  void insertMatchInfo(MatchInfo matchInfo);
}
