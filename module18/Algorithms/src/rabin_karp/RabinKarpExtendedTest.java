package rabin_karp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class RabinKarpExtendedTest {

    private RabinKarpExtended rabinKarpTest;

    @BeforeEach
    void setUp() {
        String text = "Отладка кода вдвое сложнее, чем его написание. Так что если вы пишете код настолько умно, насколько можете, то вы по определению недостаточно сообразительны, чтобы его отлаживать.";
        rabinKarpTest = new RabinKarpExtended(text);
    }

    @Test
    void testSearch() {
        rabinKarpTest.search("код");
        assertArrayEquals(rabinKarpTest.search("код").toArray(), new Integer[]{8, 70});
    }
}