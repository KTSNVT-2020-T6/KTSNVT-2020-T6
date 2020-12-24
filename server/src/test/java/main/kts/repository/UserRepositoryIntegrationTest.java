package main.kts.repository;

import static main.kts.constants.UserConstants.*;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import main.kts.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource("classpath:test.properties")
public class UserRepositoryIntegrationTest {
	
	@Autowired
	private UserRepository repository;
	
	@Test
    public void testFindByEmail_AdminOK() {
        User found = repository.findByEmail(DB_ADMIN_EMAIL);
        assertEquals(DB_ADMIN_EMAIL, found.getEmail());
    } 
	@Test
    public void testFindByEmail_RegUserOK() {
        User found = repository.findByEmail(DB_REGISTERED_USER_EMAIL);
        assertEquals(DB_REGISTERED_USER_EMAIL, found.getEmail());
    } 
	@Test
    public void testFindByEmail_NotActive() {
        User found = repository.findByEmail(DB_REGISTERED_USER_NOT_ACTIVE_EMAIL);
        assertEquals(DB_REGISTERED_USER_NOT_ACTIVE_EMAIL, found.getEmail());
    }
	@Test
    public void testFindByEmail_DoesntExist() {
        User found = repository.findByEmail(NEW_ADMIN_EMAIL_DOESNT_EXIST);
        assertEquals(null, found);
    }
	@Test
    public void testFindAllByActive_Active() {
        List<User> found = repository.findAllByActive(USER_ACTIVE);
        assertEquals(USER_ACTIVE_SIZE, found.size());
    }
	@Test
    public void testFindAllByActive_NotActive() {
        List<User> found = repository.findAllByActive(USER_NOT_ACTIVE);
        assertEquals(USER_NOT_ACTIVE_SIZE, found.size());
    }
	@Test
    public void testFindByIdAndActive_OK() {
		Optional<User> found = repository.findByIdAndActive(DB_ADMIN_ID, USER_ACTIVE);
        User user = found.orElse(null);
        assertEquals(DB_ADMIN_NAME,user.getFirstName());
    }
	@Test
    public void testFindByIdAndActive_DoesntExist() {
		Optional<User> found = repository.findByIdAndActive(NEW_ADMIN_ID_DOESNT_EXIST, USER_ACTIVE);
        User user = found.orElse(null);
        assertEquals(null,user);
    }
	@Test
    public void testFindByIdAndActive_WrongActiveParam() {
		Optional<User> found = repository.findByIdAndActive(DB_ADMIN_ID, USER_NOT_ACTIVE);
        User user = found.orElse(null);
        assertEquals(null,user);
    }
	@Test
    public void testFindByIdAndActive_DoesntExistWithFalseParams() {
		Optional<User> found = repository.findByIdAndActive(NEW_ADMIN_ID_DOESNT_EXIST, USER_NOT_ACTIVE);
        User user = found.orElse(null);
        assertEquals(null,user);
    }
	@Test
    public void testFindByEmailAndActive_AdminOK() {
        User found = repository.findByEmailAndActive(DB_ADMIN_EMAIL, USER_ACTIVE);
        assertEquals(DB_ADMIN_EMAIL, found.getEmail());
    } 
	@Test
    public void testFindByEmailAndActive_RegUserOK() {
        User found = repository.findByEmailAndActive(DB_REGISTERED_USER_EMAIL,USER_ACTIVE);
        assertEquals(DB_REGISTERED_USER_EMAIL, found.getEmail());
    } 
	@Test
    public void testFindByEmailAndActive_NotActive() {
        User found = repository.findByEmailAndActive(DB_REGISTERED_USER_NOT_ACTIVE_EMAIL,USER_ACTIVE);
        assertEquals(null,found);
    }
	@Test
    public void testFindByEmailAndActive_DoesntExist() {
        User found = repository.findByEmailAndActive(NEW_ADMIN_EMAIL_DOESNT_EXIST,USER_ACTIVE);
        assertEquals(null, found);
    }
	@Test
    public void testFindByActivePage() {
		Pageable pageable = PageRequest.of(PAGEABLE_PAGE,PAGEABLE_SIZE);
        Page<User> found = repository.findByActive(pageable, USER_ACTIVE);
        assertEquals(USER_ACTIVE_SIZE, found.getSize());
    }
	

}
