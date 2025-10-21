package org.livetiming.model;

import java.time.Year;

public class Athlete {
    private final String firstName;
    private final String lastName;
    private final Gender gender;
    private final String club;
    private final Year birthYear;
    private Category category;
    private CompetitorStatus status;

    public Athlete(String firstName, String lastName, Gender gender, String club, Year birthYear, CompetitorStatus status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.club = club;
        this.birthYear = birthYear;
        this.status = status;

        if (birthYear != null) {
            this.category = Category.getCategoryByBirthYear(birthYear);
        }
    }

    //====================================================================================================
    // Getters
    //====================================================================================================
    /**
     * @return the name of the competitor
     */
    public String getName() {
        return this.firstName + " " + this.lastName;
    }

    /**
     * @return the first name of the competitor
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * @return the last name of the competitor
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * @return the gender of the competitor
     */
    public Gender getGender () {
        return this.gender;
    }

    /**
     * @return the club of the competitor
     */
    public String getClub() {
        return this.club;
    }

    /**
     * @return the birth year of the competitor
     */
    public Year getBirthYear() {
        return this.birthYear;
    }

    /**
     * @return the category of the competitor
     */
    public Category getCategory() {
        return this.category;
    }

    /**
     * @return the status of the competitor
     */
    public CompetitorStatus getStatus() {
        return this.status;
    }

    //====================================================================================================
    // Setters
    //====================================================================================================
    /**
     * @param category the category to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(CompetitorStatus status) {
        this.status = status;
    }

}