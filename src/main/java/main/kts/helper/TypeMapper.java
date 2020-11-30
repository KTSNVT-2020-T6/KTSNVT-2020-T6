package main.kts.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.kts.dto.CategoryDTO;
import main.kts.dto.TypeDTO;
import main.kts.model.Category;
import main.kts.model.Type;

@Component
public class TypeMapper implements MapperInterface<Type, TypeDTO>{

	@Autowired 
	CategoryMapper categoryMapper;
	
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
