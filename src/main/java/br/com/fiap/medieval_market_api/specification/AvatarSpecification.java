package br.com.fiap.medieval_market_api.specification;

import br.com.fiap.medieval_market_api.model.Avatar;
import br.com.fiap.medieval_market_api.model.RoleType;
import org.springframework.data.jpa.domain.Specification;

public class AvatarSpecification {

    public static Specification<Avatar> withFilters(String name, RoleType role) {
        return (root, query, cb) -> {
            var predicates = cb.conjunction();

            if (name != null && !name.isBlank()) {
                predicates.getExpressions().add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }

            if (role != null) {
                predicates.getExpressions().add(cb.equal(root.get("role"), role));
            }

            return predicates;
        };
    }
}