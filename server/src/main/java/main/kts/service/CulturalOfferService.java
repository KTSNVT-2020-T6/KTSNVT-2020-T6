package main.kts.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import main.kts.model.Admin;
import main.kts.model.Comment;
import main.kts.model.CulturalOffer;
import main.kts.model.Post;
import main.kts.model.Rate;
import main.kts.model.RegisteredUser;
import main.kts.model.User;
import main.kts.repository.AdminRepository;
import main.kts.repository.CommentRepository;
import main.kts.repository.CulturalOfferRepository;
import main.kts.repository.PostRepository;
import main.kts.repository.RateRepository;
import main.kts.repository.RegisteredUserRepository;

@Service
public class CulturalOfferService implements ServiceInterface<CulturalOffer> {

	@Autowired
	private CulturalOfferRepository culturalOfferRepository;
	@Autowired
	private RegisteredUserRepository registeredUserRepository;
	@Autowired
	private AdminRepository adminRepository;
	@Autowired
	private RateRepository rateRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private EmailService emailService;

	@Override
	public List<CulturalOffer> findAll() {
		return culturalOfferRepository.findByActive(true);
	}

	@Override
	public CulturalOffer findOne(Long id) {
		return culturalOfferRepository.findByIdAndActive(id, true).orElse(null);
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
		co.setCity(entity.getCity());
		co.setType(entity.getType());
		if (entity.getImage() != null) {
			co.setImage(entity.getImage());
		} else
			co.setImage(null); // or default image??
		co.setComment(new HashSet<Comment>());
		co.setPost(new HashSet<Post>());
		co.setActive(true);

		co = culturalOfferRepository.save(co);

		// set admin that created this cultural offer
		Admin admin;
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String username = currentUser.getName(); 
		admin = adminRepository.findByEmail(username);
		admin.getCulturalOffer().add(co);
		adminRepository.save(admin);
		
		return co;
	}

	@Override
	public CulturalOffer update(CulturalOffer entity, Long id) throws Exception {
		Optional<CulturalOffer> optCO = culturalOfferRepository.findById(id);
		if (!optCO.isPresent()) {
			throw new Exception("Cultural offer with given id doesn't exist");
		}
		CulturalOffer existingCO = optCO.orElse(null);

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
		existingCO.setCity(entity.getCity());

		List<Long> usersId = registeredUserRepository.findByIdCO(existingCO.getId());
		ArrayList<RegisteredUser> users = getListOfRegisteredUser(usersId);
		emailService.nofiticationForUpdateCulturalOffer(users, existingCO.getName());

		return culturalOfferRepository.save(existingCO);
	}

	@Override
	public void delete(Long id) throws Exception {
		Optional<CulturalOffer> optCO = culturalOfferRepository.findById(id);
		if (!optCO.isPresent()) {
			throw new Exception("Cultural offer with given id doesn't exist");
		}
		CulturalOffer existingCO = optCO.orElse(null);

		// find and delete all rates, comments, posts and images connected to this offer
		List<Rate> rates = rateRepository.findAllByCulturalOfferId(existingCO.getId());
		for (Rate rate : rates) {
			rate.setActive(false);
			rateRepository.save(rate);
		}
		for (Post post : existingCO.getPost()) {
			post.setActive(false);
			postRepository.save(post);
		}
		for (Comment comment : existingCO.getComment()) {
			comment.setActive(false);
			commentRepository.save(comment);
		}

		List<Long> usersId = registeredUserRepository.findByIdCO(existingCO.getId());
		ArrayList<RegisteredUser> users = getListOfRegisteredUser(usersId);
		emailService.nofiticationForDeleteCulturalOffer(users, existingCO.getName());
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
		return culturalOfferRepository.findByActive(pageable, true);
	}

	public void saveAndSendMail(CulturalOffer culturalOffer) throws Exception {
		culturalOfferRepository.save(culturalOffer);
		List<Long> usersId = registeredUserRepository.findByIdCO(culturalOffer.getId());
		ArrayList<RegisteredUser> users = getListOfRegisteredUser(usersId);
		emailService.nofiticationForAddingPost(users, culturalOffer.getName());
	}

	public List<CulturalOffer> findByCity(String city) {
		return culturalOfferRepository.findByCity(city);
	}

	public List<CulturalOffer> findByContent(String content) {
		
		return culturalOfferRepository.findByContent(content);
	}

}
