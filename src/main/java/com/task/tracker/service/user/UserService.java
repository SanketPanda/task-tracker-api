package com.task.tracker.service.user;

import com.task.tracker.dao.RefUserRepository;
import com.task.tracker.dto.refuser.RefUserDTO;
import com.task.tracker.model.RefUser;
import com.task.tracker.service.user.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    RefUserRepository userRepository;

    @Autowired
    EntityManager entityManager;

    public RefUserDTO getUserDetailsById(final String userId){
        RefUser refUser = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        return UserMapper.INSTANCE.toDTO(refUser);
    }

    public List<RefUserDTO> getUserDetailsByName(final String userName){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RefUser> query = criteriaBuilder.createQuery(RefUser.class);
        Root<RefUser> userRoot = query.from(RefUser.class);

        Expression<String> usernameExpression = userRoot.get("userName");
        Expression<String> searchExpression = criteriaBuilder.literal("%" + userName + "%");

        query.select(userRoot)
                .where(criteriaBuilder.like(usernameExpression, searchExpression));

        List<RefUser> userList = entityManager.createQuery(query).getResultList();
        return userList.stream()
                .map(UserMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }
}
