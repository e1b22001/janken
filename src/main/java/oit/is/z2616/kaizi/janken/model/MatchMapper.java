package oit.is.z2616.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;

@Mapper

public interface MatchMapper {
  @Select("select * from matches")
  ArrayList<Match> selectAllMatches();

  @Insert("insert into matches (user1, user2, user1Hand, user2Hand) VALUES (#{user1}, #{user2}, #{user1Hand}, #{user2Hand})")
  void insertMatch(Match match);
}
