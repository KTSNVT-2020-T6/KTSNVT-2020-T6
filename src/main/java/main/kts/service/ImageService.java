package main.kts.service;

import java.util.List;

import org.springframework.stereotype.Service;

import main.kts.model.Image;

@Service
public class ImageService implements ServiceInterface<Image>{

	@Override
	public List<Image> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image create(Image entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image update(Image entity, Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
