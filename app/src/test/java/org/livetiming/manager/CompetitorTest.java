package org.livetiming.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.livetiming.model.Athlete;
import org.livetiming.model.CompetitorStatus;
import org.livetiming.model.Gender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CompetitorTest {

    private Athlete mockAthlete;
    private Competitor competitor;

    @BeforeEach
    void setUp() {
        mockAthlete = mock(Athlete.class);
        when(mockAthlete.getFirstName()).thenReturn("John");
        when(mockAthlete.getLastName()).thenReturn("Doe");
        when(mockAthlete.getGender()).thenReturn(Gender.MALE);
        when(mockAthlete.getClub()).thenReturn("Club A");
        when(mockAthlete.getBirthYear()).thenReturn(null);
        when(mockAthlete.getStatus()).thenReturn(CompetitorStatus.NOT_STARTED);

        competitor = new Competitor(mockAthlete, 1);
    }

    @Test
    void testCompetitorCreation() {
        assertEquals("John", competitor.getFirstName());
        assertEquals("Doe", competitor.getLastName());
        assertEquals(Gender.MALE, competitor.getGender());
        assertEquals("Club A", competitor.getClub());
        assertEquals(CompetitorStatus.NOT_STARTED, competitor.getStatus());
        assertEquals(1, competitor.getStartNumber());
    }

    @Test
    void testStart() {
        competitor.start();
        assertEquals(CompetitorStatus.ON_COURSE, competitor.getStatus());
    }

    @Test
    void testFinish() {
        competitor.finish(12345L);
        assertEquals(CompetitorStatus.FINISHED, competitor.getStatus());
        assertEquals(12345L, competitor.getFinishTime());
    }
}