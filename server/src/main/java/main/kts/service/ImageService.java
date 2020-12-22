package main.kts.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.kts.model.Image;
import main.kts.model.Rate;
import main.kts.repository.ImageRepository;

@Service
public class ImageService implements ServiceInterface<Image>{

	@Autowired
	private ImageRepository imageRepository;
	
	@Override
	public List<Image> findAll() {
		return imageRepository.findByActive(true);
	}

	@Override
	public Image findOne(Long id) {
		return imageRepository.findByIdAndActive(id, true).orElse(null);
	}

	@Override
	public Image create(Image entity) throws Exception {
		//make new image instance
		Image i = new Image();
		i.setName(entity.getName());
		i.setrelativePath(entity.getrelativePath());
		i.setActive(true);
		i = imageRepository.save(i);
		return i;
	}

	@Override
	public Image update(Image entity, Long id) throws Exception {
		Optional<Image> optImage = imageRepository.findById(id);
		if(!optImage.isPresent()) {
			throw new Exception("Image with given id doesn't exist");
		}
		Image existingImage = optImage.orElse(null);
		existingImage.setName(entity.getName());
		existingImage.setRelativePath(entity.getRelativePath());
		return imageRepository.save(existingImage);
	}

	@Override
	public void delete(Long id) throws Exception {
		Optional<Image> optImage = imageRepository.findById(id);
		if(!optImage.isPresent()) {
			throw new Exception("Image with given id doesn't exist");
		}
		Image existingImage = optImage.orElse(null);
		existingImage.setActive(false);
		imageRepository.save(existingImage);
	}

	public Page<Image> findAll(Pageable pageable) {
		return imageRepository.findByActive(pageable, true);
	}

}
