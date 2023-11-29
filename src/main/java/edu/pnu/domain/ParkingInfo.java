package edu.pnu.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ParkingInfo {

	@Id
	private Long id;

	private String prkPlaceNm; // 주차장명
	private String prkStyle; // 주차장 구분
	private String prkType; // 주차장 유형
	private String address; // 소재지 지번주소
	private int prkChargeInfo; // 주차구획수
	private String rating; // 급지구분
	
	private String fiveDaysSystem; // 차량 5부제 시행여부
	private String openDays; // 운영요일
	private String weekdayOpenHhmm;// 평일 오픈시간
	private String weekdayColseHhmm;// 평일 종료시간
	private String satOperOpenHhmm;// 토요일 오픈시간
	private String satCloseHhmm; // 토요일 종료시간
	private String holidayOpenHhmm; // 공휴일 오픈시간
	private String holidayCloseHhmm; // 공휴일 종료시간
	
	private String fee; // 요금정보(유료,무료)
	private int basicTime; // 주차 기본 시간
	private int basicFee; // 주차 기본 요금
	private int additionalTime; // 추가 단위 시간
	private int additionalFee; // 추가 단위 요금
	private double dailyTicketApplicableTime; // 1일 주차권 요금 적용 시간
	private int dailyTicketFee; // 1일 주차권 요금
	private int monthlyPassFee; // 월정기 권 요금
	private String paymentMethod; // 결제 방법
	private String specialNotes; // 특기 사항
	private String managementAgency; // 관리 기관명
	private String phoneNumber; // 주차장 전화번호
	private String dataStandardDate; // 데이터 기준 일자


}
