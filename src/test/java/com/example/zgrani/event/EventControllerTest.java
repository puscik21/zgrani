package com.example.zgrani.event;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createThenGetReturnsSameEventWithGeneratedTerms() throws Exception {
        CreateEventRequest request = new CreateEventRequest(
                "Integracja zespołu",
                "Wyjście integracyjne",
                LocalDate.of(2026, 10, 5), // Monday
                LocalDate.of(2026, 10, 11), // Sunday
                EnumSet.of(DayOfWeek.WEDNESDAY, DayOfWeek.FRIDAY),
                10,
                "organizer@example.com");

        String createResponse = mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.candidateTerms.length()").value(2))
                .andExpect(jsonPath("$.candidateTerms[0]").value("2026-10-07"))
                .andExpect(jsonPath("$.candidateTerms[1]").value("2026-10-09"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        EventResponse created = objectMapper.readValue(createResponse, EventResponse.class);

        mockMvc.perform(get("/api/events/{id}", created.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(created.id().toString()))
                .andExpect(jsonPath("$.candidateTerms.length()").value(2));
    }

    @Test
    void createWithMissingRequiredFieldReturnsProblemDetail() throws Exception {
        String invalidJson = """
                {
                  "description": "brak wymaganych pól",
                  "dateRangeStart": "2026-10-05",
                  "dateRangeEnd": "2026-10-11",
                  "daysOfWeek": ["MONDAY"],
                  "expectedParticipants": 5
                }
                """;

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
    }

    @Test
    void createExceedingThirtyTermsReturnsUnprocessableEntity() throws Exception {
        CreateEventRequest request = new CreateEventRequest(
                "Za dużo terminów",
                null,
                LocalDate.of(2026, 1, 5), // Monday
                LocalDate.of(2026, 1, 5).plusWeeks(30), // 31st Monday
                EnumSet.of(DayOfWeek.MONDAY),
                5,
                "organizer@example.com");

        mockMvc.perform(post("/api/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
    }

    @Test
    void getNonexistentEventReturnsNotFound() throws Exception {
        mockMvc.perform(get("/api/events/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON));
    }
}
