package org.example.lattice.kafka;

import lombok.RequiredArgsConstructor;
import org.example.lattice.models.Document;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EventProducer {
    private final KafkaTemplate<String , Object> kafkaTemplate;

    public void publishDocumentUpdated(Document document , String ActorEmail){
        Map<String , Object> event = Map.of(
                "type", "DOCUMENT_UPDATED",
                "documentId", document.getId(),
                "workspaceId", document.getWorkspace().getId(),
                "actor", ActorEmail,
                "timestamp", Instant.now()
        );
        kafkaTemplate.send("document Events" , event);
    }

}
