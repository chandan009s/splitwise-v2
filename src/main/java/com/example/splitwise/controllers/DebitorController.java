package com.example.splitwise.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.splitwise.model.Debitor;
import com.example.splitwise.service.DebitorService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api")
@Tag(name = "Debitors", description = "Participant/split management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class DebitorController {

    private final DebitorService debitorService;

    public DebitorController(DebitorService debitorService) {
        this.debitorService = debitorService;
    }

    // Canonical path: POST /api/events/{eventId}/debitors
    @PostMapping("/events/{eventId}/debitors")
    public ResponseEntity<?> addDebitorToEventByEventPath(
            @PathVariable Long eventId,
            @RequestBody Debitor d) {
        try {
            Debitor saved = debitorService.addDebitorToEvent(eventId, d);
            Map<String, Object> resp = Map.of(
                    "id", saved.getId(),
                    "userId", saved.getUser().getId(),
                    "debAmount", saved.getDebAmount(),
                    "included", saved.isIncluded()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(resp);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "add_debitor_failed", "message", ex.getMessage()));
        }
    }

    // Alternate (keeps backward compat): POST /api/debitors/{eventId}
    @PostMapping("/debitors/{eventId}")
    public ResponseEntity<?> addDebitorToEventByDebitorPath(
            @PathVariable Long eventId,
            @RequestBody Debitor d) {
        return addDebitorToEventByEventPath(eventId, d);
    }

    // DELETE /api/debitors/{debitorId}
    @DeleteMapping("/debitors/{debitorId}")
    public ResponseEntity<?> deleteDebitor(@PathVariable Long debitorId) {
        try {
            debitorService.deleteDebitor(debitorId);
            return ResponseEntity.noContent().build();

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", ex.getMessage()));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "delete_failed", "message", ex.getMessage()));
        }
    }

}
