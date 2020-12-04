package main.kts.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.kts.model.Comment;
import main.kts.model.CulturalOffer;
import main.kts.model.Image;
import main.kts.model.Post;
import main.kts.model.Rate;
import main.kts.model.RegisteredUser;
import main.kts.repository.CulturalOfferRepository;
import main.kts.repository.ImageRepository;
import main.kts.repository.RateRepository;
import main.kts.repository.RegisteredUserRepository;

@Service
public class CulturalOfferService implements ServiceInterface<CulturalOffer> {

	@Autowired
	private CulturalOfferRepository culturalOfferRepository;
	@Autowired
	private RegisteredUserRepository registeredUserRepository;
	@Autowired
	private RateRepository rateRepository;
	@Autowired
	private EmailService emailService;
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
		if (entity.getDate() != null)
			co.setDate(entity.getDate());
		else
			co.setDate(null);
		co.setLat(entity.getLat());
		co.setLon(entity.getLon());
		co.setType(entity.getType());
		if (entity.getImage() != null) {
			co.setImage(entity.getImage());
		} else
			co.setImage(null); // or default image??
		co.setComment(new HashSet<Comment>());
		co.setPost(new HashSet<Post>());
		co.setActive(true);
		
		co = culturalOfferRepository.save(co);
		return co;
	}

	@Override
	public CulturalOffer update(CulturalOffer entity, Long id) throws Exception {
		CulturalOffer existingCO = culturalOfferRepository.findById(id).orElse(null);
		if (existingCO == null) {
			throw new Exception("Cultural offer with given id doesn't exist");
		}

		/***
		 * set everything except averageRate, posts and comments (it is changed only
		 * through other methods)
		 */
		existingCO.setDescription(entity.getDescription());
		existingCO.setName(entity.getName());
		existingCO.setDate(entity.getDate());
		existingCO.setLat(entity.getLat());
		existingCO.setLon(entity.getLon());
		existingCO.setType(entity.getType());
		Set<Image> oldImages = imageRepository.findAllByCulturalOfferId(id);
		existingCO.setImage(oldImages);

		return culturalOfferRepository.save(existingCO);
	}

	@Override
	public void delete(Long id) throws Exception {
		CulturalOffer existingCO = culturalOfferRepository.findById(id).orElse(null);
		if (existingCO == null) {
			throw new Exception("Cultural offer with given id doesn't exist");
		}

		// find and delete all rates connected to this offer
		// comments, posts and images are also deleted because of CascadeType.ALL
		List<Rate> rates = rateRepository.findAllByCulturalOfferId(existingCO.getId());
		for (Rate rate : rates) {
			rate.setActive(false);
			rateRepository.save(rate);
		}
		List<Long> usersId = registeredUserRepository.findByIdCO(existingCO.getId());
		ArrayList<RegisteredUser> users = getListOfRegisteredUser(usersId);
		emailService.nofiticationForUpdateCulturalOffer(users, existingCO.getName());
		existingCO.setActive(false);
		culturalOfferRepository.save(existingCO);

	}

	private ArrayList<RegisteredUser> getListOfRegisteredUser(List<Long> usersId) {

		ArrayList<RegisteredUser> users = new ArrayList<RegisteredUser>();
		for (Long l : usersId) {
			RegisteredUser ru = registeredUserRepository.findByIdRU(l);
			users.add(ru);
		}
		return users;
	}


	public Page<CulturalOffer> findAll(Pageable pageable) {
		return culturalOfferRepository.findAll(pageable);
	}

}
