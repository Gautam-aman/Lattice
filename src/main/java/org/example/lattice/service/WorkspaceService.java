package org.example.lattice.service;

import lombok.RequiredArgsConstructor;
import org.example.lattice.Repository.UserRepository;
import org.example.lattice.Repository.WorkSpaceRepository;
import org.example.lattice.Repository.WorkspaceMemberRepository;
import org.example.lattice.models.Role;
import org.example.lattice.models.User;
import org.example.lattice.models.Workspace;
import org.example.lattice.models.WorkspaceMember;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkspaceService {

    private final UserRepository userRepository;
    private final WorkSpaceRepository workSpaceRepository;
    private final WorkspaceMemberRepository memberRepository;

    public Workspace createWorkspace(String name, String userEmail) {
        User owner = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Workspace workspace = Workspace.builder()
                .name(name)
                .owner(owner)
                .build();

        workspace = workSpaceRepository.save(workspace);

        WorkspaceMember member = WorkspaceMember.builder()
                .workspace(workspace)
                .user(owner)
                .role(Role.OWNER)
                .build();
        memberRepository.save(member);
        return workspace;
    }


    public List<Workspace> getUserWorkspaces(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return memberRepository.findByUser(user)
                .stream()
                .map(WorkspaceMember::getWorkspace)
                .toList();
    }

    public WorkspaceMember add (UUID workSpaceId , String emailToAdd , Role role , String requestEmail){
        Workspace workspace = workSpaceRepository.findById(workSpaceId)
                .orElseThrow(() -> new RuntimeException("Workspace not found"));

        checkPermission(workspace, requestEmail, Role.ADMIN);

        User userToAdd = userRepository.findByEmail(emailToAdd)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (memberRepository.existsByWorkspaceAndUser(workspace, userToAdd)) {
            throw new RuntimeException("User already in workspace");
        }

        WorkspaceMember member = WorkspaceMember.builder()
                .workspace(workspace)
                .user(userToAdd)
                .role(role)
                .build();
        return memberRepository.save(member);

    }

    public void checkPermission(Workspace workspace, String userEmail, Role minimumRole) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        WorkspaceMember member = memberRepository.findByWorkspaceAndUser(workspace, user)
                .orElseThrow(() -> new RuntimeException("Not a member of this workspace"));

        if (member.getRole().ordinal() > minimumRole.ordinal()) {
            throw new RuntimeException("Insufficient permissions");
        }
    }

}
