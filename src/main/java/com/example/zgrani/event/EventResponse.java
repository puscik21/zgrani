package com.example.zgrani.event;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record EventResponse(
        UUID id,
        String name,
        String description,
        LocalDate dateRangeStart,
        LocalDate dateRangeEnd,
        Set<DayOfWeek> daysOfWeek,
        int expectedParticipants,
        String organizerEmail,
        List<LocalDate> candidateTerms,
        Instant createdAt) {
}
