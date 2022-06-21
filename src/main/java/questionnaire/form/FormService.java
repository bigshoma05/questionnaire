package questionnaire.form;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import questionnaire.auth.AuthorizationManager;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FormService {
  @Autowired FormRepository formRepository;
  @Autowired AuthorizationManager authorizationManager;

  public Optional<Form> createForm(CreateFormRequest request, String userId) {
    validateCreateRequest();

    Form form = new Form();
    form.setAuthorId(userId);
    form.setSubject(request.getSubject());
    form.setStatus(Status.DRAFT);
    if (request.getAttachments() != null) {
      form.setAttachments(request.getAttachments());
    }
    form.setQuestions(request.getQuestions());
    formRepository.save(form);

    String formId = formRepository.findByAuthorId(userId).getId();

    return formResponse(formId);
  }

  public Optional<Form> setAnswers(SetAnswersRequest request, String userId) {
    validateSetAnswersRequest();

    Form form = formRepository.findById(request.getFormId()).get();
    form.setRespondentId(userId);
    form.setStatus(Status.ANSWERED);
    form.setAnswers(request.getAnswers());
    formRepository.save(form);

    String formId = formRepository.findByAuthorId(userId).getId();

    return formResponse(formId);
  }

  private void validateSetAnswersRequest() {
    if (!authorizationManager.isUser()) {
      throw new RuntimeException("Only user can answer");
    }
  }

  public Optional<Form> updateQuestions(UpdateQuestionsRequest request) {
    validateUpdateQuestionsRequest();

    Optional<Form> form = formRepository.findById(request.getFormId());
    form.get().setQuestions(request.getQuestions());
    formRepository.save(form.get());
    return formResponse(request.getFormId());
  }

  public Optional<Form> formResponse(String id) {
    return formRepository.findById(id);
  }

  private void validateCreateRequest() {
    if (!authorizationManager.isAdmin() && !authorizationManager.isAuthor()) {
      throw new RuntimeException("Only admin and author can create a form");
    }
  }

  private void validateUpdateQuestionsRequest() {
    if (!authorizationManager.isAdmin() && !authorizationManager.isAuthor()) {
      throw new RuntimeException("Only admin and author can update questions");
    }
  }

  public Optional<Form> getFormById(String id) {
    return formRepository.findById(id);
  }

  public List<Form> getAllForms(String userId) {
    if (authorizationManager.isAuthor()) {
      return formRepository.findAllByAuthorId(userId);
    }

    if (authorizationManager.isUser()) {
      return formRepository.findAllByRespondentId(userId);
    }

    return formRepository.findAll();
  }

  public Optional<Form> publishForm(String id) {
    Optional<Form> form = formRepository.findById(id);
    form.get().setStatus(Status.PUBLISHED);
    formRepository.save(form.get());
    return form;
  }
}
