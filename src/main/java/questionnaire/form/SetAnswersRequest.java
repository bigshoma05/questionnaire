package questionnaire.form;

import lombok.Data;

import java.util.List;

@Data
public class SetAnswersRequest {
  private String formId;
  private List<String> answers;
}
