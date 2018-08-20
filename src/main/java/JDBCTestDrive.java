import java.sql.SQLException;

public class JDBCTestDrive {
    public static void main(String args[]) {
        JDBCBooks books = null;
        try {
            books = new JDBCBooks("jdbc:mysql://localhost:3306/db", "root", "x7p");
            System.out.printf("Total number of books in the table : %d", books.size());
            books.display();

        } catch (SQLException sqlEx) {
            books.reportSQLException(sqlEx);
        } finally {
            books.close();
        }
    }
}
