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

    //====================================================================================================
    // Public methods
    //====================================================================================================
    /**
     * Adds a competitor to the list
     *
     * @param competitor the competitor to add
     */
    public void addCompetitor(Competitor competitor) {
        Map<Category, Set<Competitor>> targetMap = getGenderMap(competitor.getGender());
        Category category = competitor.getCategory();
        targetMap.computeIfAbsent(category, k -> new HashSet<>()).add(competitor);
    }

    /**
     * Removes a competitor from the list
     *
     * @param competitor the competitor to remove
     */
    public void removeCompetitor(Competitor competitor) {
        Map<Category, Set<Competitor>> targetMap = getGenderMap(competitor.getGender());
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
     * @return a Set of all competitors sorted as youngest female category, youngest male category, ...
     */
    public Set<Competitor> getAllCompetitors() {
        Set<Competitor> allCompetitors = new HashSet<>();
        allCompetitors.addAll(getCompetitors(maleCompetitorsByCategory));
        allCompetitors.addAll(getCompetitors(femaleCompetitorsByCategory));
        return allCompetitors;
    }

    /**
     * @param category the category to filter by
     * @return the Set of competitors of the specified category
     */
    public Set<Competitor> getCompetitorsByCategory(Category category) {
        Set<Competitor> competitors = new HashSet<>(maleCompetitorsByCategory.getOrDefault(category, new HashSet<>()));
        competitors.addAll(femaleCompetitorsByCategory.getOrDefault(category, new HashSet<>()));
        return competitors;
    }

    public Set<Competitor> getMaleCompetitorsByCategory(Category category) {
        return new HashSet<>(maleCompetitorsByCategory.getOrDefault(category, new HashSet<>()));
    }

    public Set<Competitor> getFemaleCompetitorsByCategory(Category category) {
        return new HashSet<>(femaleCompetitorsByCategory.getOrDefault(category, new HashSet<>()));
    }

    /**
     * @param status the status to filter by
     * @return the Set of competitors of the specified status
     */
    public Set<Competitor> getCompetitorsByStatus(CompetitorStatus status) {
        return getAllCompetitors().stream()
                .filter(competitor -> competitor.getStatus() == status)
                .collect(Collectors.toSet());
    }

    /**
     * @param gender the gender to filter by
     * @return the Set of competitors of the specified gender
     */
    public Set<Competitor> getCompetitorsByGender(Gender gender) {
        return new HashSet<>(getCompetitors(getGenderMap(gender)));
    }

    /**
     * @return the number of competitors
     */
    public int getCompetitorCount() {
        return getAllCompetitors().size();
    }

    /**
     * Resets the list of competitors by setting status to not started and finish time to 0
     */
    public void resetCompetitorsStatus() {
        getAllCompetitors().forEach(competitor -> {
            competitor.setStatus(CompetitorStatus.NOT_STARTED);
            competitor.setFinishTime(0);
        });
    }

    /**
     * @return the next competitor that has not started yet (status NOT_STARTED) in order of start number
     * If competitor doesn't have a start number, they start at the end of the category
     * Starting from the youngest female category, then youngest male category, ...
     */
    public Competitor getNextCompetitor() {
        List<Category> sortedCategories = new ArrayList<>(femaleCompetitorsByCategory.keySet());
        sortedCategories.addAll(maleCompetitorsByCategory.keySet());
        sortedCategories = sortedCategories.stream().distinct().sorted().collect(Collectors.toList());

        for (Category category : sortedCategories) {
            Competitor nextCompetitor = getNextCompetitorFromCategory(femaleCompetitorsByCategory, category);
            if (nextCompetitor != null) {
                return nextCompetitor;
            }
            nextCompetitor = getNextCompetitorFromCategory(maleCompetitorsByCategory, category);
            if (nextCompetitor != null) {
                return nextCompetitor;
            }
        }
        return null; // No competitor found with status NOT_STARTED
    }

    /**
     * Assigns start numbers to competitors by category, starting from females of the youngest category
     * then youngest male category, then second-youngest females,... until the oldest male category
     */
    public void assignStartNumbersByCategory() {
        List<Category> sortedCategories = new ArrayList<>(maleCompetitorsByCategory.keySet());
        sortedCategories.addAll(femaleCompetitorsByCategory.keySet());
        sortedCategories = sortedCategories.stream().distinct().sorted().collect(Collectors.toList());

        int startNumber = 1;

        for (Category category : sortedCategories) {
            if (femaleCompetitorsByCategory.containsKey(category)) {
                for (Competitor competitor : femaleCompetitorsByCategory.get(category)) {
                    competitor.setStartNumber(startNumber++);
                }
            }
            if (maleCompetitorsByCategory.containsKey(category)) {
                for (Competitor competitor : maleCompetitorsByCategory.get(category)) {
                    competitor.setStartNumber(startNumber++);
                }
            }
        }
    }

    //====================================================================================================
    // Private methods
    //====================================================================================================

    /**
     * @param gender the gender to filter by
     * @return the map of competitors by category
     */
    private Map<Category, Set<Competitor>> getGenderMap(Gender gender) {
        return gender == Gender.MALE ? maleCompetitorsByCategory : femaleCompetitorsByCategory;
    }

    /**
     * @param map the map of competitors by category
     * @return a Set of all competitors
     */
    private Set<Competitor> getCompetitors(Map<Category, Set<Competitor>> map) {
        return map.values().stream()
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    /**
     * @param competitorsByCategory the map of competitors by category
     * @param category              the category to filter by
     * @return the next competitor that has not started yet (status NOT_STARTED) in order of start number
     */
    private Competitor getNextCompetitorFromCategory(Map<Category, Set<Competitor>> competitorsByCategory, Category category) {
        if (competitorsByCategory.containsKey(category)) {
            List<Competitor> competitors = new ArrayList<>(competitorsByCategory.get(category));
            competitors.sort(Comparator.comparingInt(Competitor::getStartNumber));
            for (Competitor competitor : competitors) {
                if (competitor.getStatus() == CompetitorStatus.NOT_STARTED) {
                    return competitor;
                }
            }
        }
        return null;
    }
}