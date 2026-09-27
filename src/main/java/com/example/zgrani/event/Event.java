package com.example.zgrani.event;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Event {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private LocalDate dateRangeStart;

    @Column(nullable = false)
    private LocalDate dateRangeEnd;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "event_days_of_week", joinColumns = @jakarta.persistence.JoinColumn(name = "event_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private Set<DayOfWeek> daysOfWeek = new HashSet<>();

    @Column(nullable = false)
    private int expectedParticipants;

    @Column(nullable = false)
    private String organizerEmail;

    @Column(nullable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<CandidateTerm> candidateTerms = new HashSet<>();

    protected Event() {
        // JPA
    }

    public Event(
            UUID id,
            String name,
            String description,
            LocalDate dateRangeStart,
            LocalDate dateRangeEnd,
            Set<DayOfWeek> daysOfWeek,
            int expectedParticipants,
            String organizerEmail,
            Instant createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateRangeStart = dateRangeStart;
        this.dateRangeEnd = dateRangeEnd;
        this.daysOfWeek = new HashSet<>(daysOfWeek);
        this.expectedParticipants = expectedParticipants;
        this.organizerEmail = organizerEmail;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDateRangeStart() {
        return dateRangeStart;
    }

    public LocalDate getDateRangeEnd() {
        return dateRangeEnd;
    }

    public Set<DayOfWeek> getDaysOfWeek() {
        return daysOfWeek;
    }

    public int getExpectedParticipants() {
        return expectedParticipants;
    }

    public String getOrganizerEmail() {
        return organizerEmail;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Set<CandidateTerm> getCandidateTerms() {
        return candidateTerms;
    }

    public void addCandidateTerm(CandidateTerm term) {
        term.setEvent(this);
        this.candidateTerms.add(term);
    }
}
