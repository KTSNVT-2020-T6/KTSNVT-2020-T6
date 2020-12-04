package main.kts.helper;

import java.util.HashSet;
import java.util.Set;

import main.kts.dto.CulturalOfferDTO;
import main.kts.dto.ImageDTO;
import main.kts.dto.TypeDTO;
import main.kts.model.Comment;
import main.kts.model.CulturalOffer;
import main.kts.model.Image;
import main.kts.model.Post;
import main.kts.model.Type;

public class CulturalOfferMapper implements MapperInterface<CulturalOffer, CulturalOfferDTO>{

	TypeMapper typeMapper = new TypeMapper();
	ImageMapper imageMapper = new ImageMapper();
	
	public CulturalOfferMapper() {}
	
	@Override
	public CulturalOffer toEntity(CulturalOfferDTO dto) {
		Set<Post> posts = new HashSet<Post>();
		Set<Image> images = new HashSet<Image>();
		Set<Comment> comments = new HashSet<Comment>();
		
		for(ImageDTO imageDTO : dto.getImageDTO()) 
			images.add(imageMapper.toEntity(imageDTO));
	
		Type type = typeMapper.toEntity(dto.getTypeDTO());
		return new CulturalOffer(dto.getId(),dto.getAverageRate(), dto.getDescription(), dto.getName(), dto.getDate(), dto.getLat(), dto.getLon(), posts, type, images, comments);
	}

	@Override
	public CulturalOfferDTO toDto(CulturalOffer entity) {
		Set<ImageDTO> imagesDTO = new HashSet<ImageDTO>();
		for(Image image : entity.getImage()) 
			imagesDTO.add(imageMapper.toDto(image));

		TypeDTO typeDTO = typeMapper.toDto(entity.getType());
		return new CulturalOfferDTO(entity.getId(),entity.getAverageRate(), entity.getDescription(), entity.getName(), entity.getDate(), entity.getLat(), entity.getLon(), typeDTO, imagesDTO);
	}

}
