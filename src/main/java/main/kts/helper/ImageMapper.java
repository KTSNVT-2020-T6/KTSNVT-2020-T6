package main.kts.helper;

import org.springframework.stereotype.Component;

import main.kts.dto.ImageDTO;
import main.kts.model.Image;

@Component
public class ImageMapper implements MapperInterface<Image, ImageDTO> {

	@Override
	public Image toEntity(ImageDTO dto) {
		return new Image(dto.getName(), dto.getRelativePath());
	}

	@Override
	public ImageDTO toDto(Image entity) {
		return new ImageDTO(entity.getName(), entity.getrelativePath());
	}

}
