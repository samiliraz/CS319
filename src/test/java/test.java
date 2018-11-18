
import hello.SqlRunner;

import java.sql.SQLException;


public class test {

    public static void main(String[] args ) throws SQLException {

        SqlRunner database = new SqlRunner();

        database.testSql();

//	Server server = new Server( database );

    }

}