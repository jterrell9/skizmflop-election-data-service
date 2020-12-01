package net.skizmflop.microservice.data.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import net.skizmflop.commons.jpa.entity.County;
import net.skizmflop.commons.jpa.entity.State;
import net.skizmflop.microservice.data.service.CountyDataAccessService;
import net.skizmflop.microservice.data.service.StateDataAccessService;

/**
 * Data Service Controller V1
 * @author jack
 */
@Controller
@Api(value="County Controller")
public class SFDataServiceCountyControllerV1 extends SFDataServiceBaseControllerV1 {

	@Autowired
	private StateDataAccessService stateDataAccessService;
	
	@Autowired
	private CountyDataAccessService countyDataAccessService;
	
	/**
	 * <p>Get info on State.<p>
	 * @param id
	 * @return Response
	 */
	@GetMapping(path="/getCounties/{stateId}")
	@ResponseBody
	public List<County> getCountyByState(@PathVariable("stateId") String stateId) {
		State state = stateDataAccessService.findById(stateId);
		return countyDataAccessService.findByState(state);
	}
	
	/**
	 * <p>Add a county with a state.</p>
	 * @param state
	 * @return
	 */
	@GetMapping(path="addCounty/{stateId}/{countyName}")
	@ResponseBody
	public County addCounty(@PathVariable("stateId") String stateId, @PathVariable("countyName") String countyName) {
		County county = new County();
		county.setCountyName(countyName);
		county.setState(stateDataAccessService.findById(stateId));
		return countyDataAccessService.save(county);
	}
	
	/**
	 * <p>Add a list of counties.</p>
	 * @param states
	 * @return saved states
	 */
	@PostMapping(path="/addCounties", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<County> addCounties(@RequestBody List<County> counties) {
		return countyDataAccessService.save(counties);
	}
	
	@GetMapping(path="/countCounties")
	@ResponseBody
	public Long countCounties() {
		return countyDataAccessService.count();
	}
}
