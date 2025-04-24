package br.com.fiap.medieval_market_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.fiap.medieval_market_api.model.Avatar;
import br.com.fiap.medieval_market_api.model.RoleType;

public interface AvatarRepository extends JpaRepository<Avatar, Long>, JpaSpecificationExecutor<Avatar> {
    List<Avatar> findByNameContainingIgnoreCase(String name);

    List<Avatar> findByRole(RoleType role);
}
