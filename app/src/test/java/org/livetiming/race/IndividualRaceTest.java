package org.livetiming.race;

import org.junit.jupiter.api.Test;
import org.livetiming.exception.NoCompetitorsOnCourseException;
import org.junit.jupiter.api.BeforeEach;
import org.livetiming.manager.CompetitorList;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IndividualRaceTest {

    private CompetitorList mockCompetitorList;
    private IndividualRace individualRace;

    @BeforeEach
    void setUp() {
        mockCompetitorList = mock(CompetitorList.class);
        individualRace = new IndividualRace("Test Race", mockCompetitorList); // Mocked CompetitorList will be injected
    }

    @Test
    void testFinishNextNoNextCompetitor() {
        // Arrange
        when(mockCompetitorList.getCompetitorsOnCourse())
                .thenReturn(Set.of());

        long finishTime = 100L;

        // Act
        try {
            individualRace.finishNext(finishTime);
        } catch (Exception e) {
            // Assert
            assertInstanceOf(NoCompetitorsOnCourseException.class, e);
            assertEquals("No competitors are currently on course.", e.getMessage());
        }
    }
}
