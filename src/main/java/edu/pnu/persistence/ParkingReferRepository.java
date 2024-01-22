package edu.pnu.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.ParkingRefer;


public interface ParkingReferRepository extends JpaRepository<ParkingRefer, Long>{
	
	Page<ParkingRefer> findByDong(String dong, Pageable pageable);
	//페이징 처리 Containing으로 Like 검색 가능
	Page<ParkingRefer> findByPrkPlaceNmContaining(String prkPlaceNm, Pageable pageable);
	Page<ParkingRefer> findByGuAndDongAndPrkPlaceNmContaining(String gu, String dong, String prkPlaceNm,Pageable pageable);
	Page<ParkingRefer> findByGu(String gu,Pageable pageable);
	Page<ParkingRefer> findByGuAndDong(String gu,String dong,Pageable pageable);
	Page<ParkingRefer> findByGuAndPrkPlaceNmContaining(String gu,String prkPlaceNm,Pageable pageable);
}
