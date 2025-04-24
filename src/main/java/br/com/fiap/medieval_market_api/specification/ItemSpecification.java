package br.com.fiap.medieval_market_api.specification;

import br.com.fiap.medieval_market_api.model.Item;
import br.com.fiap.medieval_market_api.model.ItemRarity;
import br.com.fiap.medieval_market_api.model.ItemType;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

public class ItemSpecification {

    public static Specification<Item> withFilters(String name, ItemType type, ItemRarity rarity, Integer min, Integer max) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (type != null) {
                predicates.add(cb.equal(root.get("type"), type));
            }

            if (rarity != null) {
                predicates.add(cb.equal(root.get("rarity"), rarity));
            }

            if (min != null && max != null) {
                predicates.add(cb.between(root.get("price"), min, max));
            } else if (min != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("price"), min));
            } else if (max != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("price"), max));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}