package com.girikgarg.learningspringboot;

public class OrderDTO {
    
    private Long userID;
    private Long orderID;
    
    public OrderDTO() {
    }
    
    public OrderDTO(Long userID, Long orderID) {
        this.userID = userID;
        this.orderID = orderID;
    }
    
    public Long getUserID() {
        return userID;
    }
    
    public void setUserID(Long userID) {
        this.userID = userID;
    }
    
    public Long getOrderID() {
        return orderID;
    }
    
    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }
}



