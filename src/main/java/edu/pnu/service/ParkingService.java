package edu.pnu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.pnu.domain.ParkingCode;
import edu.pnu.domain.ParkingInfo;
import edu.pnu.domain.ParkingRefer;
import edu.pnu.persistence.ParkingCodeRepository;
import edu.pnu.persistence.ParkingInfoRepository;
import edu.pnu.persistence.ParkingReferRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ParkingService {
	private final ParkingInfoRepository parkingInfoRepository;
	private final ParkingCodeRepository parkingCodeRepository;
	private final ParkingReferRepository parkingReferRepository;
	
	//모든 주차장 정보 출력
	public List<ParkingInfo> getInfo(){
		return parkingInfoRepository.findAll();
	}
	
	//코드 출력
	public List<ParkingCode> getGuDong(){
		return parkingCodeRepository.findAll();
	}

	public List<ParkingRefer> getRefer() {
		return parkingReferRepository.findAll();
	}

	public ParkingInfo getParkingInfo(String prkPlaceName) {
		Optional<ParkingInfo> pInfo = parkingInfoRepository.findByprkPlaceNm(prkPlaceName);
		if(pInfo.isPresent()) {
			return pInfo.get();
		}else {
			throw new DataNotfoundException("없는 주차장 정보입니다.");
		}
	}
	
	

}
