package main.kts.service;



import static main.kts.constants.CulturalOfferConstants.DB_CO_AVERAGE_RATE;
import static main.kts.constants.CulturalOfferConstants.DB_CO_CITY;
import static main.kts.constants.CulturalOfferConstants.DB_CO_DATE;
import static main.kts.constants.CulturalOfferConstants.DB_CO_DESCRIPTION;
import static main.kts.constants.CulturalOfferConstants.DB_CO_ID;
import static main.kts.constants.CulturalOfferConstants.DB_CO_LAT;
import static main.kts.constants.CulturalOfferConstants.DB_CO_LON;
import static main.kts.constants.CulturalOfferConstants.DB_CO_NAME;
import static main.kts.constants.RegisteredUserConstants.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import main.kts.model.CulturalOffer;
import main.kts.model.RegisteredUser;
import main.kts.repository.CulturalOfferRepository;
import main.kts.repository.RegisteredUserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("classpath:test.properties")
public class RegisteredUserServiceUnitTest {
	@Autowired
	private RegisteredUserService service;
	@MockBean
	private RegisteredUserRepository repository;

	@MockBean
	private CulturalOfferRepository culturalOfferRepository;
	
	@Before
	public void setup() {
		RegisteredUser userFound = new RegisteredUser(DB_REGISTERED_USER_ID, DB_REGISTERED_USER_FIRST_NAME,
				DB_REGISTERED_USER_LAST_NAME,DB_REGISTERED_USER_EMAIL,DB_REGISTERED_USER_PASSWORD,
				 DB_REGISTERED_USER_ACTIVE,DB_REGISTERED_USER_VERIFIED);
		 
	    given(repository.findById(DB_REGISTERED_USER_ID)).willReturn(java.util.Optional.of(userFound));
	   
	    RegisteredUser savedRUser= new RegisteredUser( NEW_REGISTERED_USER_FIRST_NAME,
				NEW_REGISTERED_USER_LAST_NAME,NEW_REGISTERED_USER_EMAIL,NEW_REGISTERED_USER_PASSWORD,
				 NEW_REGISTERED_USER_ACTIVE,NEW_REGISTERED_USER_VERIFIED);
	    savedRUser.setId(DB_REGISTERED_USER_ID);
	    //find by email i save
	    given(repository.findByEmail(NEW_REGISTERED_USER_EMAIL)).willReturn(null);
	    when(repository.save(any(RegisteredUser.class))).thenReturn(savedRUser);

	    RegisteredUser alreadyExists = new RegisteredUser(DB_REGISTERED_USER_ID, DB_REGISTERED_USER_FIRST_NAME,
				DB_REGISTERED_USER_LAST_NAME,DB_REGISTERED_USER_EMAIL,DB_REGISTERED_USER_PASSWORD,
				 DB_REGISTERED_USER_ACTIVE,DB_REGISTERED_USER_VERIFIED);
	    given(repository.findByEmail(DB_REGISTERED_USER_EMAIL1)).willReturn(alreadyExists);
        doNothing().when(repository).delete(savedRUser);
        
        CulturalOffer co = new CulturalOffer(DB_CO_NAME, DB_CO_DESCRIPTION, DB_CO_DATE, DB_CO_CITY,
				DB_CO_LAT, DB_CO_LON, DB_CO_AVERAGE_RATE);
		co.setId(DB_CO_ID);
		
       
        Set<CulturalOffer> set = new HashSet<CulturalOffer>();
        Set<CulturalOffer> set1 = new HashSet<CulturalOffer>();
        RegisteredUser alreadyExists12 = new RegisteredUser(DB_REGISTERED_USER_ID1, DB_REGISTERED_USER_FIRST_NAME,
				DB_REGISTERED_USER_LAST_NAME,DB_REGISTERED_USER_EMAIL,DB_REGISTERED_USER_PASSWORD,
				 DB_REGISTERED_USER_ACTIVE,DB_REGISTERED_USER_VERIFIED);
        alreadyExists12.setFavoriteCulturalOffers(set1);
        set.add(co);
        userFound.setFavoriteCulturalOffers(set);
        given(repository.findById(DB_REGISTERED_USER_ID)).willReturn(Optional.of(userFound));
        given(repository.findById(DB_REGISTERED_USER_ID1)).willReturn(Optional.of(alreadyExists12));
        
        given(repository.findByEmail(DB_REGISTERED_USER_EMAIL)).willReturn(alreadyExists12);
        
        RegisteredUser alreadyExists123 = new RegisteredUser(DB_REGISTERED_USER_ID1, DB_REGISTERED_USER_FIRST_NAME,
				DB_REGISTERED_USER_LAST_NAME,DB_REGISTERED_USER_EMAIL,DB_REGISTERED_USER_PASSWORD,
				 DB_REGISTERED_USER_ACTIVE,DB_REGISTERED_USER_VERIFIED);
        alreadyExists123.setFavoriteCulturalOffers(set);
        given(repository.findByIdAndActive(DB_REGISTERED_USER_ID1,true)).willReturn(Optional.of(alreadyExists123));
        given(culturalOfferRepository.getOne(DB_REGISTERED_USER_CO)).willReturn(co);
        RegisteredUser ru = new RegisteredUser(DB_REGISTERED_USER_ID_DoesntActive, DB_REGISTERED_USER_FIRST_NAME,
				DB_REGISTERED_USER_LAST_NAME,DB_CHECK,DB_REGISTERED_USER_PASSWORD,
				 DB_REGISTERED_USER_ACTIVE,DB_REGISTERED_USER_VERIFIED);
        given(repository.findByIdRU(DB_REGISTERED_USER_ID_DoesntActive)).willReturn(ru);
        List<Long> asd = new ArrayList<Long>();
        asd.add(DB_REGISTERED_USER_ID1);
        given(repository.findByIdCO(DB_REGISTERED_USER_CO)).willReturn(asd);
        
	}
	
