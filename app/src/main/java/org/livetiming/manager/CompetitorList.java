package org.livetiming.manager;

import org.livetiming.model.Category;
import org.livetiming.model.CompetitorStatus;
import org.livetiming.model.Gender;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CompetitorList {
    private final Map<Category, Set<Competitor>> competitorsByCategory;

    public CompetitorList() {
        this.competitorsByCategory = new HashMap<>();
    }

    public CompetitorList(Set<Competitor> competitors) {
        this.competitorsByCategory = new HashMap<>();
        for (Competitor competitor : competitors) {
            this.addCompetitor(competitor);
        }
    }

    public void addCompetitor(Competitor competitor) {
        Category category = competitor.getCategory();
        this.competitorsByCategory
                .computeIfAbsent(category, k -> new HashSet<>())
                .add(competitor);
    }

    public void removeCompetitor(Competitor competitor) {
        Category category = competitor.getCategory();
        Set<Competitor> competitors = this.competitorsByCategory.get(category);
        if (competitors != null) {
            competitors.remove(competitor);
            if (competitors.isEmpty()) {
                this.competitorsByCategory.remove(category);
            }
        }
    }

    public Set<Competitor> getCompetitors() {
        return this.competitorsByCategory.values().stream()
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    public Set<Competitor> getCompetitorsByCategory(Category category) {
        return this.competitorsByCategory.getOrDefault(category, new HashSet<>());
    }

    public Set<Competitor> getCompetitorsByStatus(CompetitorStatus status) {
        return this.competitorsByCategory.values().stream()
                .flatMap(Set::stream)
                .filter(competitor -> competitor.getStatus() == status)
                .collect(Collectors.toSet());
    }

    public Set<Competitor> getCompetitorsByGender(Gender gender) {
        return this.competitorsByCategory.values().stream()
                .flatMap(Set::stream)
                .filter(competitor -> competitor.getGender() == gender)
                .collect(Collectors.toSet());
    }
}