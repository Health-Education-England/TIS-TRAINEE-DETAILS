package uk.nhs.hee.trainee.details.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import uk.nhs.hee.trainee.details.dto.ProgrammeMembershipDto;
import uk.nhs.hee.trainee.details.model.ProgrammeMembership;

@Mapper(componentModel = "spring")
public interface ProgrammeMembershipMapper {

  ProgrammeMembershipDto toDto(ProgrammeMembership entity);

  ProgrammeMembership toEntity(ProgrammeMembershipDto dto);

  void updateProgrammeMembership(@MappingTarget ProgrammeMembership target, ProgrammeMembership source);
}
