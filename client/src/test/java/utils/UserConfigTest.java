package utils;

import client.utils.UserConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UserConfigTest {
    TestIO IO; // dummy IO interface
    UserConfig userConfig;

    @BeforeEach
    void setUp() throws IOException {
        IO = new TestIO("""
                serverURL=http://localhost:8080/
                lang=en""");
        userConfig = new UserConfig(IO);
    }

    /**
     * Assert that the UserConfig instance is created
     */
    @Test
    void instanceNotNull() {
        assertNotNull(userConfig);
    }

    /**
     * Assert that it initializes with the correct URL
     */
    @Test
    void getUrl() {
        assertEquals("http://localhost:8080/", userConfig.getUrl());
    }

    /**
     * Assert that it initializes with the correct locale
     */
    @Test
    void getLocale() {
        assertEquals("en", userConfig.getLocale());
    }

    /**
     * Assert that it changes the locale
     */
    @Test
    void setLocale() throws IOException {
        assertEquals("en", userConfig.getLocale());
        assertFalse(IO.getContent().contains("nl"));
        userConfig.setLocale("nl");
        assertEquals("nl", userConfig.getLocale());
        assertTrue(IO.getContent().contains("nl"));
    }
}