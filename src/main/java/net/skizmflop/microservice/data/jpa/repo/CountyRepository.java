package net.skizmflop.microservice.data.jpa.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import net.skizmflop.commons.jpa.entity.County;
import net.skizmflop.commons.jpa.entity.State;

@Repository
public interface CountyRepository extends CrudRepository<County, Long> {
	
	public List<County> findByCountyName(String countyName);
	
	public List<County> findByState(State state);
	
	public List<County> findByStateAndByCountyName(State state, String countyName);

}
