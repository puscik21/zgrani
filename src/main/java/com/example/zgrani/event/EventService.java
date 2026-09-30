package com.example.zgrani.event;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final TermGenerationService termGenerationService;

    public EventService(EventRepository eventRepository, TermGenerationService termGenerationService) {
        this.eventRepository = eventRepository;
        this.termGenerationService = termGenerationService;
    }

    @Transactional
    public EventResponse create(CreateEventRequest request) {
        if (request.dateRangeEnd().isBefore(request.dateRangeStart())) {
            throw new IllegalArgumentException("dateRangeEnd must not be before dateRangeStart");
        }

        List<LocalDate> terms = termGenerationService.generate(
                request.dateRangeStart(), request.dateRangeEnd(), request.daysOfWeek());

        Event event = new Event(
                UUID.randomUUID(),
                request.name(),
                request.description(),
                request.dateRangeStart(),
                request.dateRangeEnd(),
                request.daysOfWeek(),
                request.expectedParticipants(),
                request.organizerEmail(),
                Instant.now());

        for (LocalDate term : terms) {
            event.addCandidateTerm(new CandidateTerm(term));
        }

        Event saved = eventRepository.save(event);
        return toResponse(saved);
    }

    @Transactional
    public EventResponse findById(UUID id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException(id));
        return toResponse(event);
    }

    private EventResponse toResponse(Event event) {
        List<LocalDate> candidateTerms = event.getCandidateTerms().stream()
                .map(CandidateTerm::getDate)
                .sorted(Comparator.naturalOrder())
                .toList();

        return new EventResponse(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getDateRangeStart(),
                event.getDateRangeEnd(),
                event.getDaysOfWeek(),
                event.getExpectedParticipants(),
                event.getOrganizerEmail(),
                candidateTerms,
                event.getCreatedAt());
    }
}
