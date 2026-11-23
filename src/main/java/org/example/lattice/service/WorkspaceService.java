package org.example.lattice.service;

import lombok.RequiredArgsConstructor;
import org.example.lattice.Repository.UserRepository;
import org.example.lattice.Repository.WorkSpaceRepository;
import org.example.lattice.models.User;
import org.example.lattice.models.Workspace;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final UserRepository userRepository;
    private final WorkSpaceRepository workSpaceRepository;

    public Workspace createWorkspace(String name, String username) {
        User owner = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Workspace workspace = Workspace.builder()
                .name(name)
                .owner(owner)
                .build();

        return workSpaceRepository.save(workspace);
    }

    public List<Workspace> getUserWorkspaces(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return workSpaceRepository.findByOwnerId(user.getId());
    }
}
