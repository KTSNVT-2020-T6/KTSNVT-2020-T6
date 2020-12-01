package main.kts.helper;

import main.kts.dto.CategoryDTO;
import main.kts.dto.TypeDTO;
import main.kts.model.Category;
import main.kts.model.Type;

public class TypeMapper implements MapperInterface<Type, TypeDTO>{

	 
	CategoryMapper categoryMapper = new CategoryMapper();
	
	public TypeMapper() {
		
	}
	
	@Override
	public Type toEntity(TypeDTO dto) {
		Category category = categoryMapper.toEntity(dto.getCategoryDTO());
		return new Type(dto.getName(), dto.getDescription(), category);
	}

	@Override
	public TypeDTO toDto(Type entity) {
		CategoryDTO categoryDTO = categoryMapper.toDto(entity.getCategory());
		return new TypeDTO(entity.getName(), entity.getDescription(), categoryDTO);
	}

}
