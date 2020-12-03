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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.kts.dto.UserDTO;
import main.kts.helper.UserMapper;
import main.kts.model.User;
import main.kts.service.UserService;

@RestController
@RequestMapping(value = "/api/user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
	
	@Autowired 
	private UserService service;
	
	private UserMapper userMapper = new UserMapper();
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<UserDTO>> getAllUsers(){
		List<User> users = service.findAll();
		return new ResponseEntity<>(toDTOUsersList(users), HttpStatus.OK);
	}
	
	@RequestMapping(value="/",method=RequestMethod.GET)
    public ResponseEntity<Page<UserDTO>> loadUsersPage(Pageable pageable) {
    	Page<User> users = service.findAll(pageable);
    	if(users == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    	Page<UserDTO> usersDTO = toUsersDTOPage(users);
    	return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
	public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
		User u = service.findOne(id);
		if (u == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(userMapper.toDto(u), HttpStatus.OK);
	}
	@RequestMapping(value = "/{email}", method = RequestMethod.GET)
	public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email){
		User u = service.findByEmail(email);
		if (u == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(userMapper.toDto(u), HttpStatus.OK);
	}

	public List<UserDTO> toDTOUsersList(List<User> users) {
		List<UserDTO> listDTO = new ArrayList<UserDTO>();
        for (User u: users) {
        	listDTO.add(userMapper.toDto(u));
        }
        return listDTO;
	}
	
	private Page<UserDTO> toUsersDTOPage(Page<User> users) {
		Page<UserDTO> dtoPage = users.map(new Function<User, UserDTO>() {
		    @Override
		    public UserDTO apply(User entity) {
		    	UserDTO dto = userMapper.toDto(entity);
		        return dto;
		    }
		});
		return dtoPage;
	}

}
