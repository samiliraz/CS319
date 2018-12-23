package hello;

import hello.Models.*;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import java.sql.SQLException;

@Controller
public class MainController {

    private SqlRunner runner;
    private int UserSession;
    private String message;

    public MainController() throws Exception {
        runner = new SqlRunner();
        UserSession = 0;
        message = "";
        runner.printUsers();
    }

    @RequestMapping("/INITPAGE")
    public String init( Model model , String msg ){

        return "INITPAGE";
    }

    @RequestMapping("/Arcade")
    public String arcade( Model model ){
        if ( UserSession != 0 )
            return "Arcade";
        else
            return init(model , "You must login first !");
    }

    @RequestMapping("/ArcadeTime")
    public String arcadeTime( Model model ){
        if ( UserSession != 0 )
            return "ArcadeTime";
        else
            return init(model , "You must login first !");
    }

    @RequestMapping("/WhiteArcade")
    public String whiteArcade( Model model ){
        if ( UserSession != 0 )
            return "WhiteArcade";
        else
            return init(model , "You must login first !");
    }

    @RequestMapping("/Highscores")
    public String Highscores( Model model ){
        if ( UserSession != 0 )
            return "Highscores";
        else
            return init(model , "You must login first !");
    }

    @RequestMapping("/Credits")
    public String Credits( Model model ){
        if ( UserSession != 0 )
            return "Credits";
        else
            return init(model , "You must login first !");
    }

    @RequestMapping("/UserMap")
    public String UserMap( Model model ){
        if ( UserSession != 0 )
            return "UserMap";
        else
            return init(model , "You must login first !");
    }

    @RequestMapping("/WhiteArcadeTime")
    public String whiteArcadeTime( Model model ){
        if ( UserSession != 0 )
            return "WhiteArcadeTime";
        else
            return init(model , "You must login first !");
    }

    @RequestMapping("/MAINMENU")
    public String mainmenu( Model model ){
        if ( UserSession != 0 )
            return "MAINMENU";
        else
            return init(model , "You must login first !");
    }

    @GetMapping("/LOGIN")
    public String login( Model model , String msg ){
        System.out.println("Login Message : " + msg);
        return "LOGIN";
    }

    @GetMapping("/SIGNUPPAGE")
    public String signUp( Model model , String msg ){
        System.out.println("Signup Message : " + msg);
        return "SIGNUPPAGE";
    }

    @GetMapping("/PlayMenuPage")
    public String playMenu( Model model ){
        if ( UserSession != 0 )
            return "PlayMenuPage";
        else
            return init(model , "You must login first !");
    }

    @GetMapping("/GAMEPLAY")
    public String gameplay( Model model ){
        if ( UserSession != 0 )
            return "GAMEPLAY";
        else
            return init(model , "You must login first !");
    }

    @PostMapping("/submit_signup")
    public String submit_signup(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("password2") String password2 , Model model ) throws Exception {

        System.out.println("SubmitSignup " + username + " " + password + " " + password2);

        if ( password.equals(password2) == false )
            return signUp( model , "Passwords do not match!");

        if ( runner.createUser(username,password) == false )
            return signUp( model ,"Username taken!");

        return login(model, "Signed up! You can login now.");
    }

    @PostMapping("/submit_login")
    public String submit_login(@RequestParam("Username") String username, @RequestParam("Password") String password , Model model ) throws Exception {

        User user = runner.getUserByName(username);

        if ( user.getId()!= -1 && user.getUsername().equals( username ) && user.getPassword().equals(password) ) {
            UserSession = user.getId();
            runner.play();
            return "MAINMENU";
        }

        return login(model, "No Such User !");
    }

    @RequestMapping("/submit_logout")
    public String submit_logout( Model model ){
        UserSession = 0;
        runner.pause();
        return init( model , "Successfully logged out !");
    }

    @GetMapping("/data")
    public ResponseEntity<String> data(HttpServletRequest request, HttpServletResponse response ){
        int level = Integer.parseInt( request.getParameter("level" ) );
        int score = Integer.parseInt( request.getParameter("score" ) );
        int hintUsed = Integer.parseInt( request.getParameter( "hintUsed") );
        int hintNumber = 1;
        hintNumber -= hintUsed;
        return ResponseEntity.ok("4, blueKnight.jpg, 11, #00e318, blueKnight.jpg, 2, #00e318, blueKnight.jpg, 11, #00e318, blueKnight.jpg, 2, #00e318, 2, 23, 35, 1, 25, 2, 52, 15, " + level + ", 1, 1, 1, 3, 5, " + hintNumber);
    }

