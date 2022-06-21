package questionnaire.form;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import questionnaire.auth.AuthorizationManager;
import questionnaire.form.answers.AnswerRepository;
import questionnaire.form.answers.Answers;
import questionnaire.form.answers.SetAnswersRequest;
import questionnaire.form.questions.QuestionRepository;
import questionnaire.form.questions.Questions;
import questionnaire.form.questions.UpdateQuestionsRequest;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FormService {
  @Autowired FormRepository formRepository;
  @Autowired AuthorizationManager authorizationManager;
  @Autowired AnswerRepository answerRepository;
  @Autowired QuestionRepository questionRepository;

  public Optional<Form> createForm(CreateFormRequest request, String userId) {
    validateCreateRequest();

    Form form = new Form();
    form.setAuthorId(userId);
    form.setSubject(request.getSubject());
    form.setStatus(Status.DRAFT);
    if (!request.getAttachments().isEmpty()) {
      form.setAttachments(request.getAttachments());
    }
    formRepository.save(form);

    String formId = formRepository.findByAuthorId(userId).getId();
    Questions questions = new Questions();
    questions.setFormId(formId);
    questions.setQuestions(request.getQuestions());
    questionRepository.save(questions);

    return formResponse(formId);
  }

  public Optional<Form> setAnswers(SetAnswersRequest request, String userId) {
    validateSetAnswersRequest();

    Optional<Form> form = formRepository.findById(request.getFormId());
    form.get().setRespondentId(userId);
    form.get().setStatus(Status.ANSWERED);
    formRepository.save(form.get());

    Answers answers = new Answers();
    answers.setRespondentId(userId);
    answers.setAnswers(request.getAnswers());
    answerRepository.save(answers);

    return formResponse(request.getFormId());
  }

  private void validateSetAnswersRequest() {
    if (!authorizationManager.isUser()) {
      throw new RuntimeException("Only user can answer");
    }
  }

  public Optional<Form> updateQuestions(UpdateQuestionsRequest request) {
    validateUpdateQuestionsRequest();

    Questions questions = questionRepository.findByFormId(request.getFormId());
    questions.setQuestions(request.getQuestions());
    questionRepository.save(questions);
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
