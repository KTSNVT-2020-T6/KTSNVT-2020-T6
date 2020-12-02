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

import main.kts.dto.TypeDTO;
import main.kts.helper.TypeMapper;
import main.kts.model.Category;
import main.kts.model.Type;
import main.kts.service.CategoryService;
import main.kts.service.TypeService;

@RestController
@RequestMapping(value = "/api/type", produces = MediaType.APPLICATION_JSON_VALUE)
public class TypeController {
	@Autowired
	private TypeService typeService;
	
	private TypeMapper typeMapper;
	
	@Autowired
	private CategoryService categoryService;

	public TypeController() {
		super();
		typeMapper = new TypeMapper();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<TypeDTO>> getAllTypes() {
		List<Type> types = typeService.findAll();

		return new ResponseEntity<>(toTypeDTOList(types), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<TypeDTO> getType(@PathVariable Long id){
		Type type = typeService.findOne(id);
		if(type == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(typeMapper.toDto(type), HttpStatus.OK);
	}
	
    @RequestMapping(value="/",method=RequestMethod.GET)
    public ResponseEntity<Page<TypeDTO>> loadTypePage(Pageable pageable) {
    	Page<Type> types = typeService.findAll(pageable);
    	if(types == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    	Page<TypeDTO> typesDTO = toTypeDTOPage(types);
    	return new ResponseEntity<>(typesDTO, HttpStatus.OK);
    }
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<TypeDTO> createType(@RequestBody TypeDTO typeDTO){
		Type type;
		Category category;
		if(!this.validateTypeDTO(typeDTO))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		try {
			category = categoryService.findOne(typeDTO.getCategoryDTO().getId());
			type = typeMapper.toEntity(typeDTO);
			type.setCategory(category);
			type = typeService.create(type);	
			
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(typeMapper.toDto(type), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TypeDTO> updateType(@RequestBody TypeDTO typeDTO,  @PathVariable Long id){
		Type type;
		Category category;
		if(!this.validateTypeDTO(typeDTO))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		try {
			category = categoryService.findOne(typeDTO.getCategoryDTO().getId());
			type = typeMapper.toEntity(typeDTO);
			type.setCategory(category);
			type = typeService.update(type, id);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(typeMapper.toDto(type), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteType(@PathVariable Long id){
		try {
			typeService.delete(id);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
	
	private Page<TypeDTO> toTypeDTOPage(Page<Type> types) {
		Page<TypeDTO> dtoPage = types.map(new Function<Type, TypeDTO>() {
		    @Override
		    public TypeDTO apply(Type entity) {
		    	TypeDTO dto = typeMapper.toDto(entity);
		        return dto;
		    }
		});
		return dtoPage;
	}
	
	
	private boolean validateTypeDTO(TypeDTO typeDTO) {
		if(typeDTO.getCategoryDTO() == null)
			return false;
		if(typeDTO.getName() == null)
			return false;
		if(typeDTO.getDescription() == null)
			return false;
		return true;
	}
	private List<TypeDTO> toTypeDTOList(List<Type> types){
        List<TypeDTO> typesDTOS = new ArrayList<>();
        for (Type type: types) {
        	typesDTOS.add(typeMapper.toDto(type));
        }
        return typesDTOS;
    }
}
