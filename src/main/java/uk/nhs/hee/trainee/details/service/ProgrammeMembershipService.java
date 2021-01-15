package uk.nhs.hee.trainee.details.service;

    import java.util.List;
    import java.util.Optional;
    import org.springframework.stereotype.Service;
    import uk.nhs.hee.trainee.details.mapper.ProgrammeMembershipMapper;
    import uk.nhs.hee.trainee.details.model.ProgrammeMembership;
    import uk.nhs.hee.trainee.details.model.TraineeProfile;
    import uk.nhs.hee.trainee.details.repository.TraineeProfileRepository;

@Service
public class ProgrammeMembershipService {

  private final TraineeProfileRepository repository;
  private final ProgrammeMembershipMapper mapper;

  ProgrammeMembershipService(TraineeProfileRepository repository, ProgrammeMembershipMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  /**
   * Update the programme membership for the trainee with the given TIS ID.
   *
   * @param tisId         The TIS id of the trainee.
   * @param programmeMembership The Programme Membership to update for the trainee.
   * @return The updated Programme Membership or empty if a trainee with the ID was not found.
   */
  public Optional<ProgrammeMembership> updateProgrammeMembershipByTisId(String tisId,
                                                                  ProgrammeMembership programmeMembership) {

    TraineeProfile traineeProfile = repository.findByTraineeTisId(tisId);

    if (traineeProfile == null) {
      return Optional.empty();
    }

    List<ProgrammeMembership> existingProgrammeMemberships = traineeProfile.getProgrammeMemberships();

    for (ProgrammeMembership existingProgrammeMembership : existingProgrammeMemberships) {

      if (existingProgrammeMembership.getProgrammeTisId().equals(programmeMembership.getProgrammeTisId())) {
        mapper.updateProgrammeMembership(existingProgrammeMembership, programmeMembership);
        repository.save(traineeProfile);
        return Optional.of(existingProgrammeMembership);
      }
    }

    existingProgrammeMemberships.add(programmeMembership);
    repository.save(traineeProfile);
    return Optional.of(programmeMembership);
  }
}
