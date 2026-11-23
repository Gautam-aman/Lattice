package org.example.lattice.controller;

import lombok.RequiredArgsConstructor;
import org.example.lattice.DTO.AddMemberRequest;
import org.example.lattice.DTO.CreateWorkspaceRequest;
import org.example.lattice.models.Workspace;
import org.example.lattice.models.WorkspaceMember;
import org.example.lattice.service.WorkspaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/workspaces")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @PostMapping
    public ResponseEntity<Workspace> create(@RequestBody CreateWorkspaceRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        Workspace workspace = workspaceService.createWorkspace(request.name(), userDetails.getUsername());
        return ResponseEntity.ok(workspace);
    }

    @GetMapping
    public ResponseEntity<List<Workspace>> myWorkspaces(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(workspaceService.getUserWorkspaces(userDetails.getUsername()));
    }

    @PostMapping("/{workspaceId}/members")
    public ResponseEntity<WorkspaceMember> addMember(@PathVariable UUID workspaceId, @RequestBody AddMemberRequest request, @AuthenticationPrincipal UserDetails userDetails) {
        WorkspaceMember member = workspaceService.add(
                workspaceId,
                request.email(),
                request.role(),
                userDetails.getUsername()
        );
        return ResponseEntity.ok(member);
    }
}
