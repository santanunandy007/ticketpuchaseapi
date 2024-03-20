package com.example.ticketapi.dto;

public class ModifySeatRequest {

  private String section;
  private int seatNumber;

  public ModifySeatRequest() {
    super();
  }

  public ModifySeatRequest(String section, int seatNumber) {
    super();
    this.section = section;
    this.seatNumber = seatNumber;
  }

  public String getSection() {
    return section;
  }

  public void setSection(String section) {
    this.section = section;
  }

  public int getSeatNumber() {
    return seatNumber;
  }

  public void setSeatNumber(int seatNumber) {
    this.seatNumber = seatNumber;
  }

}
