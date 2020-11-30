package main.kts.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.kts.model.Rate;
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
		return rateRepository.findAll();
	}

	@Override
	public Rate findOne(Long id) {
		return rateRepository.findById(id).orElse(null);
	}

	@Override
	public Rate create(Rate entity) throws Exception {		
		// if user or offer don't exist
		if(registeredUserRepository.findById(entity.getRegistredUser().getId()).orElse(null) == null)
			throw new Exception("User doesn't exist");		
		if(culturalOfferRepository.findById(entity.getCulturalOffer().getId()).orElse(null) == null)
			throw new Exception("Cultural offer doesn't exist");
		
		// if rate with same user and same offer exists
		Rate existingRate = rateRepository.findOneByRegisteredUserId(entity.getRegistredUser().getId());
		if (existingRate != null && existingRate.getCulturalOffer().getName().equals(entity.getCulturalOffer().getName()))
			throw new Exception("Given user already rated given cultural offer");

		// make new rate instance
		Rate r = new Rate();
		r.setNumber(entity.getNumber());
		r.setRegistredUser(entity.getRegistredUser());
		r.setCulturalOffer(entity.getCulturalOffer());
		
		r = rateRepository.save(r);
		return r;
	}

	@Override
	public Rate update(Rate entity, Long id) throws Exception {
		Rate existingRate = rateRepository.findById(id).orElse(null);
		if(existingRate == null) {
			throw new Exception("Rate with given id doesn't exist");
		}
		
		existingRate.setNumber(entity.getNumber());
		return rateRepository.save(existingRate);
	}

	@Override
	public void delete(Long id) throws Exception {
		Rate existingRate = rateRepository.findById(id).orElse(null);
		if(existingRate == null) {
			throw new Exception("Rate with given id doesn't exist");
		}
		rateRepository.delete(existingRate);		
	}

}

