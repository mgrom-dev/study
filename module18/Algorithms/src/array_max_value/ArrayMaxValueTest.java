package array_max_value;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayMaxValueTest {

    @Test
    void testGetMaxValue() {
        assertEquals(ArrayMaxValue.getMaxValue(5, 44321, -59, 1348657209, 0, 7), 1348657209);
    }
}