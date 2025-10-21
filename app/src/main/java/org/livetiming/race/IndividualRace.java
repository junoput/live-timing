package org.livetiming.race;

import org.livetiming.exception.NoCompetitorsOnCourseException;
import org.livetiming.manager.Competitor;
import org.livetiming.manager.CompetitorList;
import org.livetiming.model.CompetitorStatus;

import java.util.Set;

/**
 * Represents an individual race with a list of competitors.
 * Handles race operations such as starting, finishing, and marking competitors as "did not finish".
 */
public class IndividualRace {
    private final String name;
    private final CompetitorList competitors;

    /**
     * Constructs an IndividualRace with a specified name and a set of competitors.
     * Resets the competitors' statuses and assigns start numbers by category.
     *
     * @param name        the name of the race
     * @param competitors the set of competitors participating in the race
     */
    public IndividualRace(String name, Set<Competitor> competitors) {
        this.name = name;
        this.competitors = new CompetitorList(competitors);
        this.competitors.resetCompetitorsStatus();
        this.competitors.assignStartNumbersByCategory();
    }

    /**
     * Constructs an IndividualRace with a specified set of competitors.
     *
     * @param name        the name of the race
     * @param competitors the CompetitorList
     */
    public IndividualRace(String name, CompetitorList competitors) {
        this.name = name;
        this.competitors = competitors;
    }

    /**
     * Starts the next competitor in the race who has not yet started.
     */
    public void startNext() {
        competitors.getNextCompetitor().start();
    }

/**
 * Marks the next competitor on course as finished with the specified finish time.
 *
 * @param finishTime the finish time of the competitor
 * @throws NoCompetitorsOnCourseException if no competitors are currently on course
 */
public void finishNext(long finishTime) {
    Set<Competitor> competitorsOnCourse = competitors.getCompetitorsOnCourse();

    if (competitorsOnCourse.isEmpty()) {
        throw new NoCompetitorsOnCourseException("No competitors are currently on course.");
    }

    Competitor nextCompetitor = competitorsOnCourse.iterator().next();
    nextCompetitor.finish(finishTime);
}

    /**
     * Marks the next competitor on course as "did not finish".
     */
    public void didNotFinishNext() {
        Set<Competitor> competitorsOnCourse = competitors.getCompetitorsOnCourse();

        if (competitorsOnCourse.isEmpty()) {
            throw new NoCompetitorsOnCourseException("No competitors are currently on course.");
        }

        Competitor nextCompetitor = competitorsOnCourse.iterator().next();
        nextCompetitor.setStatus(CompetitorStatus.DID_NOT_FINISH);
    }
}
