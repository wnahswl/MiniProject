package edu.pnu.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ParkingRefer {
	@Id
	private Long id;
	
	private String prkPlaceNm;
	private String gu;
	private String dong;
	private String address;
	private String phoneNumber;

}
