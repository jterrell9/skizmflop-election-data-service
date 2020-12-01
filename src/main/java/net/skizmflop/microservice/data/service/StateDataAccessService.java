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
import net.skizmflop.commons.jpa.entity.State;
import net.skizmflop.microservice.data.jpa.repo.StateRepository;

@Service
public class StateDataAccessService implements IDataAccessService<State, String> {
	
	@Autowired
    private StateRepository stateRepo;

	@Override
	public State findById(String id) {
		return stateRepo.findById(id)
                .orElseThrow(() -> new SFBadRequestException("Could not find state with id " + id));
	}

	@Override
	public List<State> findAll() {
		List<State> states = (List<State>) stateRepo.findAll();
		if(CollectionUtils.isEmpty(states)) {
			throw new SFNoDataFoundException("No state data found");
		}
		return states;
	}

	@Override
	public State save(State state) {
		return stateRepo.save(state);
	}

	@Override
	public List<State> save(List<State> states) {
		List<State> savedStates = new ArrayList<>();
		for(State state: states) {
			savedStates.add(stateRepo.save(state));
		}
		int i, j;
		if((i=savedStates.size()) < (j=states.size())) {
			throw new SFDataException(i + " of the " + j + " states were saved to database");
		}
		return savedStates;
	}
	
	public State getByUniqueName(String name) {
		List<State> results = stateRepo.findByName(name);
		if(results.size() > 1) {
			throw new SFDataException("More than one result when name is meant to be unique.");
		}
		if(results.size() == 0) {
			throw new SFDataException("No results found.");
		}
		return results.get(0);
	}
	
	@Override
	public Long count() {
		return stateRepo.count();
	}

}
