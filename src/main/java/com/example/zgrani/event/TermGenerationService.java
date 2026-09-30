package com.example.zgrani.event;

import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class TermGenerationService {

    private static final int MAX_TERMS = 30;

    public List<LocalDate> generate(LocalDate start, LocalDate end, Set<DayOfWeek> daysOfWeek) {
        List<LocalDate> terms = new ArrayList<>();
        for (LocalDate date = start; !date.isAfter(end); date = date.plusDays(1)) {
            if (daysOfWeek.contains(date.getDayOfWeek())) {
                terms.add(date);
            }
        }
        if (terms.size() > MAX_TERMS) {
            throw new TooManyTermsException(terms.size());
        }
        return terms;
    }
}
