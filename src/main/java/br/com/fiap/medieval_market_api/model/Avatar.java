package br.com.fiap.medieval_market_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "O Nome do Personagem não pode estar vazio")
    @NotBlank(message = "O Nome do Personagem não pode estar em branco")
    private String name;

    @NotNull(message = "A Classe do Personagem não pode estar vazia")
    @Enumerated(EnumType.STRING)
    private RoleType role;

    @NotNull(message = "O Nível é obrigatório")
    @Min(value = 1, message = "O nível mínimo é 1")
    @Max(value = 99, message = "O nível máximo é 99")
    private int level;

    @NotNull(message = "O saldo de moedas não pode ser nulo")
    @PositiveOrZero(message = "O saldo de moedas não pode ser negativo")
    private int coins;
}