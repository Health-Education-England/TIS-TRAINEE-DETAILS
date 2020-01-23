package uk.nhs.hee.trainee.details.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import uk.nhs.hee.trainee.details.model.PersonDetails;

import java.util.List;

@Repository
public class PersonDALImpl implements PersonDAL{

  private final MongoTemplate mongoTemplate;

  @Autowired
  public PersonDALImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public List<PersonDetails> findById(String id) {

  }
}
