package org.livetiming.manager;

import org.livetiming.model.Category;
import org.livetiming.model.CompetitorStatus;
import org.livetiming.model.Gender;

import java.util.*;
import java.util.stream.Collectors;

public class CompetitorList {
    private final Map<Category, Set<Competitor>> maleCompetitorsByCategory;
    private final Map<Category, Set<Competitor>> femaleCompetitorsByCategory;

    public CompetitorList() {
        this.maleCompetitorsByCategory = new HashMap<>();
        this.femaleCompetitorsByCategory = new HashMap<>();
    }

    public CompetitorList(Set<Competitor> competitors) {
        this();
        for (Competitor competitor : competitors) {
            this.addCompetitor(competitor);
        }
    }

    public void addCompetitor(Competitor competitor) {
        Map<Category, Set<Competitor>> targetMap = getGendertMap(competitor.getGender());
        Category category = competitor.getCategory();
        targetMap.computeIfAbsent(category, k -> new HashSet<>()).add(competitor);
    }

    public void removeCompetitor(Competitor competitor) {
        Map<Category, Set<Competitor>> targetMap = getGendertMap(competitor.getGender());
        Category category = competitor.getCategory();
        Set<Competitor> competitors = targetMap.get(category);
        if (competitors != null) {
            competitors.remove(competitor);
            if (competitors.isEmpty()) {
                targetMap.remove(category);
            }
        }
    }

    /**
     * @return a Set of all competitors
     */
    public Set<Competitor> getCompetitors() {
        Set<Competitor> competitors = getAllCompetitors(femaleCompetitorsByCategory);
        competitors.addAll(getAllCompetitors(maleCompetitorsByCategory));
        return competitors;
    }


    public Set<Competitor> getCompetitorsByCategory(Category category) {
        Set<Competitor> competitors = new HashSet<>(maleCompetitorsByCategory.getOrDefault(category, new HashSet<>()));
        competitors.addAll(femaleCompetitorsByCategory.getOrDefault(category, new HashSet<>()));
        return competitors;
    }

    public Set<Competitor> getCompetitorsByStatus(CompetitorStatus status) {
        return getAllCompetitors(maleCompetitorsByCategory).stream()
                .filter(competitor -> competitor.getStatus() == status)
                .collect(Collectors.toSet());
    }

    public Set<Competitor> getCompetitorsByGender(Gender gender) {
        return new HashSet<>(getAllCompetitors(getGendertMap(gender)));
    }

    private Map<Category, Set<Competitor>> getGendertMap(Gender gender) {
        return gender == Gender.MALE ? maleCompetitorsByCategory : femaleCompetitorsByCategory;
    }

    private Set<Competitor> getAllCompetitors(Map<Category, Set<Competitor>> map) {
        return map.values().stream()
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }
}