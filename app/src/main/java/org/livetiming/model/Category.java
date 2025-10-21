package org.livetiming.model;

import java.time.Year;

public enum Category {
    BABY, MINI, U10, U12, U14, U16, U18, U20, SENIOR, VETERAN, MASTER, GRAND_MASTER, UNKNOWN;

    public static Category getCategoryByBirthYear(Year birthYear) {
        int year = birthYear.getValue();
        int age = Year.now().getValue() - year;

        if (age <= 2) {
            return BABY;
        } else if (age <= 5) {
            return MINI;
        } else if (age <= 10) {
            return U10;
        } else if (age <= 12) {
            return U12;
        } else if (age <= 14) {
            return U14;
        } else if (age <= 16) {
            return U16;
        } else if (age <= 18) {
            return U18;
        } else if (age <= 20) {
            return U20;
        } else if (age <= 35) {
            return SENIOR;
        } else if (age <= 50) {
            return VETERAN;
        } else if (age <= 65) {
            return MASTER;
        } else if (age > 65) {
            return GRAND_MASTER;
        } else {
            return UNKNOWN;
        }
    }
}