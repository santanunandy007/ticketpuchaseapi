package com.example.ticketapi.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.ticketapi.dto.ModifySeatRequest;
import com.example.ticketapi.dto.Seat;
import com.example.ticketapi.dto.TicketPurchaseRequest;
import com.example.ticketapi.dto.TicketReceipt;
import com.example.ticketapi.dto.User;
import com.example.ticketapi.service.TicketServiceImpl;
import com.example.ticketapi.service.TrainService;

@RunWith(MockitoJUnitRunner.class)
public class TicketServiceTest {

  TicketServiceImpl ticketService = new TicketServiceImpl(new TrainService());

  @Test
  public void testpurchaseTicket() throws Exception {

    User userdata = new User("san", "nan", "san@gmail.com");
    Seat seatdata = new Seat("A", 1, false, "Not Occupied");
    TicketReceipt receipt = ticketService.purchaseTicket(new TicketPurchaseRequest("London", "Paris", userdata, 12.0));

    assertEquals(userdata.getFirstName() + " " + userdata.getLastName(), receipt.getUserName());
    assertEquals(seatdata.getSeatNumber(), receipt.getSeatNumber());
    assertEquals(seatdata.getSection(), receipt.getSection());
  }

  @Test
  public void testgetUsersBySection() throws Exception {

    User userdata = new User("san", "nan", "san@gmail.com");
    Seat seatdata = new Seat("A", 1, false, "Not Occupied");
    ticketService.purchaseTicket(new TicketPurchaseRequest("London", "Paris", userdata, 12.0));

    final Map<String, Seat> expsectionA = new LinkedHashMap<>();
    seatdata.setOccupied(true);
    seatdata.setOccupieduseremailId("san@gmail.com");
    expsectionA.put("A1", seatdata);

    final Map<String, Seat> getsection = ticketService.getUsersBySection("A");

    assertEquals(expsectionA.get("A1").getOccupieduseremailId(), getsection.get("A1").getOccupieduseremailId());
    assertEquals(expsectionA.get("A1").isOccupied(), getsection.get("A1").isOccupied());
    assertEquals(expsectionA.get("A1").getSection(), getsection.get("A1").getSection());
  }

  @Test
  public void testremoveUser() throws Exception {

    User userdata = new User("san", "nan", "san@gmail.com");
    Seat seatdata = new Seat("A", 1, false, "Not Occupied");
    ticketService.purchaseTicket(new TicketPurchaseRequest("London", "Paris", userdata, 12.0));

    assertTrue(ticketService.getReceipt("san@gmail.com").getUserName().contains("san"));

    boolean removedtrue = ticketService.removeUser("san@gmail.com");

    assertTrue(removedtrue);
    org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> {
      ticketService.getReceipt("san@gmail.com").getUserName().contains("san");
    });
    assertFalse(seatdata.isOccupied());
    assertEquals("Not Occupied", seatdata.getOccupieduseremailId());

    boolean removedFalse = ticketService.removeUser("sant@gmail.com");
    assertFalse(removedFalse);
  }

  @Test
  public void testmodifyUserSeat() throws Exception {

    User userdata = new User("san", "nan", "san@gmail.com");
    ticketService.purchaseTicket(new TicketPurchaseRequest("London", "Paris", userdata, 12.0));

    String sectionB = "B";
    int seatNumberB = 20;
    String emailId = "san@gmail.com";

    ticketService.modifyUserSeat(emailId, new ModifySeatRequest(sectionB, seatNumberB));

    TicketReceipt modifiedTicketB = ticketService.getReceipt(emailId);
    assertNotNull(modifiedTicketB);
    assertEquals(sectionB, modifiedTicketB.getSection());
    assertEquals(seatNumberB, modifiedTicketB.getSeatNumber());

    String sectionA = "A";
    int seatNumberA = 05;

    ticketService.modifyUserSeat(emailId, new ModifySeatRequest(sectionA, seatNumberA));

    TicketReceipt modifiedTicketA = ticketService.getReceipt(emailId);
    assertNotNull(modifiedTicketA);
    assertEquals(sectionA, modifiedTicketA.getSection());
    assertEquals(seatNumberA, modifiedTicketA.getSeatNumber());
  }

}
