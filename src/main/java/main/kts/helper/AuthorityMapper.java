package main.kts.helper;


import main.kts.dto.AuthorityDTO;
import main.kts.model.Authority;


public class AuthorityMapper implements MapperInterface<Authority, AuthorityDTO>{
	
	public AuthorityMapper() {}
	
	@Override
	public Authority toEntity(AuthorityDTO dto) {
		return new Authority(dto.getId(),dto.getRole());
	}

	@Override
	public AuthorityDTO toDto(Authority entity) {
		return new AuthorityDTO(entity.getId(), entity.getRole());
	}

}
