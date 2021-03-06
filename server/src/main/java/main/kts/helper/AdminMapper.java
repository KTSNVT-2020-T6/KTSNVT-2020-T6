package main.kts.helper;


import main.kts.dto.AdminDTO;
import main.kts.model.Admin;

public class AdminMapper implements MapperInterface<Admin, AdminDTO>{
	
	ImageMapper imageMapper = new ImageMapper();
	
	public AdminMapper() {
		super();
	}
	@Override
	public AdminDTO toDto(Admin entity) {
		if(entity.getImage() == null) {
			return new AdminDTO(entity.getId(),entity.getFirstName(),entity.getLastName(), entity.getEmail(), entity.getPassword(), entity.getActive(), entity.getVerified()
					);
		}
		return new AdminDTO(entity.getId(),entity.getFirstName(),entity.getLastName(), entity.getEmail(), entity.getPassword(), entity.getActive(), entity.getVerified()
				, entity.getImage().getId());
	}
	@Override
	public Admin toEntity(AdminDTO dto) {
		Admin a = new Admin(dto.getFirstName(), dto.getLastName(),dto.getEmail(), dto.getPassword());
		System.out.println(a);
		return a;
	}

}
