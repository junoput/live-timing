package org.livetiming.manager;

import org.livetiming.model.Athlete;
import org.livetiming.model.CompetitorStatus;

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

    public void start() {
        // Set the status of the competitor to ON_COURSE
        this.setStatus(CompetitorStatus.ON_COURSE);
    }
    public void finish(long finishTime) {
        // Set the status of the competitor to FINISHED
        this.setStatus(CompetitorStatus.FINISHED);
        this.finishTime = finishTime;
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