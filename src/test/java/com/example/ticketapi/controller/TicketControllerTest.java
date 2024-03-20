package com.example.ticketapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.ticketapi.dto.ModifySeatRequest;
import com.example.ticketapi.dto.Seat;
import com.example.ticketapi.dto.Ticket;
import com.example.ticketapi.dto.TicketPurchaseRequest;
import com.example.ticketapi.dto.TicketReceipt;
import com.example.ticketapi.dto.User;
import com.example.ticketapi.service.TicketService;
import com.example.ticketapi.service.TrainService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class TicketControllerTest {
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    @InjectMocks
    private TicketController ticketController;

    @Mock
    private TicketService ticketService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(ticketController).build();
    }

    @Test
    public void testPurchaseTicket() throws Exception {
        User user = new User("Firstname", "Lastname", "abcd@gmail.com");
        TicketPurchaseRequest ticketPurchaseRequest = new TicketPurchaseRequest("London", "France", user,
                23.0);

        Seat seat = new Seat("A", 123, true, "1234");
        Ticket ticket = new Ticket("London", "France", user, 23.0, seat);
        TicketReceipt ticketReceipt = new TicketReceipt(ticket);
        when(ticketService.purchaseTicket(any())).thenReturn(ticketReceipt);

        String jsonTicketPurchaseRequest = objectMapper.writeValueAsString(ticketPurchaseRequest);

        MvcResult result = mockMvc.perform(post("/ticket/purchase")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTicketPurchaseRequest))
                .andExpect(status().isOk())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        String expectedResponseBody = "Ticket Booked Successfully for user: "+user.getEmail();
        assertEquals(expectedResponseBody, responseBody);
        
    }

    @Test
    public void testGetReceipt() throws Exception {
        User user = new User("Firstname", "Lastname", "abcd@gmail.com");

        Seat seat = new Seat("A", 123, true, "1234");
        Ticket ticket = new Ticket("London", "France", user, 23.0, seat);
        TicketReceipt ticketReceipt = new TicketReceipt(ticket);
        when(ticketService.getReceipt(any())).thenReturn(ticketReceipt);

        MvcResult result = mockMvc.perform(get("/ticket/receipt/{emailId}", "abcd@gmail.com")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        assertEquals(objectMapper.writeValueAsString(ticketReceipt), responseBody);
    }

    @Test
    public void testGetUsersBySection() throws Exception {
        Seat seat = new Seat("A", 123, true, "1234");
        Map<String, Seat> seats = new LinkedHashMap<>();
        seats.put("A", seat);
        when(ticketService.getUsersBySection(any())).thenReturn(seats);

        MvcResult result = mockMvc.perform(get("/ticket/users/{section}", "A")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        Map<String, Seat> actualMap = new ObjectMapper().readValue(responseBody,
                new TypeReference<Map<String, Seat>>() {
                });
        assertEquals(seats.size(), actualMap.size());
    }

    @Test
    public void testRemoveUser() throws Exception {
        when(ticketService.removeUser(any())).thenReturn(true);

        MvcResult result = mockMvc.perform(delete("/ticket/remove/{emailId}", "abcd@gmail.com"))
                .andExpect(status().isOk())
                .andReturn();
        String responseBody = result.getResponse().getContentAsString();
        assertEquals(responseBody, "User removed successfully."); // Check sizes first
    }

    @Test
    public void testRemoveUserNotFound() throws Exception {
        when(ticketService.removeUser(any())).thenReturn(false);

        mockMvc.perform(delete("/ticket/remove/{emailId}", "abcd@gmail.com"))
                .andExpect(status().isNotFound());

    }

    @Test
    public void testModifySeat() throws Exception {
        ModifySeatRequest modifySeatRequest = new ModifySeatRequest("A", 123);
        doNothing().when(ticketService).modifyUserSeat("abcd@gmail.com", modifySeatRequest);
        MvcResult result = mockMvc.perform(put("/ticket/modify/{emailId}", "abcd@gmail.com")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modifySeatRequest)))
                .andExpect(status().isOk()).andReturn();
        String responseBody = result.getResponse().getContentAsString();
        assertEquals(responseBody, "User's seat modified successfully.");
    }
    
    
    @Test
    public void testallocateSeat() throws Exception {
      
      double ticketPrice = 20.0;
      String emailId = "abcd@gmail.com";
      TrainService ts = new TrainService();
      Seat seatallecated = ts.allocateSeat(ticketPrice, emailId);
      Seat seatallecatedexp = new Seat("A",1,true,emailId);

      assertEquals(seatallecatedexp.getSection(), seatallecated.getSection());
      assertEquals(seatallecatedexp.getOccupieduseremailId(), seatallecated.getOccupieduseremailId());
    }
}
