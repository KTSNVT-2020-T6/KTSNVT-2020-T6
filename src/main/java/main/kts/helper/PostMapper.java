package main.kts.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.kts.dto.ImageDTO;
import main.kts.dto.PostDTO;
import main.kts.model.Image;
import main.kts.model.Post;

@Component
public class PostMapper implements MapperInterface<Post, PostDTO> {

	@Autowired
	ImageMapper imageMapper;
	
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
