package org.example.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.example.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}

