package tn.essat.pfanouveau.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('RECRUTEUR') or hasRole('CONDIDAT') or hasRole('ADMIN')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/recruteur")
    @PreAuthorize("hasRole('Condidat')")
    public String recruteurAcess() {
        return "RECRUTEUR Board.";
    }

    @GetMapping("/condidat")
    @PreAuthorize("hasRole('CONDIDAT')")
    public String adminAccess() {
        return "condidat Board.";
    }
    @GetMapping("/admin")
    @PreAuthorize("hasRole('RECRUTEUR')")
    public String adminAcess() {
        return "RECRUTEUR Board.";
    }
}
