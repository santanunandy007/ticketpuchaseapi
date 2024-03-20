package com.example.ticketapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.ticketapi.dto.Seat;
import com.example.ticketapi.service.TrainService;

@RunWith(MockitoJUnitRunner.class)
public class TrainServiceTest {

    @Mock
    private TrainService trainService;

    @Test
    public void testallocateSeatforSectionA() throws Exception {
      
      double ticketPrice = 18.0;
      String emailId = "abcd@gmail.com";
      TrainService ts = new TrainService();
      Seat seatallecated = ts.allocateSeat(ticketPrice, emailId);
      Seat seatallecatedexp = new Seat("A",1,true,emailId);

      assertEquals(seatallecatedexp.getSection(), seatallecated.getSection());
      assertEquals(seatallecatedexp.getOccupieduseremailId(), seatallecated.getOccupieduseremailId());
    }
    
    @Test
    public void testallocateSeatforSectionB() throws Exception {
      
      double ticketPrice = 35.0;
      String emailId = "xyz@gmail.com";
      TrainService ts = new TrainService();
      Seat seatallecated = ts.allocateSeat(ticketPrice, emailId);
      Seat seatallecatedexp = new Seat("B",1,true,emailId);

      assertEquals(seatallecatedexp.getSection(), seatallecated.getSection());
      assertEquals(seatallecatedexp.getOccupieduseremailId(), seatallecated.getOccupieduseremailId());
    }
    
}
