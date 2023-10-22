package com.task.tracker.controller.login;

import com.task.tracker.dto.AuthResponseDTO;
import com.task.tracker.dto.error.ErrorDetails;
import com.task.tracker.dto.refuser.RefUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.task.tracker.config.TokenGenerator;

import java.util.Date;

@RestController
@RequestMapping("v1/login")
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenGenerator tokenGenerator;

    @Autowired
    LdapTemplate ldapTemplate;

    @Autowired
    private ActiveDirectoryLdapAuthenticationProvider activeDirectoryLdapAuthenticationProvider;

    @Autowired
    private LdapAuthenticationProvider ldapAuthenticationProvider;

    @PostMapping("")
    public ResponseEntity<?> login(@RequestBody final RefUserDTO refUserDTO) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(refUserDTO.getUserId(),
                refUserDTO.getPassword());
        try {
            Authentication authenticated = ldapAuthenticationProvider.authenticate(authentication);
            if(!authenticated.isAuthenticated()){
                throw new BadCredentialsException("Bad credential.");
            }

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            ErrorDetails errorDetails = new ErrorDetails("Invalid credentials", HttpStatus.NOT_ACCEPTABLE.toString());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetails);
        }
        String jwtToken = tokenGenerator.generateToken(refUserDTO.getUserId());
        Date expiresAt = tokenGenerator.getExpireAtFromJWT(jwtToken);
        return ResponseEntity.status(HttpStatus.OK).body(new AuthResponseDTO(jwtToken, expiresAt));
    }
}
