package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilterTest {

    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final Duration MAX_GROUND_TIME = Duration.ofHours(2);
    private FlightFilter filter;

    @BeforeEach
    void setUp() {
        filter = new Filter(); // Инициализация вашего класса реализации интерфейса FlightFilter
    }

    @Test
    void testFiltr() {
        LocalDateTime now = LocalDateTime.now();
        List<Flight> flights = Arrays.asList(
                createFlight(now.plusHours(1), now.plusHours(2)),  // Полет с двумя сегментами, дата отправления после текущего момента времени
                createFlight(now.minusHours(1), now.minusMinutes(30), now.minusMinutes(30), now)  // Полет с двумя сегментами, дата отправления до текущего момента времени
        );

        List<Flight> filteredFlights = filter.testFiltr(flights);

        assertEquals(1, filteredFlights.size(), "Ожидается, что будет отфильтрован 1 полет");
    }

    // Вспомогательный метод для создания полета с сегментами
    private Flight createFlight(LocalDateTime... dates) {
        if ((dates.length % 2) != 0) {
            throw new IllegalArgumentException("you must pass an even number of dates");
        }
        List<Segment> segments = new ArrayList<>();
        for (int i = 0; i < dates.length - 1; i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }
        return new Flight(segments);
    }

    @Test
    void testFiltrTwo() {
        // Создаем тестовые данные
        LocalDateTime now = LocalDateTime.now();
        List<Flight> flights = Arrays.asList(
                createFlighTwo(now, now.plusHours(2)),   // Полет с корректным порядком времени
                createFlighTwo(now.plusHours(2), now)    // Полет с некорректным порядком времени
        );

        // Вызываем тестируемый метод
        List<Flight> filteredFlights = filter.testFiltrTwo(flights);

        // Проверяем результат
        assertEquals(1, filteredFlights.size(), "Ожидается, что будет отфильтрован 1 полет");


    }

    private Flight createFlighTwo(LocalDateTime... dates) {
        if ((dates.length % 2) != 0) {
            throw new IllegalArgumentException("you must pass an even number of dates");
        }
        List<Segment> segments = new ArrayList<>();
        for (int i = 0; i < dates.length - 1; i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }
        return new Flight(segments);
    }


    @Test
    void testFiltrThree() {
        LocalDateTime now = LocalDateTime.now();
        List<Flight> flights = Arrays.asList(
                createFlight(now, now.plusHours(1), now.plusHours(2), now.plusHours(3)),  // Полет с корректным временем ожидания между сегментами
                createFlight(now, now.plusHours(1), now.plusHours(4), now.plusHours(5)),  // Полет с некорректным временем ожидания между сегментами
                createFlight(now, now.plusHours(1), now.plusHours(6), now.plusHours(7))   // Еще один полет с некорректным временем ожидания между сегментами
        );

        List<Flight> filteredFlights = filter.testFiltrThree(flights);

        assertEquals(1, filteredFlights.size(), "Ожидается, что будет отфильтрован 1 полет");
    }


    // Вспомогательный метод для создания полета с сегментами
    private Flight createFlightThee(LocalDateTime... dates) {
        if ((dates.length % 2) != 0) {
            throw new IllegalArgumentException("you must pass an even number of dates");
        }
        List<Segment> segments = new ArrayList<>();
        for (int i = 0; i < dates.length - 1; i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }
        return new Flight(segments);
    }
}