package com.example.hello2.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor

public class Hospital {
    private int id;
    private String openServiceName; //자바는 Camel case로 이름을 설정해준다 _ 사용 자제
    //Camel case란? 설명 적어보기
    private int openLocalGovernmentCode;
    private String managementNumber;
    private LocalDateTime lisenseDate;
    private int businessStatus;
    private int businessStatusCode;
    private String phone;
    private String fullAddress ;
    private String roadNameAddress;
    private String hospitalName;
    private String businessTypeName;
    private int healthcareProviderCount;
    private int patientRoomCount;
    private int totalNumberOfBeds;
    private float TotalAreaSize;

}
