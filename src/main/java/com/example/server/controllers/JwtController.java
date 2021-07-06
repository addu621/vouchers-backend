package com.example.server.controllers;

import com.example.server.dto.request.GoogleRequest;
import com.example.server.entities.BlockedUsers;
import com.example.server.entities.Person;
import com.example.server.model.JwtResponse;
import com.example.server.model.JwtUtil;
import com.example.server.repositories.BlockedUsersRepository;
import com.example.server.repositories.PersonRepo;
import com.example.server.services.AdminService;
import com.example.server.services.CartService;
import com.example.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Autowired
    private CartService cartService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private BlockedUsersRepository blockedUsersRepository;
    //web security test api
    @RequestMapping("/welcome")
    public String welcome() {
        return "User authorized";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map createAuthenticationToken(@RequestBody Person authenticationRequest) throws Exception {

        Map<String, String> mp = new HashMap<>();

        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        if(blockedUsersRepository.findByEmail(authenticationRequest.getEmail()) != null){
            mp.put("error","User is blocked!!!");
            return mp;
        }
        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getEmail());

        final String token = jwtUtil.generateToken(userDetails);

        Person person = personRepo.findByEmail(authenticationRequest.getEmail());

        if(person==null) {
            mp.put("error", "Invalid Credentials");
            return mp;
        }

        if(!person.getIsOtpVerified()){
            mp.put("Error","This user Email is not verified");
            return mp;
        }

        mp.put("token", token);
        mp.put("userId", person.getId().toString());
        mp.put("isAdmin", person.getIsAdmin().toString());

        return mp;
    }

    @PostMapping("/gAuth")
    public Map gAuth(@RequestBody GoogleRequest googleRequest) throws Exception {
        Map<String,String> mp = new HashMap<>();

        Person oldPerson = personRepo.findByEmail(googleRequest.getEmail());
        if(oldPerson==null)
        {
            Person person = new Person();
            person.setEmail(googleRequest.getEmail());
            person.setFirstName(googleRequest.getFirstName());
            person.setLastName(googleRequest.getLastName());
            person.setImageUrl(googleRequest.getPhotoUrl());
            person.setIsAdmin(false);
            person.setPassword(bCryptPasswordEncoder.encode(""));
            personRepo.save(person);
        }
        authenticate(googleRequest.getEmail(),"");

        final UserDetails userDetails = userService.loadUserByUsername(googleRequest.getEmail());

        final String token = jwtUtil.generateToken(userDetails);

        Person newPerson = personRepo.findByEmail(googleRequest.getEmail());

        if(newPerson==null){
            mp.put("error","Invalid Credentials");
        }

        mp.put("token", token);
        mp.put("userId", newPerson.getId().toString());
        mp.put("isAdmin", newPerson.getIsAdmin().toString());

        return mp;
    }

    @PostMapping("/signup")
    public Map signUp(@RequestBody Person person){
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
