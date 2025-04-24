package br.com.fiap.medieval_market_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do item é obrigatório")
    private String name;

    @NotNull(message = "O tipo do item é obrigatório")
    @Enumerated(EnumType.STRING)
    private ItemType type;

    @NotNull(message = "A raridade do item é obrigatória")
    @Enumerated(EnumType.STRING)
    private ItemRarity rarity;

    @NotNull(message = "O preço do item é obrigatório")
    @Min(value = 0, message = "O preço do item deve ser igual ou maior que zero")
    private int price;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = true)
    private Avatar owner;

}
