package biblioteca_G_v2.demo.controller;

import biblioteca_G_v2.demo.model.Game;
import biblioteca_G_v2.demo.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/juegos") 
@CrossOrigin(origins = "*")
public class GameController {

    @Autowired
    private GameRepository gameRepository;

    @GetMapping
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable String id) {
        return gameRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Game createGame(@RequestBody Game game) {
        if (game.getId() == null || game.getId().isEmpty()) {
            game.setId(UUID.randomUUID().toString());
        }
        return gameRepository.save(game);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable String id, @RequestBody Game gameDetails) {
        return gameRepository.findById(id)
            .map(game -> {
                game.setTitle(gameDetails.getTitle());
                game.setDescription(gameDetails.getDescription());
                game.setImageUrl(gameDetails.getImageUrl());
                game.setTags(gameDetails.getTags());
                game.setExternalLinks(gameDetails.getExternalLinks());
                game.setProtectionStatusId(gameDetails.getProtectionStatusId());
                Game updatedGame = gameRepository.save(game);
                return ResponseEntity.ok(updatedGame);
            })
            .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGame(@PathVariable String id) {
        return gameRepository.findById(id)
            .map(game -> {
                gameRepository.delete(game);
                return ResponseEntity.ok().build();
            })
            .orElse(ResponseEntity.notFound().build());
    }

}
