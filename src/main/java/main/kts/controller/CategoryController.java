package main.kts.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.kts.dto.CategoryDTO;
import main.kts.dto.TypeDTO;
import main.kts.helper.CategoryMapper;
import main.kts.model.Category;
import main.kts.model.Type;
import main.kts.service.CategoryService;

@RestController
@RequestMapping(value = "/api/category", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	private CategoryMapper categoryMapper;
	
	public CategoryController() {
		super();
		categoryMapper = new CategoryMapper();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoryDTO>> getAllCategories(){
		List<Category> categories = categoryService.findAll();
		return new ResponseEntity<>(toCategoryDTOList(categories), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<CategoryDTO> getType(@PathVariable Long id){
		Category category = categoryService.findOne(id);
		if(category == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(categoryMapper.toDto(category), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO){
		Category category;
		if(!this.validateCategoryDTO(categoryDTO))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		try {
			category = categoryService.create(categoryMapper.toEntity(categoryDTO));
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(categoryMapper.toDto(category), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CategoryDTO> updateCategory(@RequestBody CategoryDTO categoryDTO, @PathVariable Long id){
		Category category;
		if(!this.validateCategoryDTO(categoryDTO))
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		try {
			category = categoryService.update(categoryMapper.toEntity(categoryDTO), id);
		}
		catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(categoryMapper.toDto(category), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
		try {
			categoryService.delete(id);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	
	private boolean validateCategoryDTO(CategoryDTO categoryDTO) {
		// category doesn't need to have type ? necessarily
		if(categoryDTO.getName() == null)
			return false;
		if(categoryDTO.getDescription() == null)
			return false;
		return true;
	}
	private List<CategoryDTO> toCategoryDTOList(List<Category> categories){
        List<CategoryDTO> categoriesDTO = new ArrayList<>();
        for (Category category: categories) {
        	categoriesDTO.add(categoryMapper.toDto(category));
        }
        return categoriesDTO;
    }
}
