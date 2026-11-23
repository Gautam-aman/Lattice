package org.example.lattice.controller;

import lombok.RequiredArgsConstructor;
import org.example.lattice.DTO.CreateWorkspaceRequest;
import org.example.lattice.models.Workspace;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/workspace")
@RequiredArgsConstructor
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @PostMapping
    public Workspace create(@RequestBody CreateWorkspaceRequest req, @AuthenticationPrincipal UserDetails userDetails) {
        return workspaceService.createWorkspace(req.name(), userDetails.getUsername());
    }

    @GetMapping
    public List<Workspace> myWorkspaces(@AuthenticationPrincipal UserDetails userDetails) {
        return workspaceService.getUserWorkspaces(userDetails.getUsername());
    }
}
