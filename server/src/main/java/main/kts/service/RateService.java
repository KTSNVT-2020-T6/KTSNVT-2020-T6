package main.kts.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import main.kts.model.CulturalOffer;
import main.kts.model.Rate;
import main.kts.model.RegisteredUser;
import main.kts.repository.CulturalOfferRepository;
import main.kts.repository.RateRepository;
import main.kts.repository.RegisteredUserRepository;

@Service
public class RateService implements ServiceInterface<Rate>{

	@Autowired
	private RateRepository rateRepository;
	
	@Autowired
	private RegisteredUserRepository registeredUserRepository;
	
	@Autowired
	private CulturalOfferRepository culturalOfferRepository;
	
	@Override
	public List<Rate> findAll() {
		return rateRepository.findByActive(true);
	}

	@Override
	public Rate findOne(Long id) {
		return rateRepository.findByIdAndActive(id, true).orElse(null);
	}

	@Override
	public Rate create(Rate entity) throws Exception {	
		// if user or offer don't exist
		Optional<RegisteredUser> ru = registeredUserRepository.findById(entity.getRegistredUser().getId());
		if(!ru.isPresent()) {
			throw new Exception("User does not exist");	
		}
		Optional<CulturalOffer> offer = culturalOfferRepository.findById(entity.getCulturalOffer().getId());
		if(!offer.isPresent())
			throw new Exception("Cultural offer does not exist");

		// if rate with same user and same offer exists
		Rate existingRate = rateRepository.findOneByRegisteredUserIdAndCulturalOfferId(entity.getRegistredUser().getId(), entity.getCulturalOffer().getId());
		if (existingRate != null)
			throw new Exception("Given user already rated given cultural offer");

		// make new rate instance
		Rate r = new Rate();
		r.setNumber(entity.getNumber());
		r.setRegistredUser(ru.orElse(null));
		r.setCulturalOffer(offer.orElse(null));
		r.setActive(true);
		// update average rate
		CulturalOffer co = culturalOfferRepository.findById(entity.getCulturalOffer().getId()).orElse(null);
		List<Rate> rates = rateRepository.findAllByCulturalOfferId(co.getId());
		co.setAverageRate((co.getAverageRate()*rates.size()+entity.getNumber())/(rates.size()+1));
		co = culturalOfferRepository.save(co);
		
		r = rateRepository.save(r);
		return r;
	}

	@Override
	public Rate update(Rate entity, Long id) throws Exception {
		Optional<Rate> optRate = rateRepository.findById(id);
		if(!optRate.isPresent()) {
			throw new Exception("Rate with given id doesn't exist");
		}
		Rate existingRate = optRate.orElse(null);
		
		int oldRate = existingRate.getNumber();
		existingRate.setNumber(entity.getNumber());
		
		// update average rate
		CulturalOffer co = culturalOfferRepository.findById(entity.getCulturalOffer().getId()).orElse(null);
		List<Rate> rates = rateRepository.findAllByCulturalOfferId(co.getId());
		co.setAverageRate((co.getAverageRate()*rates.size()-oldRate+entity.getNumber())/rates.size());
		co = culturalOfferRepository.save(co);
				
		return rateRepository.save(existingRate);
	}

	@Override
	public void delete(Long id) throws Exception {
		Optional<Rate> optRate = rateRepository.findById(id);
		if(!optRate.isPresent()) {
			throw new Exception("Rate with given id doesn't exist");
		}
		Rate existingRate = optRate.orElse(null);
		existingRate.setActive(false);
		rateRepository.save(existingRate);		
	}

	public Page<Rate> findAll(Pageable pageable) {
		return rateRepository.findByActive(pageable, true);
	}

	public Rate check(Rate entity) {
		Rate existingRate = rateRepository.findOneByRegisteredUserIdAndCulturalOfferId(entity.getRegistredUser().getId(), entity.getCulturalOffer().getId());
		if (existingRate != null)
			return existingRate;
		return null;

	}

}

