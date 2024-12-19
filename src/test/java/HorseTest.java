import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HorseTest {

    Horse horse = new Horse("Specter", 5, 10);

    @Test
    void horseConstructorThrowExceptionIfNameNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 5, 10);
        });

        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @DisplayName("Throws when first parameter instead of name contains only whitespace characters")
    @ValueSource(strings = {" ", "", "\t", "\n", "\r",})
    void horseConstructorThrowExceptionIfNameEmpty(String invalidName) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(invalidName, 5, 10);
        });

        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @Test
    void horseConstructorThrowExceptionIfNegativeSpeed() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Specter", -5, 10);
        });

        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @Test
    void horseConstructorThrowExceptionIfNegativeDistance() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse("Specter", 5, -1);
        });

        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    void getNameReturnsFirstParam() {
        assertEquals("Specter", horse.getName());
    }

    @Test
    void getSpeedReturnsSecondParam() {
        assertEquals(5, horse.getSpeed(), "Speed must be second param");
    }

    @Test
    void getDistanceReturnsThirdParam() {
        assertEquals(10, horse.getDistance(), "Distance must be third param");
    }

    @Test
    void getDistanceDefaultZeroForTwoArgsConstructor() {
        Horse horse = new Horse("Specter", 5);
        assertEquals(0, horse.getDistance(), "Default distance must be zero");
    }


    @ParameterizedTest
    @CsvSource({
            "0.2, 0.9, 0.3, 10, 3.0",
            "0.2, 0.9, 0.5, 8, 4.0",
            "0.2, 0.9, 0.7, 6, 4.2"
    })
    void testMoveMethod(double min, double max, double randomValue, double speed, double expectedDistance) {
        try (MockedStatic<Horse> mockStatic = Mockito.mockStatic(Horse.class)) {
            mockStatic.when(() -> Horse.getRandomDouble(min, max)).thenReturn(randomValue);
            Horse horse = new Horse("Specter", speed);
            horse.move();
            mockStatic.verify(() -> Horse.getRandomDouble(min, max));
            double actualDistance = horse.getDistance();
            System.out.println(actualDistance);
            //assertEquals(expectedDistance, horse.getDistance());
            assertEquals(expectedDistance, actualDistance);
        }
    }
}