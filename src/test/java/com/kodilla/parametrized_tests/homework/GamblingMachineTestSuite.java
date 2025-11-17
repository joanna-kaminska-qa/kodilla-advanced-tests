package com.kodilla.parametrized_tests.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GamblingMachineTestSuite {

    GamblingMachine gamblingMachine;

    @BeforeEach
    public void setUp(){
        gamblingMachine = new GamblingMachine();
    }

    // pomocnicza metoda do zamiany tekstu z pliku CSV na Set liczb
    private Set<Integer> parseNumbers(String numbersLine) {
        Set<Integer> numbers = new HashSet<>();
        String[] splited = numbersLine.split(" ");
        for (String s : splited) {
            numbers.add(Integer.parseInt(s));
        }
        return numbers;
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/GamblingMachineInvalidSets.csv", numLinesToSkip = 1)
    public void shouldThrowInvalidNumbersException(String numbersLine) {
        Set<Integer> userNumbers = parseNumbers(numbersLine);
        assertThrows(InvalidNumbersException.class, () -> gamblingMachine.howManyWins(userNumbers));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/GamblingMachineValidSets.csv", numLinesToSkip = 1)
    public void shouldNotThrowExceptionForValidNumbers(String numbersLine) throws InvalidNumbersException {
        Set<Integer> userNumbers = parseNumbers(numbersLine);
        assertDoesNotThrow(() -> gamblingMachine.howManyWins(userNumbers));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/GamblingMachineValidSets.csv", numLinesToSkip = 1)
    void shouldWorkForValid(String numbersLine) throws InvalidNumbersException {
        String numbersPart = numbersLine.split(",")[0];
        Set<Integer> input = parseNumbers(numbersPart);
        int hits = gamblingMachine.howManyWins(input);
        assertTrue(hits >= 0 && hits <= 6);
    }
}