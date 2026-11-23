package org.example.lattice.DTO;

import org.example.lattice.models.Role;

public record AddMemberRequest (
        String email ,
        Role role
){
}
