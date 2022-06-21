package questionnaire.form;

import lombok.Data;

import java.util.List;

@Data
public class CreateFormRequest {
  private String subject;
  private List<String> attachments;
  private List<String> questions;
  private List<String> answers;}
