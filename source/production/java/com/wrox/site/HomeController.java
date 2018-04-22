package com.wrox.site;

import com.wrox.config.annotation.WebController;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@WebController
public class HomeController
{
	private static final Logger log = LogManager.getLogger();
	
    @Inject ApplicationEventPublisher publisher;

    @RequestMapping("")
    public String login(HttpServletRequest request)
    {
    		//add some comments
    		log.warn("*****publish event in controller...");
        this.publisher.publishEvent(new LoginEvent(request.getContextPath()));
        System.out.println("print sth. to test github");
        System.out.println("print sth. more to test");
        System.out.println("add one more line to test");
        System.out.println("let's test one more line to see what happens");
        //add dome comments to test
        log.warn("*****finish publish event in controller");
        //add more comments
        return "login";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request)
    {
        this.publisher.publishEvent(new LogoutEvent(request.getContextPath()));
        return "logout";
    }

    @RequestMapping("/ping")
    public ResponseEntity<String> ping()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/plain;charset=UTF-8");
        return new ResponseEntity<>("ok", headers, HttpStatus.OK);
    }
}
