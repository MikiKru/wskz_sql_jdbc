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
                System.out.println(authors.getString(4));
                System.out.println(authors.getString(5));
                System.out.println("==============================");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void insertAuthor(Connection conn, String name, String lastName, String email, String password){
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO author VALUES (default, ?, ?, ?, ?, default, default)");
            ps.setString(1,name);
            ps.setString(2,lastName);
            ps.setString(3,email);
            ps.setString(4,password);
            ps.executeUpdate();     // wszystkie polecenia wprowadzające zmiany wykonujemy executeUpdate()
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateAuthorPassword(Connection conn, int author_id, String newPassword){
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE author SET password = ? WHERE author_id = ?");
            ps.setString(1, newPassword);
            ps.setInt(2, author_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteAuthorById(Connection conn, int author_id){
        try {
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM author WHERE author_id = ?");
            ps.setInt(1,author_id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DatabaseApp databaseApp = new DatabaseApp();
        Connection conn = databaseApp.getConn("blog_db", "blog_user","qwe123");
//        databaseApp.insertAuthor(conn, "Jan","Nowak","jnn@jnn.pl","jn");
//        databaseApp.updateAuthorPassword(conn, 3, "Test123");
        databaseApp.deleteAuthorById(conn,5);
        databaseApp.getAuthors(conn);
        databaseApp.closeConn(conn);
    }
}
