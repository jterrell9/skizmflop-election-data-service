package net.skizmflop.microservice.data.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import net.skizmflop.commons.exception.SFBadRequestException;
import net.skizmflop.commons.exception.SFDataException;
import net.skizmflop.commons.exception.SFNoDataFoundException;
import net.skizmflop.commons.interfaces.IDataAccessService;
import net.skizmflop.commons.jpa.entity.County;
import net.skizmflop.commons.jpa.entity.State;
import net.skizmflop.microservice.data.jpa.repo.CountyRepository;

@Service
public class CountyDataAccessService implements IDataAccessService<County, Long> {
	
	@Autowired
    private CountyRepository countyRepo;

	@Override
	public County findById(Long id) {
		return countyRepo.findById(id)
                .orElseThrow(() -> new SFBadRequestException("Could not find county with id " + id));
	}
	
	public List<County> findByState(State stateId) {
		return countyRepo.findByState(stateId);
	}

	@Override
	public List<County> findAll() {
		List<County> counties = (List<County>) countyRepo.findAll();
		if(CollectionUtils.isEmpty(counties)) {
			throw new SFNoDataFoundException("No County data found");
		}
		return counties;
	}

	@Override
	public County save(County county) {
		return countyRepo.save(county);
	}

	@Override
	public List<County> save(List<County> counties) {
		List<County> savedCounties = new ArrayList<>();
		for(County county: counties) {
			savedCounties.add(countyRepo.save(county));
		}
		int i, j;
		if((i=savedCounties.size()) < (j=counties.size())) {
			throw new SFDataException(i + " of the " + j + " counties were saved to database");
		}
		return savedCounties;
	}
	
	public County getByUniqueCountyNameAndByState(String countyName, State state) {
		List<County> results = countyRepo.findByStateAndByCountyName(state, countyName);
		if(results.size() > 1) {
			throw new SFDataException("More than one result when County name is meant to be unique.");
		}
		if(results.isEmpty()) {
			throw new SFDataException("No results found.");
		}
		return results.get(0);
	}
	
	@Override
	public Long count() {
		return countyRepo.count();
	}

}
