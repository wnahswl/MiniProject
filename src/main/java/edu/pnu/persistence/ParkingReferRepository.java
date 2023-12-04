package edu.pnu.persistence;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.ParkingRefer;

public interface ParkingReferRepository extends JpaRepository<ParkingRefer, Long>{
	
	//페이징 처리
	List<ParkingRefer> findByPrkPlaceNmContaining(String prkPlaceNm, Pageable pageable);

}
