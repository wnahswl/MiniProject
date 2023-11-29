package edu.pnu.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.ParkingCode;
import edu.pnu.domain.ParkingInfo;
import edu.pnu.domain.ParkingRefer;
import edu.pnu.service.ParkingService;
import lombok.RequiredArgsConstructor;

@RequestMapping("/parking")
@RequiredArgsConstructor
@RestController
public class ParkingController {
	
	private final ParkingService parkingService;

	//모든 주차장 정보 출력
	@GetMapping("/list")
	public List<ParkingInfo> getInfo(){
		return parkingService.getInfo();
	}
	
	//모든 구,동 출력
	@GetMapping("/gudong")
	public List<ParkingCode> getGuDong(){
		return parkingService.getGuDong();
	}
	
	//refer
	@GetMapping("/refer")
	public List<ParkingRefer> getRefer(){
		return parkingService.getRefer();
	}
	
	@GetMapping("/info/{prkPlaceName}")
	public ParkingInfo detail(@PathVariable("prkPlaceName")String prkPlaceName) {
		return parkingService.getParkingInfo(prkPlaceName);
	}
	

	

}
