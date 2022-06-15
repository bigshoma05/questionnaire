package questionnaire.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@Document(collection = "forms")
@NoArgsConstructor
@AllArgsConstructor
public class Form {
    @Id
    private String id;
    private String authorId;
    private String respondentId;
    private String subject;
    private Status status;
    private List<String> attachments;
    private Map<String, String> questionnaire;
}
