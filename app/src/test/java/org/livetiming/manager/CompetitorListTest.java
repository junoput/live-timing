package org.livetiming.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.livetiming.model.Category;
import org.livetiming.model.CompetitorStatus;
import org.livetiming.model.Gender;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompetitorListTest {

    private CompetitorList competitorList;
    private Competitor mockCompetitor;

    @BeforeEach
    void setUp() {
        competitorList = new CompetitorList();
        mockCompetitor = mock(Competitor.class);
        when(mockCompetitor.getGender()).thenReturn(Gender.MALE);
        when(mockCompetitor.getCategory()).thenReturn(Category.BABY);
    }

    @Test
    void testAddCompetitor() {
        competitorList.addCompetitor(mockCompetitor);
        assertTrue(competitorList.getCompetitors().contains(mockCompetitor));
    }

    @Test
    void testRemoveCompetitor() {
        competitorList.addCompetitor(mockCompetitor);
        competitorList.removeCompetitor(mockCompetitor);
        assertFalse(competitorList.getCompetitors().contains(mockCompetitor));
    }

    @Test
    void testGetCompetitors() {
        competitorList.addCompetitor(mockCompetitor);
        Set<Competitor> competitors = competitorList.getCompetitors();
        assertEquals(1, competitors.size());
        assertTrue(competitors.contains(mockCompetitor));
    }

    @Test
    void testGetCompetitorsByCategory() {
        competitorList.addCompetitor(mockCompetitor);
        Set<Competitor> competitors = competitorList.getCompetitorsByCategory(Category.BABY);
        assertEquals(1, competitors.size());
        assertTrue(competitors.contains(mockCompetitor));
    }

    @Test
    void testGetCompetitorsByStatus() {
        when(mockCompetitor.getStatus()).thenReturn(CompetitorStatus.NOT_STARTED);
        competitorList.addCompetitor(mockCompetitor);
        Set<Competitor> competitors = competitorList.getCompetitorsByStatus(CompetitorStatus.NOT_STARTED);
        assertEquals(1, competitors.size());
        assertTrue(competitors.contains(mockCompetitor));
    }

    @Test
    void testGetCompetitorsByGender() {
        competitorList.addCompetitor(mockCompetitor);
        Set<Competitor> competitors = competitorList.getCompetitorsByGender(Gender.MALE);
        assertEquals(1, competitors.size());
        assertTrue(competitors.contains(mockCompetitor));
    }

    @Test
    void testGetCompetitorCount() {
        competitorList.addCompetitor(mockCompetitor);
        assertEquals(1, competitorList.getCompetitorCount());
    }

    @Test
    void testResetCompetitorsStatus() {
        competitorList.addCompetitor(mockCompetitor);
        competitorList.resetCompetitorsStatus();
        verify(mockCompetitor).setStatus(CompetitorStatus.NOT_STARTED);
        verify(mockCompetitor).setFinishTime(0);
    }

    @Test
    void testSortByName() {
        Competitor mockCompetitor1 = mock(Competitor.class);
        Competitor mockCompetitor2 = mock(Competitor.class);
        when(mockCompetitor1.getName()).thenReturn("John");
        when(mockCompetitor2.getName()).thenReturn("Alice");

        competitorList.addCompetitor(mockCompetitor1);
        competitorList.addCompetitor(mockCompetitor2);
        competitorList.sortByName();

        assertEquals("Alice", competitorList.getCompetitors().iterator().next().getName());
    }

    @Test
    void testGetNextCompetitor() {
        when(mockCompetitor.getStatus()).thenReturn(CompetitorStatus.NOT_STARTED);
        competitorList.addCompetitor(mockCompetitor);
        assertEquals(mockCompetitor, competitorList.getNextCompetitor());
    }

    @Test
    void testAssignStartNumbersByCategory() {
        competitorList.addCompetitor(mockCompetitor);
        competitorList.assignStartNumbersByCategory();
        verify(mockCompetitor).setStartNumber(1);
    }
}