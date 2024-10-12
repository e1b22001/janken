package oit.is.z2616.kaizi.janken.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z2616.kaizi.janken.model.Janken;
import oit.is.z2616.kaizi.janken.model.Entry;

@Controller
@RequestMapping("/janken")

public class JankenController {

  @Autowired
  private Entry entry;

  // ログイン後に呼び出される
  @GetMapping
  public String loginUser(Principal prin, ModelMap model) {
    String loginUser = prin.getName();
    this.entry.addUser(loginUser);
    model.addAttribute("entry", this.entry);
    model.addAttribute("userCount", entry.getUsers().size());

    return "janken.html";
  }

  // じゃんけんの手のリンクを押すと呼び出される
  @GetMapping("/play")
  public String play(@RequestParam String userHand, ModelMap model) {
    Janken janken = new Janken("CPU");
    String cpuHand = janken.randomCpuHand();

    String result = judgeJanken(userHand, cpuHand);

    model.addAttribute("userHand", userHand);
    model.addAttribute("cpuHand", cpuHand);
    model.addAttribute("result", result);

    return "janken.html";
  }

  // じゃんけんの勝敗を決める
  private String judgeJanken(String userHand, String cpuHand) {
    if (userHand.equals(cpuHand)) {
      return "Draw";
    } else if (userHand.equals("Gu") && cpuHand.equals("Cho") || userHand.equals("Cho") && cpuHand.equals("Pa") || userHand.equals("Pa") && cpuHand.equals("Gu")) {
      return "You Win!";
    } else {
      return "You Lose...";
    }
  }
}
