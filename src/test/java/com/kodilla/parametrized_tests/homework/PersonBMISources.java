package com.kodilla.parametrized_tests.homework;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class PersonBMISources {

    static Stream<Arguments> providePersonForTestingBMICalculator() {
        return Stream.of(
                Arguments.of(new Person(1.70, 40), "Very severely underweight"),     // BMI ≈ 13.84
                Arguments.of(new Person(1.65, 45), "Underweight"),                   // BMI ≈ 16.53
                Arguments.of(new Person(1.80, 70), "Normal (healthy weight)"),       // BMI ≈ 21.6
                Arguments.of(new Person(1.60, 85), "Obese Class I (Moderately obese)"), // BMI ≈ 33.2
                Arguments.of(new Person(1.75, 160), "Obese Class V (Super Obese)"),   // BMI ≈ 52.2
                Arguments.of(new Person(0.5, 0.25), "Very severely underweight"), // wcześniak (waga 250g, wzrost 0.5m)

                // przypadki graniczne:
                Arguments.of(new Person(1.70, 43.425), "Severely underweight"),      // BMI = 15.0 dokładnie
                Arguments.of(new Person(1.70, 45.92), "Severely underweight"),       // BMI = 16.0
                Arguments.of(new Person(1.70, 53.4225), "Underweight"),              // BMI = 18.5
                Arguments.of(new Person(1.70, 129.75), "Obese Class III (Very severely obese)"), // BMI = 45.0
                Arguments.of(new Person(1.70, 143.3), "Obese Class IV (Morbidly Obese)"),     // BMI = 50.0
                Arguments.of(new Person(1.70, 171.9), "Obese Class V (Super Obese)"),          // BMI = 60.0

                // 0 i wartości ujemne
                Arguments.of(new Person(0, 70), "Invalid height"),       // wysokość zero
                Arguments.of(new Person(-1.7, 70), "Invalid height"),    // wysokość ujemna
                Arguments.of(new Person(1.7, 0), "Invalid weight"),      // waga zero
                Arguments.of(new Person(1.7, -5), "Invalid weight")      // waga ujemna
        );
    }
}
