package main.kts.helper;

import org.springframework.stereotype.Component;

import main.kts.dto.AuthorityDTO;
import main.kts.model.Authority;

@Component
public class AuthorityMapper implements MapperInterface<Authority, AuthorityDTO>{

	@Override
	public Authority toEntity(AuthorityDTO dto) {
		return new Authority(dto.getRole());
	}

	@Override
	public AuthorityDTO toDto(Authority entity) {
		return new AuthorityDTO(entity.getRole());
	}

}
