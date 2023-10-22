package com.task.tracker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.util.HashSet;
import java.util.Set;

@Component
public class CustomLdapUserDetailsMapper implements AttributesMapper<UserDetails> {

	@Value("${ldap.base.dn}")
	private String ldapBaseDn;

	@Value("${user.displayName}")
	private String displayName;

	@Value("${user.userId}")
	private String userId;

	@Autowired
	LdapTemplate ldapTemplate;

	@Override
	public UserDetails mapFromAttributes(Attributes attributes) throws NamingException {
		String username = (String) attributes.get(userId).get();
		String fullName = (String) attributes.get(displayName).get();
		String password = "{noop}";

		return User.withUsername(username)
				.password(password)
				.authorities(addUserEntitlements(fullName))
				.build();
	}

	private Set<GrantedAuthority> addUserEntitlements(String userName) throws NamingException {
		Set<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		if(userName.equalsIgnoreCase("Task Tracker 1") || userName.equalsIgnoreCase("Sanket Panda")){
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		/*
		DirContextOperations context = ldapTemplate.lookupContext("cn="+TIMEAPP_ADMIN_GROUP);
		Attribute uniqueMemberAttribute = context.getAttributes().get("member");
		if (uniqueMemberAttribute != null) {
			NamingEnumeration<?> attributeValues = uniqueMemberAttribute.getAll();
			while (attributeValues.hasMore()) {
				String uniqueMember = attributeValues.next().toString();
				if (uniqueMember.equalsIgnoreCase("cn=" + userName + "," + ldapBaseDn)) {
					authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
					return authorities;
				}
			}
		}
		 */
		return authorities;
	}
}
