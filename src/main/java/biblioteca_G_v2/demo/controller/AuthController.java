package biblioteca_G_v2.demo.controller;

import biblioteca_G_v2.demo.dto.AuthDTOs.*;
import biblioteca_G_v2.demo.model.User;
import biblioteca_G_v2.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userOpt = userRepository.findByEmail(request.email);
        
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(request.password)) {
                return ResponseEntity.ok(new AuthResponse(user, "dummy-token-123"));
            }
        }
        return ResponseEntity.status(401).body("Credenciales inválidas");
    }

    @PostMapping("/registro")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.email).isPresent()) {
            return ResponseEntity.badRequest().body("El email ya existe");
        }

        User newUser = new User();
        newUser.setId(UUID.randomUUID().toString());
        newUser.setName(request.name);
        newUser.setEmail(request.email);
        newUser.setPassword(request.password);
        newUser.setRole("USER"); 

        userRepository.save(newUser);

        return ResponseEntity.ok(new AuthResponse(newUser, "dummy-token-new"));
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/rol")
    public ResponseEntity<?> updateUserRole(@PathVariable String id, @RequestBody Map<String, String> body) {
        String newRole = body.get("role");
        
        return userRepository.findById(id).map(user -> {
            user.setRole(newRole);
            userRepository.save(user);
            return ResponseEntity.ok(user);
        }).orElse(ResponseEntity.notFound().build());
    }
}

