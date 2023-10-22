package com.task.tracker.controller.login;

import com.task.tracker.config.TokenGenerator;
import com.task.tracker.dto.AuthResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("v1/refresh-token")
public class TokenRefreshController {

	@Autowired
	private TokenGenerator tokenGenerator;

	@PostMapping("")
	public ResponseEntity<?> refreshJwtToken(@RequestHeader("Authorization") String oldToken, HttpServletRequest request){
		try {
			if(StringUtils.hasText(oldToken) && oldToken.startsWith("Bearer ")) {
				oldToken =  oldToken.substring(7);
			}else {
				throw new AuthenticationCredentialsNotFoundException("JWT token provided is incorrect");
			}
			String jwtToken = tokenGenerator.refreshToken(oldToken, request);
			Date expiresAt = tokenGenerator.getExpireAtFromJWT(jwtToken);
			return ResponseEntity.status(HttpStatus.OK).body(new AuthResponseDTO(jwtToken, expiresAt));
		} catch (Exception ex){
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("An error occurred in server");
		}
	}
}
