package edu.pnu.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pnu.domain.ParkingRefer;

public interface ParkingReferRepository extends JpaRepository<ParkingRefer, Long>{

}
