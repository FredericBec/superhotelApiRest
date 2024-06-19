package fr.fms.superhotel.dao;

import fr.fms.superhotel.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<AppRole, Long> {
    AppRole findByRolename(String rolename);
}
