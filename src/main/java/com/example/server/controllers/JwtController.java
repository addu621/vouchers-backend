package com.example.server.controllers;

import com.example.server.entities.Person;
import com.example.server.model.JwtResponse;
import com.example.server.model.JwtUtil;
import com.example.server.repositories.PersonRepo;
import com.example.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class JwtController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PersonRepo personRepo;

    //web security test api
    @RequestMapping("/welcome")
    public String welcome() {
        return "User authorized";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map createAuthenticationToken(@RequestBody Person authenticationRequest) throws Exception {

        Map<String, String> mp = new HashMap<>();

        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getEmail());

        final String token = jwtUtil.generateToken(userDetails);

        Person person = personRepo.findByEmail(authenticationRequest.getEmail());

        if(person==null) {
            mp.put("error", "Invalid Credentials");
        }

        mp.put("token", token);
        mp.put("isAdmin", person.getIsAdmin().toString());

        return mp;
    }

    @PostMapping("/signup")
    public Map signUp(@RequestBody Person person){
        System.out.println(person);
        return userService.save(person);
    }

    @PatchMapping("/otpVerify")
    public Map otpVerify(@RequestBody Map<String,String> mp) {
        return userService.otpVerify(mp.get("email"),mp.get("otp"));
    }

    @PatchMapping("/forgotPasswordReq")
    public Map forgotPasswordReq(@RequestBody Map<String,String> mp){
        return userService.sendForgotPasswordReq(mp.get("email"));
    }

    @PatchMapping("/updatePassword")
    public Map passwordUpdate(@RequestBody Map<String,String> mp) {
        return userService.updatePassword(mp.get("email"),mp.get("otp"),mp.get("newPassword"));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
