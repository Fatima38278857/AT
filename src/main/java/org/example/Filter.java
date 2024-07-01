package org.example;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Filter implements FlightFilter {


    private final Duration MAX_GROUND_TIME = Duration.ofHours(2);

    @Override
    public List<Flight> testFiltr(List<Flight> flights) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .noneMatch(segment -> segment.getDepartureDate().isBefore(localDateTime)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> testFiltrTwo(List<Flight> flights) {
        return flights.stream().filter(flight -> flight.getSegments()
                .stream().noneMatch(segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate()))).collect(Collectors.toList());
    }

    @Override
    public List<Flight> testFiltrThree(List<Flight> flights) {
        return flights.stream()
                .filter(this::groundTimeCheck)
                .collect(Collectors.toList());
    }

    private boolean groundTimeCheck(Flight flight) {
        List<Segment> segments = flight.getSegments();
        for (int i = 0; i < segments.size() - 1; i++) {
            Segment current = segments.get(i);
            Segment next = segments.get(i + 1);
            Duration groundTime = Duration.between(current.getArrivalDate(), next.getDepartureDate());
            if (groundTime.compareTo(MAX_GROUND_TIME) > 0) {
                return false;
            }
        }
        return true;
    }

}

