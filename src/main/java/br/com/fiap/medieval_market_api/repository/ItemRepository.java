package br.com.fiap.medieval_market_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.fiap.medieval_market_api.model.Item;
import br.com.fiap.medieval_market_api.model.ItemRarity;
import br.com.fiap.medieval_market_api.model.ItemType;

public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {
    List<Item> findByNameContainingIgnoreCase(String name);

    List<Item> findByType(ItemType type);

    List<Item> findByRarity(ItemRarity rarity);

    List<Item> findByPriceBetween(int min, int max);
}
