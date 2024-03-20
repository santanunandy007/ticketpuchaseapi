package com.example.ticketapi.dto;

public class Seat {

  private String section;
  private int seatNumber;
  private boolean occupied;
  private String occupieduseremailId;

  public Seat() {
    super();
  }

  public Seat(String section, int seatNumber, boolean occupied, String occupieduseremailId) {
    super();
    this.section = section;
    this.seatNumber = seatNumber;
    this.occupied = occupied;
    this.occupieduseremailId = occupieduseremailId;
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

  public boolean isOccupied() {
    return occupied;
  }

  public void setOccupied(boolean occupied) {
    this.occupied = occupied;
  }

  public String getOccupieduseremailId() {
    return occupieduseremailId;
  }

  public void setOccupieduseremailId(String occupieduseremailId) {
    this.occupieduseremailId = occupieduseremailId;
  }

}
