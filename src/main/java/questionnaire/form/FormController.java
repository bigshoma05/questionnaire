package questionnaire.form;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import questionnaire.form.answers.SetAnswersRequest;
import questionnaire.form.questions.UpdateQuestionsRequest;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/forms")
@RequiredArgsConstructor
public class FormController {

  @Autowired FormService formService;

  @GetMapping("/{id}")
  public Optional<Form> getFormById(@PathVariable String id) {
    return formService.getFormById(id);
  }

  @GetMapping
  public List<Form> getAllForms(@RequestParam String userId) {
    return formService.getAllForms(userId);
  }

  @PostMapping("/publish/{id}")
  public Optional<Form> publishForm(@PathVariable String id) {
    return formService.publishForm(id);
  }

  @PostMapping("/questions")
  public Optional<Form> createForm(
      @RequestBody CreateFormRequest request, @RequestParam String userId) {
    return formService.createForm(request, userId);
  }

  @PatchMapping("/questions")
  public Optional<Form> updateQuestions(@RequestBody UpdateQuestionsRequest request) {
    return formService.updateQuestions(request);
  }

  @PostMapping("/answers")
  public Optional<Form> setAnswers(
      @RequestBody SetAnswersRequest request, @RequestParam String userId) {
    return formService.setAnswers(request, userId);
  }
}
