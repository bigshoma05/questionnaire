package questionnaire.form.questions;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends MongoRepository<Questions, String> {
  Questions findByFormId(String formId);
}
