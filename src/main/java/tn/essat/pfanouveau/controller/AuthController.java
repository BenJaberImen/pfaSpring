package tn.essat.pfanouveau.controller;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.essat.pfanouveau.Repsitory.CompteRepisotery;
import tn.essat.pfanouveau.Repsitory.RoleRepository;
import tn.essat.pfanouveau.Security.jwt.JwtUtils;
import tn.essat.pfanouveau.Security.service.UserDetailsImpl;
import tn.essat.pfanouveau.entity.Compte;
import tn.essat.pfanouveau.entity.ERole;
import tn.essat.pfanouveau.entity.Role;
import tn.essat.pfanouveau.entity.User;
import tn.essat.pfanouveau.payload.request.LoginRequest;
import tn.essat.pfanouveau.payload.request.SignupRequest;
import tn.essat.pfanouveau.payload.response.JwtResponse;
import tn.essat.pfanouveau.payload.response.MessageResponse;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

AuthenticationManager authenticationManager;
    @Autowired
    CompteRepisotery compteRepisotery;
    @Autowired
    RoleRepository roleRepository;

    PasswordEncoder passwordEncoder;
    @Autowired
    JwtUtils jwtUtils;
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getLogin(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new JwtResponse(jwt,userDetails.getId(),roles,userDetails.getFirstName(),userDetails.getLastName()));

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (compteRepisotery.existsByLogin(signUpRequest.getLogin())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (compteRepisotery.existsByLogin(signUpRequest.getLogin())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user'cs account
        Compte compte=new Compte (signUpRequest.getLogin(),passwordEncoder.encode(signUpRequest.getPassword()),
                new User(signUpRequest.getFirstName(),signUpRequest.getLastName(),signUpRequest.getAdress(),signUpRequest.getEmail(),signUpRequest.getNumTel()));
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();


        Set<String> role1 = signUpRequest.getRole();
        Set<Role> roles1 = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByRole(ERole.ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByRole(ERole.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "condidat":
                        Role condidatRole = roleRepository.findByRole(ERole.CONDIDAT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(condidatRole);

                        break;
                    default:
                        Role userRecruteur = roleRepository.findByRole(ERole.RECRUTEUR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRecruteur);
        }
    });
        }

        compte.setRoles(roles);
        compteRepisotery.save(compte);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
