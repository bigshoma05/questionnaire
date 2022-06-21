package questionnaire.form.questions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "questions")
@AllArgsConstructor
@NoArgsConstructor
public class Questions {
  private String formId;
  private List<String> questions;
}