    @GetMapping("/HighscoresInfo")
    public ResponseEntity<String> HighscoresInfo( HttpServletRequest request, HttpServletResponse response ){
        return ResponseEntity.ok( runner.getHighscores( UserSession ) );
    }


    @GetMapping("/getMessage")
        public ResponseEntity<String> getMessage(HttpServletRequest request, HttpServletResponse response ){
            return ResponseEntity.ok(message);
    }

    @GetMapping("/newUserMap")
    public ResponseEntity<String> newUserMap(HttpServletRequest request, HttpServletResponse response ) throws Exception {
        int redKnightNumber = Integer.parseInt( request.getParameter("redKnightNumber" ) );
        int blueKnightNumber = Integer.parseInt( request.getParameter("blueKnightNumber" ) );
        int whiteKnightNumber = Integer.parseInt( request.getParameter("whiteKnightNumber" ) );
        String redKnightLocation = request.getParameter("redKnightLocation" );
        String blueKnightLocation = request.getParameter("blueKnightLocation" );
        String whiteKnightLocation = request.getParameter("whiteKnightLocation" );
        int wallNumber = Integer.parseInt( request.getParameter("wallNumber" ) );
        String wallInfos = request.getParameter( "wallInfo" ); // x1 y1 dir1reverse x2 y2 dir2reverse

        System.out.println("red knights : " + redKnightNumber);
        System.out.println("blue knights : " + blueKnightNumber);
        System.out.println("white knights : " + whiteKnightNumber);
        System.out.println("red knights LOCATIONS : " + redKnightLocation);
        System.out.println("blue knights LOCATIONS : " + blueKnightLocation);
        System.out.println("white knights LOCATIONS : " + whiteKnightLocation);
        System.out.println("wall number : " + wallNumber);
        System.out.println("WALL LOCATIONS : " + wallInfos);

        runner.submitMap( wallInfos , redKnightNumber , redKnightLocation , blueKnightNumber, blueKnightLocation , whiteKnightNumber, whiteKnightLocation );

        return ResponseEntity.ok("");
    }

    @GetMapping("/arcadeNewLevel")
    public ResponseEntity<String> arcadeNewLevel(HttpServletRequest request, HttpServletResponse response ) throws Exception {
        int level = Integer.parseInt( request.getParameter("level" ) );
        int score = Integer.parseInt( request.getParameter("score" ) );
        int hintUsed = Integer.parseInt( request.getParameter( "hintUsed") );

        runner.setNewArcadeLevelForUser( UserSession , level, score+1 , hintUsed );

        System.out.println("level : " + level + " score : " + (score+1) + " hintUsed : " + hintUsed);

        return ResponseEntity.ok( runner.getNextMapForUser( UserSession ) );
        //return ResponseEntity.ok("4, 11.png, 11, #00e318, 14.png, 14, #00e318, 11.png, 11, #00e318, 23.png, 23, #00e318, 2, 23, 35, 1, 25, 0, " + level + ", 1, 1, 1, 3, 5, " + hintNumber);
    }

    @GetMapping("/arcadeFirstCall")
    public ResponseEntity<String> arcadeFirstCall(HttpServletRequest request, HttpServletResponse response ) throws Exception {
        return ResponseEntity.ok( runner.getNextMapForUser( UserSession ) );
        //return ResponseEntity.ok("4, 1.png, 1, #00e318, 1.png, 1, #C0C0C0, 1.png, 1, #FF0000, 1.png, 1, #000000, 2, 23, 35, 1, 25, 0, 1, 2, 1, 1, 3, 5, 1, 1, 3, 5, 1");
    }

    @GetMapping("/arcadeTimeNewLevel")
    public ResponseEntity<String> arcadeTimeNewLevel(HttpServletRequest request, HttpServletResponse response ) throws Exception {
        int level = Integer.parseInt( request.getParameter("level" ) );
        int score = Integer.parseInt( request.getParameter("score" ) );
        int hintUsed = Integer.parseInt( request.getParameter( "hintUsed") );

        runner.setNewTimeChallengeLevelForUser( UserSession , level, score+1 , hintUsed );

        System.out.println("level : " + level + " score : " + (score+1) + " hintUsed : " + hintUsed);

        return ResponseEntity.ok( runner.getNextMapForUser( UserSession ) );

        //return ResponseEntity.ok("4, blueKnight.jpg, 11, #00e318, blueKnight.jpg, 2, #00e318, blueKnight.jpg, 11, #00e318, blueKnight.jpg, 2, #00e318, 2, 23, 35, 1, 25, 0, " + level + ", 1, 1, 1, 3, 5, " + hintNumber);
    }

