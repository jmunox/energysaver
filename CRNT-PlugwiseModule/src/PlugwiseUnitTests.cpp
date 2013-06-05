/*
 * Unit_Tests.cpp
 *
 *  Created on: Jan 27, 2012
 *      Author: jmunoza
 */
#include <iostream>
#include <sys/time.h>
#include <string.h> //for strlen
#include <time.h> //for clock and timer
#include <iomanip>
#include <cstring>


#include "plugwise/CRC16_CCITT.h"
//#include "alpha6/TimeOutThread.h"
#include "plugwise/PlugwiseRequest.h"
#include "plugwise/PlugwiseDataPacket.h"
//#include "alpha6/PlugwiseConnection.h"
//#include "alpha6/InitStickPlugwiseTask.h"
#include "plugwise/SwitchOnOffPlugwiseDevice.h"
#include "plugwise/NumberConverter.h"
//#include "alpha6/CalibrationRequest.h"
#include "plugwise/UnsignedCharValue.h"
//#include "alpha6/EnergyConsumptionMonitor.h"
//#include "alpha6/HourEnergyConsumptionMonitor.h"
//#include "alpha6/DeviceInformationRequest.h"
//#include "alpha6/PollEnergyRequest.h"
#include "plugwise/PollDevicesTask.h"
#include "plugwise/PlugwiseStick.h"
#include "plugwise/HTTPClientWriter.h"


#include "core/DataPacket.h"
#include "core/Thread.h"
#include "core/Mutex.h"


using namespace std;
#define TEST_CCR16 0
#define TEST_UNSIGNED_CHAR_VALUE 0
#define TEST_TIMER_THREAD 0
#define TEST_PLUGWISE_REQUEST 0
#define TEST_CONNECTION 0
#define TEST_INIT_STICK 0
#define TEST_SWITCH_ON 0
#define TEST_NUMBER_CONVERTER 0
#define TEST_CALIBRATION_REQUEST 0
#define TEST_ENERGY_MONITOR 0
#define TEST_INFO_RQUEST 0
#define TEST_HOUR_ENERGY_MONITOR 0
#define TEST_POLL_ENERGY_TASK 0
#define TEST_HTTP_CLIENT 0



