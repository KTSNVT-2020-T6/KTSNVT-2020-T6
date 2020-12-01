package main.kts.helper;

import java.util.HashSet;
import java.util.Set;

import main.kts.dto.AdminDTO;
import main.kts.dto.CulturalOfferDTO;
import main.kts.model.Admin;
import main.kts.model.CulturalOffer;

public class AdminMapper implements MapperInterface<Admin, AdminDTO>{
	
	CulturalOfferMapper culturalOfferMapper = new CulturalOfferMapper();
	
	public AdminMapper() {
		super();
	}

	@Override
	public Admin toEntity(AdminDTO dto) {
		Set<CulturalOffer> culturalOffers = new HashSet<CulturalOffer>();
		for(CulturalOfferDTO culturalOfferDTO : dto.getCulturalOfferDTO()) {
			culturalOffers.add(culturalOfferMapper.toEntity(culturalOfferDTO));
		}
		return new Admin(culturalOffers);
	}

	@Override
	public AdminDTO toDto(Admin entity) {
		Set<CulturalOfferDTO> culturalOffersDTO = new HashSet<CulturalOfferDTO>();
		for(CulturalOffer culturalOffer: entity.getCulturalOffer()) {
			culturalOffersDTO.add(culturalOfferMapper.toDto(culturalOffer));
		}
		return new AdminDTO(culturalOffersDTO);
	}

}
