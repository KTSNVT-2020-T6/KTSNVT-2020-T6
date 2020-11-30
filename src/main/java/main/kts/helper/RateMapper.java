package main.kts.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.kts.dto.CulturalOfferDTO;
import main.kts.dto.RateDTO;
import main.kts.dto.RegisteredUserDTO;
import main.kts.model.CulturalOffer;
import main.kts.model.Rate;
import main.kts.model.RegisteredUser;

@Component
public class RateMapper implements MapperInterface<Rate, RateDTO>{

	@Autowired
	RegisteredUserMapper registeredUserMapper;
	@Autowired 
	CulturalOfferMapper culturalOfferMapper;
	
	@Override
	public Rate toEntity(RateDTO dto) {
		RegisteredUser registeredUser = registeredUserMapper.toEntity(dto.getRegistredUserDTO());
		CulturalOffer culturalOffer = culturalOfferMapper.toEntity(dto.getCulturalOfferDTO());
		return new Rate(dto.getNumber(),registeredUser, culturalOffer);
	}

	@Override
	public RateDTO toDto(Rate entity) {
		RegisteredUserDTO registeredUserDTO = registeredUserMapper.toDto(entity.getRegistredUser());
		CulturalOfferDTO culturalOfferDTO = culturalOfferMapper.toDto(entity.getCulturalOffer());
		return new RateDTO(entity.getNumber(), registeredUserDTO, culturalOfferDTO);
	}

}
