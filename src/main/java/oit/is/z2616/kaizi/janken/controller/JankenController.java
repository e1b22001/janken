package oit.is.z2616.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z2616.kaizi.janken.model.Janken;

@Controller
@RequestMapping("/janken")

public class JankenController {

  // Janken GAME!に直接アクセスする
  @GetMapping
  public String janken() {
    return "janken.html";
  }

  // index.htmlからユーザー名を入力すると、janken.htmlで「Hi ユーザー名」と表示される
  @PostMapping
  public String janken(@RequestParam String name, ModelMap model) {
    model.addAttribute("name", name);
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
