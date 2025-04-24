package br.com.fiap.medieval_market_api.controller;

import br.com.fiap.medieval_market_api.model.Item;
import br.com.fiap.medieval_market_api.model.ItemRarity;
import br.com.fiap.medieval_market_api.model.ItemType;
import br.com.fiap.medieval_market_api.repository.ItemRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/items")
@Slf4j
public class ItemController {

    @Autowired
    private ItemRepository repository;

    @GetMapping
    public List<Item> index() {
        return repository.findAll();
    }

    @GetMapping("/search/by-name")
    public List<Item> searchByName(@RequestParam String name) {
        return repository.findByNameContainingIgnoreCase(name);
    }

    @GetMapping("/search/by-type")
    public List<Item> searchByType(@RequestParam ItemType type) {
        return repository.findByType(type);
    }

    @GetMapping("/search/by-rarity")
    public List<Item> searchByRarity(@RequestParam ItemRarity rarity) {
        return repository.findByRarity(rarity);
    }

    @GetMapping("/search/by-price-range")
    public List<Item> searchByPriceRange(@RequestParam int min, @RequestParam int max) {
        return repository.findByPriceBetween(min, max);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Item create(@RequestBody @Valid Item item) {
        return repository.save(item);
    }

    @GetMapping("/{id}")
    public Item get(@PathVariable Long id) {
        return repository.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado")
        );
    }

    @PutMapping("/{id}")
    public Item update(@PathVariable Long id, @RequestBody @Valid Item item) {
        get(id); // Verifica existência
        item.setId(id);
        return repository.save(item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Item item = get(id);
        repository.delete(item);
        return ResponseEntity.noContent().build();
    }
}