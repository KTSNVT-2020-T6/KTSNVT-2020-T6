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

import main.kts.dto.CategoryDTO;
import main.kts.helper.CategoryMapper;
import main.kts.model.Category;
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
	
    @RequestMapping(value="/",method=RequestMethod.GET)
    public ResponseEntity<Page<CategoryDTO>> loadCategoryPage(Pageable pageable) {
    	Page<Category> categorys = categoryService.findAll(pageable);
    	if(categorys == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    	Page<CategoryDTO> categorysDTO = toCategoryDTOPage(categorys);
    	return new ResponseEntity<>(categorysDTO, HttpStatus.OK);
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
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
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
	public ResponseEntity<String> deleteCategory(@PathVariable Long id){
		try {
			categoryService.delete(id);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>("OK", HttpStatus.OK);
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
	
	private Page<CategoryDTO> toCategoryDTOPage(Page<Category> categorys) {
		Page<CategoryDTO> dtoPage = categorys.map(new Function<Category, CategoryDTO>() {
		    @Override
		    public CategoryDTO apply(Category entity) {
		    	CategoryDTO dto = categoryMapper.toDto(entity);
		        return dto;
		    }
		});
		return dtoPage;
	}
}
