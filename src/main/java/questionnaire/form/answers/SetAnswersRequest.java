package questionnaire.form.answers;

import lombok.Data;

import java.util.List;

@Data
public class SetAnswersRequest {
  private String formId;
  private List<String> answers;
}
