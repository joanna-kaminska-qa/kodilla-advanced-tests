package com.kodilla.execution_model.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ShopTestSuite {

    Shop shop;
    List<Order> orders;

    @BeforeEach
    void setUp() {
        shop = new Shop();
        shop.addNewOrder(25.99, LocalDate.of(2025, 1, 12), "wKowalski91");
        shop.addNewOrder(100.85, LocalDate.of(2025, 7, 16), "klaudia2000");
        shop.addNewOrder(70.99, LocalDate.of(2025, 3, 1), "ml123456");
        shop.addNewOrder(200.00, LocalDate.of(2025, 3, 7), "monikaaaaa4");
        shop.addNewOrder(49.99, LocalDate.of(2025, 6, 22), "pawelmalinowski86");
        orders = shop.getListOfOrders();
    }

    static Stream<Arguments> provideDateRanges() {
        return Stream.of(
                Arguments.of(LocalDate.of(2025, 1, 1), LocalDate.of(2025, 1, 31), 1),
                Arguments.of(LocalDate.of(2025, 3, 1), LocalDate.of(2025, 3, 7), 2),
                Arguments.of(LocalDate.of(2025, 6, 1), LocalDate.of(2025, 6, 30), 1)
        );
    }

    @Test
    void shouldShowShopSize() {
        assertEquals(5, shop.showNumberOfOrders());
    }

    @ParameterizedTest
    @MethodSource("provideDateRanges")
    void shouldVerifySizeOfListOfOrdersFromDateRange(LocalDate startDate, LocalDate endDate, int expectedSize) {
        List<Order> listOfOrdersFromDateRange = shop.showOrdersFromDateRange(startDate, endDate);
        assertEquals(expectedSize, listOfOrdersFromDateRange.size());
    }

    @Test
    void shouldShowTotalValueOfAllOrders() {
        double ordersValue = orders.stream()
                .mapToDouble(Order::getOrderValue)
                .sum();
        assertEquals(ordersValue, shop.showTotalValueOfAllOrders());
    }

    @ParameterizedTest
    @CsvSource(value = {"0.00,100.00,3", "100.01,199.99,1", "25.00,49.99,2"})
    void shouldShowSizeOfOrdersWithValueWithinRange(double min, double max, int expectedSize) {
        List<Order> result = shop.showOrdersWithValueWithinRange(min, max);
        assertEquals(expectedSize, result.size());
    }

    @Test
    void shouldReturnEmptyListWhenNoOrdersInRange() {
        List<Order> result = shop.showOrdersFromDateRange(LocalDate.of(1999, 1, 1), LocalDate.of(1999, 1, 31));
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldContainCorrectOrdersWithinRange() {
        List<Order> result = shop.showOrdersWithValueWithinRange(25.00, 49.99);
        List<String> logins = result.stream()
                .map(Order::getLogin)
                .collect(Collectors.toList());
        assertTrue(logins.contains("wKowalski91"));
        assertTrue(logins.contains("pawelmalinowski86"));
    }

    @Test
    void shouldThrowExceptionWhenNullDatePassed() {
        assertThrows(NullPointerException.class, () -> {
            shop.showOrdersFromDateRange(null, LocalDate.now());
        });
    }
}