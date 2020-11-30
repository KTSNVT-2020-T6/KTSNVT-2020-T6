package main.kts.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.kts.dto.CommentDTO;
import main.kts.dto.CulturalOfferDTO;
import main.kts.dto.ImageDTO;
import main.kts.dto.RegisteredUserDTO;
import main.kts.model.Comment;
import main.kts.model.CulturalOffer;
import main.kts.model.Image;
import main.kts.model.RegisteredUser;
@Component
public class CommentMapper implements MapperInterface<Comment, CommentDTO>{

	@Autowired 
	RegisteredUserMapper registeredUserMapper;
	@Autowired
	ImageMapper imageMapper;
	@Autowired 
	CulturalOfferMapper culturalOfferMapper;
	
	@Override
	public Comment toEntity(CommentDTO dto) {
		RegisteredUser registeredUser = registeredUserMapper.toEntity(dto.getRegisteredUserDTO());
		Image image = imageMapper.toEntity(dto.getImageDTO());
		CulturalOffer culturalOffer = culturalOfferMapper.toEntity(dto.getCulturalOfferDTO());
		return new Comment(dto.getText(), dto.getDate(), registeredUser, image, culturalOffer);
	}

	@Override
	public CommentDTO toDto(Comment entity) {
		RegisteredUserDTO registeredUserDTO = registeredUserMapper.toDto(entity.getRegistredUser());
		ImageDTO imageDTO = imageMapper.toDto(entity.getImage());
		CulturalOfferDTO culturalOfferDTO = culturalOfferMapper.toDto(entity.getCulturalOffer());
		return new CommentDTO(entity.getText(), entity.getDate(), registeredUserDTO, imageDTO, culturalOfferDTO);
	}

}
