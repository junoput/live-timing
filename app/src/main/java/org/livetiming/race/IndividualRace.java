package org.livetiming.race;

import org.livetiming.manager.Competitor;
import org.livetiming.manager.CompetitorList;
import org.livetiming.model.CompetitorStatus;

import java.util.List;
import java.util.Set;

public class IndividualRace {
    private final String name;
    private final CompetitorList competitors;

    public IndividualRace(Set<Competitor> competitors) {
        this.name = "";
        this.competitors = new CompetitorList(competitors);

    }

    public IndividualRace(String name, Set<Competitor> competitors) {
        this.name = name;
        this.competitors = new CompetitorList(competitors);
        this.competitors.resetCompetitorsStatus();
        this.competitors.assignStartNumbersByCategory();
    }

    public void startNext() {
        competitors.getNextCompetitor().start();
    }

    public void finishNext(long finishTime) {
        competitors.getCompetitorsByStatus(CompetitorStatus.ON_COURSE).stream().findFirst().ifPresent(competitor -> competitor.finish(finishTime));
    }

    public void didNotFinishNext() {
        competitors.getCompetitorsByStatus(CompetitorStatus.ON_COURSE).stream().findFirst().ifPresent(competitor -> competitor.setStatus(CompetitorStatus.DID_NOT_FINISH));
    }
}
