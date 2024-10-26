package oit.is.z2616.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z2616.kaizi.janken.model.Janken;
import oit.is.z2616.kaizi.janken.model.Match;
import oit.is.z2616.kaizi.janken.model.MatchInfo;
import oit.is.z2616.kaizi.janken.model.MatchInfoMapper;
import oit.is.z2616.kaizi.janken.model.MatchMapper;
import oit.is.z2616.kaizi.janken.model.User;
import oit.is.z2616.kaizi.janken.model.UserMapper;
import oit.is.z2616.kaizi.janken.model.Entry;

@Controller

public class JankenController {

  @Autowired
  private Entry entry;

  @Autowired
  private UserMapper userMapper;

  @Autowired
  private MatchMapper matchMapper;

  @Autowired
  private MatchInfoMapper matchInfoMapper;

  // ログイン後に呼び出される
  @GetMapping("/janken")
  public String loginUser(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.entry.addUser(loginUser);
    ArrayList<User> users = userMapper.selectAllUsers();
    ArrayList<Match> matches = matchMapper.selectAllMatches();

    model.addAttribute("users", users);
    model.addAttribute("matches", matches);
    model.addAttribute("entry", this.entry);
    model.addAttribute("userCount", entry.getUsers().size());

    return "janken.html";
  }

  // ユーザーをクリックすると呼び出される
  @GetMapping("/match")
  public String match(@RequestParam Integer id, Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    User opponent = userMapper.selectById(id);

    model.addAttribute("loginUser", loginUser);
    model.addAttribute("opponent", opponent.getName());
    model.addAttribute("opponentId", opponent.getId());

    return "match.html";
  }
/*
  @GetMapping("/match/fight")
  public String fight(@RequestParam String userHand, @RequestParam Integer opponentId, Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    User user = userMapper.selectByName(loginUser);
    User opponent = userMapper.selectById(opponentId);

    Janken janken = new Janken(opponent.getName());
    String opponentHand = janken.randomCpuHand();

    String result = judgeJanken(userHand, opponentHand);
    String winner = jankenWinner(result);

    // DBに登録
    Match match = new Match();
    match.setUser1(user.getId());
    match.setUser2(opponentId);
    match.setUser1Hand(userHand);
    match.setUser2Hand(opponentHand);
    match.setWinner(winner);
    matchMapper.insertMatch(match);

    model.addAttribute("loginUser", loginUser);
    model.addAttribute("opponent", opponent.getName());
    model.addAttribute("userHand", userHand);
    model.addAttribute("opponentHand", opponentHand);
    model.addAttribute("opponentId", opponentId);
    model.addAttribute("result", result);
    model.addAttribute("winner", winner);

    return "match.html";
  }
 */
  @GetMapping("/match/wait")
  public String wait(@RequestParam String userHand, @RequestParam Integer opponentId, Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    User user = userMapper.selectByName(loginUser);
    User opponent = userMapper.selectById(opponentId);

    // DBに登録
    MatchInfo matchInfo = new MatchInfo();
    matchInfo.setUser1(user.getId());
    matchInfo.setUser2(opponentId);
    matchInfo.setUser1Hand(userHand);
    matchInfo.setActive(true);
    matchInfoMapper.insertMatchInfo(matchInfo);

    model.addAttribute("loginUser", loginUser);
    model.addAttribute("opponent", opponent.getName());
    model.addAttribute("userHand", userHand);
    model.addAttribute("opponentId", opponentId);

    return "wait.html";
  }

  // じゃんけんの勝敗を決める
  private String judgeJanken(String userHand, String cpuHand) {
    if (userHand.equals(cpuHand)) {
      return "Draw";
    } else if (userHand.equals("Gu") && cpuHand.equals("Choki") || userHand.equals("Choki") && cpuHand.equals("Pa")
        || userHand.equals("Pa") && cpuHand.equals("Gu")) {
      return "You Win!";
    } else {
      return "You Lose...";
    }
  }

  // じゃんけんの勝者を決める
  private String jankenWinner(String result) {
    if (result.contains("Win")) {
      return "user1の勝利";
    } else if (result.contains("Lose")) {
      return "user2の勝利";
    } else {
      return "引き分け";
    }
  }

}
