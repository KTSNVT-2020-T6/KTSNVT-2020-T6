package main.kts.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.kts.dto.RateDTO;
import main.kts.helper.RateMapper;
import main.kts.model.CulturalOffer;
import main.kts.model.Rate;
import main.kts.model.RegisteredUser;
import main.kts.service.CulturalOfferService;
import main.kts.service.RateService;
import main.kts.service.RegisteredUserService;

@RestController
@RequestMapping(value = "/api/rate", produces = MediaType.APPLICATION_JSON_VALUE)
public class RateController {

	@Autowired
	private RateService rateService;

	private RateMapper rateMapper;

	@Autowired
	private RegisteredUserService registeredUserService;
	
	@Autowired
	private CulturalOfferService culturalOfferService;
	
	public RateController() {
		rateMapper = new RateMapper();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<RateDTO>> getAllRates() {
		List<Rate> rates = rateService.findAll();

		return new ResponseEntity<>(toRateDTOList(rates), HttpStatus.OK);
	}
	
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<RateDTO> getRate(@PathVariable Long id){
        Rate rate = rateService.findOne(id);
        if(rate == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(rateMapper.toDto(rate), HttpStatus.OK);
    }
    
//    @RequestMapping(value = "/page", method=RequestMethod.GET)
//    public ResponseEntity<Page<RateDTO>> loadCharactersPage(Pageable pageable) {
//    	Page<Rate> rates = rateService.findAllPage(pageable);
//    	if(rates == null){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    	return new ResponseEntity<>(toRateDTOPage(rates), HttpStatus.OK);
//    }
    

	@RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<RateDTO> createRate(@RequestBody RateDTO rateDTO){
    	Rate rate;
    	RegisteredUser registeredUser;
    	CulturalOffer culturalOffer;
    	if(!this.validateRateDTO(rateDTO))
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	
        try {
        	registeredUser = registeredUserService.findOne(rateDTO.getRegistredUserDTO().getId());
        	culturalOffer = culturalOfferService.findOne(rateDTO.getCulturalOfferDTO().getId());
        	rate = rateMapper.toEntity(rateDTO);
        	rate.setCulturalOffer(culturalOffer);
        	rate.setRegistredUser(registeredUser);
            rate = rateService.create(rate);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(rateMapper.toDto(rate), HttpStatus.OK);
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RateDTO> updateRate(@RequestBody RateDTO rateDTO, @PathVariable Long id){
        Rate rate;
        RegisteredUser registeredUser;
    	CulturalOffer culturalOffer;
        try {
        	registeredUser = registeredUserService.findOne(rateDTO.getRegistredUserDTO().getId());
        	culturalOffer = culturalOfferService.findOne(rateDTO.getCulturalOfferDTO().getId());
        	rate = rateMapper.toEntity(rateDTO);
        	rate.setCulturalOffer(culturalOffer);
        	rate.setRegistredUser(registeredUser);
            rate = rateService.update(rate, id);
            
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(rateMapper.toDto(rate), HttpStatus.OK);
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<String> deleteRate(@PathVariable Long id){
        try {
            rateService.delete(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
    
	
	private List<RateDTO> toRateDTOList(List<Rate> rates){
        List<RateDTO> rateDTOS = new ArrayList<>();
        for (Rate rate: rates) {
            rateDTOS.add(rateMapper.toDto(rate));
        }
        return rateDTOS;
    }
	
//	private Object toRateDTOPage(Page<Rate> rates) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
	private boolean validateRateDTO(RateDTO rateDTO) {
		if(rateDTO.getNumber() <= 0 || rateDTO.getNumber() > 5)
			return false;
		if(rateDTO.getRegistredUserDTO() == null) 
			return false;
		if(rateDTO.getCulturalOfferDTO() == null) 
			return false;
		return true;
	}
}
