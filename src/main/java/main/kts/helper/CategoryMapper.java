package main.kts.helper;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import main.kts.dto.CategoryDTO;
import main.kts.dto.TypeDTO;
import main.kts.model.Category;
import main.kts.model.Type;

@Component 
public class CategoryMapper implements MapperInterface<Category, CategoryDTO> {

	@Autowired
	TypeMapper typeMapper;
	
	@Override
	public Category toEntity(CategoryDTO dto) {
		Set<Type> types = new HashSet<Type>();
		for(TypeDTO typeDTO : dto.getTypeDTO()) {
			types.add(typeMapper.toEntity(typeDTO));
		}
		return new Category(dto.getName(), dto.getDescription(), types);
	}

	@Override
	public CategoryDTO toDto(Category entity) {
		Set<TypeDTO> typesDTO = new HashSet<TypeDTO>();
		for (Type type : entity.getType()) {
			typesDTO.add(typeMapper.toDto(type));
		}
		return new CategoryDTO(entity.getName(), entity.getDescription(), typesDTO);
	}
	
}
