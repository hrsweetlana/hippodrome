import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {

    List<Horse> horses = List.of(
            new Horse("A", 2.4),
            new Horse("B", 2.5),
            new Horse("C", 2.6),
            new Horse("D", 2.7),
            new Horse("E", 2.8),
            new Horse("F", 2.9),
            new Horse("G", 2.7),
            new Horse("H", 2.8),
            new Horse("I", 2.9),
            new Horse("J", 2.5),
            new Horse("K", 2.6),
            new Horse("L", 2.7),
            new Horse("M", 2.8),
            new Horse("N", 2.9),
            new Horse("O", 2.7),
            new Horse("P", 2.8),
            new Horse("Q", 2.9),
            new Horse("R", 2.5),
            new Horse("S", 2.6),
            new Horse("T", 2.7),
            new Horse("U", 2.8),
            new Horse("V", 2.9),
            new Horse("W", 2.7),
            new Horse("X", 2.8),
            new Horse("Y", 2.9),
            new Horse("Z", 2.9),
            new Horse("Blaze", 2.7),
            new Horse("Lobster", 2.8),
            new Horse("Pegasus", 2.9),
            new Horse("Cherry", 3));

    Hippodrome hippodrome = new Hippodrome(horses);


    @Test
    void hippodromeConstructorThrowsIfNullList() {
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    void hippodromeConstructorThrowsIfEmptyList() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(Collections.EMPTY_LIST));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    void getHorsesReturnsHorsesFromConstructor() {
        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    void moveInvokesMoveForEachHourse() {
        List<Horse> mockedHorses = new ArrayList<Horse>();
        for(int i = 0; i < 50; i++){
            mockedHorses.add(mock(Horse.class));
        }

        Hippodrome hippodromeWithMockedHorses = new Hippodrome(mockedHorses);

        hippodromeWithMockedHorses.move();

        for (Horse horse : mockedHorses) {
            Mockito.verify(horse).move();
        }
    }

    @Test
    void getWinnerReturnsMaxDistanceHorse(){
        Horse horse1 = mock(Horse.class);
        Horse horse2 = mock(Horse.class);
        Horse horse3 = mock(Horse.class);

        when(horse1.getDistance()).thenReturn(5.0);
        when(horse2.getDistance()).thenReturn(10.0);
        when(horse3.getDistance()).thenReturn(7.0);

        List<Horse> horses = List.of(horse1, horse2, horse3);
        Hippodrome hippodrome = new Hippodrome(horses);
        Horse actualHorse = hippodrome.getWinner();
        assertEquals(horse2, actualHorse);
    }
}