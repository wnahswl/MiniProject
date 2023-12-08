package edu.pnu.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.ParkingCode;

public interface ParkingCodeRepository extends JpaRepository<ParkingCode, String>{
	Page<ParkingCode> findByGu(String gu, Pageable pageable);
	Page<ParkingCode> findByDong(String dong, Pageable pageable);

}
