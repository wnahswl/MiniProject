package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.ParkingCode;

public interface ParkingCodeRepository extends JpaRepository<ParkingCode, String>{

}
