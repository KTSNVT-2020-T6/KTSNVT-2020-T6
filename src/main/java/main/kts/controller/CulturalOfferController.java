package main.kts.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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

import main.kts.dto.CulturalOfferDTO;
import main.kts.helper.CulturalOfferMapper;
import main.kts.model.CulturalOffer;
import main.kts.model.Type;
import main.kts.service.CulturalOfferService;
import main.kts.service.TypeService;

@RestController
@RequestMapping(value = "/api/culturaloffer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CulturalOfferController {
	
	@Autowired
	private CulturalOfferService culturalOfferService;
	
	@Autowired
	private TypeService typeService;
	
	private CulturalOfferMapper culturalOfferMapper;
	
	public CulturalOfferController() {
		culturalOfferMapper = new CulturalOfferMapper();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CulturalOfferDTO>> getAllCulturalOffers() {
		List<CulturalOffer> culturalOffers = culturalOfferService.findAll();

		return new ResponseEntity<>(toCulturalOfferDTOList(culturalOffers), HttpStatus.OK);
	}
	
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<CulturalOfferDTO> getCulturalOffer(@PathVariable Long id){
        CulturalOffer culturalOffer = culturalOfferService.findOne(id);
        if(culturalOffer == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(culturalOfferMapper.toDto(culturalOffer), HttpStatus.OK);
    }
    
    @RequestMapping(value="/",method=RequestMethod.GET)
    public ResponseEntity<Page<CulturalOfferDTO>> loadRatePage(Pageable pageable) {
    	Page<CulturalOffer> culturalOffers = culturalOfferService.findAll(pageable);
    	if(culturalOffers == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    	Page<CulturalOfferDTO> culturalOffersDTO = toCulturalOfferDTOPage(culturalOffers);
    	return new ResponseEntity<>(culturalOffersDTO, HttpStatus.OK);
    }
    
    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity<CulturalOfferDTO> createCulturalOffer(@RequestBody CulturalOfferDTO culturalOfferDTO){
    	CulturalOffer culturalOffer = null;
    	Type type;
    	if(!this.validateCulturalOfferDTO(culturalOfferDTO))
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	
        try {
        	type = typeService.findOne(culturalOfferDTO.getTypeDTO().getId());
        	culturalOffer = culturalOfferMapper.toEntity(culturalOfferDTO);
        	culturalOffer.setType(type);
            culturalOffer = culturalOfferService.create(culturalOffer);
        } catch (Exception e) {
            return new ResponseEntity<>(new CulturalOfferDTO(),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(culturalOfferMapper.toDto(culturalOffer), HttpStatus.OK);
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CulturalOfferDTO> updateCulturalOffer(@RequestBody CulturalOfferDTO culturalOfferDTO, @PathVariable Long id){
        CulturalOffer culturalOffer;
        Type type;
        if(!this.validateCulturalOfferDTO(culturalOfferDTO))
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	
        try {
        	type = typeService.findOne(culturalOfferDTO.getTypeDTO().getId());
        	culturalOffer = culturalOfferMapper.toEntity(culturalOfferDTO);
        	culturalOffer.setType(type);
            culturalOffer = culturalOfferService.update(culturalOffer, id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(culturalOfferMapper.toDto(culturalOffer), HttpStatus.OK);
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Void> deleteCulturalOffer(@PathVariable Long id){
        try {
            culturalOfferService.delete(id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
    
	
	private List<CulturalOfferDTO> toCulturalOfferDTOList(List<CulturalOffer> culturalOffers){
        List<CulturalOfferDTO> culturalOfferDTOS = new ArrayList<>();
        for (CulturalOffer culturalOffer: culturalOffers) {
            culturalOfferDTOS.add(culturalOfferMapper.toDto(culturalOffer));
        }
        return culturalOfferDTOS;
    }
	
	private Page<CulturalOfferDTO> toCulturalOfferDTOPage(Page<CulturalOffer> culturalOffers) {
		Page<CulturalOfferDTO> dtoPage = culturalOffers.map(new Function<CulturalOffer, CulturalOfferDTO>() {
		    @Override
		    public CulturalOfferDTO apply(CulturalOffer entity) {
		    	CulturalOfferDTO dto = culturalOfferMapper.toDto(entity);
		        return dto;
		    }
		});
		return dtoPage;
	}
	
	private boolean validateCulturalOfferDTO(CulturalOfferDTO culturalOfferDTO) {
		if(culturalOfferDTO.getDescription() == null || culturalOfferDTO.getDescription().equals(""))
			return false;
		if(culturalOfferDTO.getName() == null || culturalOfferDTO.getName().equals(""))
			return false;
		if(culturalOfferDTO.getLat() < -90 || culturalOfferDTO.getLat() > 90)
			return false;
		if(culturalOfferDTO.getLon() < -180 || culturalOfferDTO.getLon() > 180)
			return false;
		if(culturalOfferDTO.getTypeDTO() == null)
			return false;
		
		return true;
	}
}
