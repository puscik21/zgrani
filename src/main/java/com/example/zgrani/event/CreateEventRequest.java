package com.example.zgrani.event;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

public record CreateEventRequest(
        @NotBlank String name,
        String description,
        @NotNull LocalDate dateRangeStart,
        @NotNull LocalDate dateRangeEnd,
        @NotEmpty Set<DayOfWeek> daysOfWeek,
        @Min(1) int expectedParticipants,
        @NotBlank @Email String organizerEmail) {
}
