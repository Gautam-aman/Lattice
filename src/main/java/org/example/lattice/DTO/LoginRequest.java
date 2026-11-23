package org.example.lattice.DTO;

import lombok.Data;

public record LoginRequest(
         String email ,
         String password
) {
}
