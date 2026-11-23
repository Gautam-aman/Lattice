package org.example.lattice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "documents")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private Workspace workspace;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Long version = 0L;

    @ManyToOne
    private User createdBy;

    private LocalDateTime updatedAt = LocalDateTime.now();
}
