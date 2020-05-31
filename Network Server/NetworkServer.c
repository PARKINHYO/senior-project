#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/socket.h>
#include <WaspLoRaWAN.h>
#include <WaspFrame.h>

/*
이 예제는 OTAA를 사용해서 네트워크(에 가입한 벡엔드 네트워크 서버가 ACK를 사용하여 모듈을 구성한 후, LoRaWAN 게이트웨이에 프레임을 송신하는 코드입니다.
*/

// 소켓 사용
uint8_t socket = SOCKET0;

// 디바이스 파라미터 설정
char DEVICE_EUI[] = "";	
char APP_EUI[] = "";
char APP_KEY[] = "";

// 서버에서 사용할 포트 설정
uint8_t PORT = 3;

// 에러 변수
uint8_t error;
uint8_t error_config = 0;

// 와스모트 ID 설정
char moteID[] = "node_01";

void setup()
{
	USB.ON();
	USB.println(F("LoRaWAN 송수신 예제 - 확인된 패킷 ACK 송신\n"));

	USB.println(F("------------------------------------"));
	USB.println(F("모듈 구성"));
	USB.println(F("------------------------------------\n"));

	// 1. 스위치 ON
	error = LoRaWAN.ON(socket);

	// 상태 검사
	if (error == 0)
		USB.println(F("1. Switch ON OK"));
	else
	{
		USB.print(F("1. Switch ON error = "));
		USB.println(error, DEC);
		error_config = 1;
	}

	// 데이터 전송속도 변경
	error = LoRaWAN.setDataRate(3);

	// 상태 검사
	if (error == 0)
		USB.println(F("2. Data rate set OK"));
	else
	{
		USB.print(F("2. Data rate set error= "));
		USB.println(error, DEC);
		error_config = 2;
	}

	// 3. 디바이스 EUI 설정
	error = LoRaWAN.setDeviceEUI(DEVICE_EUI);

	// 상태 검사
	if (error == 0)
		USB.println(F("3. Device EUI set OK"));
	else
	{
		USB.print(F("3. Device EUI set error = "));
		USB.println(error, DEC);
		error_config = 3;
	}

	// 4. 애플리케이션 EUI 설정
	error = LoRaWAN.setAppEUI(APP_EUI);

	// 상태 검사
	if (error == 0)
		USB.println(F("4. Application EUI set OK"));
	else
	{
		USB.print(F("4. Application EUI set error = "));
		USB.println(error, DEC);
		error_config = 4;
	}

	// 5. 애플리케이션 세션 키 설정
	error = LoRaWAN.setAppKey(APP_KEY);

	// 상태 검사
	if (error == 0)
		USB.println(F("5. Application Key set OK"));
	else
	{
		USB.print(F("5. Application Key set error = "));
		USB.println(error, DEC);
		error_config = 5;
	}

	// 6. OTAA에 가입하여 서버와 키 협상
	error = LoRaWAN.joinOTAA();

	// 상태 검사
	if (error == 0)
		USB.println(F("6. Join network OK"));
	else
	{
		USB.print(F("6. Join network error = "));
		USB.println(error, DEC);
		error_config = 6;
	}

	// 7. 구성 설정
	error = LoRaWAN.saveConfig();

	// 상태 검사
	if (error == 0)	
		USB.println(F("7. Save configuration OK"));
	else
	{
		USB.print(F("7. Save configuration error = "));
		USB.println(error, DEC);
		error_config = 7;
	}

	// 8. 스위치 OFF
	error = LoRaWAN.OFF(socket);

	// 상태 검사
	if (error == 0)	
		USB.println(F("8. Switch OFF OK"));
	else
	{
		USB.print(F("8. Switch OFF error = "));
		USB.println(error, DEC);
		error_config = 8;
	}

	if (error_config == 0) {
		USB.println(F("\n---------------------------------------------------------------"));
		USB.println(F("Module configured"));
		USB.println(F("After joining through OTAA, the module and the network exchanged "));
		USB.println(F("the Network Session Key and the Application Session Key which "));
		USB.println(F("are needed to perform communications. After that, 'ABP mode' is used"));
		USB.println(F("to join the network and send messages after powering on the module"));
		USB.println(F("---------------------------------------------------------------\n"));
		USB.println();
	}
	else {
		USB.println(F("\n---------------------------------------------------------------"));
		USB.println(F("Module not configured"));
		USB.println(F("Check OTTA parameters and reestart the code."));
		USB.println(F("If you continue executing the code, frames will not be sent."));
		USB.println(F("\n---------------------------------------------------------------"));
	}

	frame.setID(moteID);
}

