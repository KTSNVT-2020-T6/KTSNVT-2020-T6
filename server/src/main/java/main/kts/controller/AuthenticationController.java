package main.kts.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.kts.dto.UserDTO;
import main.kts.dto.UserLoginDTO;
import main.kts.dto.UserTokenStateDTO;
import main.kts.helper.UserMapper;
import main.kts.model.User;
import main.kts.security.TokenUtils;
import main.kts.service.CustomUserDetailsService;
import main.kts.service.UserService;
import main.kts.verification_handler.OnAccessLinkEvent;

//Kontroler zaduzen za autentifikaciju korisnika
@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
	private ApplicationEventPublisher eventPublisher;

    @Autowired
    private UserService userService;

    private UserMapper userMapper;

    // Prvi endpoint koji pogadja korisnik kada se loguje.
    // Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
    @RequestMapping(value = "/log-in", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserLoginDTO authenticationRequest,
                                                                    HttpServletResponse response) {
       
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));
       
        // Ubaci korisnika u trenutni security kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // Kreiraj token za tog korisnika
        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getEmail()); // prijavljujemo se na sistem sa email adresom
        int expiresIn = tokenUtils.getExpiredIn();
        
        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenStateDTO(jwt, expiresIn));
    }

    // Endpoint za registraciju novog korisnika
    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<?> addUser(@RequestBody UserDTO userRequest, HttpServletRequest request) throws Exception {

        User existUser = this.userService.findByEmail(userRequest.getEmail());
        

        try {
        	if (existUser != null) {
                throw new Exception("Username already exists");
            }
            existUser = userService.create(userMapper.toEntity(userRequest));
            eventPublisher.publishEvent(new OnAccessLinkEvent(existUser,
					request.getLocale(), request.getContextPath()));
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userMapper.toDto(existUser), HttpStatus.CREATED);
    }
    
    // Endpoint za registraciju novog admina
    @RequestMapping(value = "/sign-up-admin", method = RequestMethod.POST)
    public ResponseEntity<?> addAdmin(@RequestBody UserDTO userRequest) throws Exception {

        User existUser = this.userService.findByEmail(userRequest.getEmail());
        

        try {
        	if (existUser != null) {
                throw new Exception("Username already exists");
            }
            existUser = userService.createAdmin(userMapper.toEntity(userRequest));
        } catch (Exception e) {
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(userMapper.toDto(existUser), HttpStatus.CREATED);
    }

    // U slucaju isteka vazenja JWT tokena, endpoint koji se poziva da se token osvezi
    @RequestMapping(value = "/refresh", method = RequestMethod.POST)
    public ResponseEntity<UserTokenStateDTO> refreshAuthenticationToken(HttpServletRequest request) {

        String token = tokenUtils.getToken(request);
        String username = this.tokenUtils.getUsernameFromToken(token);
        User user = (User) this.userDetailsService.loadUserByUsername(username);

        if (this.tokenUtils.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = tokenUtils.refreshToken(token);
            int expiresIn = tokenUtils.getExpiredIn();

            return ResponseEntity.ok(new UserTokenStateDTO(refreshedToken, expiresIn));
        } else {
            UserTokenStateDTO userTokenState = new UserTokenStateDTO();
            return ResponseEntity.badRequest().body(userTokenState);
        }
    }

    @RequestMapping(value = "/change-password", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('REGISTERED_USER', 'ADMIN')")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChanger passwordChanger) {
        userDetailsService.changePassword(passwordChanger.oldPassword, passwordChanger.newPassword);

        Map<String, String> result = new HashMap<>();
        result.put("result", "success");
        return ResponseEntity.accepted().body(result);
    }

    static class PasswordChanger {
        public String oldPassword;
        public String newPassword;
    }

    public AuthenticationController() {
        userMapper = new UserMapper();
    }
}
