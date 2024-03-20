package com.example.ticketapi.dto;

public class TicketPurchaseRequest {

  private String from;
  private String to;
  private User user;
  private double price;

  public TicketPurchaseRequest() {
    super();
  }

  public TicketPurchaseRequest(String from, String to, User user, double price) {
    super();
    this.from = from;
    this.to = to;
    this.user = user;
    this.price = price;
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

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

}
