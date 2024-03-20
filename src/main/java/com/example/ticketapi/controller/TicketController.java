package com.example.ticketapi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ticketapi.dto.ModifySeatRequest;
import com.example.ticketapi.dto.Seat;
import com.example.ticketapi.dto.TicketPurchaseRequest;
import com.example.ticketapi.dto.TicketReceipt;
import com.example.ticketapi.service.TicketService;

@RestController
@RequestMapping("/ticket")
public class TicketController {

  @Autowired
  private TicketService ticketService;

  /*
   * This method handles HTTP POST requests to the "/purchase" endpoint to book
   * tickets
   */
  @PostMapping("/purchase")
  public ResponseEntity<String> purchaseTicket(@RequestBody TicketPurchaseRequest request) {
    try {
      TicketReceipt receipt = ticketService.purchaseTicket(request);
      if (receipt != null) {
        return ResponseEntity.ok("Ticket Booked Successfully for user: "+request.getUser().getEmail());
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  /*
   * This method handles HTTP GET requests to the "/receipt/{emailId}" endpoint to
   * get tickets receipt based on user detail where {emailId} is a path variable
   * representing the email address of the ticket purchaser.
   */
  @GetMapping("/receipt/{emailId}")
  public ResponseEntity<TicketReceipt> getReceipt(@PathVariable("emailId") String email) {
    try {
      TicketReceipt receipt = ticketService.getReceipt(email);
      if (receipt != null) {
        return ResponseEntity.ok(receipt);
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  /*
   * This method handles HTTP GET requests to the "/users/{section}" endpoint to
   * get seat details based on seat section where {section} is a path variable
   * representing the section of seats to retrieve users
   */

  @GetMapping(value = "/users/{section}")
  public ResponseEntity<Map<String, Seat>> getUsersBySection(@PathVariable("section") String section) {
    try {
      Map<String, Seat> seats = ticketService.getUsersBySection(section);
      if (seats != null) {
        return ResponseEntity.ok(seats);
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  /*
   * This method handles HTTP DELETE requests to the "/remove/{emailId}" endpoint
   * to delete purchased ticket of a user where {emailId} is a path variable
   * representing the email address of the user to be deleted.
   */
  @DeleteMapping("/remove/{emailId}")
  public ResponseEntity<String> removeUser(@PathVariable("emailId") String emailId) {
    try {
      if (ticketService.removeUser(emailId)) {
        return ResponseEntity.ok("User removed successfully.");
      } else {
        return ResponseEntity.notFound().build();
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  /*
   * This method handles HTTP PUT requests to the "/modify/{emailId}" endpoint to
   * modify purchased seat of a user where {emailId} is a path variable
   * representing the email address of the user whose seat is to be modified.
   */
  @PutMapping("/modify/{emailId}")
  public ResponseEntity<String> modifySeat(@PathVariable("emailId") String emailId,
      @RequestBody ModifySeatRequest request) {
    try {
      ticketService.modifyUserSeat(emailId, request);
      return ResponseEntity.ok("User's seat modified successfully.");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

}
