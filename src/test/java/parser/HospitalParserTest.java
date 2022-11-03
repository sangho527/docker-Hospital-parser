package parser;

import com.example.hello2.Hello2Application;
import com.example.hello2.dao.HospitalDao;
import com.example.hello2.domain.Hospital;
import com.example.hello2.parser.HospitalParser;
import com.example.hello2.parser.ReadLineContext;
import com.example.hello2.service.HospitalService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Hello2Application.class) //원인은 테스트 파일이 hello1 패키지 하위에 존재하지 않아서 @SpringBootApplication을 찾지 못하는 것이다.
class HospitalParserTest {

    String line1 = "\"1\",\"의원\",\"01_01_02_P\",\"3620000\",\"PHMA119993620020041100004\",\"19990612\",\"\",\"01\",\"영업/정상\",\"13\",\"영업중\",\"\",\"\",\"\",\"\",\"062-515-2875\",\"\",\"500881\",\"광주광역시 북구 풍향동 565번지 4호 3층\",\"광주광역시 북구 동문대로 24, 3층 (풍향동)\",\"61205\",\"효치과의원\",\"20211115113642\",\"U\",\"2021-11-17 02:40:00.0\",\"치과의원\",\"192630.735112\",\"185314.617632\",\"치과의원\",\"1\",\"0\",\"0\",\"52.29\",\"401\",\"치과\",\"\",\"\",\"\",\"0\",\"0\",\"\",\"\",\"0\",\"\",";

    @Autowired
    ReadLineContext<Hospital> hospitalReadLineContext;

    @Autowired // factory도없는데 di가 되는이유는..? => hospital 안에있는 component 어노테이션을 빈으로 등록하기 때문,
    HospitalDao hospitalDao;

    @Autowired
    HospitalService hospitalService;

    @Test
    @DisplayName("10만건 이상 데이터가 파싱 되는지")
    void oneHundreadThousandRows() throws IOException {
        // 서버환경에서 build할 때 문제가 생길 수 있습니다.

        // 어디에서든지 실행할 수 있게 짜는 것이 목표.
        hospitalDao.deleteAll();
        String filename = "C:\\Users\\PC\\Downloads\\fulldata_01_01_02_P_의원.csv";
        int cnt = this.hospitalService.insertLargeVolumeHospitalData(filename);
        assertTrue(cnt > 1000);
        assertTrue(cnt > 10000);
        System.out.printf("파싱된 데이터 개수:%d %n", cnt);
    }




    @Test
    @DisplayName("insert, select가 잘 되는지")
    void addAndGet(){
//        hospitalDao.deleteAll();
//        assertEquals(0, hospitalDao.getCount());
//        HospitalParser hp = new HospitalParser();
//        Hospital hospital = hp.parse(line1);
//        hospitalDao.add(hospital);
//        assertEquals(1, hospitalDao.getCount());
//
//        Hospital selectedHospital = hospitalDao.findById(hospital.getId());
//        assertEquals(selectedHospital.getId(), hospital.getId());
//        assertEquals(selectedHospital.getOpenServiceName(), hospital.getOpenServiceName());
//        assertEquals(selectedHospital.getHospitalName(), hospital.getHospitalName());
//        //날짜, float
//        assertTrue(selectedHospital.getLisenseDate().isEqual(hospital.getLisenseDate()));
//        assertEquals(selectedHospital.getTotalAreaSize(), hospital.getTotalAreaSize());
//        //get이 없어서 assert는 눈으로 하기
//        assertEquals(selectedHospital.getOpenLocalGovernmentCode(), hospital.getOpenLocalGovernmentCode());
//        assertEquals(selectedHospital.getBusinessStatus(), hospital.getBusinessStatus());
//        assertEquals(selectedHospital.getBusinessStatusCode(), hospital.getBusinessStatusCode());
//        assertEquals(selectedHospital.getManagementNumber(), hospital.getManagementNumber());
//        assertEquals(selectedHospital.getPhone(), hospital.getPhone());
//        assertEquals(selectedHospital.getFullAddress(), hospital.getFullAddress());
//        assertEquals(selectedHospital.getRoadNameAddress(), hospital.getRoadNameAddress());
//        assertEquals(selectedHospital.getBusinessTypeName(), hospital.getBusinessTypeName());
//        assertEquals(selectedHospital.getHealthcareProviderCount(), hospital.getHealthcareProviderCount());
//        assertEquals(selectedHospital.getPatientRoomCount(), hospital.getPatientRoomCount());
//        assertEquals(selectedHospital.getTotalNumberOfBeds(), hospital.getTotalNumberOfBeds());
    }

    @Test
    void name() throws IOException {
        String filename = "C:\\Users\\PC\\Downloads\\fulldata_01_01_02_P_의원.csv";
        List<Hospital> hospitalList = hospitalReadLineContext.readByLine(filename);
        assertTrue(hospitalList.size() > 1000);
        assertTrue(hospitalList.size() > 10000);

        for (int i = 0; i < 10; i++) {
            System.out.println(hospitalList.get(i).getHospitalName());
        }
        System.out.printf("파싱된 데이터 개수:", hospitalList.size());
    }

    @Test
    @DisplayName("csv 1줄을 Hostpital로 잘 만드는지 Test")
    void convertToHospital() {

        HospitalParser hp = new HospitalParser();
        Hospital hospital = hp.parse(line1);

        assertEquals(1, hospital.getId()); //컬럼 넘버 적기 col:0
        assertEquals("의원", hospital.getOpenServiceName()); //col:1
        assertEquals(3620000,hospital.getOpenLocalGovernmentCode()); //col:3
        assertEquals("PHMA119993620020041100004",hospital.getManagementNumber()); //col:4
        assertEquals(LocalDateTime.of(1999, 6, 12, 0, 0, 0), hospital.getLisenseDate()); //19990612 col:5
        assertEquals(6, hospital.getBusinessStatus()); //col:7
        assertEquals(13, hospital.getBusinessStatusCode()); //col:9
        assertEquals("062-515-2875", hospital.getPhone()); //col:15
        assertEquals("광주광역시 북구 풍향동 565번지 4호 3층", hospital.getFullAddress()); //col:18
        assertEquals("광주광역시 북구 동문대로 24, 3층 (풍향동)", hospital.getRoadNameAddress()); //col:19
        assertEquals("효치과의원", hospital.getHospitalName()); //col:21
        assertEquals("치과의원", hospital.getBusinessTypeName()); //col:25
        assertEquals(1, hospital.getHealthcareProviderCount()); //col:29
        assertEquals(0, hospital.getPatientRoomCount()); //col:30
        assertEquals(0, hospital.getTotalNumberOfBeds()); //col:31
        assertEquals(52.29f, hospital.getTotalAreaSize()); //col:32



    }

}