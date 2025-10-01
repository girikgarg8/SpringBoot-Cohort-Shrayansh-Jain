package com.girikgarg.learningspringboot;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public User create(User user) {
        return userRepository.save(user);
    }

    public UserDTO findByUsernameWithNativeQuery(String name) {
        return userRepository.findByUsernameWithNativeQuery(name);
    }

    public List <UserDTO> getUserDetailsByNameFromNativeQuery(String name) {
        List <Object[]> results = userRepository.getUserDetailsByNameWithNativeQuery(name, PageRequest.of(0,5, Sort.by("user_name").descending()));
        return results.stream().map(obj -> new UserDTO((String) obj[0])).collect(Collectors.toList());
    }

    public List <UserDTO> getUserDetailsByNameNativeQuery(String userName) {
        StringBuilder queryBuilder = new StringBuilder(" SELECT u.user_name AS user_name ");
        queryBuilder.append(" FROM user_details u ");
        queryBuilder.append(" WHERE 1=1 ");

        List <Object> parameters = new ArrayList<>();

        // dynamically add conditions

        if (!StringUtils.isEmpty(userName)) {
            queryBuilder.append(" AND u.user_name = ? ");
            parameters.add(userName);
        }

        /**
         * Till this point the query
         * SELECT u.user_name AS user_name FROM USER u WHERE 1=1 AND u.user_name = ? is generated
         *
         * Now we need to replace the ? with the actual parameter values
         */
        // sorting
        queryBuilder.append(" ORDER BY ").append("u.user_name").append(" DESC");

        // pagination
        int size = 5;
        int page = 0;
        queryBuilder.append(" LIMIT ? OFFSET ?");
        parameters.add(size);
        parameters.add(page * size);

        // Create the native query
        Query nativeQuery = entityManager.createNativeQuery(queryBuilder.toString());

        // Set the parameters for query
        for (int i = 0; i < parameters.size(); i++) {
            nativeQuery.setParameter(i + 1, parameters.get(i));
        }

        // Execute and get results
        List <String> result = nativeQuery.getResultList();

        return result.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    public List <User> getUserDetailsByName(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class); // what my each row would look like, so in this case each row would be User
        Root<User> user = criteriaQuery.from(User.class); // The FROM clause in the query, FROM user_details
        criteriaQuery.select(user); // SELECT *

        Predicate predicate = cb.equal(user.get("name"), name); // WHERE user_name = <<name>>
        criteriaQuery.where(predicate);

        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
        List <User> result = typedQuery.getResultList();
        return result;
    }

    public List <UserDTO> findUserDetailsByName(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = cb.createQuery(Object[].class); // what my each row from result set would look like

        Root <User> user = criteriaQuery.from(User.class); // FROM clause

        criteriaQuery.multiselect(user.get("name"), user.get("id")); // SELECT multiple fields

        Predicate predicate = cb.equal(user.get("name"), name);
        criteriaQuery.where(predicate);

        criteriaQuery.orderBy(cb.desc(user.get("id")));

        TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(0); //offset
        typedQuery.setMaxResults(5); // limit

        List <Object[]> result = typedQuery.getResultList();

        // Processing results
        List <UserDTO> userDTOList = new ArrayList<>();
        for (Object [] row: result) {
            String userName = (String) row[0];
            Long id = (Long) row[1];

            UserDTO userDTO = new UserDTO(userName);
            userDTOList.add(userDTO);
        }
        return userDTOList;
    }

    public List <User> getUserDetailsByNameWithSpecification(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = cb.createQuery(User.class); // what my each row would look like, so in this case each row would be User
        Root<User> user = criteriaQuery.from(User.class); // The FROM clause in the query, FROM user_details
        criteriaQuery.select(user); // SELECT *

        Specification <User> specification = UserSpecification.equalsName(name);
        Predicate predicate = specification.toPredicate(user, criteriaQuery, cb);
        criteriaQuery.where(predicate);

        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
        List <User> result = typedQuery.getResultList();
        return result;
    }

    public List <User> getUserDetailsByNameSpecification(String name, Long id) {
        Specification<User> specification = Specification.where(UserSpecification.equalsName(name)).and(UserSpecification.equalsId(id));
        return userRepository.findAll(specification);
    }
}



