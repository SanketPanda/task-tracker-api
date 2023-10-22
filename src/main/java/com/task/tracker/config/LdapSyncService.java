package com.task.tracker.config;

import com.task.tracker.dao.RefUserRepository;
import com.task.tracker.model.RefUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.naming.NamingException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class LdapSyncService {

    @Autowired
    LdapTemplate ldapTemplate;

    @Autowired
    RefUserRepository refUserRepository;

    @Value("${ldap.base.dn}")
    private String ldapBaseDn;

    @Value("${user.displayName}")
    private String displayName;

    @Value("${user.userId}")
    private String userId;

    @Transactional
    public void syncLdapDataToTable(){
        List<RefUser> users = ldapTemplate.search(
          "","(objectClass=person)",
                (AttributesMapper<RefUser>) attrs -> {
                    RefUser user = new RefUser();
                    if(attrs.get(displayName) != null && attrs.get(userId) != null){
                        user.setUserId((String) attrs.get(userId).get());
                        user.setUserName((String) attrs.get(displayName).get());
                        user.setLastSyncDate(new Date().toInstant());
                        addUserEntitlements(user);
                        refUserRepository.save(user);
                    }
                    return user;
                }
        );
    }

    @Scheduled(fixedRateString = "3600000")
    public void syncLdapData(){
        syncLdapDataToTable();
    }

    private void addUserEntitlements(RefUser refUser) throws NamingException {
        refUser.setRoles("ROLE_USER");
        if(refUser.getUserName().equalsIgnoreCase("Task Tracker 1") || refUser.getUserName().equalsIgnoreCase("Sanket Panda")){
            refUser.setRoles(refUser.getRoles() + ",ROLE_ADMIN");
        }
        /*
        DirContextOperations context = ldapTemplate.lookupContext("cn="+TIMEAPP_ADMIN_GROUP);
        Attribute uniqueMemberAttribute = context.getAttributes().get("member");
        if (uniqueMemberAttribute != null) {
            NamingEnumeration<?> attributeValues = uniqueMemberAttribute.getAll();

            while (attributeValues.hasMore()) {
                String uniqueMember = attributeValues.next().toString();
                if (uniqueMember.equalsIgnoreCase("cn=" + refUser.getUserName() + "," + ldapBaseDn)) {
                    refUser.setRoles(refUser.getRoles() + ",ROLE_ADMIN");
                    break; // User found in the group, no need to check further
                }
            }
        }
         */
    }
}
