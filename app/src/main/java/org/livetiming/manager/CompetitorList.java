package org.livetiming.manager;

import org.livetiming.model.Category;
import org.livetiming.model.CompetitorStatus;
import org.livetiming.model.Gender;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Manages a list of competitors, grouped by category and gender.
 * Provides methods for adding, removing, and retrieving competitors based on various criteria.
 */
public class CompetitorList {
    private final Map<Category, Set<Competitor>> maleCompetitorsByCategory;
    private final Map<Category, Set<Competitor>> femaleCompetitorsByCategory;

    /**
     * Constructs an empty CompetitorList.
     */
    public CompetitorList() {
        this.maleCompetitorsByCategory = new HashMap<>();
        this.femaleCompetitorsByCategory = new HashMap<>();
    }

    /**
     * Constructs a CompetitorList and initializes it with a set of competitors.
     *
     * @param competitors the set of competitors to initialize the list with
     */
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
     * Adds a competitor to the list.
     * The competitor is added to the appropriate gender and category group.
     *
     * @param competitor the competitor to add
     */
    public void addCompetitor(Competitor competitor) {
        Map<Category, Set<Competitor>> targetMap = getGenderMap(competitor.getGender());
        Category category = competitor.getCategory();
        targetMap.computeIfAbsent(category, k -> new HashSet<>()).add(competitor);
    }

    /**
     * Removes a competitor from the list.
     * If the category becomes empty after removal, it is removed from the map.
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
     * Retrieves all competitors in the list.
     * Competitors are sorted by category and gender, starting with the youngest female category.
     *
     * @return a Set of all competitors
     */
    public Set<Competitor> getAllCompetitors() {
        Set<Competitor> allCompetitors = new HashSet<>();
        allCompetitors.addAll(getCompetitors(maleCompetitorsByCategory));
        allCompetitors.addAll(getCompetitors(femaleCompetitorsByCategory));
        return allCompetitors;
    }

    /**
     * Retrieves competitors of a specific category.
     *
     * @param category the category to filter by
     * @return the Set of competitors in the specified category
     */
    public Set<Competitor> getCompetitorsByCategory(Category category) {
        Set<Competitor> competitors = new HashSet<>(maleCompetitorsByCategory.getOrDefault(category, new HashSet<>()));
        competitors.addAll(femaleCompetitorsByCategory.getOrDefault(category, new HashSet<>()));
        return competitors;
    }

    /**
     * Retrieves male competitors of a specific category.
     *
     * @param category the category to filter by
     * @return the Set of male competitors in the specified category
     */
    public Set<Competitor> getMaleCompetitorsByCategory(Category category) {
        return new HashSet<>(maleCompetitorsByCategory.getOrDefault(category, new HashSet<>()));
    }

    /**
     * Retrieves female competitors of a specific category.
     *
     * @param category the category to filter by
     * @return the Set of female competitors in the specified category
     */
    public Set<Competitor> getFemaleCompetitorsByCategory(Category category) {
        return new HashSet<>(femaleCompetitorsByCategory.getOrDefault(category, new HashSet<>()));
    }

    /**
     * Retrieves competitors with a specific status.
     *
     * @param status the status to filter by
     * @return the Set of competitors with the specified status
     */
    public Set<Competitor> getCompetitorsByStatus(CompetitorStatus status) {
        return getAllCompetitors().stream()
                .filter(competitor -> competitor.getStatus() == status)
                .collect(Collectors.toSet());
    }

    /**
     * Retrieves the competitors currently on course.
     *
     * @return a Set of competitors with the status ON_COURSE
     */
    public Set<Competitor> getCompetitorsOnCourse() {
        return getAllCompetitors().stream()
            .filter(competitor -> competitor.getStatus() == CompetitorStatus.ON_COURSE)
            .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparingLong(Competitor::getStartTime))));
    }

    /**
     * Retrieves competitors of a specific gender.
     *
     * @param gender the gender to filter by
     * @return the Set of competitors with the specified gender
     */
    public Set<Competitor> getCompetitorsByGender(Gender gender) {
        return new HashSet<>(getCompetitors(getGenderMap(gender)));
    }

    /**
     * Retrieves the total number of competitors in the list.
     *
     * @return the number of competitors
     */
    public int getCompetitorCount() {
        return getAllCompetitors().size();
    }

    /**
     * Resets the status and finish time of all competitors in the list.
     * All competitors are marked as NOT_STARTED, and their finish times are set to 0.
     */
    public void resetCompetitorsStatus() {
        getAllCompetitors().forEach(competitor -> {
            competitor.setStatus(CompetitorStatus.NOT_STARTED);
            competitor.setFinishTime(0);
        });
    }

    /**
     * Retrieves the next competitor who has not started yet.
     * Competitors are selected in order of start number, starting with the youngest female category.
     *
     * @return the next competitor with status NOT_STARTED, or null if none are found
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
     * Assigns start numbers to competitors by category.
     * Start numbers are assigned sequentially, starting with the youngest female category.
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
     * Retrieves the map of competitors grouped by category for a specific gender.
     *
     * @param gender the gender to filter by
     * @return the map of competitors grouped by category
     */
    private Map<Category, Set<Competitor>> getGenderMap(Gender gender) {
        return gender == Gender.MALE ? maleCompetitorsByCategory : femaleCompetitorsByCategory;
    }

    /**
     * Retrieves all competitors from a map of competitors grouped by category.
     *
     * @param map the map of competitors grouped by category
     * @return a Set of all competitors
     */
    private Set<Competitor> getCompetitors(Map<Category, Set<Competitor>> map) {
        return map.values().stream()
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    /**
     * Retrieves the next competitor with status NOT_STARTED from a specific category.
     * Competitors are sorted by start number, with those without a start number placed at the end.
     *
     * @param competitorsByCategory the map of competitors grouped by category
     * @param category              the category to filter by
     * @return the next competitor with status NOT_STARTED, or null if none are found
     */
    private Competitor getNextCompetitorFromCategory(Map<Category, Set<Competitor>> competitorsByCategory, Category category) {
        if (competitorsByCategory.containsKey(category)) {
            List<Competitor> competitors = new ArrayList<>(competitorsByCategory.get(category));
            competitors.sort((c1, c2) -> {
                int startNumber1 = c1.getStartNumber() == 0 ? Integer.MAX_VALUE : c1.getStartNumber();
                int startNumber2 = c2.getStartNumber() == 0 ? Integer.MAX_VALUE : c2.getStartNumber();
                return Integer.compare(startNumber1, startNumber2);
            });
            for (Competitor competitor : competitors) {
                if (competitor.getStatus() == CompetitorStatus.NOT_STARTED) {
                    return competitor;
                }
            }
        }
        return null;
    }
}
