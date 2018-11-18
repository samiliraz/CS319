package hello;


import hello.Models.*;
import java.sql.*;
import java.util.*;

@SuppressWarnings("Duplicates")
public class SqlRunner {

    private Connection con;

    public SqlRunner() throws SQLException {

        String host = "sql7.freesqldatabase.com";
        String databaseName = "sql7265894";
        String userName = "sql7265894";
        String password = "NBz8MM8GqF";
        String port = "3306";
        String url = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;

        con = DriverManager.getConnection(url,userName,password);
    }

    public void createTables() throws SQLException {

        String dropTable = "DROP TABLE IF EXISTS users;";
        con.createStatement().executeUpdate(dropTable);
        String userTable = "CREATE TABLE users (id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, username VARCHAR(30) NOT NULL, password VARCHAR(30) NOT NULL, ArcadeScore INT(6), TimeChallengeScore INT(6), ArcadeWhiteKnightScore INT(6), TimeChallengeWhiteKnightScore INT(6)" + ")";
        con.createStatement().executeUpdate(userTable);


    }

    public void createUser( String username, String password ) throws SQLException {
        String sql = "INSERT INTO users VALUES(NULL,'" + username + "','" + password + "',0,0,0,0)";
        con.createStatement().executeUpdate(sql);
    }



    public User getUserById(int id ) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = " + id;
        ResultSet result = con.createStatement().executeQuery(sql);

        User user = new User();
        while ( result.next() ){
            user.setId( result.getInt(1) );
            user.setUsername( result.getString(2) );
            user.setPassword( result.getString(3) );
            user.setArcadeScore( result.getInt(4) );
            user.setTimeChallengeScore( result.getInt(5) );
            user.setArcadeWhiteKnightScore( result.getInt(6) );
            user.setTimeChallengeWhiteKnightScore( result.getInt(7) );
        }
        return user;
    }

    public User getUserByName(String username ) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = " + username;
        ResultSet result = con.createStatement().executeQuery(sql);

        User user = new User();

        while ( result.next() ){
            user.setId( result.getInt(1) );
            user.setUsername( result.getString(2) );
            user.setPassword( result.getString(3) );
            user.setArcadeScore( result.getInt(4) );
            user.setTimeChallengeScore( result.getInt(5) );
            user.setArcadeWhiteKnightScore( result.getInt(6) );
            user.setTimeChallengeWhiteKnightScore( result.getInt(7) );
        }
        return user;
    }

    public void testSql() throws SQLException {

        String statement = "CREATE TABLE MyGuests (" + "id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY," + "firstname VARCHAR(30) NOT NULL," + "lastname VARCHAR(30) NOT NULL," + "email VARCHAR(50)," + "reg_date TIMESTAMP" + ")";
        String sql1 = "INSERT INTO MyGuests VALUES(1,'ENES','ERDEM',null,null)";
        String sql2 = "SELECT * FROM MyGuests";

//        con.createStatement().executeUpdate( statement );

//        con.createStatement().executeUpdate( sql1 );
        ResultSet deneme = con.createStatement().executeQuery( sql2 );

        while (deneme.next()) {
            System.out.println("Printing result...");
            String nameUser = deneme.getString("firstName");
            System.out.print("+++>>>> " + nameUser);
        }

    }

}
