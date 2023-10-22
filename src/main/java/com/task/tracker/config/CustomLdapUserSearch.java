package com.task.tracker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.ldap.search.LdapUserSearch;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomLdapUserSearch implements LdapUserSearch {

	@Value("${ldap.user.search-pattern}")
	private String ldapUserSearchPattern;

	@Value("${ldap.base.dn}")
	private String ldapBaseDn;

	@Value("${user.userNameAttribute}")
	private String usernameAttribute;

	private final LdapTemplate ldapTemplate;

	@Autowired
	public CustomLdapUserSearch(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	@Override
	public DirContextOperations searchForUser(String username) throws UsernameNotFoundException {
		String searchBase = ldapBaseDn;
		String searchFilter = String.format(usernameAttribute, username);

		List<DirContextOperations> searchResults = ldapTemplate.search(searchBase, searchFilter, (ContextMapper<DirContextOperations>) ctx -> (DirContextOperations) ctx);

		if (!searchResults.isEmpty()) {
			return searchResults.get(0);
		}
		throw new UsernameNotFoundException("User not found");
	}


}
