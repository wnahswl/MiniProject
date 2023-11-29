package edu.pnu.persistence;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.ParkingInfo;

public interface ParkingInfoRepository extends JpaRepository<ParkingInfo, Long>{

	Optional<ParkingInfo> findByprkPlaceNm(String prkPlaceNm);

}
