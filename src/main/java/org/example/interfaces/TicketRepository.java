package org.example.interfaces;

import org.example.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT t FROM Ticket t WHERE t.user.id = :userId")
    List<Ticket> findTicketsByUserId(@Param("userId") Long userId);

    void deleteByUser_Id(Long userId);

    List<Ticket> findByTicketType(org.example.models.enums.TicketType ticketType);
}
