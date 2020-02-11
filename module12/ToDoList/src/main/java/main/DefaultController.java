package main;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class DefaultController {

    @RequestMapping("/")
    public String index(){
        return "Current Date: " + (new Date()).toString();
    }
}
