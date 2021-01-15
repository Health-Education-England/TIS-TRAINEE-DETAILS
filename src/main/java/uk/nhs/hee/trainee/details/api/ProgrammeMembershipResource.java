package uk.nhs.hee.trainee.details.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import uk.nhs.hee.trainee.details.dto.ProgrammeMembershipDto;
import uk.nhs.hee.trainee.details.mapper.ProgrammeMembershipMapper;
import uk.nhs.hee.trainee.details.model.ProgrammeMembership;
import uk.nhs.hee.trainee.details.service.ProgrammeMembershipService;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/programme-membership")
public class ProgrammeMembershipResource {

  private ProgrammeMembershipService service;
  private ProgrammeMembershipMapper mapper;

  public ProgrammeMembershipResource(ProgrammeMembershipService service, ProgrammeMembershipMapper mapper) {
    this.service = service;
    this.mapper = mapper;
  }


  /**
   * Update the Programme Membership details for the trainee.
   *
   * @param traineeTisId The ID of the trainee to update.
   * @param dto   The Programme Membership details to update with.
   * @return The updated Programme Membership.
   */


  @PatchMapping("/{traineeTisId}")
  public ResponseEntity<ProgrammeMembershipDto> updateProgrammeMembership(
      @PathVariable(name = "traineeTisId") String traineeTisId,
      @RequestBody @Validated ProgrammeMembershipDto dto) {
    log.trace("Update Programme Membership of trainee with TIS ID {}", traineeTisId);
    ProgrammeMembership entity = mapper.toEntity(dto);
    Optional<ProgrammeMembership> optionalEntity = service
        .updateProgrammeMembershipByTisId(traineeTisId, entity);
    entity = optionalEntity
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trainee not found."));
    return ResponseEntity.ok(mapper.toDto(entity));
  }
}
