package com.example.workouttracker.Repository;

import com.example.workouttracker.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);
    Boolean existsUserByUsernameAndPasswordAndEmail( String username,  String password, String email);

    User findByUsername(String username);
}
