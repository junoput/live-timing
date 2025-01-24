package org.livetiming.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.livetiming.model.Category;
import org.livetiming.model.CompetitorStatus;
import org.livetiming.model.Gender;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompetitorListTest {

    private CompetitorList competitorList;
    private Competitor mockCompetitor1;
    private Competitor mockCompetitor2;
    private Competitor mockCompetitor3;
    private Competitor mockCompetitor4;
    private Competitor mockCompetitor5;
    private Competitor mockCompetitor6;
    private Competitor mockCompetitor7;
    private Competitor mockCompetitor8;
    private Competitor mockCompetitor9;
    private Competitor mockCompetitor10;

    @BeforeEach
    void setUp() {
        competitorList = new CompetitorList();
        mockCompetitor1 = mock(Competitor.class);
        mockCompetitor2 = mock(Competitor.class);
        mockCompetitor3 = mock(Competitor.class);
        mockCompetitor4 = mock(Competitor.class);
        mockCompetitor5 = mock(Competitor.class);
        mockCompetitor6 = mock(Competitor.class);
        mockCompetitor7 = mock(Competitor.class);
        mockCompetitor8 = mock(Competitor.class);
        mockCompetitor9 = mock(Competitor.class);
        mockCompetitor10 = mock(Competitor.class);

        when(mockCompetitor1.getGender()).thenReturn(Gender.MALE);
        when(mockCompetitor1.getCategory()).thenReturn(Category.BABY);
        when(mockCompetitor1.getStatus()).thenReturn(CompetitorStatus.NOT_STARTED);
        when(mockCompetitor1.getName()).thenReturn("John");

        when(mockCompetitor2.getGender()).thenReturn(Gender.FEMALE);
        when(mockCompetitor2.getCategory()).thenReturn(Category.U14);
        when(mockCompetitor2.getStatus()).thenReturn(CompetitorStatus.ON_COURSE);
        when(mockCompetitor2.getName()).thenReturn("Alice");

        when(mockCompetitor3.getGender()).thenReturn(Gender.MALE);
        when(mockCompetitor3.getCategory()).thenReturn(Category.SENIOR);
        when(mockCompetitor3.getStatus()).thenReturn(CompetitorStatus.FINISHED);
        when(mockCompetitor3.getName()).thenReturn("Bob");

        when(mockCompetitor4.getGender()).thenReturn(Gender.FEMALE);
        when(mockCompetitor4.getCategory()).thenReturn(Category.BABY);
        when(mockCompetitor4.getStatus()).thenReturn(CompetitorStatus.NOT_STARTED);
        when(mockCompetitor4.getName()).thenReturn("Eve");

        when(mockCompetitor5.getGender()).thenReturn(Gender.MALE);
        when(mockCompetitor5.getCategory()).thenReturn(Category.U14);
        when(mockCompetitor5.getStatus()).thenReturn(CompetitorStatus.ON_COURSE);
        when(mockCompetitor5.getName()).thenReturn("Charlie");

        when(mockCompetitor6.getGender()).thenReturn(Gender.FEMALE);
        when(mockCompetitor6.getCategory()).thenReturn(Category.BABY);
        when(mockCompetitor6.getStatus()).thenReturn(CompetitorStatus.FINISHED);
        when(mockCompetitor6.getName()).thenReturn("Diana");

        when(mockCompetitor7.getGender()).thenReturn(Gender.MALE);
        when(mockCompetitor7.getCategory()).thenReturn(Category.BABY);
        when(mockCompetitor7.getStatus()).thenReturn(CompetitorStatus.NOT_STARTED);
        when(mockCompetitor7.getName()).thenReturn("Frank");

        when(mockCompetitor8.getGender()).thenReturn(Gender.FEMALE);
        when(mockCompetitor8.getCategory()).thenReturn(Category.U14);
        when(mockCompetitor8.getStatus()).thenReturn(CompetitorStatus.ON_COURSE);
        when(mockCompetitor8.getName()).thenReturn("Grace");

        when(mockCompetitor9.getGender()).thenReturn(Gender.MALE);
        when(mockCompetitor9.getCategory()).thenReturn(Category.SENIOR);
        when(mockCompetitor9.getStatus()).thenReturn(CompetitorStatus.FINISHED);
        when(mockCompetitor9.getName()).thenReturn("Henry");

        when(mockCompetitor10.getGender()).thenReturn(Gender.FEMALE);
        when(mockCompetitor10.getCategory()).thenReturn(Category.BABY);
        when(mockCompetitor10.getStatus()).thenReturn(CompetitorStatus.NOT_STARTED);
        when(mockCompetitor10.getName()).thenReturn("Ivy");
    }

    @Test
    void testAddCompetitor() {
        competitorList.addCompetitor(mockCompetitor1);
        competitorList.addCompetitor(mockCompetitor2);
        assertTrue(competitorList.getAllCompetitors().contains(mockCompetitor1));
        assertTrue(competitorList.getAllCompetitors().contains(mockCompetitor2));
    }

    @Test
    void testRemoveCompetitor() {
        competitorList.addCompetitor(mockCompetitor1);
        competitorList.addCompetitor(mockCompetitor2);
        competitorList.removeCompetitor(mockCompetitor1);
        assertFalse(competitorList.getAllCompetitors().contains(mockCompetitor1));
        assertTrue(competitorList.getAllCompetitors().contains(mockCompetitor2));
    }

    @Test
    void testGetAllCompetitors() {
        competitorList.addCompetitor(mockCompetitor1);
        competitorList.addCompetitor(mockCompetitor2);
        competitorList.addCompetitor(mockCompetitor3);
        competitorList.addCompetitor(mockCompetitor4);
        competitorList.addCompetitor(mockCompetitor5);
        competitorList.addCompetitor(mockCompetitor6);
        competitorList.addCompetitor(mockCompetitor7);
        competitorList.addCompetitor(mockCompetitor8);
        competitorList.addCompetitor(mockCompetitor9);
        competitorList.addCompetitor(mockCompetitor10);
        Set<Competitor> competitors = competitorList.getAllCompetitors();
        assertEquals(10, competitors.size());
        assertTrue(competitors.contains(mockCompetitor1));
        assertTrue(competitors.contains(mockCompetitor2));
        assertTrue(competitors.contains(mockCompetitor3));
        assertTrue(competitors.contains(mockCompetitor4));
        assertTrue(competitors.contains(mockCompetitor5));
        assertTrue(competitors.contains(mockCompetitor6));
        assertTrue(competitors.contains(mockCompetitor7));
        assertTrue(competitors.contains(mockCompetitor8));
        assertTrue(competitors.contains(mockCompetitor9));
        assertTrue(competitors.contains(mockCompetitor10));
    }

    @Test
    void testGetAllCompetitorsByCategory() {
        competitorList.addCompetitor(mockCompetitor1);
        competitorList.addCompetitor(mockCompetitor2);
        competitorList.addCompetitor(mockCompetitor3);
        competitorList.addCompetitor(mockCompetitor4);
        competitorList.addCompetitor(mockCompetitor5);
        competitorList.addCompetitor(mockCompetitor6);
        competitorList.addCompetitor(mockCompetitor7);
        competitorList.addCompetitor(mockCompetitor8);
        competitorList.addCompetitor(mockCompetitor9);
        competitorList.addCompetitor(mockCompetitor10);
        Set<Competitor> competitors = competitorList.getCompetitorsByCategory(Category.BABY);
        assertEquals(5, competitors.size());
        assertTrue(competitors.contains(mockCompetitor1));
        assertTrue(competitors.contains(mockCompetitor4));
        assertTrue(competitors.contains(mockCompetitor7));
        assertTrue(competitors.contains(mockCompetitor10));
    }

    @Test
    void testGetCompetitorsByStatus() {
        competitorList.addCompetitor(mockCompetitor1);
        competitorList.addCompetitor(mockCompetitor2);
        competitorList.addCompetitor(mockCompetitor3);
        competitorList.addCompetitor(mockCompetitor4);
        competitorList.addCompetitor(mockCompetitor5);
        competitorList.addCompetitor(mockCompetitor6);
        competitorList.addCompetitor(mockCompetitor7);
        competitorList.addCompetitor(mockCompetitor8);
        competitorList.addCompetitor(mockCompetitor9);
        competitorList.addCompetitor(mockCompetitor10);
        Set<Competitor> competitors = competitorList.getCompetitorsByStatus(CompetitorStatus.NOT_STARTED);
        assertEquals(4, competitors.size());
        assertTrue(competitors.contains(mockCompetitor1));
        assertTrue(competitors.contains(mockCompetitor4));
        assertTrue(competitors.contains(mockCompetitor7));
        assertTrue(competitors.contains(mockCompetitor10));
    }

    @Test
    void testGetAllCompetitorsByGender() {
        competitorList.addCompetitor(mockCompetitor1);
        competitorList.addCompetitor(mockCompetitor2);
        competitorList.addCompetitor(mockCompetitor3);
        competitorList.addCompetitor(mockCompetitor4);
        competitorList.addCompetitor(mockCompetitor5);
        competitorList.addCompetitor(mockCompetitor6);
        competitorList.addCompetitor(mockCompetitor7);
        competitorList.addCompetitor(mockCompetitor8);
        competitorList.addCompetitor(mockCompetitor9);
        competitorList.addCompetitor(mockCompetitor10);
        Set<Competitor> competitors = competitorList.getCompetitorsByGender(Gender.MALE);
        assertEquals(5, competitors.size());
        assertTrue(competitors.contains(mockCompetitor1));
        assertTrue(competitors.contains(mockCompetitor3));
        assertTrue(competitors.contains(mockCompetitor5));
        assertTrue(competitors.contains(mockCompetitor7));
        assertTrue(competitors.contains(mockCompetitor9));
    }

    @Test
    void testGetCompetitorCount() {
        competitorList.addCompetitor(mockCompetitor1);
        competitorList.addCompetitor(mockCompetitor2);
        competitorList.addCompetitor(mockCompetitor3);
        competitorList.addCompetitor(mockCompetitor4);
        competitorList.addCompetitor(mockCompetitor5);
        competitorList.addCompetitor(mockCompetitor6);
        competitorList.addCompetitor(mockCompetitor7);
        competitorList.addCompetitor(mockCompetitor8);
        competitorList.addCompetitor(mockCompetitor9);
        competitorList.addCompetitor(mockCompetitor10);
        assertEquals(10, competitorList.getCompetitorCount());
    }

    @Test
    void testResetCompetitorsStatus() {
        competitorList.addCompetitor(mockCompetitor1);
        competitorList.addCompetitor(mockCompetitor2);
        competitorList.addCompetitor(mockCompetitor3);
        competitorList.addCompetitor(mockCompetitor4);
        competitorList.addCompetitor(mockCompetitor5);
        competitorList.addCompetitor(mockCompetitor6);
        competitorList.addCompetitor(mockCompetitor7);
        competitorList.addCompetitor(mockCompetitor8);
        competitorList.addCompetitor(mockCompetitor9);
        competitorList.addCompetitor(mockCompetitor10);
        competitorList.resetCompetitorsStatus();
        verify(mockCompetitor1).setStatus(CompetitorStatus.NOT_STARTED);
        verify(mockCompetitor1).setFinishTime(0);
        verify(mockCompetitor2).setStatus(CompetitorStatus.NOT_STARTED);
        verify(mockCompetitor2).setFinishTime(0);
        verify(mockCompetitor3).setStatus(CompetitorStatus.NOT_STARTED);
        verify(mockCompetitor3).setFinishTime(0);
        verify(mockCompetitor4).setStatus(CompetitorStatus.NOT_STARTED);
        verify(mockCompetitor4).setFinishTime(0);
        verify(mockCompetitor5).setStatus(CompetitorStatus.NOT_STARTED);
        verify(mockCompetitor5).setFinishTime(0);
        verify(mockCompetitor6).setStatus(CompetitorStatus.NOT_STARTED);
        verify(mockCompetitor6).setFinishTime(0);
        verify(mockCompetitor7).setStatus(CompetitorStatus.NOT_STARTED);
        verify(mockCompetitor7).setFinishTime(0);
        verify(mockCompetitor8).setStatus(CompetitorStatus.NOT_STARTED);
        verify(mockCompetitor8).setFinishTime(0);
        verify(mockCompetitor9).setStatus(CompetitorStatus.NOT_STARTED);
        verify(mockCompetitor9).setFinishTime(0);
        verify(mockCompetitor10).setStatus(CompetitorStatus.NOT_STARTED);
        verify(mockCompetitor10).setFinishTime(0);
    }

    @Test
    void testGetNextCompetitor() {
        competitorList.addCompetitor(mockCompetitor1);
        competitorList.addCompetitor(mockCompetitor2);
        competitorList.addCompetitor(mockCompetitor3);
        competitorList.addCompetitor(mockCompetitor4);
        competitorList.addCompetitor(mockCompetitor5);
        competitorList.addCompetitor(mockCompetitor6);
        competitorList.addCompetitor(mockCompetitor7);
        competitorList.addCompetitor(mockCompetitor8);
        competitorList.addCompetitor(mockCompetitor9);
        competitorList.addCompetitor(mockCompetitor10);
        // Assert that it starts from the first in the youngest female category
        assertEquals(mockCompetitor4, competitorList.getNextCompetitor());
    }

//    @Test
//    void testAssignStartNumbersByCategory() {
//        competitorList.addCompetitor(mockCompetitor1);
//        competitorList.addCompetitor(mockCompetitor2);
//        competitorList.addCompetitor(mockCompetitor3);
//        competitorList.addCompetitor(mockCompetitor4);
//        competitorList.addCompetitor(mockCompetitor5);
//        competitorList.addCompetitor(mockCompetitor6);
//        competitorList.addCompetitor(mockCompetitor7);
//        competitorList.addCompetitor(mockCompetitor8);
//        competitorList.addCompetitor(mockCompetitor9);
//        competitorList.addCompetitor(mockCompetitor10);
//        competitorList.assignStartNumbersByCategory();
//        verify(mockCompetitor1).setStartNumber(1);
//        verify(mockCompetitor2).setStartNumber(2);
//        verify(mockCompetitor3).setStartNumber(3);
//        verify(mockCompetitor4).setStartNumber(4);
//        verify(mockCompetitor5).setStartNumber(5);
//        verify(mockCompetitor6).setStartNumber(6);
//        verify(mockCompetitor7).setStartNumber(7);
//        verify(mockCompetitor8).setStartNumber(8);
//        verify(mockCompetitor9).setStartNumber(9);
//        verify(mockCompetitor10).setStartNumber(10);
//    }
}