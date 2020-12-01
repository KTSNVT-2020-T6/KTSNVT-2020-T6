package main.kts.helper;

import main.kts.dto.ImageDTO;
import main.kts.dto.PostDTO;
import main.kts.model.Image;
import main.kts.model.Post;

public class PostMapper implements MapperInterface<Post, PostDTO> {

	
	ImageMapper imageMapper = new ImageMapper();
	
	public PostMapper() {}
	
	@Override
	public Post toEntity(PostDTO dto) {
		Image image = imageMapper.toEntity(dto.getImageDTO());
		return new Post(dto.getText(), dto.getDate(), image);	
	}

	@Override
	public PostDTO toDto(Post entity) {
		ImageDTO imageDTO = imageMapper.toDto(entity.getImage());
		return new PostDTO(entity.getText(), entity.getDate(), imageDTO);
	}

}
