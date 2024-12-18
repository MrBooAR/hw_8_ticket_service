package org.example.interfaces;

import org.example.models.User;
import org.example.models.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.tickets WHERE u.id = :id")
    Optional<User> findUserWithTicketsById(@Param("id") Long id);

    List<User> findByStatus(Status status);

    void deleteAll();

    @Query("UPDATE User u SET u.status = :status WHERE u.id = :id")
    void activateUser(@Param("id") Long userId, @Param("status") Status status);
}
