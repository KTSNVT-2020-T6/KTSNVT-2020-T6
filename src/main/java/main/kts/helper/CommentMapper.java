package main.kts.helper;

import main.kts.dto.CommentDTO;
import main.kts.dto.CulturalOfferDTO;
import main.kts.dto.ImageDTO;
import main.kts.dto.RegisteredUserDTO;
import main.kts.model.Comment;
import main.kts.model.CulturalOffer;
import main.kts.model.Image;
import main.kts.model.RegisteredUser;

public class CommentMapper implements MapperInterface<Comment, CommentDTO>{

	 
	RegisteredUserMapper registeredUserMapper = new RegisteredUserMapper();
	
	ImageMapper imageMapper = new ImageMapper();
	 
	CulturalOfferMapper culturalOfferMapper = new CulturalOfferMapper();
	
	public CommentMapper() {
		
	}
	
	@Override
	public Comment toEntity(CommentDTO dto) {
		RegisteredUser registeredUser = registeredUserMapper.toEntity(dto.getRegisteredUserDTO());
		Image image = imageMapper.toEntity(dto.getImageDTO());
		CulturalOffer culturalOffer = culturalOfferMapper.toEntity(dto.getCulturalOfferDTO());
		return new Comment(dto.getId(),dto.getText(), dto.getDate(), registeredUser, image, culturalOffer);
	}

	@Override
	public CommentDTO toDto(Comment entity) {
		RegisteredUserDTO registeredUserDTO = registeredUserMapper.toDto(entity.getRegistredUser());
		ImageDTO imageDTO = imageMapper.toDto(entity.getImage());
		CulturalOfferDTO culturalOfferDTO = culturalOfferMapper.toDto(entity.getCulturalOffer());
		return new CommentDTO(entity.getId(),entity.getText(), entity.getDate(), registeredUserDTO, imageDTO, culturalOfferDTO);
	}

}
