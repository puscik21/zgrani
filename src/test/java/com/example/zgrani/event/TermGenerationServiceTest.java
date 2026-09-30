package com.example.zgrani.event;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TermGenerationServiceTest {

    private final TermGenerationService service = new TermGenerationService();

    @Test
    void returnsEmptyListWhenNoDayOfWeekMatchesInRange() {
        LocalDate start = LocalDate.of(2026, 10, 5); // Monday
        LocalDate end = LocalDate.of(2026, 10, 9); // Friday
        Set<DayOfWeek> daysOfWeek = EnumSet.of(DayOfWeek.SATURDAY);

        List<LocalDate> result = service.generate(start, end, daysOfWeek);

        assertThat(result).isEmpty();
    }

    @Test
    void acceptsExactlyThirtyMatches() {
        LocalDate start = LocalDate.of(2026, 1, 5); // Monday
        LocalDate end = start.plusWeeks(29); // 30th Monday
        Set<DayOfWeek> daysOfWeek = EnumSet.of(DayOfWeek.MONDAY);

        List<LocalDate> result = service.generate(start, end, daysOfWeek);

        assertThat(result).hasSize(30);
        assertThat(result).isSorted();
    }

    @Test
    void rejectsThirtyOneMatchesWithTooManyTermsException() {
        LocalDate start = LocalDate.of(2026, 1, 5); // Monday
        LocalDate end = start.plusWeeks(30); // 31st Monday
        Set<DayOfWeek> daysOfWeek = EnumSet.of(DayOfWeek.MONDAY);

        assertThatThrownBy(() -> service.generate(start, end, daysOfWeek))
                .isInstanceOf(TooManyTermsException.class)
                .satisfies(ex -> assertThat(((TooManyTermsException) ex).getGeneratedCount()).isEqualTo(31));
    }

    @Test
    void returnsSingleMatchingDayInAMultiDayRange() {
        LocalDate start = LocalDate.of(2026, 10, 5); // Monday
        LocalDate end = LocalDate.of(2026, 10, 11); // Sunday
        Set<DayOfWeek> daysOfWeek = EnumSet.of(DayOfWeek.WEDNESDAY);

        List<LocalDate> result = service.generate(start, end, daysOfWeek);

        assertThat(result).containsExactly(LocalDate.of(2026, 10, 7));
    }

    @Test
    void handlesStartEqualToEnd() {
        LocalDate day = LocalDate.of(2026, 10, 5); // Monday
        Set<DayOfWeek> daysOfWeek = EnumSet.of(DayOfWeek.MONDAY);

        List<LocalDate> result = service.generate(day, day, daysOfWeek);

        assertThat(result).containsExactly(day);
    }
}
