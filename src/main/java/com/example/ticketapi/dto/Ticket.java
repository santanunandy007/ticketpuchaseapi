package com.example.ticketapi.dto;

public class Ticket {

  private String from;
  private String to;
  private User user;
  private double pricePaid;
  private Seat seat;

  public Ticket() {
    super();
  }

  public Ticket(String from, String to, User user, double pricePaid, Seat seat) {
    super();
    this.from = from;
    this.to = to;
    this.user = user;
    this.pricePaid = pricePaid;
    this.seat = seat;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public double getPricePaid() {
    return pricePaid;
  }

  public void setPricePaid(double pricePaid) {
    this.pricePaid = pricePaid;
  }

  public Seat getSeat() {
    return seat;
  }

  public void setSeat(Seat seat) {
    this.seat = seat;
  }

}
