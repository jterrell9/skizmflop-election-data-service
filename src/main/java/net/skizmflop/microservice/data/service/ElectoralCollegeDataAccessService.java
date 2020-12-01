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
import net.skizmflop.commons.jpa.entity.ElectoralCollege;
import net.skizmflop.commons.jpa.entity.State;
import net.skizmflop.microservice.data.jpa.repo.ElectoralCollegeRepository;

@Service
public class ElectoralCollegeDataAccessService implements IDataAccessService<ElectoralCollege, Long> {
	
	@Autowired
    private ElectoralCollegeRepository electoralCollegeRepo;
	
	@Autowired
	private StateDataAccessService stateDataAccessService;

	@Override
	public ElectoralCollege findById(Long id) {
		return electoralCollegeRepo.findById(id)
                .orElseThrow(() -> new SFBadRequestException("Could not find electoral college entry with id " + id));
	}

	@Override
	public List<ElectoralCollege> findAll() {
		List<ElectoralCollege> states = (List<ElectoralCollege>) electoralCollegeRepo.findAll();
		if(CollectionUtils.isEmpty(states)) {
			throw new SFNoDataFoundException("No state data found");
		}
		return states;
	}

	@Override
	public ElectoralCollege save(ElectoralCollege electoralCollege) {
		return electoralCollegeRepo.save(electoralCollege);
	}

	@Override
	public List<ElectoralCollege> save(List<ElectoralCollege> electoralColleges) {
		List<ElectoralCollege> savedElectoralColleges = new ArrayList<>();
		for(ElectoralCollege electoralCollege: electoralColleges) {
			savedElectoralColleges.add(electoralCollegeRepo.save(electoralCollege));
		}
		int i, j;
		if((i=savedElectoralColleges.size()) < (j=electoralColleges.size())) {
			throw new SFDataException(i + " of the " + j + " electoral college entries were saved to database");
		}
		return savedElectoralColleges;
	}
	
	@Override
	public Long count() {
		return electoralCollegeRepo.count();
	}
	
	public List<ElectoralCollege> getResultByState(String stateId) {
		State state = stateDataAccessService.findById(stateId);
		return electoralCollegeRepo.findByState(state);
	}
	
	public List<ElectoralCollege> getResultByElectionYear(Integer electionYear) {
		return electoralCollegeRepo.findByElectionYear(electionYear);
	}
	
	public List<ElectoralCollege> getResultByStateAndElectionYear(String stateId, Integer electionYear) {
		return electoralCollegeRepo.findByStateAndElectionYear(stateDataAccessService.findById(stateId), electionYear);
	}
	
	public Integer get2020ElectoralVotesResultByState(String stateId) {
		State state = stateDataAccessService.findById(stateId);
		List<ElectoralCollege> resultSet = electoralCollegeRepo.findByStateAndElectionYear(state, 2020);
		if(resultSet.size() != 1) {
			throw new SFDataException("Found " + resultSet.size() + " results, which is not 1.");
		}
		return resultSet.get(0).getElectoralVotes();
	}

}
