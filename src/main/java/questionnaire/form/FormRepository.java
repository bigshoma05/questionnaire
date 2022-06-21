package questionnaire.form;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormRepository extends MongoRepository<Form, String> {
  Form findByAuthorId(String authorId);

  List<Form> findAllByAuthorId(String authorId);

  List<Form> findAllByRespondentId(String respondentId);
}
