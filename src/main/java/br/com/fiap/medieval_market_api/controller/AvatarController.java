package br.com.fiap.medieval_market_api.controller;

import br.com.fiap.medieval_market_api.model.Avatar;
import br.com.fiap.medieval_market_api.model.Item;
import br.com.fiap.medieval_market_api.model.RoleType;
import br.com.fiap.medieval_market_api.repository.AvatarRepository;
import br.com.fiap.medieval_market_api.repository.ItemRepository;
import br.com.fiap.medieval_market_api.specification.AvatarSpecification;
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
@RequestMapping("/avatars")
@Slf4j
public class AvatarController {

    @Autowired
    private AvatarRepository repository;

    @Autowired
    private ItemRepository itemRepository;


    @GetMapping
    @Operation(
        summary = "Listar Avatares",
        description = "Retorna todos os Avatares cadastrados"
    )
    public List<Avatar> index() {
        return repository.findAll();
    }

    @GetMapping("/search")
    @Operation(
        summary = "Buscar Avatares com filtro, paginação e ordenação",
        description = """
            Retorna uma página de Avatares filtrando por:
            - `name` (texto parcial)
            - `role` (classe: WARRIOR, MAGE, ARCHER)
            
            Exemplo: `/avatars/search?name=ana&role=MAGE&page=0&size=10&sort=level,desc`
        """
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    public Page<Avatar> search(
        @RequestParam(required = false) String name,
        @RequestParam(required = false) RoleType role,
        @PageableDefault(size = 10) Pageable pageable
    ) {
        return repository.findAll(AvatarSpecification.withFilters(name, role), pageable);
    }

    @PostMapping
    @Operation(
        summary = "Cadastrar um novo Avatar",
        responses = @ApiResponse(responseCode = "400", description = "Dados inválidos")
    )
    @ResponseStatus(HttpStatus.CREATED)
    public Avatar create(@RequestBody @Valid Avatar avatar) {
        return repository.save(avatar);
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar Avatar por ID",
        description = "Retorna os dados de um Avatar",
        responses = @ApiResponse(responseCode = "404", description = "Avatar não encontrado")
    )
    public Avatar get(@PathVariable Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avatar não encontrado"));
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Atualizar um Avatar existente",
        description = "Atualiza os dados de um Avatar pelo ID"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "404", description = "Avatar não encontrado")
    })
    public Avatar update(@PathVariable Long id, @RequestBody @Valid Avatar avatar) {
        get(id);
        avatar.setId(id);
        return repository.save(avatar);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Deletar um Avatar",
        responses = @ApiResponse(responseCode = "404", description = "Avatar não encontrado")
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Avatar avatar = get(id);
    
        // Remove o vínculo dos itens com o avatar
        List<Item> items = itemRepository.findByOwner(avatar);
        items.forEach(item -> item.setOwner(null));
        itemRepository.saveAll(items);
    
        repository.delete(avatar);
        return ResponseEntity.noContent().build();
    }
}