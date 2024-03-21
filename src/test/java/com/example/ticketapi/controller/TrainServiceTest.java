package com.example.ticketapi.controller;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.ticketapi.dto.Seat;
import com.example.ticketapi.dto.Ticket;
import com.example.ticketapi.dto.TicketReceipt;
import com.example.ticketapi.dto.User;
import com.example.ticketapi.service.TrainService;

@RunWith(MockitoJUnitRunner.class)
public class TrainServiceTest {

  TrainService trainService = new TrainService();

  @Test
  public void testallocateSeatforSectionA() throws Exception {

    double ticketPriceA = 18.0;
    String emailIdA = "abcd@gmail.com";
    TrainService ts = new TrainService();
    Seat seatallocatedA = ts.allocateSeat(ticketPriceA, emailIdA);
    Seat seatallocatedexpA = new Seat("A", 1, true, emailIdA);

    assertEquals(seatallocatedexpA.getSection(), seatallocatedA.getSection());
    assertEquals(seatallocatedexpA.getOccupieduseremailId(), seatallocatedA.getOccupieduseremailId());

    double ticketPriceB = 35.0;
    String emailIdB = "xyz@gmail.com";

    Seat seatallocatedB = ts.allocateSeat(ticketPriceB, emailIdB);
    Seat seatallocatedexpB = new Seat("B", 1, true, emailIdB);

    assertEquals(seatallocatedexpB.getSection(), seatallocatedB.getSection());
    assertEquals(seatallocatedexpB.getOccupieduseremailId(), seatallocatedB.getOccupieduseremailId());

  }

  User userdata = new User();
  Seat seatdata = new Seat();

  @BeforeEach
  public void setUp() {
    userdata = new User("santanu", "nandy", "santanu@gmail.com");
    seatdata = new Seat("A", 10, false, "Not Occupied");
    trainService.addUser(new Ticket("CityA", "CityB", userdata, 50, seatdata));
  }

  @Test
  public void testmodifySeat() throws Exception {

    String emailId = "santanu@gmail.com";
    String sectionB = "B";
    int seatNumberB = 20;

    trainService.modifySeat(emailId, sectionB, seatNumberB);

    TicketReceipt modifiedTicketB = trainService.getReceipt(emailId);
    assertNotNull(modifiedTicketB);
    assertEquals(sectionB, modifiedTicketB.getSection());
    assertEquals(seatNumberB, modifiedTicketB.getSeatNumber());

    String sectionA = "A";
    int seatNumberA = 05;

    trainService.modifySeat(emailId, sectionA, seatNumberA);

    TicketReceipt modifiedTicketA = trainService.getReceipt(emailId);
    assertNotNull(modifiedTicketA);
    assertEquals(sectionA, modifiedTicketA.getSection());
    assertEquals(seatNumberA, modifiedTicketA.getSeatNumber());
  }

  @Test
  public void testremoveUser() throws Exception {

    userdata = new User("santanu", "nandy", "santanu@gmail.com");
    seatdata = new Seat("A", 5, false, "Not Occupied");
    trainService.addUser(new Ticket("London", "Paris", userdata, 12.0, seatdata));

    assertTrue(trainService.getReceipt("santanu@gmail.com").getUserName().contains("santanu"));

    boolean removed = trainService.removeUser("santanu@gmail.com");

    assertTrue(removed);
    org.junit.jupiter.api.Assertions.assertThrows(Exception.class, () -> {
      trainService.getReceipt("santanu@gmail.com").getUserName().contains("santanu");
    });
    assertFalse(seatdata.isOccupied());
    assertEquals("Not Occupied", seatdata.getOccupieduseremailId());

    boolean removedFalse = trainService.removeUser("sant@gmail.com");
    assertFalse(removedFalse);
  }

  @Test
  public void testgetReceipt() throws Exception {

    User userdata = new User("san", "nan", "san@gmail.com");
    Seat seatdata = new Seat("A", 5, false, "Not Occupied");
    trainService.addUser(new Ticket("London", "Paris", userdata, 12.0, seatdata));

    TicketReceipt receipt = trainService.getReceipt("san@gmail.com");

    assertEquals(userdata.getFirstName() + " " + userdata.getLastName(),
        trainService.getReceipt("san@gmail.com").getUserName());
    assertEquals(seatdata.getSeatNumber(), receipt.getSeatNumber());
    assertEquals(seatdata.getSection(), receipt.getSection());

    org.junit.jupiter.api.Assertions.assertThrows(NullPointerException.class, () -> {
      trainService.getReceipt(null).getUserName().contains("san");
    });
  }

}
