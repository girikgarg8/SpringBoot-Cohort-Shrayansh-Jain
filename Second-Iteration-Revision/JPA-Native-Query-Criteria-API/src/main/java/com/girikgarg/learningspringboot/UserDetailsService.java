package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsService {
    
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public UserDetails saveUser(UserDetails userDetails) {
        return userDetailsRepository.save(userDetails);
    }
    
    public Optional<UserDetails> getUser(Long id) {
        return userDetailsRepository.findById(id);
    }
    
    public List<UserDetails> getAllUsers() {
        return userDetailsRepository.findAll();
    }
    
    public void deleteUser(Long id) {
        userDetailsRepository.deleteById(id);
    }
    
    // Native Query with all fields - works fine
    public List<UserDetails> getUsersByNameNative(String name) {
        return userDetailsRepository.getUserDetailsByNameNativeQuery(name);
    }
    
    // Native Query with partial fields - WILL THROW ERROR
    public List<UserDetails> getUsersByNameNativePartial(String name) {
        return userDetailsRepository.getUserDetailsByNameNativeQueryPartial(name);
    }
    
    // Solution 1: Using @SqlResultSetMapping with @NamedNativeQuery
    public List<UserDTO> getUsersByNameWithMapping(String name) {
        return userDetailsRepository.getUserDetailsByName(name);
    }
    
    // Solution 2: Manual mapping from Object[] to DTO
    public List<UserDTO> getUsersByNameManualMapping(String name) {
        List<Object[]> results = userDetailsRepository.getUserDetailsByNameNativeQueryManual(name);
        return results.stream()
                .map(obj -> new UserDTO((String) obj[0], (String) obj[1]))
                .collect(Collectors.toList());
    }
    
    // Dynamic Native Query using EntityManager
    public List<UserDTOExtended> getUserDetailsByNameNativeQuery(String userName) {
        StringBuilder queryBuilder = new StringBuilder("SELECT ud.user_name AS user_name, ud.phone AS phone, ua.city AS city ");
        queryBuilder.append("FROM user_details ud ");
        queryBuilder.append("JOIN user_address ua ON ud.address_id = ua.address_id ");
        queryBuilder.append("WHERE 1=1 ");
        
        List<Object> parameters = new ArrayList<>();
        
        if (userName != null && !userName.isEmpty()) {
            queryBuilder.append("AND ud.user_name = ? ");
            parameters.add(userName);
        }
        
        Query nativeQuery = entityManager.createNativeQuery(queryBuilder.toString());
        
        for (int i = 0; i < parameters.size(); i++) {
            nativeQuery.setParameter(i + 1, parameters.get(i));
        }
        
        List<Object[]> result = nativeQuery.getResultList();
        
        return mapResultToDTO(result);
    }
    
    private List<UserDTOExtended> mapResultToDTO(List<Object[]> result) {
        return result.stream()
                .map(obj -> new UserDTOExtended((String) obj[0], (String) obj[1], (String) obj[2]))
                .collect(Collectors.toList());
    }
    
    // Way 1: Manual Pagination and Sorting with EntityManager
    public List<UserDTOExtended> getUserDetailsByNameNativeQueryPaginated(String userName, int page, int size) {
        StringBuilder queryBuilder = new StringBuilder("SELECT ud.user_name AS user_name, ud.phone AS phone, ua.city AS city ");
        queryBuilder.append("FROM user_details ud ");
        queryBuilder.append("JOIN user_address ua ON ud.address_id = ua.address_id ");
        queryBuilder.append("WHERE 1=1 ");
        
        List<Object> parameters = new ArrayList<>();
        
        if (userName != null && !userName.isEmpty()) {
            queryBuilder.append("AND ud.user_name = ? ");
            parameters.add(userName);
        }
        
        // Sorting
        queryBuilder.append("ORDER BY ud.user_name DESC ");
        
        // Pagination
        queryBuilder.append("LIMIT ? OFFSET ?");
        int size1 = size;
        int page1 = page;
        parameters.add(size1);
        parameters.add(page1 * size1);
        
        Query nativeQuery = entityManager.createNativeQuery(queryBuilder.toString());
        
        for (int i = 0; i < parameters.size(); i++) {
            nativeQuery.setParameter(i + 1, parameters.get(i));
        }
        
        List<Object[]> result = nativeQuery.getResultList();
        
        return mapResultToDTO(result);
    }
    
    // Way 2: Using Spring Data JPA Pageable with Native Query
    public List<UserDetails> getUserDetailsByNameNativeQueryPageable(String name, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "phone"));
        return userDetailsRepository.getUserDetailsByNameNativeQuery(name, pageable);
    }
    
    // Criteria API - Type-safe query building
    public List<UserDetails> getUserDetailsByPhoneCriteriaAPI(String phoneNo) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<UserDetails> crQuery = cb.createQuery(UserDetails.class);
        
        Root<UserDetails> user = crQuery.from(UserDetails.class);
        
        crQuery.select(user);
        
        Predicate predicate = cb.equal(user.get("phone"), phoneNo);
        crQuery.where(predicate);
        
        TypedQuery<UserDetails> query = entityManager.createQuery(crQuery);
        List<UserDetails> output = query.getResultList();
        
        return output;
    }
    
    // Criteria API - Select Multiple Fields
    public List<UserDTO> getUserDetailsByPhoneCriteriaAPIMultiSelect(String phoneNo) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<Object[]> crQuery = cb.createQuery(Object[].class);
        
        Root<UserDetails> user = crQuery.from(UserDetails.class);
        
        crQuery.multiselect(user.get("name"), user.get("phone"));
        
        Predicate predicate1 = cb.equal(user.get("phone"), phoneNo);
        crQuery.where(predicate1);
        
        TypedQuery<Object[]> query = entityManager.createQuery(crQuery);
        List<Object[]> results = query.getResultList();
        
        List<UserDTO> output = new ArrayList<>();
        for (Object[] row : results) {
            String name = (String) row[0];
            String phone = (String) row[1];
            UserDTO result = new UserDTO(name, phone);
            output.add(result);
        }
        
        return output;
    }
    
    // Criteria API - Join
    public List<UserDTO> getUserDetailsByPhoneCriteriaAPIJoin(String phoneNo) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<Object[]> crQuery = cb.createQuery(Object[].class);
        
        Root<UserDetails> user = crQuery.from(UserDetails.class);
        
        Join<UserDetails, UserAddress> address = user.join("userAddress", JoinType.INNER);
        
        crQuery.multiselect(user.get("name"), address.get("city"));
        
        Predicate predicate1 = cb.equal(user.get("phone"), phoneNo);
        crQuery.where(predicate1);
        
        TypedQuery<Object[]> query = entityManager.createQuery(crQuery);
        List<Object[]> results = query.getResultList();
        
        List<UserDTO> output = new ArrayList<>();
        for (Object[] row : results) {
            String name = (String) row[0];
            String city = (String) row[1];
            UserDTO result = new UserDTO(name, city);
            output.add(result);
        }
        
        return output;
    }
    
    // Criteria API - Pagination and Sorting
    public List<UserDetails> getUserDetailsByPhoneCriteriaAPIPaginated(String phoneNo, int page, int size) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        
        CriteriaQuery<UserDetails> crQuery = cb.createQuery(UserDetails.class);
        
        Root<UserDetails> user = crQuery.from(UserDetails.class);
        
        crQuery.select(user);
        
        Predicate predicate1 = cb.equal(user.get("phone"), phoneNo);
        crQuery.where(predicate1);
        
        crQuery.orderBy(cb.desc(user.get("name")));
        
        TypedQuery<UserDetails> query = entityManager.createQuery(crQuery);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        
        List<UserDetails> results = query.getResultList();
        
        return results;
    }
}

