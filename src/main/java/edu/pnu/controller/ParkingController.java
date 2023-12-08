package edu.pnu.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	// 모든 주차장 정보 출력
	@GetMapping("/list")
	public List<ParkingInfo> getInfo() {
		return parkingService.getInfo();
	}

	// 모든 구,동 출력
	@GetMapping("/gudong")
	public List<ParkingCode> getGuDong() {
		return parkingService.getGuDong();
	}

	// refer
	@GetMapping("/refer")
	public List<ParkingRefer> getRefer() {
		return parkingService.getRefer();
	}

	@GetMapping("/paging")
	public ResponseEntity<Page<ParkingRefer>> getWithPaging(
			@RequestParam(required = false) String prkPlaceNm,
			@RequestParam(defaultValue = "1") int page, 
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(required = false) String gu,
			@RequestParam(required = false) String dong) {

		// 페이지 번호를 1부터 시작하도록 조정
		Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));

		Page<ParkingRefer> searchList = parkingService.searchPaging(prkPlaceNm, gu, dong, pageable);
		Page<ParkingRefer> responsePage = new PageImpl<>(searchList.getContent(), pageable,
				searchList.getTotalElements());

		return ResponseEntity.ok(responsePage);
	}

	// 주차장 상세보기
	@GetMapping("/detail/{prkPlaceName}")
	public ParkingInfo detail(@PathVariable("prkPlaceName") String prkPlaceName) {
		return parkingService.getParkingInfo(prkPlaceName);
	}

}
