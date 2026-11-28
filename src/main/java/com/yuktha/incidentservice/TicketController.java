package com.yuktha.incidentservice;

import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketRepository repo;

    public TicketController(TicketRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Ticket> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public Ticket create(@RequestBody Ticket ticket) {
        ticket.setStatus("OPEN");
        ticket.setCreatedAt(java.time.LocalDateTime.now());
        return repo.save(ticket);
    }
    
    @PutMapping("/{id}/status")
    public Ticket updateStatus(@PathVariable Integer id, @RequestParam String status) {
        Ticket ticket = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        ticket.setStatus(status);
        return repo.save(ticket);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        repo.deleteById(id);
    }
}