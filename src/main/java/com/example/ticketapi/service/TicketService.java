package com.example.ticketapi.service;

import java.util.Map;

import com.example.ticketapi.dto.ModifySeatRequest;
import com.example.ticketapi.dto.Seat;
import com.example.ticketapi.dto.TicketPurchaseRequest;
import com.example.ticketapi.dto.TicketReceipt;

public interface TicketService {

  /**
   * Method signature for purchasing a ticket.
   * 
   * @param request The TicketPurchaseRequest object containing information about
   *                the ticket to be purchased.
   * @return The receipt for the purchased ticket, encapsulated in a TicketReceipt
   *         object.
   */
  TicketReceipt purchaseTicket(TicketPurchaseRequest request);

  /**
   * Method signature for retrieving the receipt for a ticket purchase associated
   * with the provided email address.
   * 
   * @param email The email address of the user for whom the receipt is requested.
   * @return The receipt for the ticket purchase, encapsulated in a TicketReceipt
   *         object, or null if no ticket is found for the provided email address.
   */
  TicketReceipt getReceipt(String emailId);

  /**
   * Method signature for retrieving users seated in the specified section.
   * 
   * @param section The section of the train for which users are to be retrieved
   *                (e.g., "A" or "B").
   * @return A map containing seat information for users seated in the specified
   *         section.
   */
  Map<String, Seat> getUsersBySection(String section);

  /**
   * Method signature for removing a user from the system based on their userId.
   * 
   * @param userId The unique identifier of the user to be removed.
   * @return True if the user is successfully removed; otherwise, false.
   */
  boolean removeUser(String emailId);

  /**
   * Method signature for modifying the seat assigned to a user.
   * 
   * @param emailId  The emailId of the user whose seat is to be
   *                modified.
   * @param request The ModifySeatRequest object containing information about the
   *                new seat assignment.
   */
  void modifyUserSeat(String emailId, ModifySeatRequest request);

}
