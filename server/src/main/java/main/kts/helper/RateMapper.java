package main.kts.helper;

import main.kts.dto.RateDTO;
import main.kts.model.CulturalOffer;
import main.kts.model.Rate;
import main.kts.model.RegisteredUser;

public class RateMapper implements MapperInterface<Rate, RateDTO>{

	RegisteredUserMapper registeredUserMapper = new RegisteredUserMapper();
	
	CulturalOfferMapper culturalOfferMapper = new CulturalOfferMapper();
	
	ImageMapper imageMapper = new ImageMapper();

	public RateMapper() {}
	
	@Override
	public Rate toEntity(RateDTO dto) {
		RegisteredUser registeredUser = new RegisteredUser();
		CulturalOffer culturalOffer = new CulturalOffer();
		return new Rate(dto.getId(),dto.getNumber(),registeredUser, culturalOffer);
	}

	@Override
	public RateDTO toDto(Rate entity) {
		return new RateDTO(entity.getId(),entity.getNumber(), entity.getRegistredUser().getId(), entity.getCulturalOffer().getId());
	}

}