void loop()
{
	// 1. 새 프레임 생성

	// ACC 초기화
	ACC.ON();

	USB.println(F("1. Creating an BINARY frame"));

	// 새 프레임 생성
	frame.createFrame(BINARY);

	// 프레임 필드 설정 (배터리 센서 - uint8_t)
	frame.addSensor(SENSOR_BAT, PWR.getBatteryLevel());

	// 나머지 프레임 필드 설정
	frame.addSensor(SENSOR_ACC, ACC.getX(), ACC.getY(), ACC.getZ());

	// 프레임 출력
	frame.showFrame();

	// 가속도계 OFF
	ACC.OFF();

	// 2. 스위치 ON
	error = LoRaWAN.ON(socket);

	// 상태 검사
	if (error == 0)	
		USB.println(F("2. Switch ON OK"));
	else
	{
		USB.print(F("2. Switch ON error = "));
		USB.println(error, DEC);
	}

	// 3. 네트워크 가입
	error = LoRaWAN.joinABP();

	// 상태 검사
	if (error == 0)
	{
		USB.println(F("3. Join network OK"));

		error = LoRaWAN.getMaxPayload();

		if (error == 0)
		{
			// 4. 에러가 없으면 타이니 프레임 생성
			USB.print(F("4.1. LoRaWAN maximum payload: "));
			USB.println(LoRaWAN._maxPayload, DEC);

			// 최대 페이로드 설정
			frame.setTinyLength(LoRaWAN._maxPayload);

			boolean end = false;
			uint8_t pending_fields = 0;

			while (end == false)
			{
				pending_fields = frame.generateTinyFrame();

				USB.print(F("4.2. Tiny frame generated:"));
				USB.printHexln(frame.bufferTiny, frame.lengthTiny);

				// 5. 확인된 패킷 전송
				USB.println(F("5. LoRaWAN confirmed sending..."));
				error = LoRaWAN.sendConfirmed(PORT, frame.bufferTiny, frame.lengthTiny);

				// 에러 메시지:
				/*
				  '6' : 모듈이 네트워크에 가입되어 있지 않음
				  '5' : 전송 에러
				  '4' : 데이터 길이 에러
				  '2' : 모듈 반응 없음
				  '1' : 모듈 송수신 에러
				*/

				// 상태 검사
				if (error == 0)
				{
					USB.println(F("5.1. LoRaWAN send packet OK"));
					if (LoRaWAN._dataReceived == true)
					{
						USB.print(F("  There's data on port number: "));
						USB.print(LoRaWAN._port, DEC);
						USB.print(F("\r\n  Data: "));
						USB.println(LoRaWAN._data);
					}
				}
				else
				{
					USB.print(F("5.1. LoRaWAN send packet error = "));
					USB.println(error, DEC);
				}

				if (pending_fields > 0)
				{
					end = false;
					delay(10000);
				}
				else
					end = true;
				
			}
		}
		else
		{
			USB.println(F("4. LoRaWAN error getting the maximum payload"));
		}
	}
	else
	{
		USB.print(F("2. Join network error = "));
		USB.println(error, DEC);
	}

	// 6. 스위치 OFF
	error = LoRaWAN.OFF(socket);

	// 상태 검사
	if (error == 0)
		USB.println(F("6. Switch OFF OK"));
	else
	{
		USB.print(F("6. Switch OFF error = "));
		USB.println(error, DEC);
	}

	USB.println();
	delay(10000);
}