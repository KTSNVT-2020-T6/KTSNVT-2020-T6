package main.kts.helper;

import main.kts.dto.ImageDTO;
import main.kts.model.Image;


public class ImageMapper implements MapperInterface<Image, ImageDTO> {

	public ImageMapper() {}
	
	@Override
	public Image toEntity(ImageDTO dto) {
		return new Image(dto.getId(),dto.getName(), dto.getRelativePath());
	}

	@Override
	public ImageDTO toDto(Image entity) {
		return new ImageDTO(entity.getId(),entity.getName(), entity.getrelativePath());
	}

}
