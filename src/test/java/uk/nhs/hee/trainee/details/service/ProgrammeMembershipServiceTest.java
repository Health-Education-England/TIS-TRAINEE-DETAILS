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

package uk.nhs.hee.trainee.details.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import uk.nhs.hee.trainee.details.mapper.ProgrammeMembershipMapper;
import uk.nhs.hee.trainee.details.model.ProgrammeMembership;
import uk.nhs.hee.trainee.details.model.TraineeProfile;
import uk.nhs.hee.trainee.details.repository.TraineeProfileRepository;
import java.time.LocalDate;
import java.util.Optional;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class ProgrammeMembershipServiceTest {

  private static final LocalDate DATE = LocalDate.EPOCH;
  private static final LocalDate PROGRAMME_DATE = LocalDate.EPOCH;
  private static final String TRAINEE_TIS_ID = "40";
  private static final String PROGRAMME_TIS_ID = "1";
  private static final String PROGRAMME_NAME = "Cardiology-";
  private static final String MANAGING_DEANERY = "East of England";
  private static final String PROGRAMME_NUMBER = "EOE8945";
  private static final String PROGRAMME_MEMBERSHIP_TYPE = "SUBSTANTIVE";
  private static final String MODIFIED_SUFFIX = "post";
  private static final String ORIGINAL_SUFFIX = "pre";
  private static final String NEW_PROGRAMME_MEMBERSHIP_ID = "1";
  private static final String EXISTING_PROGRAMME_MEMBERSHIP_ID = "2";
  private static final Integer DATE_ADJUSTMENT_DAYS = 100;
  private static final Integer PROGRAMME_MEMBERSHIP_DURATION_DAYS = 7;
  private static final Integer PROGRAMME_DURATION_DAYS = 7;

  private ProgrammeMembershipService service;
  private TraineeProfileRepository repository;

  @BeforeEach
  void setUp() {
    repository = mock(TraineeProfileRepository.class);
    service = new ProgrammeMembershipService(repository,
        Mappers.getMapper(ProgrammeMembershipMapper.class));
  }

  @Test
  void shouldNotUpdateProgrammeMembershipWhenTraineeIdNotFound() {
    Optional<ProgrammeMembership> programmeMembership = service
        .updateProgrammeMembershipByTisId("notFound", new ProgrammeMembership());

    assertThat("Unexpected optional isEmpty flag.", programmeMembership.isEmpty(), is(true));
    verify(repository).findByTraineeTisId("notFound");
    verifyNoMoreInteractions(repository);
  }

  @Test
  void shouldAddProgrammeMembershipWhenTraineeFoundAndNoProgrammeMembershipsExists() {
    TraineeProfile traineeProfile = new TraineeProfile();

    when(repository.findByTraineeTisId(TRAINEE_TIS_ID)).thenReturn(traineeProfile);
    when(repository.save(traineeProfile)).thenAnswer(invocation -> invocation.getArgument(0));


    Optional<ProgrammeMembership> programmeMembership = service.updateProgrammeMembershipByTisId(TRAINEE_TIS_ID,
        createProgrammeMembership(NEW_PROGRAMME_MEMBERSHIP_ID, MODIFIED_SUFFIX, DATE_ADJUSTMENT_DAYS));

    assertThat("Unexpected optional isEmpty flag.", programmeMembership.isEmpty(), is(false));

    ProgrammeMembership expectedProgrammeMembership = new ProgrammeMembership();
    expectedProgrammeMembership.setTisId(NEW_PROGRAMME_MEMBERSHIP_ID);
    expectedProgrammeMembership.setProgrammeTisId(PROGRAMME_TIS_ID);
    expectedProgrammeMembership.setProgrammeMembershipType(PROGRAMME_MEMBERSHIP_TYPE);
    expectedProgrammeMembership.setProgrammeName(PROGRAMME_NAME + MODIFIED_SUFFIX);
    expectedProgrammeMembership.setManagingDeanery(MANAGING_DEANERY);
    expectedProgrammeMembership.setProgrammeNumber(PROGRAMME_NUMBER);
    expectedProgrammeMembership.setStartDate(DATE.plusDays(DATE_ADJUSTMENT_DAYS));
    expectedProgrammeMembership.setEndDate(DATE.plusDays(DATE_ADJUSTMENT_DAYS + PROGRAMME_MEMBERSHIP_DURATION_DAYS));
    expectedProgrammeMembership.setProgrammeCompletionDate(PROGRAMME_DATE.plusDays(DATE_ADJUSTMENT_DAYS + PROGRAMME_DURATION_DAYS));

    assertThat("Unexpected programme membership.", programmeMembership.get(), is(expectedProgrammeMembership));
  }

  @Test
  void shouldAddProgrammeMembershipWhenTraineeFoundAndProgrammeMembershipNotExists() {
    TraineeProfile traineeProfile = new TraineeProfile();
    traineeProfile.getProgrammeMemberships()
        .add(createProgrammeMembership(EXISTING_PROGRAMME_MEMBERSHIP_ID, ORIGINAL_SUFFIX, 0));

    when(repository.findByTraineeTisId(TRAINEE_TIS_ID)).thenReturn(traineeProfile);
    when(repository.save(traineeProfile)).thenAnswer(invocation -> invocation.getArgument(0));

    Optional<ProgrammeMembership> programmeMembership = service.updateProgrammeMembershipByTisId(TRAINEE_TIS_ID,
        createProgrammeMembership(NEW_PROGRAMME_MEMBERSHIP_ID, MODIFIED_SUFFIX, DATE_ADJUSTMENT_DAYS));

    assertThat("Unexpected optional isEmpty flag.", programmeMembership.isEmpty(), is(false));

    ProgrammeMembership expectedProgrammeMembership = new ProgrammeMembership();
    expectedProgrammeMembership.setTisId(NEW_PROGRAMME_MEMBERSHIP_ID);
    expectedProgrammeMembership.setProgrammeTisId(PROGRAMME_TIS_ID);
    expectedProgrammeMembership.setProgrammeMembershipType(PROGRAMME_MEMBERSHIP_TYPE);
    expectedProgrammeMembership.setProgrammeName(PROGRAMME_NAME + MODIFIED_SUFFIX);
    expectedProgrammeMembership.setManagingDeanery(MANAGING_DEANERY);
    expectedProgrammeMembership.setProgrammeNumber(PROGRAMME_NUMBER);
    expectedProgrammeMembership.setStartDate(DATE.plusDays(DATE_ADJUSTMENT_DAYS));
    expectedProgrammeMembership.setEndDate(DATE.plusDays(DATE_ADJUSTMENT_DAYS + PROGRAMME_MEMBERSHIP_DURATION_DAYS));
    expectedProgrammeMembership.setProgrammeCompletionDate(PROGRAMME_DATE.plusDays(DATE_ADJUSTMENT_DAYS + PROGRAMME_DURATION_DAYS));

    assertThat("Unexpected programme membership.", programmeMembership.get(), is(expectedProgrammeMembership));
  }

  @Test
  void shouldUpdateProgrammeMembershipWhenTraineeFoundAndProgrammeMembershipExists() {
    TraineeProfile traineeProfile = new TraineeProfile();
    traineeProfile.getProgrammeMemberships()
        .add(createProgrammeMembership(EXISTING_PROGRAMME_MEMBERSHIP_ID, ORIGINAL_SUFFIX, 0));

    when(repository.findByTraineeTisId(TRAINEE_TIS_ID)).thenReturn(traineeProfile);
    when(repository.save(traineeProfile)).thenAnswer(invocation -> invocation.getArgument(0));

    Optional<ProgrammeMembership> programmeMembership = service.updateProgrammeMembershipByTisId(TRAINEE_TIS_ID,
        createProgrammeMembership(EXISTING_PROGRAMME_MEMBERSHIP_ID, MODIFIED_SUFFIX, DATE_ADJUSTMENT_DAYS));

    assertThat("Unexpected optional isEmpty flag.", programmeMembership.isEmpty(), is(false));

    ProgrammeMembership expectedProgrammeMembership = createProgrammeMembership(EXISTING_PROGRAMME_MEMBERSHIP_ID,
        ORIGINAL_SUFFIX, 0);
    expectedProgrammeMembership.setProgrammeName(PROGRAMME_NAME + MODIFIED_SUFFIX);
    expectedProgrammeMembership.setStartDate(DATE.plusDays(DATE_ADJUSTMENT_DAYS));
    expectedProgrammeMembership.setEndDate(DATE.plusDays(DATE_ADJUSTMENT_DAYS + PROGRAMME_MEMBERSHIP_DURATION_DAYS));
    expectedProgrammeMembership.setProgrammeCompletionDate(PROGRAMME_DATE.plusDays(DATE_ADJUSTMENT_DAYS + PROGRAMME_DURATION_DAYS));

    assertThat("Unexpected qualification.", programmeMembership.get(), is(expectedProgrammeMembership));
  }

  /**
   * Create an instance of Programme Membership with default dummy values.
   *
   * @param tisId              The TIS ID to set on the programme membership.
   * @param stringSuffix       The suffix to use for string values.
   * @param dateAdjustmentDays The number of days to add to dates.
   * @return The dummy entity.
   */
  private ProgrammeMembership createProgrammeMembership(String tisId, String stringSuffix,
                                            int dateAdjustmentDays) {
    ProgrammeMembership programmeMembership = new ProgrammeMembership();
    programmeMembership.setTisId(tisId);
    programmeMembership.setProgrammeTisId(PROGRAMME_TIS_ID);
    programmeMembership.setProgrammeMembershipType(PROGRAMME_MEMBERSHIP_TYPE);
    programmeMembership.setProgrammeName(PROGRAMME_NAME + stringSuffix);
    programmeMembership.setManagingDeanery(MANAGING_DEANERY);
    programmeMembership.setProgrammeNumber(PROGRAMME_NUMBER);
    programmeMembership.setStartDate(DATE.plusDays(dateAdjustmentDays));
    programmeMembership.setEndDate(DATE.plusDays(dateAdjustmentDays + PROGRAMME_MEMBERSHIP_DURATION_DAYS));
    programmeMembership.setProgrammeCompletionDate(PROGRAMME_DATE.plusDays(dateAdjustmentDays + PROGRAMME_DURATION_DAYS));

    return programmeMembership;
  }
}
