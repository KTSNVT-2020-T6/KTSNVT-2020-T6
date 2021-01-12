package main.kts.helper;


import main.kts.dto.CommentDTO;
import main.kts.dto.ImageDTO;
import main.kts.model.Comment;
import main.kts.model.CulturalOffer;
import main.kts.model.Image;
import main.kts.model.RegisteredUser;

public class CommentMapper implements MapperInterface<Comment, CommentDTO>{
	
	ImageMapper imageMapper = new ImageMapper();
	
	public CommentMapper() {}
	
	@Override
	public Comment toEntity(CommentDTO dto) {
		RegisteredUser registeredUser = new RegisteredUser();
		CulturalOffer culturalOffer = new CulturalOffer(); 
		Image image = imageMapper.toEntity(dto.getImageDTO());
		return new Comment(dto.getId(),dto.getText(), dto.getDate(), registeredUser, image, culturalOffer);
	}

	@Override
	public CommentDTO toDto(Comment entity) {
		String nameSurname = entity.getRegistredUser().getFirstName()+" "+entity.getRegistredUser().getLastName();
		Long userId = entity.getRegistredUser().getId();
		ImageDTO imageDTO = null;
		if(entity.getImage() != null) {
			imageDTO = imageMapper.toDto(entity.getImage());
		}
		
		ImageDTO userImage = imageMapper.toDto(entity.getRegistredUser().getImage());
		Long culturalOfferId = entity.getCulturalOffer().getId();
		return new CommentDTO(entity.getId(), entity.getText(), entity.getDate(), nameSurname, userId, userImage, imageDTO, culturalOfferId);
	}

}
