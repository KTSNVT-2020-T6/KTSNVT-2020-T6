package main.kts.helper;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.kts.dto.AdminDTO;
import main.kts.dto.AuthorityDTO;
import main.kts.dto.CulturalOfferDTO;
import main.kts.model.Admin;
import main.kts.model.Authority;
import main.kts.model.CulturalOffer;

@Component
public class AdminMapper implements MapperInterface<Admin, AdminDTO>{

	@Autowired
	ImageMapper imageMapper;
	
	@Autowired
	CulturalOfferMapper culturalOfferMapper;
	
	@Override
	public Admin toEntity(AdminDTO dto) {
		Set<Authority> auth = new HashSet<Authority>();
		auth.add(new Authority("ADMIN"));
		Set<CulturalOffer> culturalOffers = new HashSet<CulturalOffer>();
		for(CulturalOfferDTO culturalOfferDTO : dto.getCulturalOfferDTO()) {
			culturalOffers.add(culturalOfferMapper.toEntity(culturalOfferDTO));
		}
		return new Admin(dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getPassword(),
				dto.getActive(), dto.getVerified(), imageMapper.toEntity(dto.getImageDTO()), auth, culturalOffers);
	}

	@Override
	public AdminDTO toDto(Admin entity) {
		Set<AuthorityDTO> auth = new HashSet<AuthorityDTO>();
		auth.add(new AuthorityDTO("ADMIN"));
		Set<CulturalOfferDTO> culturalOffersDTO = new HashSet<CulturalOfferDTO>();
		for(CulturalOffer culturalOffer: entity.getCulturalOffer()) {
			culturalOffersDTO.add(culturalOfferMapper.toDto(culturalOffer));
		}
		return new AdminDTO(entity.getFirstName(), entity.getLastName(), entity.getEmail(),
				entity.getPassword(), entity.getActive(), entity.getVerified(), imageMapper.toDto(entity.getImage()),
				auth, culturalOffersDTO);
		
	}

}
