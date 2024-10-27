package oit.is.z2616.kaizi.janken.model;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

@Mapper

public interface MatchMapper {
  @Select("select * from matches")
  ArrayList<Match> selectAllMatches();

  @Select("select * from matches where isActive = true")
  Match selectActiveMatch();

  @Insert("insert into matches (user1, user2, user1Hand, user2Hand, isActive, winner) VALUES (#{user1}, #{user2}, #{user1Hand}, #{user2Hand}, #{isActive}, #{winner})")
  void insertMatch(Match match);

  @Update("update matches set isActive = false where id = #{id}")
  void deactivateMatches(int id);
}
