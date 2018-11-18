package hello;

import hello.Models.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.FormParam;
import java.sql.SQLException;

@Controller
public class MainController {

    private SqlRunner runner;

    public MainController() throws SQLException {
        runner = new SqlRunner();
    }

    @RequestMapping("/INITPAGE")
    public String init( Model model ){

        return "INITPAGE";
    }

    @RequestMapping("/MAINMENU")
    public String mainmenu( Model model ){
        return "MAINMENU";
    }

    @GetMapping("/LOGIN")
    public String login( Model model ){
        return "LOGIN";
    }

    @GetMapping("/SIGNUPPAGE")
    public String signUp( Model model ){
        return "SIGNUPPAGE";
    }

    @GetMapping("/PlayMenuPage")
    public String playMenu( Model model ){
        return "PlayMenuPage";
    }

    @GetMapping("/GAMEPLAY")
    public String gameplay( Model model ){
        return "GAMEPLAY";
    }

    @PostMapping("/submit_signup")
    public String submit_signup(@FormParam("username") String username, @FormParam("password") String password, @FormParam("password2") String password2 , Model model ) throws SQLException {
        return "LOGIN";
    }

    @PostMapping("/submit_login")
    public String submit_login(@FormParam("username") String username, @FormParam("password") String password , Model model ) throws SQLException {
        return "MAINMENU";
    }


//    @GetMapping("/error")
//    public String errorPage( Model model ){
//        return "error";
//    }

}

