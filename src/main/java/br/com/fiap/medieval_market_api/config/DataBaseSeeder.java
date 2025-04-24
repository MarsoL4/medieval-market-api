package br.com.fiap.medieval_market_api.config;

import br.com.fiap.medieval_market_api.model.*;
import br.com.fiap.medieval_market_api.repository.AvatarRepository;
import br.com.fiap.medieval_market_api.repository.ItemRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataBaseSeeder {

    @Autowired
    private AvatarRepository avatarRepository;

    @Autowired
    private ItemRepository itemRepository;

    @PostConstruct
    public void init() {
        Avatar arthur = Avatar.builder()
                .name("Arthur, o Bravo")
                .role(RoleType.WARRIOR)
                .level(20)
                .coins(150)
                .build();

        Avatar merlin = Avatar.builder()
                .name("Merlin, o Sábio")
                .role(RoleType.MAGE)
                .level(35)
                .coins(300)
                .build();

        Avatar elena = Avatar.builder()
                .name("Elena, a Caçadora")
                .role(RoleType.ARCHER)
                .level(15)
                .coins(200)
                .build();

        avatarRepository.saveAll(List.of(arthur, merlin, elena));
        
        itemRepository.saveAll(List.of(
                Item.builder()
                        .name("Espada Longa")
                        .type(ItemType.WEAPON)
                        .rarity(ItemRarity.RARE)
                        .price(120)
                        .owner(arthur)
                        .build(),

                Item.builder()
                        .name("Armadura de Placas")
                        .type(ItemType.ARMOR)
                        .rarity(ItemRarity.EPIC)
                        .price(250)
                        .owner(arthur)
                        .build(),

                Item.builder()
                        .name("Poção de Mana")
                        .type(ItemType.POTION)
                        .rarity(ItemRarity.COMMON)
                        .price(30)
                        .owner(merlin)
                        .build(),

                Item.builder()
                        .name("Arco de Ébano")
                        .type(ItemType.WEAPON)
                        .rarity(ItemRarity.LEGENDARY)
                        .price(400)
                        .owner(elena)
                        .build(),

                Item.builder()
                        .name("Anel da Furtividade")
                        .type(ItemType.ACCESSORY)
                        .rarity(ItemRarity.RARE)
                        .price(180)
                        .owner(elena)
                        .build()
        ));
    }
}