package uk.nhs.hee.trainee.details.query;

import uk.nhs.hee.trainee.details.model.PersonDetails;

import java.util.List;

public interface PersonDAL {

  List<PersonDetails> findById(String id);

}
