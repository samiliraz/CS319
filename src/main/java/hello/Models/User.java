package hello.Models;
public class User {


    private int id;
    private String username;
    private String password;

    private int ArcadeLevel;
    private int TimeChallengeLevel;
    private int ArcadeWhiteKnightLevel;
    private int TimeChallengeWhiteKnightLevel;
    
    private int ArcadeScore;
    private int TimeChallengeScore;
    private int ArcadeWhiteKnightScore;
    private int TimeChallengeWhiteKnightScore;
    private int Hints;

    public User(){
        id = -1; // invalid id
    }

    public User(int id, String username, String password, int arcadeLevel, int timeChallengeLevel, int arcadeWhiteKnightLevel, int timeChallengeWhiteKnightLevel, int arcadeScore, int timeChallengeScore, int arcadeWhiteKnightScore, int timeChallengeWhiteKnightScore , int hints) {
        this.id = id;
        this.username = username;
        this.password = password;
        ArcadeLevel = arcadeLevel;
        TimeChallengeLevel = timeChallengeLevel;
        ArcadeWhiteKnightLevel = arcadeWhiteKnightLevel;
        TimeChallengeWhiteKnightLevel = timeChallengeWhiteKnightLevel;
        ArcadeScore = arcadeScore;
        TimeChallengeScore = timeChallengeScore;
        ArcadeWhiteKnightScore = arcadeWhiteKnightScore;
        TimeChallengeWhiteKnightScore = timeChallengeWhiteKnightScore;
        Hints = hints;
    }

    public User(int id, String username, String password ){
        this.id = id;
        this.username = username;
        this.password = password;

        this.ArcadeLevel = 0;
        this.TimeChallengeLevel = 0;
        this.ArcadeWhiteKnightLevel = 0;
        this.TimeChallengeWhiteKnightLevel = 0;
        
        this.ArcadeScore = 0;
        this.TimeChallengeScore = 0;
        this.ArcadeWhiteKnightScore = 0;
        this.TimeChallengeWhiteKnightScore = 0;
        this.Hints = 5; // default number of hints given
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


    public int getArcadeLevel() {
        return ArcadeLevel;
    }

    public void setArcadeLevel(int arcadeLevel) {
        ArcadeLevel = arcadeLevel;
    }

    public int getTimeChallengeLevel() {
        return TimeChallengeLevel;
    }

    public void setTimeChallengeLevel(int timeChallengeLevel) {
        TimeChallengeLevel = timeChallengeLevel;
    }

    public int getArcadeWhiteKnightLevel() {
        return ArcadeWhiteKnightLevel;
    }

    public void setArcadeWhiteKnightLevel(int arcadeWhiteKnightLevel) {
        ArcadeWhiteKnightLevel = arcadeWhiteKnightLevel;
    }

    public int getTimeChallengeWhiteKnightLevel() {
        return TimeChallengeWhiteKnightLevel;
    }

    public void setTimeChallengeWhiteKnightLevel(int timeChallengeWhiteKnightLevel) {
        TimeChallengeWhiteKnightLevel = timeChallengeWhiteKnightLevel;
    }

    public int getHints() {
        return Hints;
    }

    public void setHints(int hints) {
        Hints = hints;
    }
}
