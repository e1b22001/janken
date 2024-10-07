package oit.is.z2616.kaizi.janken.model;

import java.util.Random;

public class Janken {
  String name;
  String userHand;
  String cpuHand[] = { "Gu", "Cho", "Pa" };
  String result;

  public Janken(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUserHand() {
    return userHand;
  }

  public void setUserHand(String userHand) {
    this.userHand = userHand;
  }

  public String randomCpuHand() {
    Random random = new Random();
    int index = random.nextInt(3);
    return cpuHand[index];
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }
}
