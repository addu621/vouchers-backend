package com.example.server.controllers;

import com.example.server.entities.User;
import com.example.server.entities.UserDto;
import com.example.server.model.JwtRequest;
import com.example.server.model.JwtResponse;
import com.example.server.model.JwtUtil;
import com.example.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
public class JwtController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    //web security test api
    @RequestMapping("/welcome")
    public String welcome() {
        return "User authorized";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userService.loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDto user) throws Exception {
        return ResponseEntity.ok(userService.save(user));
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

//    @RequestMapping(value = "/login", method = RequestMethod.GET)
//    public ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest) throws Exception {
//        System.out.println(jwtRequest);
//        try{
//            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword()));
//        }catch (UsernameNotFoundException e) {
//            e.printStackTrace();
//            throw new Exception("Bad Credentials");
//        }catch (BadCredentialsException e) {
//            e.printStackTrace();
//            throw new Exception("Bad Credentials");
//        }
//
//        UserDetails userDetails = this.userService.loadUserByUsername(jwtRequest.getUsername());
//
//        String token = this.jwtUtil.generateToken(userDetails);
//
//        System.out.println("jwt token" + token);
//
//        return ResponseEntity.ok(new JwtResponse(token));
//    }


//        @GetMapping(value = "/login")
//        public Map generateAuthenticationToken(@RequestHeader String loginCredentials) throws Exception {
//
//            String userLoginCredentials[]=loginCredentials.split(":");
//        try {
//            authenticate(userLoginCredentials[0], userLoginCredentials[1]);
//
//            UserDetails userDetails = userService.loadUserByUsername(userLoginCredentials[0]);
//
//            Map<String, String> mp = new HashMap<>();
//
//            String token = jwtUtil.generateToken(userDetails);
//
//
//            mp.put("token", token);
//            mp.put("userEmail", userLoginCredentials[0]);
//
//            return mp;
//        }
//        catch (Exception e)
//        {
//            Map<String, String> mp = new HashMap<>();
//            mp.put("Error", "Invalid Credentials");
//            return mp;
//        }
//        }
//
//        private void authenticate(String username, String password) throws Exception {
//        //        Objects.requireNonNull(username);
//        //        Objects.requireNonNull(password);
//            try {
//                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//            }catch (DisabledException e) {
//                throw new Exception("USER_DISABLED", e);
//            } catch (BadCredentialsException e) {
//                throw new Exception("INVALID_CREDENTIALS", e);
//            }
//        }
}
