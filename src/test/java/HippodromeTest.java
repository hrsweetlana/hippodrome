import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {

    @Test
    void constructorNullListParamPassedThrowsIllegalArgumentException() {
        String expectedMessage = "Horses cannot be null.";
        List<Horse> horses = null;

        Throwable exception =
                assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void constructorEmptyListParamPassedThrowsIllegalArgumentException() {
        String expectedMessage = "Horses cannot be empty.";
        List<Horse> horses = new ArrayList<>();

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getHorsesReturnsListWithAllHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            horses.add(new Horse("Horse" + i, 1 + (i * 0.1), 150 + i));
        }
        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    void moveInvokesMoveForEachHourse() {
        List<Horse> mockedHorses = new ArrayList<Horse>();
        for (int i = 0; i < 50; i++) {
            mockedHorses.add(mock(Horse.class));
        }
        Hippodrome hippodromeWithMockedHorses = new Hippodrome(mockedHorses);

        hippodromeWithMockedHorses.move();

        for (Horse horse : mockedHorses) {
            verify(horse, times(1)).move();
        }
    }

    @Test
    void getWinnerReturnsMaxDistanceHorse() {
        Horse horse1 = mock(Horse.class);
        Horse horse2 = mock(Horse.class);
        Horse horse3 = mock(Horse.class);
        List<Horse> horses = List.of(horse1, horse2, horse3);
        Hippodrome hippodrome = new Hippodrome(horses);

        when(horse1.getDistance()).thenReturn(5.0);
        when(horse2.getDistance()).thenReturn(10.0);
        when(horse3.getDistance()).thenReturn(7.0);
        Horse actualHorse = hippodrome.getWinner();

        assertEquals(horse2, actualHorse);
    }
}