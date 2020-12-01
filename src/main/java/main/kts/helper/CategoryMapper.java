package main.kts.helper;

import java.util.HashSet;
import java.util.Set;

import main.kts.dto.CategoryDTO;
import main.kts.dto.TypeDTO;
import main.kts.model.Category;
import main.kts.model.Type;
 
public class CategoryMapper implements MapperInterface<Category, CategoryDTO> {

	public CategoryMapper() {
		
	}
	@Override
	public Category toEntity(CategoryDTO dto) {
		Set<Type> types = new HashSet<Type>();
		return new Category(dto.getId(),dto.getName(), dto.getDescription(), types);
	}

	@Override
	public CategoryDTO toDto(Category entity) {
		Set<TypeDTO> typesDTO = new HashSet<TypeDTO>();
		return new CategoryDTO(entity.getId(),entity.getName(), entity.getDescription(), typesDTO);
	}
	
}
