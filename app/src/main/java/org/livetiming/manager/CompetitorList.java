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
        List<Category> sortedCategories = new ArrayList<>(maleCompetitorsByCategory.keySet());
        sortedCategories.addAll(femaleCompetitorsByCategory.keySet());
        sortedCategories = sortedCategories.stream().distinct().sorted().collect(Collectors.toList());

        Set<Competitor> sortedCompetitors = new LinkedHashSet<>();

        for (Category category : sortedCategories) {
            if (femaleCompetitorsByCategory.containsKey(category)) {
                sortedCompetitors.addAll(femaleCompetitorsByCategory.get(category));
            }
            if (maleCompetitorsByCategory.containsKey(category)) {
                sortedCompetitors.addAll(maleCompetitorsByCategory.get(category));
            }
        }

        return sortedCompetitors;
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
     * Sort all categories by name
     */
    public void sortByName() {
        Comparator<Competitor> nameComparator = Comparator.comparing(Competitor::getName);

        maleCompetitorsByCategory.values().forEach(set -> {
            List<Competitor> sortedList = new ArrayList<>(set);
            sortedList.sort(nameComparator);
            set.clear();
            set.addAll(sortedList);
        });

        femaleCompetitorsByCategory.values().forEach(set -> {
            List<Competitor> sortedList = new ArrayList<>(set);
            sortedList.sort(nameComparator);
            set.clear();
            set.addAll(sortedList);
        });
    }

    public Competitor getNextCompetitor() {
        for (Competitor competitor : getAllCompetitors()) {
            if (competitor.getStatus() == CompetitorStatus.NOT_STARTED) {
                return competitor;
            }
        }
        return null;
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
}