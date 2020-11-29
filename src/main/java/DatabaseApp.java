import java.sql.*;

public class DatabaseApp {
    public Connection getConn(String db_name, String username, String password){
        try {
            System.out.println("Ustanowiono połączenie z DB");
            return DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + db_name + "?serverTimezone=Europe/Berlin",
                    username,
                    password
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Błąd połączenia z DB");
        return null;
    }
    public void closeConn(Connection conn){
        try {
            System.out.println("Zamknięto połączenie z DB");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void getAuthors(Connection conn){
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM author;");
            ResultSet authors = ps.executeQuery();
            while (authors.next()){
                System.out.println(authors.getInt(1));
                System.out.println(authors.getString(2));
                System.out.println(authors.getString(3));
                System.out.println("==============================");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DatabaseApp databaseApp = new DatabaseApp();
        Connection conn = databaseApp.getConn("blog_db", "blog_user","qwe123");
        databaseApp.getAuthors(conn);
        databaseApp.closeConn(conn);
    }
}
