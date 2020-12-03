# LoRa를 활용한 어린이집 안심 지킴이 서비스
한국산업기술대학교 컴퓨터공학과 졸업작품<br>
Korea Polytechnic University<br>
Computer Engineering Senier Project<br>

--------------------------------------

### 프로젝트명 
#### LoRa를 활용한 어린이집 안심 지킴이 서비스 


### 개요  
----------------------------------------------------------
- 증가하는 아동 실종 신고와 미처리된 현황 (Increasing child missing reports and untreated status)

- 아이의 안전에 온전히 신경을 쓰기 힘든 부모 (Parents who are unable to take full care of their child's safety)

- LoRa 모듈을 사용한 저전력, 장거리 데이터 통신 (Low power, long distance data communication using LoRa module)

- 아이의 실시간 위치 정보와 활동량 정보 수집 및 활용 (Collects and utilizes real-time location and activity information for children)

- 부모의 애플리케이션을 통한 정보 확인 (Identifying information through parents' applications)


### 목적
----------------------------------------------------------
- 아이가 들고 다닐 End device에 부착된 GPS, Gyro 센서를 통해 아이의 실시간 위치 정보와 걸음 수 정보 수집 (Get real-time location and number of steps of a child through GPS and Gyro sensors attached to the child's end device)

- End device와 Gateway 간 LoRa 모듈을 통한 저전력, 장거리 통신 (Low power, long distance communication through LoRa module between End device and Gateway)

- 부모의 애플리케이션에서 수집한 위치, 활동량 정보 확인 및 아이의 이동 범위 제한 (Identifying the location, activity information collected by parents' applications, and limiting the child's range of travel)

- 웹을 통한 수집 정보 확인 및 기타 정보 확인 (View collection information and other information via the Web and other information)

### 기능 
----------------------------------------------------------
- 실시간 위치 및 이동 경로 표시 (Show real-time location and travel path)

- 이동 범위 제한 및 알림 (Restrictions and Notifications on Movements)

- 걸음 수 측정 및 활동량 표시 (Measure the number of steps and display the activity)

- 아이 정보 (Child information)

- 기기 등록 (Device registration)

### 역할
----------------------------------------------------------
* 박인효 : CoAP 서버&클라이언트 구현, Web Server 내 MySQL DB 구축 및 연동
* 양현용 : End Device와 Gateway 간 LoRa 통신으로 GPS 데이터 송수신 기능 구현
* 한승훈 : Android App UI 및 기능 구현, Gyro 센서를 활용한 활동량 측정 기능 구현


### :pencil2: 개발환경
----------------------------------------------------------
* HW 구성 요소
- 디바이스 : Raspberry Pi 4/zero WH – End-device, Gateway, Network server

- 모듈 : mpu-6050, L80-39 GPS, Dragino LoRa/GPS Hat – End-device 위치 추적, 활동량 측정

- 통신 : LoRa, Ethernet – End-device와 Gateway간 통신, 그 외 통신

- 개발 언어 : C, Java – LoRa driver 설정, CoAP 설정


* SW 구현 환경
- OS : Raspbian, Windows10 – 디바이스, Web, Application 개발

- 개발환경(IDE) : Eclipse, Android Studio – Web, Application 개발

- 개발도구 : Maven, MySQL, Gradle – CoAP, DB, Application 개발

- 개발언어 : C, Java, Python – CoAP, GPS data, Web 개발


### :pencil2: 시연
----------------------------------------------------------
#### Device
![image](https://user-images.githubusercontent.com/50897259/100999032-96bd6980-359f-11eb-96de-483413c4196e.png)

#### Gateway
![image](https://user-images.githubusercontent.com/50897259/101002655-6972ba80-35a3-11eb-91b0-6248b3f3e674.png)

#### Network Server
![image](https://user-images.githubusercontent.com/50897259/100999485-2ebb5300-35a0-11eb-9eec-e124fa9d3fb6.png)

#### Application Server
![image](https://user-images.githubusercontent.com/50897259/100999585-4abef480-35a0-11eb-80c8-0ff2a2f03d1f.png)

#### Application
<iframe width="560" height="315" src="https://www.youtube.com/embed/-hwELHBaq9k" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>

### :pencil2: 참고
----------------------------------------------------------
* [Statistical data](https://www.index.go.kr/potal/main/EachDtlPageDetail.do?idx_cd=1610)
* [How to set CoAP using californium Library](https://github.com/eclipse/californium)
* [Comparison of LoRa technology with other technologies](https://m.blog.naver.com/PostView.nhn?blogId=2011topcit&logNo=220756027775&proxyReferer=https:%2F%2Fwww.google.co.kr%2F)
* [Raspberry Pi zero WH Setting](https://inmile.tistory.com/27)
* [Raspberry Pi 4 Setting](https://geeksvoyage.com/raspberry%20pi4/preparation-for-pi4/)
* [How to use with Single Channel Gateway](https://www.youtube.com/watch?v=32eLnlYoLoI)
* [mpu-6050 setting](https://medium.com/@kalpeshnpatil/raspberry-pi-interfacing-with-mpu6050-motion-sensor-c9608cd5f59c)
* [Dragino, "LoRa/GPS HAT" with RPi 3](http://wiki.dragino.com/index.php?title=Getting_GPS_to_work_on_Raspberry_Pi_3_Model_B)
* [L80-39 USB GPS Module](https://wiki.52pi.com/index.php/USB-Port-GPS_Module_SKU:EZ-0048)
* [Get GPS location Data](https://gist.github.com/tinti/6415130)
* [Reference Manual about Gyro sensor](https://m.blog.naver.com/PostView.nhn?blogId=dlwjddns5&logNo=220725348476&proxyReferer=https%3A%2F%2Fwww.google.com%2F)
* [Count step using Gyro Sensor(mpu6050) on RPi](https://3246902.blog.me/221965791915)
