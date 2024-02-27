package server.api;

import commons.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.HttpStatus.*;

class EventControllerTest {

    private TestEventRepository repo;
    private EventController sut;
    @BeforeEach
    void setUp() {
        repo = new TestEventRepository();
        sut = new EventController(repo);
    }

    @Test
    public void databaseIsUsed() {
        sut.add(new Event("title"));
        assertTrue(repo.getCalledMethods().contains("save"));
    }
    @Test
    void noFindById() {
        var actual = sut.findById(0);
        assertTrue(repo.getCalledMethods().contains("findById"));
        assertEquals(NOT_FOUND, actual.getStatusCode());
    }

    @Test
    void findById() {
        Event e = new Event("test");
        var saved = sut.add(e);
        var actual = sut.findById(Objects.requireNonNull(saved.getBody()).getId());
        assertTrue(repo.getCalledMethods().contains("findById"));
        assertEquals(OK, actual.getStatusCode());
        assertEquals(e.getTitle(), Objects.requireNonNull(actual.getBody()).getTitle());
    }

    @Test
    void add() {
        var actual = sut.add(new Event("title"));
        assertTrue(repo.getCalledMethods().contains("save"));
        assertEquals(OK, actual.getStatusCode());
        assertNotNull(actual.getBody()); // check that body is not null
    }

    @Test
    public void cannotAddEventWithNullTitle() {
        var actual = sut.add(new Event(null));
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void cannotAddEventWithEmptyTitle() {
        var actual = sut.add(new Event(""));
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void cannotAddNull() {
        var actual = sut.add(null);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }
}