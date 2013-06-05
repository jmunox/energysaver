/*
 * PlugwiseTestTask.cpp
 *
 *
 *  This file is part of the "Plugwise Module for CRN Toolbox".
 *
 * 	"Plugwise Module for CRN Toolbox" is free software: you can redistribute it and/or modify
 * 	it under the terms of the GNU General Public License as published by
 * 	the Free Software Foundation, either version 3 of the License, or
 * 	(at your option) any later version.
 *
 * 	"Plugwise Module for CRN Toolbox" is distributed in the hope that it will be useful,
 * 	but WITHOUT ANY WARRANTY; without even the implied warranty of
 * 	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * 	GNU General Public License for more details.
 *
 * 	You should have received a copy of the GNU General Public License
 * 	along with "Plugwise Module for CRN Toolbox". If not, see <http://www.gnu.org/licenses/>
 *
 *
 * 	"Plugwise Module for CRN Toolbox" is based on the protocol information provided by
 * 	<a href="http://www.maartendamen.com/category/plugwise-unleashed/">Maarten Damen</a> and
 * 	<a href="http://roheve.wordpress.com/2011/04/24/plugwise-protocol-analyse/">Roheve</a>;
 * 	and the code implementations from
 * 	<a href="https://github.com/gonium/libplugwise">Gonium's libplugwise</a>, and
 *  <a href="http://www.cs.stir.ac.uk/~kjt/software/comms/plugwise.html"> Kenneth J. Turner's work</a>.
 *
 *	@author Jesus Muñoz-Alcantara
 *	@version beta.1.0: 07/05/2012
 */

#include "PlugwiseTestTask.h"

#include "PlugwiseStick.h"
#include "PlugwiseCircle.h"
#include "CRC16_CCITT.h"
#include "PlugwiseRequest.h"
#include "PlugwiseDataPacket.h"
#include "NumberConverter.h"
#include "UnsignedCharValue.h"
#include "SwitchOnOffPlugwiseDevice.h"

using namespace std;


PlugwiseTestTask::PlugwiseTestTask() {
	_test_case = 0;
}

PlugwiseTestTask::~PlugwiseTestTask() {

}

