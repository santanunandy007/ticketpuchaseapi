package com.example.ticketapi.service;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.ticketapi.dto.Seat;
import com.example.ticketapi.dto.Ticket;
import com.example.ticketapi.dto.TicketReceipt;

@Service
public class TrainService {

  private final Map<String, Ticket> tickets = new LinkedHashMap<>();
  private final Map<String, Seat> sectionA = new LinkedHashMap<>();
  private final Map<String, Seat> sectionB = new LinkedHashMap<>();

  /**
   * Constructor for the TrainService class. Initializes the seating sections A
   * and B with seats numbered from 1 to 50 and mark it as unoccupied.
   */

  public TrainService() {
    for (int i = 1; i <= 50; i++) {
      sectionA.put("A" + i, new Seat("A", i, false, "Not Occupied"));
      sectionB.put("B" + i, new Seat("B", i, false, "Not Occupied"));
    }
  }

  /**
   * Allocates a seat based on the ticket price and assigns it to the user with
   * the provided email address. If the ticket price is less than or equal to 20,
   * searches for an available seat in section A. If the ticket price is greater
   * than 20, searches for an available seat in section B.
   * 
   * @param ticketPrice The price of the ticket for which the seat is being
   *                    allocated.
   * @param emailId     The email address of the user to whom the seat will be
   *                    assigned.
   * @return The allocated seat if available; otherwise, returns null.
   */
  public Seat allocateSeat(double ticketPrice, String emailId) {
    try {
      if (ticketPrice <= 20) {
        for (Seat seat : sectionA.values()) {
          if (!seat.isOccupied()) {
            seat.setOccupied(true);
            seat.setOccupieduseremailId(emailId);

            return seat;
          }
        }
      } else {
        for (Seat seat : sectionB.values()) {
          if (!seat.isOccupied()) {
            seat.setOccupied(true);
            seat.setOccupieduseremailId(emailId);

            return seat;
          }
        }
      }
    } catch (NullPointerException e) {
      e.printStackTrace();
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return null;
  }

  /**
   * Adds a new user along with their ticket information to the system.
   * 
   * @param ticket The ticket object containing information about the user and
   *               their seat assignment.
   */

  public void addUser(Ticket ticket) {
    tickets.put(ticket.getUser().getEmail(), ticket);
  }

  /**
   * Retrieves the receipt for a ticket purchase associated with the provided
   * email address.
   * 
   * @param email The email address of the user for whom the receipt is requested.
   * @return The receipt for the ticket purchase, encapsulated in a TicketReceipt
   *         object, or null if no ticket is found for the provided email address.
   */

  public TicketReceipt getReceipt(String email) {
    try {
      Ticket ticket = tickets.get(email);
      if (ticket != null) {
        return new TicketReceipt(ticket);
      }
    } catch (NullPointerException e) {
      e.printStackTrace();
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return null;
  }

  public Map<String, Seat> getUsersBySection(String section) {
    return section.equals("A") ? sectionA : sectionB;
  }

  /**
   * Removes a user from the system based on their userId and marks their assigned
   * seat as unoccupied.
   * 
   * @param userId The unique identifier of the user to be removed.
   * @return true if the user is successfully removed along with their seat
   *         assignment; otherwise, false.
   */
  public boolean removeUser(String userId) {
    try {
      Ticket ticket = tickets.remove(userId);
      if (ticket != null) {
        ticket.getSeat().setOccupied(false);
        ticket.getSeat().setOccupieduseremailId("Not Occupied");

        return true;
      }
    } catch (NullPointerException e) {
      e.printStackTrace();
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    return false;
  }

  /**
   * Modifies the seat assigned to a user identified by their userId.
   * 
   * @param emailId    The emailId of the user whose seat is to be modified.
   * 
   * @param section    The section of the train where the new seat is located
   *                 
   * @param seatNumber The seat number within the specified section.
   */
  public void modifySeat(String emailId, String section, int seatNumber) {
    try {

      Ticket existingTicket = tickets.get(emailId);
      removeUser(emailId);
      Seat seat = section.equals("A") ? sectionA.get("A" + seatNumber) : sectionB.get("B" + seatNumber);
      seat.setOccupied(true);
      seat.setOccupieduseremailId(emailId);
      Ticket ticket = new Ticket(existingTicket.getFrom(), existingTicket.getTo(), existingTicket.getUser(),
          existingTicket.getPricePaid(), seat);
      ticket.setSeat(seat);
      addUser(ticket);
    } catch (NullPointerException e) {
      e.printStackTrace();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

}
