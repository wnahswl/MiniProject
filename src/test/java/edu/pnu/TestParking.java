package edu.pnu;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import edu.pnu.domain.ParkingInfo;
import edu.pnu.persistence.ParkingInfoRepository;

@SpringBootTest
public class TestParking {
	
	@Autowired
	ParkingInfoRepository parkingInfoRepository;
	
	@Test
	public void test1() {
		List<ParkingInfo> list = parkingInfoRepository.findAll();
		list.forEach(p->System.out.println(p));
	}
	

}
