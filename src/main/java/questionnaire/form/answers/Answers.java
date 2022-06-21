package questionnaire.form.answers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "answers")
@AllArgsConstructor
@NoArgsConstructor
public class Answers {
  private String respondentId;
  private List<String> answers;
}