int main() {
	std::cout << "Unit tests" << std::endl;

	#if TEST_CCR16
	cout << "Starting Test: [CRC Test]" << std::endl;
	std::string pre_crc = "00130002000D6F0000AF63250000000100000017000000000004"; //F49A
	//std::string pre_crc = "0012000D6F0000D33D8C"; //0668
	CRC16_CCITT *crc_test(new CRC16_CCITT());
	std::string pos_crc = crc_test->calculate_CRC(pre_crc);
	std::cout << "[CRC Test] data = " << pre_crc << " | CRC = " << pos_crc << std::endl;
	#endif


	#if TEST_UNSIGNED_CHAR_VALUE
	cout << "Starting Test: [UnsignedCharValue Test]" << std::endl;
	const char* my_char = "00130002000D6F0000AF63250000000100000017000000000004"; //F49A
	const unsigned char* my_char_un = reinterpret_cast<const unsigned char*>(my_char);
	UnsignedCharValue* u_char = new UnsignedCharValue(my_char_un);
	cout << "[UnsignedCharValue Test] UnsignedChar Value: " << u_char->getChar() << std::endl;
	DataPacket* dp = new DataPacket();
	dp->dataVector.push_back(u_char);
	cout << "[UnsignedCharValue Test] DataPacket StreamOut Value: " << dp->dataVector.at(0) << std::endl;
	const unsigned char* my_char_un2 = ((UnsignedCharValue*) dp->dataVector.at(0))->getChar();
	cout << "[UnsignedCharValue Test] Char DataPacket Value: " << my_char_un2 << std::endl;
	float my_float = 11.8666;
	std::ostringstream float_stream;
	float_stream << my_float;
	cout << "[UnsignedCharValue Test] float: " << my_float << " - float string:" << float_stream.str() << std::endl;
	UnsignedCharValue* u_char_float = new UnsignedCharValue(float_stream.str());
	dp->dataVector.push_back(u_char_float);
	const unsigned char* my_char_un_float = ((UnsignedCharValue*) dp->dataVector.at(1))->getChar();
	cout << "[UnsignedCharValue Test] Char DataPacket Value: " << my_char_un_float << std::endl;
	#endif


#if TEST_TIMER_THREAD
	cout << "Starting Test: [TIMER_THREAD Test]" << std::endl;
	//struct timeval fireTimeVal;
	//gettimeofday(&fireTimeVal, NULL);
	//fireTimeVal.tv_sec += 15;
	TimeOutThread *mytimer(new TimeOutThread(10));
	cout << "[TIMER_THREAD Test] Running Timer" << std::endl;
	mytimer->start();
	cout << "[TIMER_THREAD Test] Sleep 20 seg" << std::endl;
	usleep(1000*1000*20);
	cout << "[TIMER_THREAD Test] Finish" << std::endl;
#endif

	#if TEST_PLUGWISE_REQUEST
	cout << "Starting Test: [PlugwiseRequest Test]" << std::endl;
	std::string message((char*)PlugwiseRequest::STICK_INITIAL_REQUEST);
	PlugwiseRequest *request(new PlugwiseRequest(message));
	cout << "[PlugwiseRequest Test] Message: " << request->get_message() << std::endl;
	cout << "[PlugwiseRequest Test] Request Data: " << request->get_data(0) << std::endl;
	cout << "[PlugwiseRequest Test] Request Data Packet: " << request->get_data(0) << std::endl;

	//TEST String Constructor
	std::ostringstream request_str;
	request_str << PlugwiseRequest::POWER_CHANGE_REQUEST;
	request_str << PlugwiseDevice::MAC_ADDRESS_PREFFIX;
	request_str << 1;
	std::string req_string = request_str.str();
	cout << "[PlugwiseRequest Test] Req string: " << req_string << std::endl;
	PlugwiseRequest *request2(new PlugwiseRequest(req_string));
	cout << "[PlugwiseRequest Test] Message: " << request2->get_message() << std::endl;
	cout << "[PlugwiseRequest Test] Request: " << request2->get_data(0) << std::endl;

	#endif



	#if TEST_CONNECTION
	cout << "Starting Test: [Connection Test]" << std::endl;
	const std::string message = "000A"; //Init
	//const std::string message = "0017000D6F0000AF632501"; //energy on off
	//const std::string message = "003E000D6F0000AF6325"; //energy request

	//const std::string message = "000A"; //Init
				//const std::string message = "0017000D6F0000AF632501"; //energy on off
				//const std::string message = "003E000D6F0000AF6325"; //energy request

	PlugwiseStick *conn(new PlugwiseStick("/dev/ttyUSB0"));
	conn->open_conn();
	//std::string message((char*)PlugwiseRequest::STICK_INITIAL_REQUEST);
	PlugwiseRequest *request(new PlugwiseRequest(message));
	cout << "[Connection Test] Message: " << request->get_message() << std::endl;
	cout << "[Connection Test] Request: " << (request->get_message()).length() << std::endl;
	PlugwiseDataPacket *response;
	response = &conn->send_message(request);
	const unsigned char* resp_char;
	resp_char = response->get_data(0);
	std::cout << "[Connection Test] Response: " << resp_char << std::endl;
	std::cout << "[Connection Test] Response Size: " << strlen((char*)resp_char) << std::endl;
	conn->close_conn();
	#endif



#if TEST_INIT_STICK
	cout << "Starting Test: [Init Stick Test]" << std::endl;
	PlugwiseStick *conn(new PlugwiseStick("/dev/ttyUSB0"));
	conn->open_conn();
	conn->initialize_plugwise_stick();
	cout << "[/Init Stick Test] " << std::endl;
	conn->close_conn();
#endif


#if TEST_SWITCH_ON
	cout << "Starting Test: [Switch On Off Test]" << std::endl;
	SwitchOnOffPlugwiseDevice *my_switch(new SwitchOnOffPlugwiseDevice("/dev/ttyUSB0", "AF6325", true));
	my_switch->run();
	cout << "[Switch On Off Test] " << std::endl;
	//std::cout << "[Connection Test] Response: " << conn->send_message(message).toString() << std::endl;
	//conn->close_conn();
#endif


#if TEST_NUMBER_CONVERTER
cout << "Starting Test: [NumberConverter Test]" << std::endl;
NumberConverter *numbconv(new NumberConverter());
const char* my_char = "1C";
int my_int = -1450639356;
my_int -=8;
std::cout << "[NumberConverter Test] char value: " << my_char << " , int value: " << numbconv->get_int_from_hex_char(my_char) << std::endl;
std::cout << "[NumberConverter Test] char value: " << my_char << " , float value: " << numbconv->get_float_from_hex_char(my_char) << std::endl;

//-1450639356
//A988FFFC0
//A988FFFC
std::cout << "[NumberConverter Test] int value: " << my_int << " , char value: " << numbconv->get_hex_char_from_int(my_int,8) << std::endl;
int minutes = 100;
std::cout << "[NumberConverter Test] minutes value: " << minutes << " , time value: " << numbconv->get_time(minutes) << std::endl;
#endif


#if TEST_CALIBRATION_REQUEST
cout << "Starting Test: [CalibrationRequest Test]" << std::endl;
PlugwiseCircle *circle(new PlugwiseCircle("728967"));
			circle->set_plugwise_stick(new PlugwiseStick("/dev/ttyUSB0"));
			circle->get_calibration_information();
#endif
			//617.349

#if TEST_ENERGY_MONITOR
cout << "Starting Test: [Energy Monitor Test]" << std::endl; //AF6325 //D328D9 729903
PlugwiseCircle *circle(new PlugwiseCircle("729D6B"));
			circle->set_plugwise_stick(new PlugwiseStick("/dev/ttyUSB0"));
			circle->get_instant_energy_consumption();
#endif

#if TEST_INFO_RQUEST
cout << "Starting Test: [Device Info Test]" << std::endl;
PlugwiseCircle *circle(new PlugwiseCircle("AF6325"));
			circle->set_plugwise_stick(new PlugwiseStick("/dev/ttyUSB0"));
			circle->get_device_information();
			circle->get_instant_energy_consumption();
#endif


#if TEST_HOUR_ENERGY_MONITOR
cout << "Starting Test: [Hour Energy Monitor Test]" << std::endl;
PlugwiseCircle *circle(new PlugwiseCircle("5993DE"));
			circle->set_plugwise_stick(new PlugwiseStick("/dev/ttyUSB0"));
			circle->get_hour_energy_consumption();
#endif



#if TEST_POLL_ENERGY_TASK
cout << "Starting Test: [PollEnergyTask Test]" << std::endl;
PollDevicesTask *task(new PollDevicesTask("/dev/ttyUSB0",10,3));

task->device_list.push_back("D33D8C");//me
task->device_list.push_back("729903");//j
task->device_list.push_back("7290C9");//nic
//task->device_list.push_back("D33D78"); //marija
//task->device_list.push_back("D328D9"); // paola
cout << "[PollEnergyTask Test] Run:" << std::endl;
task->run();
#endif

#if TEST_HTTP_CLIENT
cout << "Starting HTTP Client";
	HTTPClientWriter* client (new HTTPClientWriter());
	client->host="www.actlab.ele.tue.nl";
	client->port=80;
	client->end_point= "/~jmunoz/CRNTrequest";
	//std::string message = "time=1022022&plugid=729903&power=1.2300644334556";
	client->testMessage();



	struct timeval timestamp;
	gettimeofday( &timestamp, NULL );
	ostringstream outs;

		outs <<timestamp.tv_sec;
		outs << setfill('0') << setw(6) << timestamp.tv_usec;
		string s = outs.str();
		//cout << s<<std::endl;

		float pulsesOffsetNoise=35;
		float _device_gain_B=-1.30328e-06;
		float _device_gain_A=0.995317;
		float _device_offset_total=0.0180741;
		float _device_offset_noise=0;

		float pulsesCorrected = ((pulsesOffsetNoise * pulsesOffsetNoise) * _device_gain_B) + (pulsesOffsetNoise * _device_gain_A) + _device_offset_total;
		cout<<"pulsesCorrected"<<pulsesCorrected<<endl;
		float power =  (pulsesCorrected/(468.9385193))*1000; // get power in watts
		cout<<"power"<<power<<endl;
#endif

#if TEST_PARSE_RESPONSE
		std::ostringstream request_str;
				request_str << PlugwiseRequest::DEVICE_CALIBRATION_REQUEST;
				request_str << PlugwiseRequest::DEVICE_CALIBRATION_REQUEST;
				request_str << PlugwiseRequest::DEVICE_CALIBRATION_REQUEST;
				request_str << PlugwiseRequest::DEVICE_CALIBRATION_REQUEST;
				request_str << PlugwiseRequest::HEADER;
				request_str << PlugwiseRequest::DEVICE_CALIBRATION_REQUEST;
				request_str << PlugwiseRequest::CLOCK_GET_REQUEST;
				request_str << PlugwiseRequest::CLOCK_GET_REQUEST;
				request_str << PlugwiseRequest::CLOCK_GET_REQUEST;
				request_str << PlugwiseRequest::HEADER;
				request_str << PlugwiseRequest::DEVICE_CALIBRATION_REQUEST;
				request_str << PlugwiseRequest::CLOCK_GET_REQUEST;
				request_str << PlugwiseRequest::CLOCK_GET_REQUEST;
				request_str << PlugwiseRequest::CLOCK_GET_REQUEST;
				request_str << PlugwiseRequest::HEADER;
				request_str << PlugwiseRequest::DEVICE_CALIBRATION_REQUEST;
				request_str << PlugwiseRequest::CLOCK_GET_REQUEST;
				request_str << PlugwiseRequest::CLOCK_GET_REQUEST;
				request_str << PlugwiseRequest::CLOCK_GET_REQUEST;
				request_str << PlugwiseRequest::HEADER;
				request_str << PlugwiseRequest::DEVICE_CALIBRATION_REQUEST;
				request_str << PlugwiseRequest::DEVICE_CALIBRATION_REQUEST;
				//request_str << PlugwiseRequest::ACKNOWLEDGE;
				request_str << PlugwiseRequest::HEADER;
				request_str << PlugwiseRequest::DEVICE_CALIBRATION_REQUEST;
				request_str << PlugwiseRequest::DEVICE_CALIBRATION_REQUEST;
				//request_str << PlugwiseRequest::DEVICE_CALIBRATION_REQUEST;
				request_str << PlugwiseRequest::ACKNOWLEDGE;
				request_str << PlugwiseCircle::MAC_ADDRESS_PREFFIX;
				request_str << "5993DE";

				std::string message_string = request_str.str();
				PlugwiseRequest *request(new PlugwiseRequest(message_string));


				std::string response_str = request->_my_data;
				cout<<"response_str"<<response_str<<endl;
				int location_first_header; //pos 0 for firmware 2011
				int location_second_header; //pos 23 for firmware 2011
				int location_third_header; // for old firmware

				std::string ack;
				location_first_header = response_str.find((char*)PlugwiseRequest::HEADER);

				while((response_str.substr(location_first_header+12, 4) != (char*)PlugwiseRequest::ACKNOWLEDGE)&&location_first_header>=0){
					location_first_header = response_str.find((char*)PlugwiseRequest::HEADER,(location_first_header+4));
				}
				ack = response_str.substr(location_first_header+12, 4);


				if(ack == (char*)PlugwiseRequest::ACKNOWLEDGE)
					cout<<"ack: "<<ack<<endl;

				//const unsigned char* req_char = reinterpret_cast<const unsigned char*>(request_str.str().c_str());
				//PlugwiseRequest *request= new PlugwiseRequest(req_char);


				  time_t seconds1, seconds2;

				  seconds1 = time (NULL);

				  cout << seconds1 << " seconds since January 1, 1970"<<endl;
				  usleep(1000*1000*10);
				  seconds2 = time (NULL);
				  cout << seconds2 << " seconds since January 1, 1970"<<endl;
				  int diff = seconds2 - seconds1;
				  diff = 12 - diff;
				  cout << diff << " seconds diff"<<endl;



				  std::string device_address = "2620810";
				  cout << device_address.size();
				  cout << device_address.length();
				  std::string d2; //= "0" + device_address;
				  cout << d2.length()<<endl;
				  cout << d2<<endl;
				  std::ostringstream _device;
				  _device  << std::right<< setfill('0')<<setw(8) << std::hex << std::uppercase << device_address;
				  d2 = _device.str();
				  cout << d2<<endl;
#endif
		return 0;
}
