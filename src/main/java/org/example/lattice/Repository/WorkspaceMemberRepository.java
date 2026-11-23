package org.example.lattice.Repository;

import org.example.lattice.models.User;
import org.example.lattice.models.Workspace;
import org.example.lattice.models.WorkspaceMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, UUID> {
    Optional<WorkspaceMember> findByWorkspaceAndUser(Workspace workspace, User user);
    List<WorkspaceMember> findByUser(User user);
    boolean existsByWorkspaceAndUser(Workspace workspace, User user);
}
