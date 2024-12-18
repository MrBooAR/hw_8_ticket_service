import org.example.interfaces.TicketRepository;
import org.example.models.Ticket;
import org.example.models.User;
import org.example.models.enums.TicketType;
import org.example.services.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;


class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTickets_Positive() {
        Ticket ticket = new Ticket(null, TicketType.DAY, "STANDARD", LocalDate.now(), BigDecimal.TEN);
        when(ticketRepository.findAll()).thenReturn(List.of(ticket));

        List<Ticket> tickets = ticketService.getAllTickets();

        assertEquals(1, tickets.size());
        assertEquals(TicketType.DAY, tickets.get(0).getTicketType());
        verify(ticketRepository, times(1)).findAll();
    }

    @Test
    void testCreateTicket_Positive() {
        User user = new User("Alice");
        LocalDate date = LocalDate.of(2025, 1, 1);
        BigDecimal price = BigDecimal.valueOf(50);

        Ticket ticket = new Ticket(user, TicketType.DAY, "VIP", date, price);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket result = ticketService.createTicket(user, TicketType.DAY, "VIP", date, price);

        assertNotNull(result);
        assertEquals(TicketType.DAY, result.getTicketType());
        assertEquals("VIP", result.getTicketClass());
        verify(ticketRepository, times(1)).save(any(Ticket.class));
    }

    @Test
    void testGetAllTickets_EmptyList() {
        when(ticketRepository.findAll()).thenReturn(Collections.emptyList());

        List<Ticket> tickets = ticketService.getAllTickets();

        assertTrue(tickets.isEmpty());
        verify(ticketRepository, times(1)).findAll();
    }

    @Test
    void testDeleteTicket() {
        long ticketId = 1L;

        doNothing().when(ticketRepository).deleteById(ticketId);
        ticketService.deleteTicket(ticketId);

        verify(ticketRepository, times(1)).deleteById(ticketId);
    }

    @Test
    void testGetTicketById_Positive() {
        long ticketId = 1L;
        Ticket ticket = new Ticket(null, TicketType.DAY, "STANDARD", LocalDate.now(), BigDecimal.TEN);

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));

        Ticket result = ticketService.getTicketById(ticketId);

        assertNotNull(result);
        assertEquals(TicketType.DAY, result.getTicketType());
        verify(ticketRepository, times(1)).findById(ticketId);
    }

    @Test
    void testGetTicketById_NotFound() {
        long ticketId = 1L;

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            ticketService.getTicketById(ticketId);
        });

        assertTrue(exception.getMessage().contains("Ticket not found"));
        verify(ticketRepository, times(1)).findById(ticketId);
    }
}