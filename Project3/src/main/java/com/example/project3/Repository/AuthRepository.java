package com.example.project3.Repository;

import com.example.project3.Model.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<User, Integer> {

    User findUsersByUsername(String username);


    User findUsersById(Integer id);

    User findUfserById(Integer id);
}
