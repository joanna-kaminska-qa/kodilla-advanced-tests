package com.kodilla.execution_model.homework;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Shop {

    private List<Order> listOfOrders = new ArrayList<>();

    public void addNewOrder(double orderValue, LocalDate date, String login) {
        listOfOrders.add(new Order(orderValue, date, login));
    }

    public List<Order> showOrdersFromDateRange(LocalDate startDate, LocalDate endDate) {
        return listOfOrders.stream()
                .filter(order -> (order.getDate().isEqual(startDate) || order.getDate().isAfter(startDate)) &&
                        (order.getDate().isEqual(endDate) || order.getDate().isBefore(endDate)))
                .collect(Collectors.toList());
    }

    public int showNumberOfOrders() {
        return listOfOrders.size();
    }

    public double showTotalValueOfAllOrders() {
        return listOfOrders.stream()
                .mapToDouble(Order::getOrderValue)
                .sum();
    }

    public List<Order> showOrdersWithValueWithinRange(double min, double max) {
        return listOfOrders.stream()
                .filter(order -> order.getOrderValue() >= min && order.getOrderValue() <= max)
                .collect(Collectors.toList());
    }

    public List<Order> getListOfOrders() {
        return listOfOrders;
    }
}
