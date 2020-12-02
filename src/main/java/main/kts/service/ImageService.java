package main.kts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.kts.model.Image;
import main.kts.repository.ImageRepository;

@Service
public class ImageService implements ServiceInterface<Image>{

	@Autowired
	private ImageRepository imageRepository;
	
	@Override
	public List<Image> findAll() {
		return imageRepository.findAll();
	}

	@Override
	public Image findOne(Long id) {
		return imageRepository.findById(id).orElse(null);
	}

	@Override
	public Image create(Image entity) throws Exception {
		//make new image instance
		Image i = new Image();
		i.setName(entity.getName());
		i.setrelativePath(entity.getrelativePath());
		
		i = imageRepository.save(i);
		return i;
	}

	@Override
	public Image update(Image entity, Long id) throws Exception {
		Image existingImage = imageRepository.findById(id).orElse(null);
		if(existingImage == null) {
			throw new Exception("Image with given id doesn't exist");
		}
		
		existingImage.setName(entity.getName());
		existingImage.setRelativePath(entity.getRelativePath());
		return imageRepository.save(existingImage);
	}

	@Override
	public void delete(Long id) throws Exception {
		Image existingImage = imageRepository.findById(id).orElse(null);
		if(existingImage == null) {
			throw new Exception("Image with given id doesn't exist");
		}
		imageRepository.delete(existingImage);		
	}

}
