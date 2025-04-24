package br.com.fiap.medieval_market_api.specification;

import br.com.fiap.medieval_market_api.model.Avatar;
import br.com.fiap.medieval_market_api.model.RoleType;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

public class AvatarSpecification {

    public static Specification<Avatar> withFilters(String name, RoleType role) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (role != null) {
                predicates.add(cb.equal(root.get("role"), role));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}