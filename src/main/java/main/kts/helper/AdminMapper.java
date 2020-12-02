package main.kts.helper;

import java.util.HashSet;
import java.util.Set;

import main.kts.dto.AdminDTO;
import main.kts.dto.AuthorityDTO;
import main.kts.dto.CulturalOfferDTO;
import main.kts.dto.RegisteredUserDTO;
import main.kts.model.Admin;
import main.kts.model.Authority;
import main.kts.model.CulturalOffer;
import main.kts.model.RegisteredUser;

public class AdminMapper implements MapperInterface<Admin, AdminDTO>{
	
	CulturalOfferMapper culturalOfferMapper = new CulturalOfferMapper();

	ImageMapper imageMapper = new ImageMapper();
	
	public AdminMapper() {
		super();
	}

	@Override
	public Admin toEntity(AdminDTO dto) {
		Set<CulturalOffer> culturalOffers = new HashSet<CulturalOffer>();
		for(CulturalOfferDTO culturalOfferDTO : dto.getCulturalOfferDTO()) {
			culturalOffers.add(culturalOfferMapper.toEntity(culturalOfferDTO));
		}
		Set<Authority> auth = new HashSet<Authority>();
		auth.add(new Authority(2L,"ADMIN"));
		return new Admin(dto.getId(),dto.getFirstName(), dto.getLastName(),dto.getEmail(), dto.getPassword(), dto.getActive(), dto.getVerified(), imageMapper.toEntity(dto.getImageDTO()), auth,culturalOffers);
	}

	@Override
	public AdminDTO toDto(Admin entity) {
		Set<CulturalOfferDTO> culturalOffersDTO = new HashSet<CulturalOfferDTO>();
		for(CulturalOffer culturalOffer: entity.getCulturalOffer()) {
			culturalOffersDTO.add(culturalOfferMapper.toDto(culturalOffer));
		}

		Set<AuthorityDTO> auth = new HashSet<AuthorityDTO>();
		auth.add(new AuthorityDTO(2L,"ADMIN"));
		return new AdminDTO(entity.getId(),entity.getFirstName(),entity.getLastName(), entity.getEmail(), entity.getPassword(), entity.getActive(), entity.getVerified()
				, imageMapper.toDto(entity.getImage()), auth,culturalOffersDTO);
	}

}
