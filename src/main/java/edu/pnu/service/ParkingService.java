package edu.pnu.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import edu.pnu.domain.ParkingCode;
import edu.pnu.domain.ParkingInfo;
import edu.pnu.domain.ParkingRefer;
import edu.pnu.exception.DataNotfoundException;
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

	public Page<ParkingRefer> getWithPaging(Pageable pageable) {
		return parkingReferRepository.findAll(pageable);
	}

	// 모든 주차장 정보 출력
	public List<ParkingInfo> getInfo() {
		return parkingInfoRepository.findAll();
	}

	// 코드 출력
	public List<ParkingCode> getGuDong() {
		return parkingCodeRepository.findAll();
	}

	public List<ParkingRefer> getRefer() {

		return parkingReferRepository.findAll();
	}

	public ParkingInfo getParkingInfo(String prkPlaceName) {
		Optional<ParkingInfo> pInfo = parkingInfoRepository.findByprkPlaceNm(prkPlaceName);
		if (pInfo.isPresent()) {
			return pInfo.get();
		} else {
			throw new DataNotfoundException("없는 주차장 정보입니다.");
		}
	}

	// 페이징처리
	public Page<ParkingRefer> searchPaging(String prkPlaceNm, String gu, String dong, Pageable pageable) {

		// 주차장명,구,동이 다 있을경우
		if (prkPlaceNm != null && !prkPlaceNm.isEmpty() && gu != null && !gu.isEmpty() && dong != null
				&& !dong.isEmpty()) {
			return parkingReferRepository.findByGuAndDongAndPrkPlaceNmContaining(gu, dong, prkPlaceNm, pageable);
			// 구와 동만 있을 경우
		} else if ((prkPlaceNm == null || prkPlaceNm.isEmpty()) && gu != null && !gu.isEmpty() && dong != null
				&& !dong.isEmpty()) {
			return parkingReferRepository.findByGuAndDong(gu, dong, pageable);
			// 구만 있을 경우
		} else if (prkPlaceNm == null
				|| prkPlaceNm.isEmpty() && (dong == null || dong.isEmpty()) && gu != null && !gu.isEmpty()) {
			return parkingReferRepository.findByGu(gu, pageable);
		}
		// 주차장 명과 구만 있을 경우
		else if (prkPlaceNm != null && !prkPlaceNm.isEmpty() && gu != null && !gu.isEmpty()
				&& (dong == null || dong.isEmpty())) {
			return parkingReferRepository.findByGuAndPrkPlaceNmContaining(gu, prkPlaceNm, pageable);
			// 주차장 명만 있을 경우
		} else if (prkPlaceNm != null && !prkPlaceNm.isEmpty()
				&& (gu == null || gu.isEmpty() || dong == null || dong.isEmpty())) {
			return parkingReferRepository.findByPrkPlaceNmContaining(prkPlaceNm, pageable);
		} else {
			return parkingReferRepository.findAll(pageable);
		}
	}

}
