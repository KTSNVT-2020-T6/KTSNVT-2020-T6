package main.kts.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.kts.dto.CulturalOfferDTO;
import main.kts.dto.ImageDTO;
import main.kts.helper.CulturalOfferMapper;
import main.kts.model.CulturalOffer;
import main.kts.model.Image;
import main.kts.model.RegisteredUser;
import main.kts.model.Type;
import main.kts.model.User;
import main.kts.repository.CulturalOfferRepository;
import main.kts.service.CulturalOfferService;
import main.kts.service.ImageService;
import main.kts.service.RegisteredUserService;
import main.kts.service.TypeService;


@RestController
@RequestMapping(value = "/api/culturaloffer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CulturalOfferController {
	
	@Autowired
	private CulturalOfferService culturalOfferService;
	
	@Autowired
	private TypeService typeService;
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private RegisteredUserService registeredUserService;
	
	
	private CulturalOfferMapper culturalOfferMapper;
	
	public CulturalOfferController() {
		culturalOfferMapper = new CulturalOfferMapper();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasAnyRole('ADMIN', 'REGISTERED_USER')")
	public ResponseEntity<List<CulturalOfferDTO>> getAllCulturalOffers() {
		List<CulturalOffer> culturalOffers = culturalOfferService.findAll();

		return new ResponseEntity<>(toCulturalOfferDTOList(culturalOffers), HttpStatus.OK);
	}
	
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ADMIN', 'REGISTERED_USER')")
    public ResponseEntity<CulturalOfferDTO> getCulturalOffer(@PathVariable Long id){
        CulturalOffer culturalOffer = culturalOfferService.findOne(id);
        if(culturalOffer == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(culturalOfferMapper.toDto(culturalOffer), HttpStatus.OK);
    }
    
    @RequestMapping(value="/",method=RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ADMIN', 'REGISTERED_USER')")
    public ResponseEntity<Page<CulturalOfferDTO>> loadRatePage(Pageable pageable) {
    	Page<CulturalOffer> culturalOffers = culturalOfferService.findAll(pageable);
    	if(culturalOffers == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    	Page<CulturalOfferDTO> culturalOffersDTO = toCulturalOfferDTOPage(culturalOffers);
    	return new ResponseEntity<>(culturalOffersDTO, HttpStatus.OK);
    }
    
    @RequestMapping(method=RequestMethod.POST)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CulturalOfferDTO> createCulturalOffer(@RequestBody CulturalOfferDTO culturalOfferDTO){
    	CulturalOffer culturalOffer = null;
    	Type type;
    	Set<Image> images = new HashSet<Image>();
    	if(!this.validateCulturalOfferDTO(culturalOfferDTO))
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	
        try {
        	for (ImageDTO i : culturalOfferDTO.getImageDTO()) {
        		images.add(imageService.findOne(i.getId()));
        	}
        	type = typeService.findOne(culturalOfferDTO.getTypeDTO().getId());
        	culturalOffer = culturalOfferMapper.toEntity(culturalOfferDTO);
        	culturalOffer.setType(type);
        	culturalOffer.setImage(images);
        	culturalOffer = culturalOfferService.create(culturalOffer);
        } catch (Exception e) {
            return new ResponseEntity<>(new CulturalOfferDTO(),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(culturalOfferMapper.toDto(culturalOffer), HttpStatus.OK);
    }
 
    @RequestMapping(value="/subscribe/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('REGISTERED_USER')")
    public ResponseEntity<String> subscribeUserToCulturalOffer(@PathVariable Long id){
        registeredUserService.subscribeUser(id);
        try {
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Subscription done", HttpStatus.OK);
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CulturalOfferDTO> updateCulturalOffer(@RequestBody CulturalOfferDTO culturalOfferDTO, @PathVariable Long id){
        CulturalOffer culturalOffer;
        Type type;
        Set<Image> images = new HashSet<Image>();
        if(!this.validateCulturalOfferDTO(culturalOfferDTO))
    		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    	
        try {
        	for (ImageDTO i : culturalOfferDTO.getImageDTO()) {
        		images.add(imageService.findOne(i.getId()));
        	}
        	type = typeService.findOne(culturalOfferDTO.getTypeDTO().getId());
        	culturalOffer = culturalOfferMapper.toEntity(culturalOfferDTO);
        	culturalOffer.setType(type);
        	culturalOffer.setImage(images);
        	culturalOffer = culturalOfferService.update(culturalOffer, id);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(culturalOfferMapper.toDto(culturalOffer), HttpStatus.OK);
    }
    
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    @PreAuthorize("hasRole('ADMIN')")
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
