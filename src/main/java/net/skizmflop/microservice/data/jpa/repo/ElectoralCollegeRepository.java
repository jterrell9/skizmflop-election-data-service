package net.skizmflop.microservice.data.jpa.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.skizmflop.commons.jpa.entity.ElectoralCollege;
import net.skizmflop.commons.jpa.entity.State;

@Repository
public interface ElectoralCollegeRepository extends CrudRepository<ElectoralCollege, Long> {
	
	public List<ElectoralCollege> findByState(State state);
	
	public List<ElectoralCollege> findByElectionYear(Integer electionYear);
	
	public List<ElectoralCollege> findByStateAndElectionYear(State state, Integer electionYear);

}
