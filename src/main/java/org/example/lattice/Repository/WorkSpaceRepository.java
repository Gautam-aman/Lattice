package org.example.lattice.Repository;

import org.example.lattice.models.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WorkSpaceRepository extends JpaRepository<Workspace , UUID> {

    List<Workspace> findByOwnerId(UUID ownerId);
}
