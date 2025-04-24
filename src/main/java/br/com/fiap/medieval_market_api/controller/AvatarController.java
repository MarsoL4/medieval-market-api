package br.com.fiap.medieval_market_api.controller;

import br.com.fiap.medieval_market_api.model.Avatar;
import br.com.fiap.medieval_market_api.model.RoleType;
import br.com.fiap.medieval_market_api.repository.AvatarRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/avatars")
@Slf4j
public class AvatarController {

    @Autowired
    private AvatarRepository repository;

    @GetMapping
    public List<Avatar> index() {
        return repository.findAll();
    }

    @GetMapping("/search/by-name")
    public List<Avatar> searchByName(@RequestParam String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    @GetMapping("/search/by-role")
    public List<Avatar> searchByRole(@RequestParam RoleType role) {
        return repository.findByRole(role);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Avatar create(@RequestBody @Valid Avatar avatar) {
        return repository.save(avatar);
    }

    @GetMapping("/{id}")
    public Avatar get(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Personagem não encontrado")
        );
    }

    @PutMapping("/{id}")
    public Avatar update(@PathVariable Long id, @RequestBody @Valid Avatar avatar) {
        get(id);
        avatar.setId(id);
        return repository.save(avatar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Avatar avatar = get(id);
        repository.delete(avatar);
        return ResponseEntity.noContent().build();
    }
}