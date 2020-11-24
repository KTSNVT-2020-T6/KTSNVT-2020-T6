package main.kts.service;

import java.util.List;

import org.springframework.stereotype.Service;

import main.kts.model.Rate;

@Service
public class RateService implements ServiceInterface<Rate>{

	@Override
	public List<Rate> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rate findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rate create(Rate entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rate update(Rate entity, Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