    @GetMapping("/arcadeTimeFirstCall")
    public ResponseEntity<String> arcadeTimeFirstCall(HttpServletRequest request, HttpServletResponse response ) throws Exception {
        return ResponseEntity.ok( runner.getNextMapForUser( UserSession ) );
        //return ResponseEntity.ok("4, redKnight.jpg, 1, #00e318, redKnight.jpg, 1, #C0C0C0, redKnight.jpg, 1, #FF0000, redKnight.jpg, 1, #000000, 2, 23, 35, 1, 25, 0, 1, 1, 1, 1, 3, 5, 0");
    }

    @GetMapping("/whiteArcadeNewLevel")
    public ResponseEntity<String> whiteArcadeNewLevel(HttpServletRequest request, HttpServletResponse response ) throws Exception {
        int level = Integer.parseInt( request.getParameter("level" ) );
        int score = Integer.parseInt( request.getParameter("score" ) );
        int hintUsed = Integer.parseInt( request.getParameter( "hintUsed") );
        runner.setNewArcadeWhiteKnightLevelForUser( UserSession , level, score+1 , hintUsed );
        System.out.println("level : " + level + " score : " + (score+1) + " hintUsed : " + hintUsed);
        return ResponseEntity.ok( runner.getNextWhiteKnightMapForUser( UserSession ) );
        //return ResponseEntity.ok("4, blueKnight.jpg, 11, #00e318, blueKnight.jpg, 2, #00e318, blueKnight.jpg, 11, #00e318, blueKnight.jpg, 2, #00e318, 2, 23, 45, 1, 25, 1, 15, " + level + ", 1, 1, 1, 3, 5, " + hintNumber);
    }

    @GetMapping("/whiteArcadeFirstCall")
    public ResponseEntity<String> whiteArcadeFirstCall(HttpServletRequest request, HttpServletResponse response ) throws Exception {
        return ResponseEntity.ok( runner.getNextWhiteKnightMapForUser( UserSession ) );
        //return ResponseEntity.ok("4, redKnight.jpg, 1, #00e318, redKnight.jpg, 1, #C0C0C0, redKnight.jpg, 1, #FF0000, redKnight.jpg, 1, #000000, 2, 23, 35, 1, 25, 1, 53, 1, 1, 1, 1, 3, 5, 0");
    }

    @GetMapping("/whiteArcadeTimeNewLevel")
    public ResponseEntity<String> whiteArcadeTimeNewLevel(HttpServletRequest request, HttpServletResponse response ) throws Exception {
        int level = Integer.parseInt( request.getParameter("level" ) );
        int score = Integer.parseInt( request.getParameter("score" ) );
        int hintUsed = Integer.parseInt( request.getParameter( "hintUsed") );
        runner.setNewTimeChallengeWhiteKnightLevelForUser( UserSession , level, score+1 , hintUsed );
        System.out.println("level : " + level + " score : " + (score+1) + " hintUsed : " + hintUsed);
        return ResponseEntity.ok( runner.getNextWhiteKnightMapForUser( UserSession ) );
        //return ResponseEntity.ok("4, blueKnight.jpg, 11, #00e318, blueKnight.jpg, 2, #00e318, blueKnight.jpg, 11, #00e318, blueKnight.jpg, 2, #00e318, 2, 23, 35, 1, 25, 1, 51, " + level + ", 1, 1, 1, 3, 5, " + hintNumber);
    }

    @GetMapping("/whiteArcadeTimeFirstCall")
    public ResponseEntity<String> whiteArcadeTimeFirstCall(HttpServletRequest request, HttpServletResponse response ) throws Exception {
        return ResponseEntity.ok( runner.getNextWhiteKnightMapForUser( UserSession ) );
        //return ResponseEntity.ok("4, redKnight.jpg, 1, #00e318, redKnight.jpg, 1, #C0C0C0, redKnight.jpg, 1, #FF0000, redKnight.jpg, 1, #000000, 2, 23, 35, 1, 25, 1, 53, 1, 1, 1, 1, 3, 5, 0");
    }

}

