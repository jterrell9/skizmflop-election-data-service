package net.skizmflop.microservice.data.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import net.skizmflop.commons.jpa.entity.ElectoralCollege;
import net.skizmflop.commons.jpa.entity.State;
import net.skizmflop.commons.util.DataPopulator;
import net.skizmflop.microservice.data.service.ElectoralCollegeDataAccessService;
import net.skizmflop.microservice.data.service.StateDataAccessService;

/**
 * Data Service Controller V1
 * @author jack
 */
@Controller
@Api(value="State Controller")
public class SFDataServiceStateControllerV1 extends SFDataServiceBaseControllerV1 {

	@Autowired
	private StateDataAccessService stateDataAccessService;
	
	@Autowired
	private ElectoralCollegeDataAccessService electoralCollegeService;
	
	/**
	 * <p>Get info on State.<p>
	 * @param id
	 * @return Response
	 */
	@GetMapping(path="/state/{id}")
	@ResponseBody
	public State getState(@PathVariable("id") String id) {
		return stateDataAccessService.findById(id);
	}
	
	/**
	 * <p>Add a state.</p>
	 * @param state
	 * @return
	 */
	@PostMapping(path="/addState", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public State addState(@RequestBody State state) {
		return stateDataAccessService.save(state);
	}
	
	/**
	 * <p>Add a state by ID and NAME.</p>
	 * @param state
	 * @return
	 */
	@PostMapping(path="/addState/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public State addState(@PathVariable("id") String id, @RequestParam("name") String name) {
		return stateDataAccessService.save(new State(id, name));
	}
	
	/**
	 * <p>Add a list of states.</p>
	 * @param states
	 * @return saved states
	 */
	@PostMapping(path="/addStates", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<State> addStates(@RequestBody List<State> states) {
		return stateDataAccessService.save(states);
	}
	
	/**
	 * <p>Populate electoral_college table.</p>
	 * @return saved states
	 */
	@GetMapping(path="/populate2020ElectoralData")
	@ResponseBody
	public List<ElectoralCollege> populate2020ElectoralData() {
		return electoralCollegeService.save(DataPopulator.populate2020ElectoralData());
	}
	
	/**
	 * <p>Populate us_states table.</p>
	 * @return saved states
	 */
	@GetMapping(path="/populateStatesData")
	@ResponseBody
	public List<State> populateStatesData() {
		return stateDataAccessService.save(DataPopulator.getUSStates());
	}
	
	@GetMapping(path="/countStates")
	@ResponseBody
	public Long countStates() {
		return stateDataAccessService.count();
	}
}
