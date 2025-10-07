package net.engineeringdigest.journalApp.entity;

import lombok.*;
import net.engineeringdigest.journalApp.enums.Sentiment;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "jounal_entries")
@Data
@NoArgsConstructor
public class JournalEntry {

    @Id
    private ObjectId id;
    private String content;
    @NonNull
    private String title;
    private LocalDateTime date;
    private Sentiment sentiment;


}
