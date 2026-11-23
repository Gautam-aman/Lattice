package org.example.lattice.DTO;

public record RegisterRequest(
        String name,
        String email ,
        String Password
) {
}
