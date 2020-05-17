package io.project.edoctor.model.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;

    private String password;

    private String role;

    @OneToOne(mappedBy = "userData")
    private UserData userData;

    @OneToMany
    private Set<UserInterview> interviews;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public Set<UserInterview> getInterviews() {
        return interviews;
    }

    public void setInterviews(Set<UserInterview> interviews) {
        this.interviews = interviews;
    }
}

