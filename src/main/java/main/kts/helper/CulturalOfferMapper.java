package main.kts.helper;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.kts.dto.CommentDTO;
import main.kts.dto.CulturalOfferDTO;
import main.kts.dto.ImageDTO;
import main.kts.dto.PostDTO;
import main.kts.dto.TypeDTO;
import main.kts.model.Comment;
import main.kts.model.CulturalOffer;
import main.kts.model.Image;
import main.kts.model.Post;
import main.kts.model.Type;
@Component
public class CulturalOfferMapper implements MapperInterface<CulturalOffer, CulturalOfferDTO>{

	@Autowired
	PostMapper postMapper;
	@Autowired
	TypeMapper typeMapper;
	@Autowired 
	ImageMapper imageMapper;
	@Autowired
	CommentMapper commentMapper;
	
	@Override
	public CulturalOffer toEntity(CulturalOfferDTO dto) {
		Set<Post> posts = new HashSet<Post>();
		Set<Image> images = new HashSet<Image>();
		Set<Comment> comments = new HashSet<Comment>();
		for (PostDTO postDTO : dto.getPostDTO()) 
			posts.add(postMapper.toEntity(postDTO));
		
		for(ImageDTO imageDTO : dto.getImageDTO()) 
			images.add(imageMapper.toEntity(imageDTO));
		
		for(CommentDTO commentDTO : dto.getCommentDTO()) 
			comments.add(commentMapper.toEntity(commentDTO));
		
		Type type = typeMapper.toEntity(dto.getTypeDTO());
		return new CulturalOffer(dto.getAverageRate(), dto.getDescription(), dto.getName(), dto.getDate(), dto.getLat(), dto.getLon(), posts, type, images, comments);
	}

	@Override
	public CulturalOfferDTO toDto(CulturalOffer entity) {
		Set<PostDTO> postsDTO = new HashSet<PostDTO>();
		Set<ImageDTO> imagesDTO = new HashSet<ImageDTO>();
		Set<CommentDTO> commentsDTO = new HashSet<CommentDTO>();
		for (Post post : entity.getPost()) 
			postsDTO.add(postMapper.toDto(post));
		
		for(Image image : entity.getImage()) 
			imagesDTO.add(imageMapper.toDto(image));
		
		for(Comment comment : entity.getComment()) 
			commentsDTO.add(commentMapper.toDto(comment));
		
		TypeDTO typeDTO = typeMapper.toDto(entity.getType());
		return new CulturalOfferDTO(entity.getAverageRate(), entity.getDescription(), entity.getName(), entity.getDate(), entity.getLat(), entity.getLon(), postsDTO, typeDTO, imagesDTO, commentsDTO);
	}

}
