package hello.Models;
public class User {

    private int id;
    private String username;
    private String password;

    private int ArcadeScore;
    private int TimeChallengeScore;
    private int ArcadeWhiteKnightScore;
    private int TimeChallengeWhiteKnightScore;

    public User(){
    }

    public User( int id, String username, String password ){
        this.id = id;
        this.username = username;
        this.password = password;

        this.ArcadeScore = 0;
        this.TimeChallengeScore = 0;
        this.ArcadeWhiteKnightScore = 0;
        this.TimeChallengeWhiteKnightScore = 0;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getArcadeScore() {
        return ArcadeScore;
    }

    public void setArcadeScore(int arcadeScore) {
        ArcadeScore = arcadeScore;
    }

    public int getTimeChallengeScore() {
        return TimeChallengeScore;
    }

    public void setTimeChallengeScore(int timeChallengeScore) {
        TimeChallengeScore = timeChallengeScore;
    }

    public int getArcadeWhiteKnightScore() {
        return ArcadeWhiteKnightScore;
    }

    public void setArcadeWhiteKnightScore(int arcadeWhiteKnightScore) {
        ArcadeWhiteKnightScore = arcadeWhiteKnightScore;
    }

    public int getTimeChallengeWhiteKnightScore() {
        return TimeChallengeWhiteKnightScore;
    }

    public void setTimeChallengeWhiteKnightScore(int timeChallengeWhiteKnightScore) {
        TimeChallengeWhiteKnightScore = timeChallengeWhiteKnightScore;
    }


}
