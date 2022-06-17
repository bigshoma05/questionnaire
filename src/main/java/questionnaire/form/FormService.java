package questionnaire.form;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import questionnaire.auth.AuthorizationManager;
import questionnaire.user.UserService;

@Service
@RequiredArgsConstructor
public class FormService {
  @Autowired
  FormRepository formRepository;
  @Autowired
  AuthorizationManager authorizationManager;

  public void createForm(CreateFormRequest request) {
    validateCreateRequest();
    
    Form form = new Form();
    form.setAuthorId(request.getAuthorId());
    form.setSubject(request.getSubject());
    if (!request.getAttachments().isEmpty()){
      form.setAttachments(request.getAttachments());
    }
    formRepository.save(form);

    Form.Answers answers = new Form.Answers();
    form.getQuestionnaire().put((Form.Questions) request.getQuestions(),answers);
    formRepository.save(form);
  }

  private void validateCreateRequest() {
    if (!authorizationManager.isAdmin() && !authorizationManager.isAuthor()){
      throw new RuntimeException("Only admin and author can create a form");
    }
  }
}
