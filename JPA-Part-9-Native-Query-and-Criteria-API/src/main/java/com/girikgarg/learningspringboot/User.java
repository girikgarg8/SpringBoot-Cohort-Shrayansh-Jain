package com.girikgarg.learningspringboot;

import jakarta.persistence.*;

@Entity
@Table(name = "user_details")
@NamedNativeQuery(
        name = "UserDetails.getUserDetailsByName",
        query = "SELECT user_name FROM user_details WHERE user_name = :userFirstName",
        resultSetMapping = "UserDTOMapping"
)
@SqlResultSetMapping(name="UserDTOMapping", classes = @ConstructorResult(
        targetClass = UserDTO.class,
        columns = {
                @ColumnResult(name="user_name", type = String.class),
        }
))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="user_name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
