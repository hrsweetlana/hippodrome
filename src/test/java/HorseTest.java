import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

@ExtendWith(MockitoExtension.class)
class HorseTest {

    @Test
    void constructorNullNameParamPassedThrowsIllegalArgumentException() {
        String expectedMessage = "Name cannot be null.";
        String name = null;
        double speed = 2.4;
        double distance = 150;

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(name, speed, distance);
        });

        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @DisplayName("Throws when first parameter instead of name contains only whitespace characters")
    @ValueSource(strings = {" ", "  ", "\t", "\t\t", "\n", "\n\n", "\r", "\t \t"})
    void constructorEmptyNameParamPassedThrowsIllegalArgumentException(String name) {
        String expectedMessage = "Name cannot be blank.";
        double speed = 2.4;
        double distance = 150;

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(name, speed, distance);
        });

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void constructorNegativeSpeedParamPassedThrowsIllegalArgumentException() {
        String expectedMessage = "Speed cannot be negative.";
        String name = "TestName";
        double speed = -2.4;
        double distance = 150;

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(name, speed, distance);
        });

        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void constructorNegativeDistanceParamPassedThrowsIllegalArgumentException() {
        String expectedException = "Distance cannot be negative.";
        String name = "TestName";
        double speed = 2.4;
        double distance = -150;

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(name, speed, distance);
        });

        assertEquals(expectedException, exception.getMessage());
    }

    @Test
    void getNameReturnsCorrectName() {
        String name = "TestName";
        double speed = 2.4;
        double distance = 150;
        Horse horse = new Horse(name, speed, distance);

        String actualName = horse.getName();

        assertEquals(name, actualName);
    }

    @Test
    void getSpeedReturnsCorrectSpeed() {

        String name = "TestName";
        double speed = 2.4;
        double distance = 150;
        Horse horse = new Horse(name, speed, distance);

        double actualSpeed = horse.getSpeed();

        assertEquals(speed, actualSpeed);
    }

    @Test
    void getDistanceReturnsCorrectDistance() {

        String name = "TestName";
        double speed = 2.4;
        double distance = 150;
        Horse horse = new Horse(name, speed, distance);

        double actualDistance = horse.getDistance();

        assertEquals(distance, actualDistance);
    }

    @Test
    void getDistanceInvokeTwoArgsConstructorReturnsZero() {
        String name = "TestName";
        double speed = 2.4;
        double distance = 0;
        Horse horse = new Horse(name, speed);
        String infoMessage = "Default distance must be zero";

        double actualDistance = horse.getDistance();

        assertEquals(distance, actualDistance, infoMessage);
    }


    @Test
    void moveCallsGetRandomDoubleMethodWithCorrectParams() {

        String name = "TestName";
        double speed = 2.4;
        double distance = 150;
        Horse horse = new Horse(name, speed, distance);
        double min = 0.2;
        double max = 0.9;

        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {

            horse.move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(min, max));

        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.3, 0.5, 0.8, 15, 0, 153})
    public void moveUsedFormulaForDistanceIsCorrect(double fakeRandomValue) {
        double min = 0.2;
        double max = 0.9;
        String name = "TestName";
        double speed = 2.4;
        double distance = 150;
        Horse horse = new Horse(name, speed, distance);

        double expectedDistance = distance + speed * fakeRandomValue;

        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(min, max)).thenReturn(fakeRandomValue);
            horse.move();
        }
        double actualDistance = horse.getDistance();

        assertEquals(expectedDistance, actualDistance);
    }
}