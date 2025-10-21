package org.livetiming.manager;

import org.livetiming.model.Athlete;

public class Competitor extends Athlete {
    private int startNumber;
    private long finishTime;

    public Competitor(Athlete athlete, int startNumber) {
        super(athlete.getFirstName(), athlete.getLastName(), athlete.getGender(), athlete.getClub(), athlete.getBirthYear(), athlete.getStatus());
        this.startNumber = startNumber;
        this.finishTime = 0;
    }

    public static Competitor fromAthlete(Athlete athlete, int startNumber) {
        return new Competitor(athlete, startNumber);
    }

    // Additional methods specific to Competitor
    public int getStartNumber() {
        return startNumber;
    }

    public void setStartNumber(int startNumber) {
        this.startNumber = startNumber;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }
}