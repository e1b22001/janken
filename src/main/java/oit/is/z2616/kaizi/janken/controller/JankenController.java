package oit.is.z2616.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z2616.kaizi.janken.model.Janken;
import oit.is.z2616.kaizi.janken.model.Match;
import oit.is.z2616.kaizi.janken.model.MatchInfo;
import oit.is.z2616.kaizi.janken.model.MatchInfoMapper;
import oit.is.z2616.kaizi.janken.model.MatchMapper;
import oit.is.z2616.kaizi.janken.model.User;
import oit.is.z2616.kaizi.janken.model.UserMapper;
import oit.is.z2616.kaizi.janken.model.Entry;
import oit.is.z2616.kaizi.janken.service.AsyncKekka;

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

  @Autowired
  private AsyncKekka asynckekka;

  // ログイン後に呼び出される
  @GetMapping("/janken")
  public String loginUser(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.entry.addUser(loginUser);
    ArrayList<User> users = userMapper.selectAllUsers();
    ArrayList<MatchInfo> matchInfos = matchInfoMapper.selectAllMatchInfos();
    ArrayList<Match> matches = matchMapper.selectAllMatches();

    Match resetMatch = matchMapper.selectActiveMatch();
    if (resetMatch != null && resetMatch.isActive()) {
      matchMapper.deactivateMatches(resetMatch.getId());
    }

    model.addAttribute("users", users);
    model.addAttribute("matchInfos", matchInfos);
    model.addAttribute("matches", matches);
    model.addAttribute("entry", this.entry);
    model.addAttribute("userCount", entry.getUsers().size());

    return "janken.html";
  }

  @GetMapping("/matches/result")
  public SseEmitter matchesResult() {
    final SseEmitter sseEmitter = new SseEmitter();
    this.asynckekka.asyncSendAllMatchesResults(sseEmitter);;
    return sseEmitter;
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

  @GetMapping("/match/fight")
  @Transactional
  public String fight(@RequestParam String userHand, @RequestParam Integer opponentId, Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    User user = userMapper.selectByName(loginUser);
    User opponent = userMapper.selectById(opponentId);

    if (opponent.getName().equals("CPU")) {
      Janken janken = new Janken(opponent.getName());
      String cpuHand = janken.randomCpuHand();
      String result = judgeJanken(userHand, cpuHand);
      String winner = jankenWinner(result);
      Match match = new Match();

      match.setUser1(user.getId());
      match.setUser2(opponentId);
      match.setUser1Hand(userHand);
      match.setUser2Hand(cpuHand);
      match.setActive(true);
      match.setWinner(winner);
      this.asynckekka.syncInsertMatch(match);
      
      model.addAttribute("loginUser", loginUser);
      model.addAttribute("userHand", userHand);
      model.addAttribute("cpuHand", cpuHand);
      model.addAttribute("result", result);

      return "cpu.html";
    } else {
      MatchInfo activeMatchInfo = matchInfoMapper.selectActiveMatchInfo(user.getId());
      if (activeMatchInfo == null) {
        MatchInfo matchInfo = new MatchInfo();

        matchInfo.setUser1(user.getId());
        matchInfo.setUser2(opponentId);
        matchInfo.setUser1Hand(userHand);
        matchInfo.setActive(true);
        matchInfoMapper.insertMatchInfo(matchInfo);
      } else {
        String opponentHand = activeMatchInfo.getUser1Hand();
        Match match = new Match();

        String result = judgeJanken(opponentHand, userHand);
        String winner = jankenWinner(result);

        match.setUser1(opponentId);
        match.setUser2(user.getId());
        match.setUser1Hand(opponentHand);
        match.setUser2Hand(userHand);
        match.setActive(true);
        match.setWinner(winner);
        this.asynckekka.syncInsertMatch(match);

        matchInfoMapper.deactivateMatchInfo(activeMatchInfo.getId());

        model.addAttribute("match", match);
      }
      model.addAttribute("loginUser", loginUser);

      return "wait.html";
    }
  }

  @GetMapping("wait")
  public SseEmitter waitKekka() {
    final SseEmitter sseEmitter = new SseEmitter();
    this.asynckekka.asyncSendMatchResult(sseEmitter);
    return sseEmitter;
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
