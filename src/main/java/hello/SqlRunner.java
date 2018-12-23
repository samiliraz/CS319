package hello;


import hello.Models.*;
import hello.Models.Map;

import javax.websocket.MessageHandler;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.*;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

@SuppressWarnings("Duplicates")
public class SqlRunner {

    private Connection con;

    private User[] Users;
    private Map[] Maps;
    private Map[] WhiteKnightMaps;
    private String[] colors = {"#800000","#9A6324","#e6194B","#f58231","#911eb4","#3cb44b","#000000","#469990","#ffe119","#4363d8","#bfef45","#fabebe"};
    private int N, MapCount,WhiteKnightMapCount;
    private static Player mp3player;

    public SqlRunner() throws Exception {

        readUsers();
        readMaps();
        readWhiteKnightMaps();

        /*
        String host = "sql7.freesqldatabase.com";
        String databaseName = "sql7265894";
        String userName = "sql7265894";
        String password = "NBz8MM8GqF";
        String port = "3306";
        String url = "jdbc:mysql://" + host + ":" + port + "/" + databaseName;

        con = DriverManager.getConnection(url,userName,password);
        */
    }

    public static void playSong(){

        mp3player = null;
        BufferedInputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new URL("http://sc-baroque.1.fm:8045/").openStream());
            mp3player = new Player(inputStream);
            Runnable runnable = new Runnable() {
                public void run() {
                    try {
                        mp3player.play();
                    } catch (Exception e) {

                    }
                }
            };
            new Thread(runnable).start();
        } catch (Exception ex) {

        }
    }

    public void play(){
        playSong();
    }

    public void pause(){
        mp3player.close();
    }


    public void readUsers() throws Exception {

        FileReader r = new FileReader("users.txt");
        BufferedReader reader = new BufferedReader(r);
        String str;
        Users = new User[500];
        N=0;
        while( (str=reader.readLine())!=null ){
            String usr[] = str.split(" ");
            int id = Integer.parseInt(usr[0]);
            String username = usr[1];
            String password = usr[2];
            int lv1 = Integer.parseInt(usr[3]);
            int lv2 = Integer.parseInt(usr[4]);
            int lv3 = Integer.parseInt(usr[5]);
            int lv4 = Integer.parseInt(usr[6]);
            int sc1 = Integer.parseInt(usr[7]);
            int sc2 = Integer.parseInt(usr[8]);
            int sc3 = Integer.parseInt(usr[9]);
            int sc4 = Integer.parseInt(usr[10]);
            int hints = Integer.parseInt(usr[11]);

            Users[N++] = new User(id,username,password,lv1,lv2,lv3,lv4,sc1,sc2,sc3,sc4,hints);
        }
    }

    public void readMaps() throws Exception{
        FileReader r = new FileReader("maps.txt");
        BufferedReader reader = new BufferedReader(r);
        String str;
        Maps = new Map[100];
        MapCount = 0;
        while ( (str=reader.readLine())!=null ){
            String map[] = str.split(" ");

            System.out.println("--------------------------------------MAP---------------------------------------");

            int walls = Integer.parseInt(map[0]);
            List<Wall> wallList = new ArrayList<Wall>();
            for ( int i=0 ; i<walls ; i++ ){
                wallList.add( new Wall( map[1+i] ) );
            }

            List<RedKnight> redList = new ArrayList<RedKnight>();
            List<BlueKnight> blueList = new ArrayList<BlueKnight>();
            List<WhiteKnight> whiteList = new ArrayList<WhiteKnight>();
            int reds = Integer.parseInt(map[walls+1]);
            int blues;
            int whites;
            for ( int i=0 ; i<reds ; i++ ){
                int coordinates = Integer.parseInt(map[walls+2+i]);
                redList.add( new RedKnight( coordinates/10 , coordinates%10 ) );
            }
            blues = Integer.parseInt(map[walls+2+reds]);
            for ( int i=0 ; i<blues ; i++ ){
                int coordinates = Integer.parseInt(map[walls+3+reds+i]);
                blueList.add( new BlueKnight(coordinates/10 , coordinates%10 ) );
            }
            whites = Integer.parseInt(map[walls+3+reds+blues]);
            for ( int i=0 ; i<whites ; i++ ){
                int coordinates = Integer.parseInt(map[walls+4+reds+blues+i]);
                whiteList.add( new WhiteKnight(coordinates/10 , coordinates%10 ) );
            }

            List<Hint> hintList = new ArrayList<Hint>();
            int hints = Integer.parseInt(map[whites+walls+4+reds+blues]);

            System.out.println("NUMBeer of walls : " + walls + "  number of reds : " + reds + "  numer of blues : "+ blues + "  number of  whites : " + whites + " numer of hints : " + hints);
            for ( int i=0 ; i<4*hints ; i+=4 ){
                int hintWall = Integer.parseInt(map[whites+walls+5+reds+blues+i]);
                int hintRotation = Integer.parseInt(map[whites+walls+5+reds+blues+i+1]);
                int hintX = Integer.parseInt(map[whites+walls+5+reds+blues+i+2]);
                int hintY = Integer.parseInt(map[whites+walls+5+reds+blues+i+3]);

                System.out.println("hint detail : " + hintWall + " " + hintRotation + " " + hintX + " " + hintY);

                hintList.add( new Hint(hintWall,hintRotation,hintX,hintY) );
            }

            Maps[MapCount++] = new Map(wallList,redList,blueList,whiteList,hintList,0);

            System.out.println("WALL READ !!!");
            System.out.println("number of walls : " + wallList.size());
            for ( int i=0 ; i<wallList.size() ; i++ ){
                System.out.println( "Direction of the wall " + i + " : " + wallList.get(i).getDirection() + "  image : " + wallList.get(i).getImg());
            }
            System.out.println("number of red knights : " + redList.size());
            for ( int i=0 ; i<redList.size() ; i++ )
                System.out.println("Red Knight " + i + " : " + redList.get(i).getX() + "  " + redList.get(i).getY() + "  "  + redList.get(i).getImg());

            System.out.println("number of blue knights : " + blueList.size());
            for ( int i=0 ; i<blueList.size() ; i++ )
                System.out.println("blue Knight " + i + " : " + blueList.get(i).getX() + "  " + blueList.get(i).getY() + "  "  + blueList.get(i).getImg());

            System.out.println("number of white knights : " + whiteList.size());
            for ( int i=0 ; i<whiteList.size() ; i++ )
                System.out.println("white Knight " + i + " : " + whiteList.get(i).getX() + "  " + whiteList.get(i).getY() + "  "  + whiteList.get(i).getImg());

            System.out.println("number of hints: " + hintList.size());
            for ( int i=0 ; i<hintList.size() ; i++ )
                System.out.println("hint " + i + " : " + hintList.get(i).getWall() + "  " + hintList.get(i).getRotation() + "  "  + hintList.get(i).getX() + " " + hintList.get(i).getY());

        }

    }

    public void readWhiteKnightMaps() throws Exception{
        System.out.println("--------WHITE MAPS ------------");

        FileReader r = new FileReader("whitemaps.txt");
        BufferedReader reader = new BufferedReader(r);
        String str;
        WhiteKnightMaps = new Map[100];
        WhiteKnightMapCount = 0;
        while ( (str=reader.readLine())!=null ){
            String map[] = str.split(" ");
            int walls = Integer.parseInt(map[0]);
            List<Wall> wallList = new ArrayList<Wall>();
            for ( int i=0 ; i<walls ; i++ ){
                wallList.add( new Wall( map[1+i] ) );
            }

            List<RedKnight> redList = new ArrayList<RedKnight>();
            List<BlueKnight> blueList = new ArrayList<BlueKnight>();
            List<WhiteKnight> whiteList = new ArrayList<WhiteKnight>();
            int reds = Integer.parseInt(map[walls+1]);
            int blues;
            int whites;
            for ( int i=0 ; i<reds ; i++ ){
                int coordinates = Integer.parseInt(map[walls+2+i]);
                redList.add( new RedKnight( coordinates/10 , coordinates%10 ) );
            }
            blues = Integer.parseInt(map[walls+2+reds]);
            for ( int i=0 ; i<blues ; i++ ){
                int coordinates = Integer.parseInt(map[walls+3+reds+i]);
                blueList.add( new BlueKnight(coordinates/10 , coordinates%10 ) );
            }
            whites = Integer.parseInt(map[walls+3+reds+blues]);
            for ( int i=0 ; i<whites ; i++ ){
                int coordinates = Integer.parseInt(map[walls+4+reds+blues+i]);
                whiteList.add( new WhiteKnight(coordinates/10 , coordinates%10 ) );
            }

            List<Hint> hintList = new ArrayList<Hint>();
            int hints = Integer.parseInt(map[whites+walls+4+reds+blues]);
            for ( int i=0 ; i<4*hints ; i+=4 ){
                int hintWall = Integer.parseInt(map[whites+walls+5+reds+blues+i]);
                int hintRotation = Integer.parseInt(map[whites+walls+5+reds+blues+i+1]);
                int hintX = Integer.parseInt(map[whites+walls+5+reds+blues+i+2]);
                int hintY = Integer.parseInt(map[whites+walls+5+reds+blues+i+3]);
                hintList.add( new Hint(hintWall,hintRotation,hintX,hintY) );
            }

            WhiteKnightMaps[WhiteKnightMapCount++] = new Map(wallList,redList,blueList,whiteList,hintList,0);

            System.out.println("WALL READ !!!");
            System.out.println("number of walls : " + wallList.size());
            for ( int i=0 ; i<wallList.size() ; i++ ){
                System.out.println( "Direction of the wall " + i + " : " + wallList.get(i).getDirection() + "  image : " + wallList.get(i).getImg());
            }
            System.out.println("number of red knights : " + redList.size());
            for ( int i=0 ; i<redList.size() ; i++ )
                System.out.println("Red Knight " + i + " : " + redList.get(i).getX() + "  " + redList.get(i).getY() + "  "  + redList.get(i).getImg());

            System.out.println("number of blue knights : " + blueList.size());
            for ( int i=0 ; i<blueList.size() ; i++ )
                System.out.println("blue Knight " + i + " : " + blueList.get(i).getX() + "  " + blueList.get(i).getY() + "  "  + blueList.get(i).getImg());

            System.out.println("number of white knights : " + whiteList.size());
            for ( int i=0 ; i<whiteList.size() ; i++ )
                System.out.println("white Knight " + i + " : " + whiteList.get(i).getX() + "  " + whiteList.get(i).getY() + "  "  + whiteList.get(i).getImg());

            System.out.println("number of hints: " + hintList.size());
            for ( int i=0 ; i<hintList.size() ; i++ )
                System.out.println("hint " + i + " : " + hintList.get(i).getWall() + "  " + hintList.get(i).getRotation() + "  "  + hintList.get(i).getX() + " " + hintList.get(i).getY());

        }

    }

    /*
    public void createTables() throws SQLException {

        String dropTable = "DROP TABLE IF EXISTS users;";
        con.createStatement().executeUpdate(dropTable);
        String userTable = "CREATE TABLE users (id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY, username VARCHAR(30) NOT NULL, password VARCHAR(30) NOT NULL, ArcadeScore INT(6), TimeChallengeScore INT(6), ArcadeWhiteKnightScore INT(6), TimeChallengeWhiteKnightScore INT(6)" + ")";
        con.createStatement().executeUpdate(userTable);


    }
    */

    public void setNewArcadeLevelForUser( int id , int newLevel , int score , int hintUsed ) throws Exception {
        for ( int i=0 ; i<N ; i++ ){
            if ( Users[i].getId() == id ){
                Users[i].setArcadeLevel( newLevel-1 );
                Users[i].setArcadeScore( Users[i].getArcadeScore() + score );
                Users[i].setHints( Users[i].getHints() - hintUsed );
                break;
            }
        }
        UpdateUsers();
    }

    public void setNewArcadeWhiteKnightLevelForUser( int id , int newLevel , int score , int hintUsed ) throws Exception {
        for ( int i=0 ; i<N ; i++ ){
            if ( Users[i].getId() == id ){
                Users[i].setArcadeWhiteKnightLevel( newLevel-1 );
                Users[i].setArcadeWhiteKnightScore( Users[i].getArcadeWhiteKnightScore() + score );
                Users[i].setHints( Users[i].getHints() - hintUsed );
                break;
            }
        }
        UpdateUsers();
    }

    public void setNewTimeChallengeLevelForUser( int id , int newLevel , int score , int hintUsed ) throws Exception {
        for ( int i=0 ; i<N ; i++ ){
            if ( Users[i].getId() == id ){
                Users[i].setTimeChallengeLevel( newLevel-1 );
                Users[i].setTimeChallengeScore( Users[i].getTimeChallengeScore() + score );
                Users[i].setHints( Users[i].getHints() - hintUsed );
                break;
            }
        }
        UpdateUsers();
    }

    public void setNewTimeChallengeWhiteKnightLevelForUser( int id , int newLevel , int score , int hintUsed ) throws Exception {
        for ( int i=0 ; i<N ; i++ ){
            if ( Users[i].getId() == id ){
                Users[i].setTimeChallengeWhiteKnightLevel( newLevel-1 );
                Users[i].setTimeChallengeWhiteKnightScore( Users[i].getTimeChallengeWhiteKnightScore() + score );
                Users[i].setHints( Users[i].getHints() - hintUsed );
                break;
            }
        }
        UpdateUsers();
    }

    public void UpdateUsers() throws Exception {
        FileWriter w = new FileWriter("users.txt",false);
        BufferedWriter writer = new BufferedWriter(w);

        System.out.println("Updating Users------------------------------------");

        for ( int i=0 ; i<N ; i++ ){
            writer.write( Users[i].getId() + " " );
            writer.write( Users[i].getUsername() + " " );
            writer.write( Users[i].getPassword() + " " );
            writer.write( Users[i].getArcadeLevel() + " " );
            writer.write( Users[i].getTimeChallengeLevel() + " " );
            writer.write( Users[i].getArcadeWhiteKnightLevel() + " " );
            writer.write( Users[i].getTimeChallengeWhiteKnightLevel() + " " );
            writer.write( Users[i].getArcadeScore() + " " );
            writer.write( Users[i].getTimeChallengeScore() + " " );
            writer.write( Users[i].getArcadeWhiteKnightScore() + " " );
            writer.write( Users[i].getTimeChallengeWhiteKnightScore() + " " );
            writer.write( Users[i].getHints() + "");

            if ( i<(N-1) )
                writer.write("\n");
        }

        writer.close();
    }
    

    public void submitMap( String walls , int red, String reds, int blue, String blues, int white, String whites) throws Exception {
        FileWriter w = new FileWriter("submittedMaps.txt",true);
        BufferedWriter writer = new BufferedWriter(w);
        writer.write( walls );
        writer.write( " " + red );
        writer.write( " " + reds );
        writer.write( " " + blue );
        writer.write( " " + blues );
        writer.write( " " + white );
        writer.write( " " + whites );
        writer.write("\n");
        writer.close();
    }

    public String getNextMapForUser( int id ) throws Exception {
        String map = "";
        User user = getUserById( id );
        int level = (user.getArcadeLevel() + user.getTimeChallengeLevel())%MapCount;

        System.out.println("generating map : " + level + "  for user : " + user.getId());

        map += Maps[level].getWalls().size() + ", ";
        for ( int i=0 ; i<Maps[level].getWalls().size() ; i++ ) {
            map += Maps[level].getWalls().get(i).getImg() + ", ";
            map += Maps[level].getWalls().get(i).getDirection() + ", ";
            map += colors[i] + ", ";
        }
        map += Maps[level].getRed().size() + ", ";
        for ( int i=0 ; i<Maps[level].getRed().size() ; i++ ){
            map += Maps[level].getRed().get(i).getX();
            map += Maps[level].getRed().get(i).getY() + ", ";
        }
        map += Maps[level].getBlue().size() + ", ";
        for ( int i=0 ; i<Maps[level].getBlue().size() ; i++ ){
            map += Maps[level].getBlue().get(i).getX();
            map += Maps[level].getBlue().get(i).getY() + ", ";
        }
        map += Maps[level].getWhite().size() + ", ";
        for ( int i=0 ; i<Maps[level].getWhite().size() ; i++ ){
            map += Maps[level].getWhite().get(i).getX();
            map += Maps[level].getWhite().get(i).getY() + ", ";
        }
        map += (level+1) + ", ";

        map += Maps[level].getHint().size() + ", ";
        for ( int i=0 ; i<Maps[level].getHint().size() ; i++ ){
            map += Maps[level].getHint().get(i).getWall() + ", ";
            map += Maps[level].getHint().get(i).getRotation() + ", ";
            map += Maps[level].getHint().get(i).getX() + ", ";
            map += Maps[level].getHint().get(i).getY() + ", ";
        }

        map += user.getHints();

        return map;
    }


    public String getNextWhiteKnightMapForUser( int id ) throws Exception {
        String map = "";
        User user = getUserById( id );
        int level = (user.getArcadeWhiteKnightLevel() + user.getTimeChallengeWhiteKnightLevel())%WhiteKnightMapCount;

        System.out.println("generating map : " + level + "  for user : " + user.getId());

        map += WhiteKnightMaps[level].getWalls().size() + ", ";
        for ( int i=0 ; i<WhiteKnightMaps[level].getWalls().size() ; i++ ) {
            map += WhiteKnightMaps[level].getWalls().get(i).getImg() + ", ";
            map += WhiteKnightMaps[level].getWalls().get(i).getDirection() + ", ";
            map += colors[i] + ", ";
        }
        map += WhiteKnightMaps[level].getRed().size() + ", ";
        for ( int i=0 ; i<WhiteKnightMaps[level].getRed().size() ; i++ ){
            map += WhiteKnightMaps[level].getRed().get(i).getX();
            map += WhiteKnightMaps[level].getRed().get(i).getY() + ", ";
        }
        map += WhiteKnightMaps[level].getBlue().size() + ", ";
        for ( int i=0 ; i<WhiteKnightMaps[level].getBlue().size() ; i++ ){
            map += WhiteKnightMaps[level].getBlue().get(i).getX();
            map += WhiteKnightMaps[level].getBlue().get(i).getY() + ", ";
        }
        map += WhiteKnightMaps[level].getWhite().size() + ", ";
        for ( int i=0 ; i<WhiteKnightMaps[level].getWhite().size() ; i++ ){
            map += WhiteKnightMaps[level].getWhite().get(i).getX();
            map += WhiteKnightMaps[level].getWhite().get(i).getY() + ", ";
        }
        map += (level+1) + ", ";

        map += WhiteKnightMaps[level].getHint().size() + ", ";
        for ( int i=0 ; i<WhiteKnightMaps[level].getHint().size() ; i++ ){
            map += WhiteKnightMaps[level].getHint().get(i).getWall() + ", ";
            map += WhiteKnightMaps[level].getHint().get(i).getRotation() + ", ";
            map += WhiteKnightMaps[level].getHint().get(i).getX() + ", ";
            map += WhiteKnightMaps[level].getHint().get(i).getY() + ", ";
        }

        map += user.getHints();

        return map;
    }

    public boolean createUser( String username, String password ) throws Exception {
        /*String sql = "INSERT INTO users VALUES(NULL,'" + username + "','" + password + "',0,0,0,0)";
        con.createStatement().executeUpdate(sql);*/

        System.out.println("Create User : " + username + " " + password);

        for ( int i=0 ; i<N ; i++ ){
            if ( Users[i].getUsername().equals(username) )
                return false;
        }

        Users[N] = new User(N+1,username,password);
        FileWriter w = new FileWriter("users.txt",true);
        BufferedWriter writer = new BufferedWriter(w);
        writer.write("\n" + Users[N].getId() + " " + username + " " + password + " " +  Users[N].getArcadeLevel() + " " +
                                                                                            Users[N].getTimeChallengeLevel() + " " +
                                                                                            Users[N].getArcadeWhiteKnightLevel() + " " +
                                                                                            Users[N].getArcadeScore() + " " +
                                                                                            Users[N].getTimeChallengeWhiteKnightScore() + " " +
                                                                                            Users[N].getTimeChallengeScore() + " " +
                                                                                            Users[N].getArcadeWhiteKnightScore() + " " +
                                                                                            Users[N].getTimeChallengeWhiteKnightScore() + " " +
                                                                                            Users[N].getHints());
        N++;
        writer.close();
        System.out.println("--------User Created---------");
        return true;
    }



    public User getUserById(int id ) throws Exception {
        /*String sql = "SELECT * FROM users WHERE id = " + id;
        ResultSet result = con.createStatement().executeQuery(sql);

        User user = new User();
        while ( result.next() ){
            user.setId( result.getInt(1) );
            user.setUsername( result.getString(2) );
            user.setPassword( result.getString(3) );
            user.setArcadeScore( result.getInt(4) );
            user.setTimeChallengeScore( result.getInt(5) );
            user.setArcadeWhiteKnightScore( result.getInt(6) );
            user.setTimeChallengeWhiteKnightScore( result.getInt(7) a);
        }
        return user;*/

        for ( int i=0 ; i<N ; i++ ){
            if ( Users[i].getId() == id )
                return Users[i];
        }

        return new User();

    }

    public User getUserByName(String username ) throws Exception {
        /*String sql = "SELECT * FROM users WHERE username = " + username;
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
        return user;*/
        for ( int i=0 ; i<N ; i++ )
            if ( Users[i].getUsername().equals( username ) )
                return Users[i];

        return new User();
    }

    public String getArcadeHighscores() {
        User[] list = new User[5];
        boolean[] used = new boolean[5000];

        for ( int i=0 ; i<N ; i++ )
            used[i] = false;

        for ( int k=0 ; k<5 ; k++ ) {
            int maxScore = -1;
            int pos = 0;
            for (int i = 0; i < N; i++) {
                if (used[i] == false && maxScore < Users[i].getArcadeScore()) {
                    maxScore = Users[i].getArcadeScore();
                    pos = i;
                }
            }
            list[k] = Users[pos];
            used[pos] = true;
        }
        String str = "5";
        for ( int i=0 ; i<5 ; i++ ){
            str = str + ",";
            str = str + list[i].getUsername();
        }
        for ( int i=0 ; i<5 ; i++ ){
            str = str + ",";
            str = str + list[i].getArcadeScore();
        }
        return str;
    }

    public String getTimeChallengeHighscores() {
        User[] list = new User[5];
        boolean[] used = new boolean[5000];

        for ( int i=0 ; i<N ; i++ )
            used[i] = false;

        for ( int k=0 ; k<5 ; k++ ) {
            int maxScore = -1;
            int pos = 0;
            for (int i = 0; i < N; i++) {
                if (used[i] == false && maxScore < Users[i].getTimeChallengeScore()) {
                    maxScore = Users[i].getTimeChallengeScore();
                    pos = i;
                }
            }
            list[k] = Users[pos];
            used[pos] = true;
        }
        String str = "5";
        for ( int i=0 ; i<5 ; i++ ) {
            str = str + ",";
            str = str + list[i].getUsername();
        }
        for ( int i=0 ; i<5 ; i++ ){
            str = str + ",";
            str = str + list[i].getTimeChallengeScore();
        }
        return str;
    }

    public String getArcadeWhiteKnightHighscores() {
        User[] list = new User[5];
        boolean[] used = new boolean[5000];

        for ( int i=0 ; i<N ; i++ )
            used[i] = false;

        for ( int k=0 ; k<5 ; k++ ) {
            int maxScore = -1;
            int pos = 0;
            for (int i = 0; i < N; i++) {
                if (used[i] == false && maxScore < Users[i].getArcadeWhiteKnightScore()) {
                    maxScore = Users[i].getArcadeWhiteKnightScore();
                    pos = i;
                }
            }
            list[k] = Users[pos];
            used[pos] = true;
        }
        String str = "5";
        for ( int i=0 ; i<5 ; i++ ){
            str = str + ",";
            str = str + list[i].getUsername();
        }
        for ( int i=0 ; i<5 ; i++ ) {
            str = str + ",";
            str = str + list[i].getArcadeWhiteKnightScore();
        }
        return str;
    }

    public String getTimeChallengeWhiteKnightHighscores() {
        User[] list = new User[5];
        boolean[] used = new boolean[5000];

        for ( int i=0 ; i<N ; i++ )
            used[i] = false;

        for ( int k=0 ; k<5 ; k++ ) {
            int maxScore = -1;
            int pos = 0;
            for (int i = 0; i < N; i++) {
                if (used[i] == false && maxScore < Users[i].getTimeChallengeWhiteKnightScore()) {
                    maxScore = Users[i].getTimeChallengeWhiteKnightScore();
                    pos = i;
                }
            }
            list[k] = Users[pos];
            used[pos] = true;
        }
        String str = "5";
        for ( int i=0 ; i<5 ; i++ ){
            str = str + ",";
            str = str + list[i].getUsername();
        }
        for ( int i=0 ; i<5 ; i++ ){
            str = str + ",";
            str = str + list[i].getTimeChallengeWhiteKnightScore();
        }
        return str;
    }

    public String getHighscores( int id ){
        String scores = "";

        String user1, user2, user3, user4, user5;
        int score1, score2, score3, score4, score5;

        score1 = -1; score2 = -1; score3 = -1; score4 = -1; score5 = -1;
        user1 = ""; user2 = ""; user3 = ""; user4 = ""; user5 = "";

        for ( int i=0 ; i<N ; i++ ){
            if ( Users[i].getArcadeScore() > score1 ){
                user5 = user4;  score5 = score4;
                user4 = user3;  score4 = score3;
                user3 = user2;  score3 = score2;
                user2 = user1;  score2 = score1;
                user1 = Users[i].getUsername(); score1 = Users[i].getArcadeScore();
            }
            else if ( Users[i].getArcadeScore() > score2 ){
                user5 = user4;  score5 = score4;
                user4 = user3;  score4 = score3;
                user3 = user2;  score3 = score2;
                user2 = Users[i].getUsername(); score2 = Users[i].getArcadeScore();
            }
            else if ( Users[i].getArcadeScore() > score3 ){
                user5 = user4;  score5 = score4;
                user4 = user3;  score4 = score3;
                user3 = Users[i].getUsername(); score3 = Users[i].getArcadeScore();
            }
            else if ( Users[i].getArcadeScore() > score4 ){
                user5 = user4;  score5 = score4;
                user4 = Users[i].getUsername(); score4 = Users[i].getArcadeScore();
            }
            else if ( Users[i].getArcadeScore() > score5 ){
                user5 = Users[i].getUsername(); score5 = Users[i].getArcadeScore();
            }
        }

        scores += "1, " + user1 + ", " + score1 + ", ";
        scores += "2, " + user2 + ", " + score2 + ", ";
        scores += "3, " + user3 + ", " + score3 + ", ";
        scores += "4, " + user4 + ", " + score4 + ", ";
        scores += "5, " + user5 + ", " + score5 + ", ";

        for ( int i=0 ; i<N ; i++ ){
            if ( Users[i].getId() == id ){

                int cnt = 1;
                for ( int j=0 ; j<N ; j++ ){
                    if ( i!=j && ( Users[j].getArcadeScore() > Users[i].getArcadeScore() || ( Users[j].getArcadeScore() == Users[i].getArcadeScore() && Users[j].getId() < Users[i].getId()) ) ){
                        cnt++;
                    }
                }
                scores += cnt + ", " + Users[i].getUsername() + ", " + Users[i].getArcadeScore() + ", ";
                break;
            }
        }

        score1 = -1; score2 = -1; score3 = -1; score4 = -1; score5 = -1;
        user1 = ""; user2 = ""; user3 = ""; user4 = ""; user5 = "";

        for ( int i=0 ; i<N ; i++ ){
            if ( Users[i].getTimeChallengeScore() > score1 ){
                user5 = user4;  score5 = score4;
                user4 = user3;  score4 = score3;
                user3 = user2;  score3 = score2;
                user2 = user1;  score2 = score1;
                user1 = Users[i].getUsername(); score1 = Users[i].getTimeChallengeScore();
            }
            else if ( Users[i].getTimeChallengeScore() > score2 ){
                user5 = user4;  score5 = score4;
                user4 = user3;  score4 = score3;
                user3 = user2;  score3 = score2;
                user2 = Users[i].getUsername(); score2 = Users[i].getTimeChallengeScore();
            }
            else if ( Users[i].getTimeChallengeScore() > score3 ){
                user5 = user4;  score5 = score4;
                user4 = user3;  score4 = score3;
                user3 = Users[i].getUsername(); score3 = Users[i].getTimeChallengeScore();
            }
            else if ( Users[i].getTimeChallengeScore() > score4 ){
                user5 = user4;  score5 = score4;
                user4 = Users[i].getUsername(); score4 = Users[i].getTimeChallengeScore();
            }
            else if ( Users[i].getTimeChallengeScore() > score5 ){
                user5 = Users[i].getUsername(); score5 = Users[i].getTimeChallengeScore();
            }
        }
        
        scores += "1, " + user1 + ", " + score1 + ", ";
        scores += "2, " + user2 + ", " + score2 + ", ";
        scores += "3, " + user3 + ", " + score3 + ", ";
        scores += "4, " + user4 + ", " + score4 + ", ";
        scores += "5, " + user5 + ", " + score5 + ", ";

        for ( int i=0 ; i<N ; i++ ){
            if ( Users[i].getId() == id ){

                int cnt = 1;
                for ( int j=0 ; j<N ; j++ ){
                    if ( i!=j && ( Users[j].getTimeChallengeScore() > Users[i].getTimeChallengeScore() || ( Users[j].getTimeChallengeScore() == Users[i].getTimeChallengeScore() && Users[j].getId() < Users[i].getId()) ) ){
                        cnt++;
                    }
                }
                scores += cnt + ", " + Users[i].getUsername() + ", " + Users[i].getTimeChallengeScore() + ", ";
                break;
            }
        }

        score1 = -1; score2 = -1; score3 = -1; score4 = -1; score5 = -1;
        user1 = ""; user2 = ""; user3 = ""; user4 = ""; user5 = "";

        for ( int i=0 ; i<N ; i++ ){
            if ( Users[i].getArcadeWhiteKnightScore() > score1 ){
                user5 = user4;  score5 = score4;
                user4 = user3;  score4 = score3;
                user3 = user2;  score3 = score2;
                user2 = user1;  score2 = score1;
                user1 = Users[i].getUsername(); score1 = Users[i].getArcadeWhiteKnightScore();
            }
            else if ( Users[i].getArcadeWhiteKnightScore() > score2 ){
                user5 = user4;  score5 = score4;
                user4 = user3;  score4 = score3;
                user3 = user2;  score3 = score2;
                user2 = Users[i].getUsername(); score2 = Users[i].getArcadeWhiteKnightScore();
            }
            else if ( Users[i].getArcadeWhiteKnightScore() > score3 ){
                user5 = user4;  score5 = score4;
                user4 = user3;  score4 = score3;
                user3 = Users[i].getUsername(); score3 = Users[i].getArcadeWhiteKnightScore();
            }
            else if ( Users[i].getArcadeWhiteKnightScore() > score4 ){
                user5 = user4;  score5 = score4;
                user4 = Users[i].getUsername(); score4 = Users[i].getArcadeWhiteKnightScore();
            }
            else if ( Users[i].getArcadeWhiteKnightScore() > score5 ){
                user5 = Users[i].getUsername(); score5 = Users[i].getArcadeWhiteKnightScore();
            }
        }

        scores += "1, " + user1 + ", " + score1 + ", ";
        scores += "2, " + user2 + ", " + score2 + ", ";
        scores += "3, " + user3 + ", " + score3 + ", ";
        scores += "4, " + user4 + ", " + score4 + ", ";
        scores += "5, " + user5 + ", " + score5 + ", ";

        for ( int i=0 ; i<N ; i++ ){
            if ( Users[i].getId() == id ){

                int cnt = 1;
                for ( int j=0 ; j<N ; j++ ){
                    if ( i!=j && ( Users[j].getArcadeWhiteKnightScore() > Users[i].getArcadeWhiteKnightScore() || ( Users[j].getArcadeWhiteKnightScore() == Users[i].getArcadeWhiteKnightScore() && Users[j].getId() < Users[i].getId()) ) ){
                        cnt++;
                    }
                }
                scores += cnt + ", " + Users[i].getUsername() + ", " + Users[i].getArcadeWhiteKnightScore() + ", ";
                break;
            }
        }

        score1 = -1; score2 = -1; score3 = -1; score4 = -1; score5 = -1;
        user1 = ""; user2 = ""; user3 = ""; user4 = ""; user5 = "";

        for ( int i=0 ; i<N ; i++ ){
            if ( Users[i].getTimeChallengeWhiteKnightScore() > score1 ){
                user5 = user4;  score5 = score4;
                user4 = user3;  score4 = score3;
                user3 = user2;  score3 = score2;
                user2 = user1;  score2 = score1;
                user1 = Users[i].getUsername(); score1 = Users[i].getTimeChallengeWhiteKnightScore();
            }
            else if ( Users[i].getTimeChallengeWhiteKnightScore() > score2 ){
                user5 = user4;  score5 = score4;
                user4 = user3;  score4 = score3;
                user3 = user2;  score3 = score2;
                user2 = Users[i].getUsername(); score2 = Users[i].getTimeChallengeWhiteKnightScore();
            }
            else if ( Users[i].getTimeChallengeWhiteKnightScore() > score3 ){
                user5 = user4;  score5 = score4;
                user4 = user3;  score4 = score3;
                user3 = Users[i].getUsername(); score3 = Users[i].getTimeChallengeWhiteKnightScore();
            }
            else if ( Users[i].getTimeChallengeWhiteKnightScore() > score4 ){
                user5 = user4;  score5 = score4;
                user4 = Users[i].getUsername(); score4 = Users[i].getTimeChallengeWhiteKnightScore();
            }
            else if ( Users[i].getTimeChallengeWhiteKnightScore() > score5 ){
                user5 = Users[i].getUsername(); score5 = Users[i].getTimeChallengeWhiteKnightScore();
            }
        }

        scores += "1, " + user1 + ", " + score1 + ", ";
        scores += "2, " + user2 + ", " + score2 + ", ";
        scores += "3, " + user3 + ", " + score3 + ", ";
        scores += "4, " + user4 + ", " + score4 + ", ";
        scores += "5, " + user5 + ", " + score5 + ", ";

        for ( int i=0 ; i<N ; i++ ){
            if ( Users[i].getId() == id ){

                int cnt = 1;
                for ( int j=0 ; j<N ; j++ ){
                    if ( i!=j && ( Users[j].getTimeChallengeWhiteKnightScore() > Users[i].getTimeChallengeWhiteKnightScore() || ( Users[j].getTimeChallengeWhiteKnightScore() == Users[i].getTimeChallengeWhiteKnightScore() && Users[j].getId() < Users[i].getId()) ) ){
                        cnt++;
                    }
                }
                scores += cnt + ", " + Users[i].getUsername() + ", " + Users[i].getTimeChallengeWhiteKnightScore();
                break;
            }
        }
        
        return scores;
    }

    public void printUsers() {
        for (int i = 0; i < N; i++) {
            System.out.print(Users[i].getId() + " ");
            System.out.print(Users[i].getUsername() + " ");
            System.out.print(Users[i].getPassword() + " ");
            System.out.print(Users[i].getArcadeLevel() + " ");
            System.out.print(Users[i].getTimeChallengeLevel() + " ");
            System.out.print(Users[i].getArcadeWhiteKnightLevel() + " ");
            System.out.print(Users[i].getTimeChallengeWhiteKnightLevel() + " ");
            System.out.print(Users[i].getArcadeScore() + " ");
            System.out.print(Users[i].getTimeChallengeScore() + " ");
            System.out.print(Users[i].getArcadeWhiteKnightScore() + " ");
            System.out.print(Users[i].getTimeChallengeWhiteKnightScore() + "\n");
        }
    }

}
