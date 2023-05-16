package com.example.demofx.repository;

import com.example.demofx.exception.QueryFailureException;
import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcDataSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A Singleton manager for queries which
 * 1) creates a connection
 * 2) executes soe SQL
 * 3) closes the connection.
 * This is no doubt resource intensive but doesn't leave hanging connections
 * if the lambda is closed down.
 */
@Slf4j
public class DbRepository {

    public static final Connection connection;
    private Statement statement;

    static {

        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:mem:test;DATABASE_TO_UPPER=FALSE;TIME ZONE=UTC;");
        ds.setUser("sa");
        ds.setPassword("sa");
        try {
            connection = ds.getConnection();
            // setup initial data
            performInitialSetup();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * @param query String The query to be executed
     * @return a ResultSet object containing the results or null if not available
     * @throws SQLException
     */
    public List<HashMap<String, Object>> query(String query) throws QueryFailureException {

        log.info("Request to execute query {}", query);

        try {
            statement = connection.createStatement();
            return convertResultSetToList(statement.executeQuery(query));
        } catch (Exception ex) {
            log.error("Unable to execute query " + query, ex);
            throw new QueryFailureException("Unable to execute query " + query, ex);
        } finally {
            try {
                if ((statement != null) && (!statement.isClosed())) {
                    statement.close();
                }
            } catch (SQLException e) {
                log.error("Could not close statement", e);
            }
            // Leave the connection in place
        }
    }

    /**
     * We have to expand the result Set here as it will not be available
     * after we close the statement and connection ....
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private List<HashMap<String, Object>> convertResultSetToList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<HashMap<String, Object>> list = new ArrayList<>();

        while (rs.next()) {
            HashMap<String, Object> row = new HashMap<>(columns);
            for (int i = 1; i <= columns; ++i) {
                row.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(row);
        }

        return list;
    }

    private static void performInitialSetup() throws QueryFailureException {
        processInputStream(DbRepository.class.getClassLoader().getResourceAsStream("db/create-schema.ddl"));
        processInputStream(DbRepository.class.getClassLoader().getResourceAsStream("db/initialise-data.sql"));
    }

    private static void processInputStream(InputStream is) throws QueryFailureException {

        try (InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                executeStatement(line);
            }

        } catch (IOException e) {
            log.error("IOException ", e);
        }

    }

    private static void executeStatement(String statement) throws QueryFailureException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(statement);
        } catch (SQLException e) {
            log.error("SQL " + statement + " failed", e);
            throw new QueryFailureException("Unable to execute statement " + statement, e);
        }
    }

}
