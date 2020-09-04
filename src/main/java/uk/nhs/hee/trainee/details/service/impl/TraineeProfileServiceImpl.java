/*
 * The MIT License (MIT)
 *
 * Copyright 2020 Crown Copyright (Health Education England)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package uk.nhs.hee.trainee.details.service.impl;

import org.springframework.stereotype.Service;
import uk.nhs.hee.trainee.details.dto.enumeration.Status;
import uk.nhs.hee.trainee.details.model.TraineeProfile;
import uk.nhs.hee.trainee.details.repository.TraineeProfileRepository;
import uk.nhs.hee.trainee.details.service.TraineeProfileService;

@Service
public class TraineeProfileServiceImpl implements TraineeProfileService {

  private final TraineeProfileRepository repository;

  TraineeProfileServiceImpl(TraineeProfileRepository repository) {
    this.repository = repository;
  }

  public TraineeProfile getTraineeProfile(String id) {
    return repository.findById(id).orElse(null);
  }

  public TraineeProfile getTraineeProfileByTraineeTisId(String traineeTisId) {
    return repository.findByTraineeTisId(traineeTisId);
  }

  public TraineeProfile save(TraineeProfile traineeProfile) {
    return repository.save(traineeProfile);
  }

  public TraineeProfile hidePastProgrammes(TraineeProfile traineeProfile) {
    traineeProfile.getProgrammeMemberships().removeIf(c -> c.getStatus() == Status.PAST);
    return traineeProfile;
  }

  public TraineeProfile hidePastPlacements(TraineeProfile traineeProfile) {
    traineeProfile.getPlacements().removeIf(c -> c.getStatus() == Status.PAST);
    return traineeProfile;
  }
}
