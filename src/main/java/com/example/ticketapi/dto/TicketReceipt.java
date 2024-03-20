package com.example.ticketapi.dto;

public class TicketReceipt {

  private String from;
  private String to;
  private String userName;
  private double pricePaid;
  private String section;
  private int seatNumber;

  public TicketReceipt() {
    super();
  }

  public TicketReceipt(Ticket ticket) {
    this.from = ticket.getFrom();
    this.to = ticket.getTo();
    this.userName = ticket.getUser().getFirstName() + " " + ticket.getUser().getLastName();
    this.pricePaid = ticket.getPricePaid();
    this.section = ticket.getSeat().getSection();
    this.seatNumber = ticket.getSeat().getSeatNumber();
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

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public double getPricePaid() {
    return pricePaid;
  }

  public void setPricePaid(double pricePaid) {
    this.pricePaid = pricePaid;
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

  @Override
  public String toString() {
    return "from: " + from + ", " + "\n" + "to: " + to + ", " + "\n" + "userName: " + userName + "," + "\n"
        + "pricePaid: " + pricePaid + ", " + "\n" + "section: " + section + "," + "\n" + "seatNumber: " + seatNumber;
  }

}
