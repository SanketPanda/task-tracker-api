package com.task.tracker.config;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.search.LdapUserSearch;
import org.springframework.security.ldap.userdetails.LdapUserDetailsService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CustomUserDetailsService extends LdapUserDetailsService  {

	@Autowired
	LdapTemplate ldapTemplate;

	@Autowired
	CustomLdapUserDetailsMapper customLdapUserDetailsMapper;

	@Value("${user.userNameAttribute}")
	private String usernameAttribute;

	public CustomUserDetailsService(LdapUserSearch userSearch) {
		super(userSearch);
	}

	@SneakyThrows
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String searchFilter = String.format(usernameAttribute, username);
		List<UserDetails> users = ldapTemplate.search("", searchFilter, customLdapUserDetailsMapper);
		if(users.size() == 1){
			return users.get(0);
		}
		throw new UsernameNotFoundException("User not found");
	}
}
