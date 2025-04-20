package org.livetiming.manager;

import org.livetiming.model.Athlete;
import org.livetiming.model.CompetitorStatus;

/**
 * Represents a competitor in a race.
 * Extends the Athlete class and adds race-specific attributes such as start number and finish time.
 */
public class Competitor extends Athlete {
    private int startNumber;
    private long startTime;
    private long finishTime;

    /**
     * Constructs a Competitor from an Athlete and assigns a start number.
     * The finish time is initialized to 0.
     *
     * @param athlete     the athlete to base the competitor on
     * @param startNumber the start number assigned to the competitor
     */
    public Competitor(Athlete athlete, int startNumber) {
        super(athlete.getFirstName(), athlete.getLastName(), athlete.getGender(), athlete.getClub(), athlete.getBirthYear(), athlete.getStatus());
        this.startNumber = startNumber;
        this.startTime = 0;
        this.finishTime = 0;
    }

    /**
     * Creates a Competitor from an Athlete and assigns a start number.
     *
     * @param athlete     the athlete to base the competitor on
     * @param startNumber the start number assigned to the competitor
     * @return a new Competitor instance
     */
    public static Competitor fromAthlete(Athlete athlete, int startNumber) {
        return new Competitor(athlete, startNumber);
    }

    /**
     * Starts the competitor in the race, recording the start time.
     * Sets the competitor's status to ON_COURSE.
     */
    public void start() {
        this.startTime = System.currentTimeMillis();
        this.setStatus(CompetitorStatus.ON_COURSE);
    }

    /**
     * Starts the competitor in the race with a specified start time.
     * Meant to start multiple competitors at once
     * Sets the competitor's status to ON_COURSE.
     *
     * @param startTime the time at which the competitor started
     */
    public void start(long startTime) {
        this.startTime = startTime;
        this.setStatus(CompetitorStatus.ON_COURSE);
    }

    /**
     * Marks the competitor as finished and records the finish time.
     * Sets the competitor's status to FINISHED.
     *
     * @param finishTime the time at which the competitor finished
     */
    public void finish(long finishTime) {
        this.setStatus(CompetitorStatus.FINISHED);
        this.finishTime = finishTime;
    }

    /**
     * Retrieves the start number of the competitor.
     *
     * @return the start number
     */
    public int getStartNumber() {
        return startNumber;
    }

    /**
     * Sets the start number of the competitor.
     *
     * @param startNumber the start number to assign
     */
    public void setStartNumber(int startNumber) {
        this.startNumber = startNumber;
    }

    /**
     * Retrieves the start time of the competitor.
     *
     * @return the start time
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Retrieves the finish time of the competitor.
     *
     * @return the finish time
     */
    public long getFinishTime() {
        return finishTime;
    }

    /**
     * Sets the finish time of the competitor.
     *
     * @param finishTime the finish time to assign
     */
    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }
}
