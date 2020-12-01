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
import net.skizmflop.commons.jpa.entity.StatePopulation;
import net.skizmflop.microservice.data.jpa.repo.StatePopulationRepository;

@Service
public class StatePopulationDataAccessService implements IDataAccessService<StatePopulation, Long> {
	
	@Autowired
    private StatePopulationRepository statePopulationRepo;

	@Override
	public StatePopulation findById(Long id) {
		return statePopulationRepo.findById(id)
                .orElseThrow(() -> new SFBadRequestException("Could not find electoral college entry with id " + id));
	}

	@Override
	public List<StatePopulation> findAll() {
		List<StatePopulation> statePopulations = (List<StatePopulation>) statePopulationRepo.findAll();
		if(CollectionUtils.isEmpty(statePopulations)) {
			throw new SFNoDataFoundException("No state data found");
		}
		return statePopulations;
	}

	@Override
	public StatePopulation save(StatePopulation statePopulation) {
		return statePopulationRepo.save(statePopulation);
	}

	@Override
	public List<StatePopulation> save(List<StatePopulation> statePopulations) {
		List<StatePopulation> savedStatePopulation = new ArrayList<>();
		for(StatePopulation statePopulation: statePopulations) {
			savedStatePopulation.add(statePopulationRepo.save(statePopulation));
		}
		int i, j;
		if((i=savedStatePopulation.size()) < (j=statePopulations.size())) {
			throw new SFDataException(i + " of the " + j + " electoral college entries were saved to database");
		}
		return savedStatePopulation;
	}
	
	@Override
	public Long count() {
		return statePopulationRepo.count();
	}

}
