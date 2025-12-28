package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        // For bidirectional relationships, we need to set both sides
        if (user.getOrders() != null) {
            for (Order order : user.getOrders()) {
                order.setUser(user); //IMPORTANT:  Set the owning side
            }
        }
        return userRepository.save(user);
    }

    public User getUser(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public User testOrphanRemoval(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        
        if (user != null && !user.getOrders().isEmpty()) {
            user.getOrders().remove(0);
            return userRepository.save(user);
        }
        
        return user;
    }

    // ❌ WRONG WAY: Only setting inverse side (User.orders)
    // This will NOT save the relationship to the database!
    @Transactional
    public User addOrderWrongWay(Long userId, String productName) {
        User user = userRepository.findById(userId).orElse(null);
        
        if (user != null) {
            Order order = new Order(productName);
            user.getOrders().add(order);  // Only setting inverse side
            // NOT calling order.setUser(user) - This is the problem!
            
            return userRepository.save(user);
        }
        
        return user;
    }

    // ✅ CORRECT WAY: Setting both sides of the relationship
    // This WILL save the relationship to the database!
    @Transactional
    public User addOrderCorrectWay(Long userId, String productName) {
        User user = userRepository.findById(userId).orElse(null);
        
        if (user != null) {
            Order order = new Order(productName);
            user.addOrder(order);  // Using helper method that sets both sides
            // This internally calls:
            // 1. user.orders.add(order)
            // 2. order.setUser(user) ← CRITICAL for persistence!
            
            return userRepository.save(user);
        }
        
        return user;
    }
}