void PlugwiseTestTask::run(){



	running = true;
#if DEBUG
	log( "Running PlugwiseTestTask")<<std::endl;
#endif

	switch(_test_case){
		case 0:
			log("For crc_test type 1");
			log("For uns_char_test type 2");
			log("For request_object type 3");
			log("For connection type 4");
			log("For init_usb type 5");
			log("For switch_on type 6");
			log("For switch_off type 7");
			log("For numb_conv type 8");
			break;
		case 1:
			if(true){
			log("Starting Test: [CRC Test]") << std::endl;
			std::string pre_crc = "00130002000D6F0000AF63250000000100000017000000000004"; //F49A
			std::string exp_crc = "F49A";
			CRC16_CCITT *crc_test(new CRC16_CCITT());
			std::string pos_crc = crc_test->calculate_CRC(pre_crc);
			log("[CRC Test]") << "data = " << pre_crc << " | EXPECTED CRC = " << exp_crc << " | CRC = " << pos_crc << std::endl;
			}
			break;
		case 2:
			if(true){
			log ( "Starting Test: [UnsignedCharValue Test]") << std::endl;
			const char* my_char = "00130002000D6F0000AF63250000000100000017000000000004"; //F49A
			const unsigned char* my_char_un = reinterpret_cast<const unsigned char*>(my_char);
			UnsignedCharValue* u_char = new UnsignedCharValue(my_char_un);
			log("[UnsignedCharValue Test] UnsignedChar Value: ") << u_char->getChar() << std::endl;
			DataPacket* dp = new DataPacket();
			dp->dataVector.push_back(u_char);
			log("[UnsignedCharValue Test] DataPacket StreamOut Value: ") << dp->dataVector.at(0) << std::endl;
			const unsigned char* my_char_un2 = ((UnsignedCharValue*) dp->dataVector.at(0))->getChar();
			log ("[UnsignedCharValue Test] Char DataPacket Value: ") << my_char_un2 << std::endl;
			}
			break;
		case 3:
			if(true){
			log("Starting Test: [PlugwiseRequest Test]") << std::endl;
			//std::string message((char*)PlugwiseRequest::STICK_INITIAL_REQUEST);
			PlugwiseRequest *request(new PlugwiseRequest(_message));
			log( "[PlugwiseRequest Test] Message: ") << request->get_message() << std::endl;
			log( "[PlugwiseRequest Test] Request Data: ") << request->get_data(0) << std::endl;
			log( "[PlugwiseRequest Test] Request Data Packet: ") << request->get_data(0) << std::endl;

			//TEST String Constructor
			std::ostringstream request_str;
			request_str << PlugwiseRequest::POWER_CHANGE_REQUEST;
			request_str << PlugwiseDevice::MAC_ADDRESS_PREFFIX;
			request_str << 1;
			std::string req_string = request_str.str();
			log("[PlugwiseRequest Test] Req string: " )<< req_string << std::endl;
			PlugwiseRequest *request2(new PlugwiseRequest(req_string));
			log( "[PlugwiseRequest Test] Message: ") << request2->get_message() << std::endl;
			log( "[PlugwiseRequest Test] Request: ") << request2->get_data(0) << std::endl;
			}
			break;
		case 4:
			if(true){
			log( "Starting Test: [Connection Test]") << std::endl;
						//const std::string message = "000A"; //Init
						//const std::string message = "0017000D6F0000AF632501"; //energy on off
						//const std::string message = "003E000D6F0000AF6325"; //energy request

						PlugwiseStick *conn(new PlugwiseStick(_serial_port));
						conn->open_conn();
						//std::string message((char*)PlugwiseRequest::STICK_INITIAL_REQUEST);
						PlugwiseRequest *request(new PlugwiseRequest(_message));
						log( "[Connection Test] Message: ") << request->get_message() << std::endl;
						log( "[Connection Test] Request: ") << (request->get_message()).length() << std::endl;
						PlugwiseDataPacket *response;
						response = &conn->send_message(request);
						const unsigned char* resp_char;
						resp_char = response->get_data(0);
						log( "[Connection Test] Response: ") << resp_char << std::endl;
						log( "[Connection Test] Response Size: ") << strlen((char*)resp_char) << std::endl;
						conn->close_conn();
			}
			break;
		case 5:
			if(true){
			log( "Starting Test: [Init Stick Test]" )<< std::endl;
			PlugwiseStick *my_stick(new PlugwiseStick(_serial_port));
				my_stick->initialize_plugwise_stick();
				my_stick->close_conn();
				log( "[/Init Stick Test] ") << std::endl;
			}
				break;
		case 6:
			if(true){
			log(  "Starting Test: [Switch On  Test]" )<< std::endl;
						SwitchOnOffPlugwiseDevice *my_switch(new SwitchOnOffPlugwiseDevice(_serial_port, _device_address, true));
						my_switch->run();
						log(  "[Switch On  Test] ") << std::endl;
			}
			break;
		case 7:
			if(true){
			log(  "Starting Test: [Switch Off  Test]" )<< std::endl;
						SwitchOnOffPlugwiseDevice *my_switch(new SwitchOnOffPlugwiseDevice(_serial_port, _device_address, false));
						my_switch->run();
						log(  "[Switch Off  Test] ") << std::endl;
			}
			break;
		case 8:
			if(true){
			log(  "Starting Test: [NumberConverter Test]") << std::endl;
					NumberConverter *numbconv(new NumberConverter());
					const char* my_char = "1C";
					int my_int = -1450639356;
					my_int -=8;
					log(  "[NumberConverter Test] char value: " )<< my_char << " , int value: " << numbconv->get_int_from_hex_char(my_char) << std::endl;
					log(  "[NumberConverter Test] char value: " )<< my_char << " , float value: " << numbconv->get_float_from_hex_char(my_char) << std::endl;

					log(  "[NumberConverter Test] int value: " )<< my_int << " , char value: " << numbconv->get_hex_char_from_int(my_int,8) << std::endl;
					int minutes = 100;
					log(  "[NumberConverter Test] minutes value: ") << minutes << " , time value: " << numbconv->get_time(minutes) << std::endl;
			}
					break;

	}




		#if TEST_CALIBRATION_REQUEST
		cout << "Starting Test: [CalibrationRequest Test]" << std::endl;
		CalibrationRequest *calib (new CalibrationRequest("/dev/ttyUSB0", "AF6325"));
		calib->run();
		//calib->get_calibration();
		#endif


		#if TEST_ENERGY_MONITOR
		cout << "Starting Test: [Energy Monitor Test]" << std::endl; //AF6325 //D328D9
		EnergyConsumptionMonitor *energy_monitor(new EnergyConsumptionMonitor("/dev/ttyUSB0", "AF6325", 30, 1, 3));
		energy_monitor->run();
		#endif

		#if TEST_INFO_RQUEST
		cout << "Starting Test: [Device Info Test]" << std::endl;
		DeviceInformationRequest *device_info(new DeviceInformationRequest("/dev/ttyUSB0", "AF6325"));
		device_info->run();
		#endif


		#if TEST_HOUR_ENERGY_MONITOR
		cout << "Starting Test: [Hour Energy Monitor Test]" << std::endl;
		HourEnergyConsumptionMonitor *energy_monitor(new HourEnergyConsumptionMonitor("/dev/ttyUSB0", "AF6325", 10, 3, 3));
		energy_monitor->run();

		#endif



}
