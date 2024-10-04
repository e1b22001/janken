package oit.is.z2616.kaizi.janken.model;

public class Janken {
  String name;
  String userHand;
  String cpuHand;
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

  public String getCpuHand() {
    return cpuHand;
  }

  public void setCpuHand(String cpuHand) {
    this.cpuHand = cpuHand;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }
}
