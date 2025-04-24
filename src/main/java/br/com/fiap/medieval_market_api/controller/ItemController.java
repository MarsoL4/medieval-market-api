package br.com.fiap.medieval_market_api.controller;

import br.com.fiap.medieval_market_api.model.Item;
import br.com.fiap.medieval_market_api.model.ItemRarity;
import br.com.fiap.medieval_market_api.model.ItemType;
import br.com.fiap.medieval_market_api.repository.ItemRepository;
import br.com.fiap.medieval_market_api.specification.ItemSpecification;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    @Operation(
        summary = "Listar Itens",
        description = "Retorna todos os Itens cadastrados"
    )
    public List<Item> index() {
        return repository.findAll();
    }

    @GetMapping("/search")
    @Operation(
        summary = "Buscar Itens com filtro, paginação e ordenação",
        description = """
            Retorna uma página de Itens filtrando por:
            - `name` (texto parcial)
            - `type` (WEAPON, ARMOR, POTION, ACCESSORY)
            - `rarity` (COMMON, RARE, EPIC, LEGENDARY)
            - `min` e `max` (intervalo de preço)

            Exemplo: `/items/search?name=espada&rarity=EPIC&type=WEAPON&min=100&max=1000&sort=price,asc`
        """
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public Page<Item> search(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) ItemType type,
        @RequestParam(required = false) ItemRarity rarity,
        @RequestParam(required = false) Integer min,
        @RequestParam(required = false) Integer max,
        @PageableDefault(size = 10) Pageable pageable
    ) {
        return repository.findAll(ItemSpecification.withFilters(name, type, rarity, min, max), pageable);
    }

    @PostMapping
    @Operation(
        summary = "Cadastrar um novo Item",
        responses = @ApiResponse(responseCode = "400", description = "Dados inválidos")
    )
    @ResponseStatus(HttpStatus.CREATED)
    public Item create(@RequestBody @Valid Item item) {
        return repository.save(item);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar Item por ID",
        description = "Retorna os dados de um Item",
        responses = @ApiResponse(responseCode = "404", description = "Item não encontrado")
    )
    public Item get(@PathVariable Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item não encontrado"));
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar um Item existente",
        description = "Atualiza os dados de um Item pelo ID"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    public Item update(@PathVariable Long id, @RequestBody @Valid Item item) {
        get(id);
        item.setId(id);
        return repository.save(item);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Deletar um Item",
        responses = @ApiResponse(responseCode = "404", description = "Item não encontrado")
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Item item = get(id);
        repository.delete(item);
        return ResponseEntity.noContent().build();
    }

}