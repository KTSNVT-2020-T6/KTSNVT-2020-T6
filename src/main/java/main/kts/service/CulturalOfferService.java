package main.kts.service;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.kts.model.Comment;
import main.kts.model.CulturalOffer;
import main.kts.model.Image;
import main.kts.model.Post;
import main.kts.model.Rate;
import main.kts.repository.CulturalOfferRepository;
import main.kts.repository.ImageRepository;
import main.kts.repository.RateRepository;

@Service
public class CulturalOfferService implements ServiceInterface<CulturalOffer>{

	@Autowired
	private CulturalOfferRepository culturalOfferRepository;
	
	@Autowired
	private RateRepository rateRepository;
	
	@Autowired
	private ImageRepository imageRepository;
	
	@Override
	public List<CulturalOffer> findAll() {
		return culturalOfferRepository.findAll();
	}

	@Override
	public CulturalOffer findOne(Long id) {
		return culturalOfferRepository.findById(id).orElse(null);
	}

	@Override
	public CulturalOffer create(CulturalOffer entity) throws Exception {
		// make new cultural offer instance
		CulturalOffer co = new CulturalOffer();
		co.setAverageRate(0.0);
		co.setDescription(entity.getDescription());
		co.setName(entity.getName());
		if(entity.getDate() != null)
			co.setDate(entity.getDate());
		else
			co.setDate(null);
		co.setLat(entity.getLat());
		co.setLon(entity.getLon());
		co.setType(entity.getType());
		if(entity.getImage() != null) {
			for(Image i : entity.getImage()) {
				if(validateImage(i)) {
					imageRepository.save(i);
				}
				else
				{
					throw new Exception("Relative path isn't valid.");
				}
			}
			
			co.setImage(entity.getImage());
		}
		else
			co.setImage(null); // or default image??
		co.setComment(new HashSet<Comment>());
		co.setPost(new HashSet<Post>());
		
		co = culturalOfferRepository.save(co);
		return co;
	}

	@Override
	public CulturalOffer update(CulturalOffer entity, Long id) throws Exception {
		CulturalOffer existingCO = culturalOfferRepository.findById(id).orElse(null);
		if(existingCO == null) {
			throw new Exception("Cultural offer with given id doesn't exist");
		}
		
		/***
		 *  set everything except averageRate, posts and comments 
		 *  (it is changed only through other methods)
		 */ 
		existingCO.setDescription(entity.getDescription());
		existingCO.setName(entity.getName());
		existingCO.setDate(entity.getDate());
		existingCO.setLat(entity.getLat());
		existingCO.setLon(entity.getLon());
		existingCO.setType(entity.getType());
		Set<Image> oldImages = imageRepository.findAllByCulturalOfferId(id);
		if(entity.getImage() != null) {
			for(Image i : entity.getImage()) {
				if(validateImage(i)) {
					imageRepository.save(i);
					oldImages.add(i);
				}
				else
				{
					throw new Exception("Not valid.");
				}
			}
			
			existingCO.setImage(oldImages);
		}

		
		return culturalOfferRepository.save(existingCO);
	}

	@Override
	public void delete(Long id) throws Exception {
		CulturalOffer existingCO = culturalOfferRepository.findById(id).orElse(null);
		if(existingCO == null) {
			throw new Exception("Cultural offer with given id doesn't exist");
		}
		
		// find and delete all rates connected to this offer
		// comments, posts and images are also deleted because of CascadeType.ALL
		List<Rate> rates = rateRepository.findAllByCulturalOfferId(existingCO.getId());
		for (Rate rate : rates) {
			rateRepository.delete(rate);
		}
		culturalOfferRepository.delete(existingCO);
	}

	private boolean validateImage(Image image) {
		if(image.getRelativePath() == null) 
			return false;
		if(image.getName() == null) 
			return false;
		try {
	        Paths.get(image.getRelativePath());
	    } catch (InvalidPathException | NullPointerException ex) {
	        return false;
	    }
		return true;
	}

}
