package com.example.demofx.repository;

import com.example.demofx.exception.QueryFailureException;
import com.example.demofx.utils.Constants;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DbRepositoryTest {

    @Test
    void testQueryAfterInitialisation() throws QueryFailureException {
        var repo = new DbRepository(); // will initialise DB
        var results = repo.query(Constants.CATEGORY_QUERY);
        assertEquals(5, results.size());
    }

    @Test
    void testQueryExpectQueryFailure() {
        var repo = new DbRepository(); // will initialise DB
        assertThrows(QueryFailureException.class, () -> repo.query(" "));
    }

}