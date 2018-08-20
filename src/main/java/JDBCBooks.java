import java.sql.*;

/**
 * Simple Java program to connect to MySQL database running on localhost.
 * @author Anton Tobolkin
 */

public class JDBCBooks {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public JDBCBooks(String url, String user, String password) throws SQLException {
        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
    }

    public void reportSQLException(SQLException sqlEx) {
        System.out.println("SQLException: " + sqlEx.getMessage());
        System.out.println("SQLState: " + sqlEx.getSQLState());
        System.out.println("VendorError: " + sqlEx.getErrorCode());
    }

    public void close() {
        if (connection != null) {
            try { connection.close(); } catch(SQLException se) {}
        }
        if (statement != null) {
            try { statement.close(); } catch(SQLException se) {}
        }
        if (resultSet != null) {
            try { resultSet.close(); } catch(SQLException se) {}
        }
    }

    public int size() throws SQLException {
        resultSet = statement.executeQuery("select count(*) from books");
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    public void display() throws SQLException {
        resultSet = statement.executeQuery("select id, name, author from books");
        while (resultSet.next()) {
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String author = resultSet.getString(3);
            System.out.printf("id: %d, name: %s, author: %s %n", id, name, author);
        }
        System.out.println("---------------------------");
    }

    public void insert(String book, String author) throws SQLException {
        String query = "INSERT INTO books (id, name, author) VALUES ('%d', '%s', '%s');";
        statement.executeUpdate(String.format(query, size()+1, book, author));
    }

    public void delete(int id) throws SQLException {
        String query = "DELETE FROM books WHERE id = %d;";
        statement.executeUpdate(String.format(query, id));
    }

}