	@Test
	public void testFindByIdRU() throws Exception {
		//i za neaktivne trazi
		RegisteredUser ru = repository.findByIdRU(DB_REGISTERED_USER_ID_DoesntActive);
		assertEquals(DB_CHECK, ru.getEmail());
		
    }
	@Test
	public void testFindByIdCO() throws Exception {
		List<Long> found = repository.findByIdCO(DB_REGISTERED_USER_CO);
		assertEquals(DB_REGISTERED_USER_CO_SIZE,found.size());
		
    }
	
	@Test
	@Rollback(true)
	@Transactional
	@WithMockUser(username = DB_REGISTERED_USER_EMAIL, password = DB_REGISTERED_USER_PASSWORD,
	roles = "REGISTERED_USER")
	public void testSubscribeUser_OK() throws Exception {
		service.subscribeUser(DB_REGISTERED_USER_CO);
        
		//provera
		RegisteredUser us = service.findOne(DB_REGISTERED_USER_ID1);
	    assertEquals(DB_REGISTERED_USER_CO_SIZE, us.getFavoriteCulturalOffers().size());
    }
	
	@Test
	public void testFindAllSubscribedCO_Ok() throws Exception {
		List<CulturalOffer> found = service.findAllSubscribedCO(DB_REGISTERED_USER_ID);
		assertEquals(DB_REGISTERED_USER_CO_SIZE, found.size());
    }
	
	@Test
	public void testFindAllSubscribedCO_NotOk() throws Exception {
		List<CulturalOffer> found = service.findAllSubscribedCO(DB_REGISTERED_USER_ID1);
		assertEquals(DB_REGISTERED_USER_CO_SIZE1, found.size());
    }
	
	@Test
    public void testUpdate() throws Exception {

	    RegisteredUser user= new RegisteredUser( NEW_REGISTERED_USER_FIRST_NAME,
				NEW_REGISTERED_USER_LAST_NAME,NEW_REGISTERED_USER_EMAIL,NEW_REGISTERED_USER_PASSWORD,
				 NEW_REGISTERED_USER_ACTIVE,NEW_REGISTERED_USER_VERIFIED);
		
        RegisteredUser updated = service.update(user,DB_REGISTERED_USER_ID);

        verify(repository, times(1)).findById(DB_REGISTERED_USER_ID);
        verify(repository, times(1)).findByEmail(NEW_REGISTERED_USER_EMAIL);
      
        assertEquals(new Long(4L), updated.getId());
        assertEquals(NEW_REGISTERED_USER_FIRST_NAME, updated.getFirstName());
    }
	@Test
    public void testUpdate_EmailExists() throws Exception {
		Throwable exception = assertThrows(
	            Exception.class, () -> {
	            	RegisteredUser user= new RegisteredUser(NEW_REGISTERED_USER_FIRST_NAME,
	         				NEW_REGISTERED_USER_LAST_NAME,NEW_REGISTERED_USER_EMAIL1,NEW_REGISTERED_USER_PASSWORD,
	         				 NEW_REGISTERED_USER_ACTIVE,NEW_REGISTERED_USER_VERIFIED);
	       		
	            	RegisteredUser updated = service.update(user,DB_REGISTERED_USER_ID);

	            }
	    );
		verify(repository, times(1)).findById(DB_REGISTERED_USER_ID);
	    verify(repository, times(1)).findByEmail(NEW_REGISTERED_USER_EMAIL1);
	    assertEquals("User with given email already exist", exception.getMessage());
    }
	@Test
    public void testDelete() throws Exception {
		service.delete(DB_REGISTERED_USER_ID);
	
        verify(repository, times(1)).findById(DB_REGISTERED_USER_ID);
    }
	@Test
    public void testDelete_IdDoesntExists() throws Exception {
		Throwable exception = assertThrows(
	            Exception.class, () -> {
	            	service.delete(NEW_REGISTERED_USER_ID1);
	            }
	    );
	    assertEquals("Registered user doesn't exist", exception.getMessage());
    }
}
