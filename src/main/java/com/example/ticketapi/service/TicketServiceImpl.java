package com.example.ticketapi.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.ticketapi.dto.ModifySeatRequest;
import com.example.ticketapi.dto.Seat;
import com.example.ticketapi.dto.Ticket;
import com.example.ticketapi.dto.TicketPurchaseRequest;
import com.example.ticketapi.dto.TicketReceipt;

@Service
public class TicketServiceImpl implements TicketService {

  private final TrainService trainService;

  public TicketServiceImpl(TrainService trainService) {
    this.trainService = trainService;
  }

  /**
   * Overrides the purchaseTicket method to process a ticket purchase request.
   * 
   * @param request The TicketPurchaseRequest object containing information about
   *                the ticket purchase.
   * @return The receipt for the purchased ticket, in a TicketReceipt object.
   */

  @Override
  public TicketReceipt purchaseTicket(TicketPurchaseRequest request) {
    // Allocate a seat for the user based on the requested ticket price and their
    // email address.
    Seat seat = trainService.allocateSeat(request.getPrice(), request.getUser().getEmail());
    Ticket ticket = new Ticket(request.getFrom(), request.getTo(), request.getUser(), request.getPrice(), seat);
    ticket.setSeat(seat);
    trainService.addUser(ticket);
    return new TicketReceipt(ticket);
  }

  /**
   * Overrides the getReceipt method to retrieve the receipt for a ticket purchase
   * associated with the provided email address.
   * 
   * @param email The email address of the user for whom the receipt is requested.
   * @return The receipt for the ticket purchase, in a TicketReceipt object.
   */

  @Override
  public TicketReceipt getReceipt(String email) {
    return trainService.getReceipt(email);
  }

  /**
   * Overrides the getUsersBySection method to retrieve users seated in the
   * specified section.
   * 
   * @param section The section of the train for which users & seat details are to
   *                be retrieved (e.g., "A" or "B").
   * @return A map containing seat information for users seated in the specified
   *         section.
   */

  @Override
  public Map<String, Seat> getUsersBySection(String section) {
    return trainService.getUsersBySection(section);
  }

  /**
   * Overrides the removeUser method to remove a user from the system based on
   * their emailId.
   * 
   * @param emailId The emailId of the user to be removed.
   * @return True if the user is successfully removed; otherwise, false.
   */

  @Override
  public boolean removeUser(String emailId) {
    if (trainService.removeUser(emailId)) {
      return true;
    }
    return false;
  }

  /**
   * Overrides the modifyUserSeat method to modify the seat assigned to a user.
   * 
   * @param emailId The emailId of the user whose seat is to be modified.
   * @param request The ModifySeatRequest object containing information about the
   *                new seat assignment.
   */

  @Override
  public void modifyUserSeat(String emailId, ModifySeatRequest request) {
    trainService.modifySeat(emailId, request.getSection(), request.getSeatNumber());
  }

}
