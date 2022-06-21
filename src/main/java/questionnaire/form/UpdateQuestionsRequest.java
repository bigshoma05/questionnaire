package questionnaire.form;

import lombok.Data;

import java.util.List;

@Data
public class UpdateQuestionsRequest {
  private String formId;
  private List<String> questions;
}
