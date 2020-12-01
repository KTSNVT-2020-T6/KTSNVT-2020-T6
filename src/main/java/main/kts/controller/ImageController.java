package main.kts.controller;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.kts.dto.ImageDTO;
import main.kts.helper.ImageMapper;
import main.kts.model.Image;
import main.kts.service.ImageService;

@RestController
@RequestMapping(value = "/api/image", produces = MediaType.APPLICATION_JSON_VALUE)
public class ImageController {

	@Autowired
	ImageService imageService;

	ImageMapper imageMapper;

	public ImageController() {
		imageMapper = new ImageMapper();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ImageDTO>> getAllImages() {
		List<Image> images = imageService.findAll();

		return new ResponseEntity<>(toImageDTOList(images), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ImageDTO> getImage(@PathVariable Long id) {
		Image image = imageService.findOne(id);
		if (image == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(imageMapper.toDto(image), HttpStatus.OK);
	}

//  @RequestMapping(value = "/page", method=RequestMethod.GET)
//  public ResponseEntity<Page<ImageDTO>> loadCharactersPage(Pageable pageable) {
//  	Page<Image> images = imageService.findAllPage(pageable);
//  	if(images == null){
//          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//      }
//  	return new ResponseEntity<>(toImageDTOPage(images), HttpStatus.OK);
//  }

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<ImageDTO> createImage(@RequestBody ImageDTO imageDTO) {
		Image image;

		if (!this.validateImageDTO(imageDTO))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		try {
			image = imageService.create(imageMapper.toEntity(imageDTO));
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(imageMapper.toDto(image), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ImageDTO> updateImage(@RequestBody ImageDTO imageDTO, @PathVariable Long id) {
		Image image;
		try {
			image = imageService.update(imageMapper.toEntity(imageDTO), id);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(imageMapper.toDto(image), HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
		try {
			imageService.delete(id);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	private List<ImageDTO> toImageDTOList(List<Image> images) {
		List<ImageDTO> imageDTOS = new ArrayList<>();
		for (Image image : images) {
			imageDTOS.add(imageMapper.toDto(image));
		}
		return imageDTOS;
	}

//	private Object toImageDTOPage(Page<Image> images) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	private boolean validateImageDTO(ImageDTO imageDTO) {
		if(imageDTO.getRelativePath() == null) 
			return false;
		if(imageDTO.getName() == null) 
			return false;
		try {
	        Paths.get(imageDTO.getRelativePath());
	    } catch (InvalidPathException | NullPointerException ex) {
	        return false;
	    }
		return true;
	}
}
