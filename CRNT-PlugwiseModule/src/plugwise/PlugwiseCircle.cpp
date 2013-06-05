/*
 * PlugwiseCircle.cpp
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
 *	@version beta.2.1: 26/11/2012
 */
#define DEBUG 1
#define CONSOLE 1

#include "PlugwiseCircle.h"
#include "UnsignedCharValue.h"
#include "../core/FloatValue.h"

using namespace std;

PlugwiseCircle::PlugwiseCircle() : PlugwiseDevice() {
	_device_gain_A = 1.0f;
	_device_gain_B = 0.0f;
	_device_offset_noise = 0.0f;
	_device_offset_total = 0.0f;

}

PlugwiseCircle::PlugwiseCircle(const std::string &device_address) : PlugwiseDevice(device_address){
	_device_gain_A = 1.0f;
	_device_gain_B = 0.0f;
	_device_offset_noise = 0.0f;
	_device_offset_total = 0.0f;
}

PlugwiseCircle::~PlugwiseCircle() {
	// TODO Auto-generated destructor stub
}

DataPacket & PlugwiseCircle::switch_on()
{
	//create data packet
	DataPacket *data_packet(new DataPacket());

	std::ostringstream request_str;
	request_str << PlugwiseRequest::POWER_CHANGE_REQUEST;
	request_str << MAC_ADDRESS_PREFFIX;
	request_str << _device_address;
	request_str << "01";
	std::string message_string = request_str.str();
	PlugwiseRequest *request(new PlugwiseRequest(message_string));
	PlugwiseDataPacket *response;
	response = NULL;
	response = &_stick->send_message(request);

	return *data_packet;

}



DataPacket & PlugwiseCircle::switch_off()
{
	//create data packet
	DataPacket *data_packet(new DataPacket());

	std::ostringstream request_str;
	request_str << PlugwiseRequest::POWER_CHANGE_REQUEST;
	request_str << MAC_ADDRESS_PREFFIX;
	request_str << _device_address;
	request_str << "00";
	std::string message_string = request_str.str();
	PlugwiseRequest *request(new PlugwiseRequest(message_string));
	PlugwiseDataPacket *response;
	response = NULL;
	response = &_stick->send_message(request);

	return *data_packet;
}



DataPacket & PlugwiseCircle::get_hour_energy_consumption()
{
	//create data packet
	DataPacket *data_packet(new DataPacket());

	std::string log_string = "";

    float power = 0.0f;				// assume no power consumed
    std::string my_time;
    int pulses = 0;
    int minutes = 0;

	get_device_information();
	usleep(1000*1000*5);

	std::ostringstream request_str;
	request_str << PlugwiseRequest::POWER_BUFFER_REQUEST;
	request_str << MAC_ADDRESS_PREFFIX;
	request_str << _device_address;
	request_str << _log_address_string;
	std::string message_string = request_str.str();
	PlugwiseRequest *request(new PlugwiseRequest(message_string));
	PlugwiseDataPacket *response;
	response = NULL;
	response = &_stick->send_message(request);

	if(!response->is_succesful())
		throw EMPTY_RESPONSE_ERROR;

	std::ostringstream stream_response;
	stream_response << response->get_data(0);
	std::string response_str = stream_response.str();
#if DEBUG || CONSOLE
			log_string ="[" +_device_address + "] Response:[" + response_str + "][/" +_device_address + "]";
			log(log_string.c_str())<<std::endl;
#endif
	int location_first_header; //pos 0 for firmware 2011
	int location_second_header; //pos 23 for firmware 2011

	std::string ack;
	std::string protocol_response;

	location_first_header = response_str.find((char*)PlugwiseRequest::HEADER);

	while((response_str.substr(location_first_header+12, 4) != (char*)PlugwiseRequest::ACKNOWLEDGE)&&location_first_header>=0){
			location_first_header = response_str.find((char*)PlugwiseRequest::HEADER,(location_first_header+4));
		}

	ack = response_str.substr(location_first_header+12, 4);

	if(ack == (char*)PlugwiseRequest::ACKNOWLEDGE){
		 location_second_header = response_str.find((char*)PlugwiseRequest::HEADER,(location_first_header+4));
			//response code is originally in the 27th position

		 while((response_str.substr(location_second_header+4, 4) != (char*)PlugwiseRequest::POWER_BUFFER_RESPONSE)&&location_second_header>=0){
		 		location_second_header = response_str.find((char*)PlugwiseRequest::HEADER,(location_second_header+4));
		 }

		 protocol_response = response_str.substr(location_second_header+4,4);

		if(protocol_response ==(char*)PlugwiseRequest::POWER_BUFFER_RESPONSE){

			minutes = numbconv->get_int_from_hex_char((response_str.substr((location_second_header+28),4)).c_str());// get minutes from month start
			//pos 55
			pulses =			// get 8-second pulse count
					numbconv->get_int_from_hex_char((response_str.substr((location_second_header+32),4)).c_str());

		}
		else{
#if DEBUG
			log_string ="[" +_device_address + "] Hour Power Response Wrong";
			log(log_string.c_str())<<std::endl;
#endif
			throw WRONG_RESPONSE_ERROR;
		}
	}else{

		//FIXME It is possible that it responds 00C2 instead of the regular 00C1 as ACK response.
		while((response_str.substr(location_first_header+12, 4) != (char*)PlugwiseRequest::OTHER_ACKNOWLEDGE)&&location_first_header>=0){
				location_first_header = response_str.find((char*)PlugwiseRequest::HEADER,(location_first_header+4));
			}

		ack = response_str.substr(location_first_header+12, 4);

		if(ack == (char*)PlugwiseRequest::OTHER_ACKNOWLEDGE){
			 location_second_header = response_str.find((char*)PlugwiseRequest::HEADER,(location_first_header+4));
				//response code is originally in the 27th position

			 while((response_str.substr(location_second_header+4, 4) != (char*)PlugwiseRequest::POWER_BUFFER_RESPONSE)&&location_second_header>=0){
			 		location_second_header = response_str.find((char*)PlugwiseRequest::HEADER,(location_second_header+4));
			 }

			 protocol_response = response_str.substr(location_second_header+4,4);

			if(protocol_response ==(char*)PlugwiseRequest::POWER_BUFFER_RESPONSE){

				minutes = numbconv->get_int_from_hex_char((response_str.substr((location_second_header+28),4)).c_str());// get minutes from month start
				//pos 55
				pulses =			// get 8-second pulse count
						numbconv->get_int_from_hex_char((response_str.substr((location_second_header+32),4)).c_str());

			}
			else{
	#if DEBUG
				log_string ="[" +_device_address + "] Hour Power Response Wrong";
				log(log_string.c_str())<<std::endl;
	#endif
				throw WRONG_RESPONSE_ERROR;
			}
	}else {
		location_first_header = response_str.find((char*)PlugwiseRequest::HEADER);

		while((response_str.substr(location_first_header+12-4, 4) != (char*)PlugwiseRequest::ACKNOWLEDGE)&&location_first_header>=0){
				location_first_header = response_str.find((char*)PlugwiseRequest::HEADER,(location_first_header+4));
			}

		ack = response_str.substr(location_first_header+12-4, 4);

		if(ack == (char*)PlugwiseRequest::ACKNOWLEDGE){
			 location_second_header = response_str.find((char*)PlugwiseRequest::HEADER,(location_first_header+4));
				//response code is originally in the 27th position

			 while((response_str.substr(location_second_header+4, 4) != (char*)PlugwiseRequest::POWER_BUFFER_RESPONSE)&&location_second_header>=0){
			 		location_second_header = response_str.find((char*)PlugwiseRequest::HEADER,(location_second_header+4));
			 }

			 protocol_response = response_str.substr(location_second_header+4,4);

			if(protocol_response ==(char*)PlugwiseRequest::POWER_BUFFER_RESPONSE){
			  minutes = numbconv->get_int_from_hex_char((response_str.substr((location_second_header+28-4),4)).c_str());// get minutes from month start
			//pos 55
			 pulses =			// get 8-second pulse count
					numbconv->get_int_from_hex_char((response_str.substr((location_second_header+32-4),4)).c_str());
		}
		else{
#if DEBUG
			log_string ="[" +_device_address + "] Hour Power Response Wrong";
			log(log_string.c_str())<<std::endl;
#endif
			throw WRONG_RESPONSE_ERROR;
		}
	}else{
#if DEBUG
		log_string ="[" +_device_address + "] ACK Response Wrong";
		log(log_string.c_str())<<std::endl;
#endif
		throw ACK_RESPONSE_ERROR;
		}
	}
}



#if DEBUG
		log_string ="[" +_device_address + "] Response Correct, Now get Hour Energy Consumption and Calibration Info";
		log(log_string.c_str())<<std::endl;
#endif
			usleep(1000*1000*5);
			get_calibration_information();

#if DEBUG || CONSOLE
		  	stringstream ss1;
		  	ss1 << minutes;
			log_string ="[" +_device_address + "] minutes:" + ss1.str();
			log(log_string.c_str())<<std::endl;
#endif
			  my_time = numbconv->get_time(minutes);	// get time from minutes

			int period = 3600;			// non-zero period?
			log("pulses:")<< pulses;

		      if (pulses != 0 &&			// meaningful pulse count?
			  pulses != 65535 && pulses != -1) {
			float pulsesAverage =			// get pulses per second
			  ((float) pulses) / period;
			float pulsesOffsetNoise =		// correct pulses for noise
			  pulsesAverage + _device_offset_noise;

			float pulsesCorrected = ((pulsesOffsetNoise * pulsesOffsetNoise) * _device_gain_B) + (pulsesOffsetNoise * _device_gain_A) + _device_offset_total;
			power =  (pulsesCorrected/(POWER_PULSE_WATT_FACTOR))*1000; // get power in watts
		      }
#if DEBUG || CONSOLE
		      stringstream ss2;
		      ss2 << power;
		      log_string ="[" +_device_address + "] Hour Power Consumption =" + ss2.str();
		      log(log_string.c_str())<<std::endl;
#endif


  	//assemble data packet
	data_packet->dataVector.push_back(new UnsignedCharValue(_device_address));
	data_packet->dataVector.push_back(new UnsignedCharValue(my_time));
	data_packet->dataVector.push_back(new FloatValue(power));

	return *data_packet;
}



DataPacket & PlugwiseCircle::get_calibration_information()
{
	//create data packet
	DataPacket *data_packet(new DataPacket());
	std::string log_string = "";

	if(!is_initialized()) {

		std::ostringstream request_str;
		request_str << PlugwiseRequest::DEVICE_CALIBRATION_REQUEST;
		request_str << MAC_ADDRESS_PREFFIX;
		request_str << _device_address;
		//const unsigned char* req_char = reinterpret_cast<const unsigned char*>(request_str.str().c_str());
		//PlugwiseRequest *request= new PlugwiseRequest(req_char);
		std::string message_string = request_str.str();
		PlugwiseRequest *request(new PlugwiseRequest(message_string));
		PlugwiseDataPacket *response;
		response = NULL;
		response = &_stick->send_message(request);

		if(!response->is_succesful())
			throw EMPTY_RESPONSE_ERROR;

		std::ostringstream stream_response;
		stream_response << response->get_data(0);
		std::string response_str = stream_response.str();
#if DEBUG || CONSOLE
			log_string ="[" +_device_address + "] Response:[" + response_str + "][/" +_device_address + "]";
			log(log_string.c_str())<<std::endl;
#endif
		int location_first_header; //pos 0 for firmware 2011
		int location_second_header; //pos 23 for firmware 2011

		std::string ack;
		std::string protocol_response;

		location_first_header = response_str.find((char*)PlugwiseRequest::HEADER);

		while((response_str.substr(location_first_header+12, 4) != (char*)PlugwiseRequest::ACKNOWLEDGE)&&location_first_header>=0){
				location_first_header = response_str.find((char*)PlugwiseRequest::HEADER,(location_first_header+4));
			}

		ack = response_str.substr(location_first_header+12, 4);

		if(ack == (char*)PlugwiseRequest::ACKNOWLEDGE){
			 location_second_header = response_str.find((char*)PlugwiseRequest::HEADER,(location_first_header+4));
			//response code is originally in the 27th position
			//pos 27

			 while((response_str.substr(location_second_header+4, 4) != (char*)PlugwiseRequest::DEVICE_CALIBRATION_RESPONSE)&&location_second_header>=0){
				 location_second_header = response_str.find((char*)PlugwiseRequest::HEADER,(location_second_header+4));
				}

			protocol_response = response_str.substr(location_second_header+4,4);
			if(protocol_response ==(char*)PlugwiseRequest::DEVICE_CALIBRATION_RESPONSE){
			//if((response_str.find((char*)PlugwiseRequest::DEVICE_CALIBRATION_RESPONSE,1))==(location_second_header+4)){

				//pos 51
				_device_gain_A = numbconv->get_float_from_hex_char((response_str.substr((location_second_header+28),8)).c_str());
				//pos 59
				_device_gain_B = numbconv->get_float_from_hex_char((response_str.substr((location_second_header+36),8)).c_str());
				//pos 67
				_device_offset_total = numbconv->get_float_from_hex_char((response_str.substr((location_second_header+44),8)).c_str());
				// pos 75
				_device_offset_noise = numbconv->get_float_from_hex_char((response_str.substr((location_second_header+52),8)).c_str());

			}
			else{
#if DEBUG
				log_string ="[" +_device_address + "] Calibration Response Wrong";
				log(log_string.c_str())<<std::endl;
#endif
				_initialized = false;
				throw WRONG_RESPONSE_ERROR;
			}
		}
		//old firmware, 
		else {


			// ACKNOWLEDGE in the second line, there are more headers
			while((response_str.substr(location_first_header+12-4, 4) != (char*)PlugwiseRequest::ACKNOWLEDGE)&&location_first_header>=0){
					location_first_header = response_str.find((char*)PlugwiseRequest::HEADER,(location_first_header+4));
				}

			ack = response_str.substr(location_first_header+12-4, 4);
			if (ack == (char*)PlugwiseRequest::ACKNOWLEDGE){

			location_second_header = response_str.find((char*)PlugwiseRequest::HEADER,(location_first_header+4));
					 			//response code is originally in the 27th position

			while((response_str.substr(location_second_header+4, 4) != (char*)PlugwiseRequest::DEVICE_CALIBRATION_RESPONSE)&&location_second_header>=0){
							 location_second_header = response_str.find((char*)PlugwiseRequest::HEADER,(location_second_header+4));
							}

			protocol_response = response_str.substr(location_second_header+4,4);

			if(protocol_response ==(char*)PlugwiseRequest::DEVICE_CALIBRATION_RESPONSE){
			//if((response_str.find((char*)PlugwiseRequest::DEVICE_CALIBRATION_RESPONSE,1))==(location_second_header+4)){

				//pos 51
				_device_gain_A = numbconv->get_float_from_hex_char((response_str.substr((location_second_header+28-4),8)).c_str());
				//pos 59
				_device_gain_B = numbconv->get_float_from_hex_char((response_str.substr((location_second_header+36-4),8)).c_str());
				//pos 67
				_device_offset_total = numbconv->get_float_from_hex_char((response_str.substr((location_second_header+44-4),8)).c_str());
				// pos 75
				_device_offset_noise = numbconv->get_float_from_hex_char((response_str.substr((location_second_header+52-4),8)).c_str());

			}
			else{
#if DEBUG
				log_string ="[" +_device_address + "] Calibration Response Wrong";
				log(log_string.c_str())<<std::endl;
#endif
				_initialized = false;
				throw WRONG_RESPONSE_ERROR;
			}
		} else{
	#if DEBUG
			log_string ="[" +_device_address + "] ACK Response Wrong";
			log(log_string.c_str())<<std::endl;
	#endif
			_initialized = false;
			throw ACK_RESPONSE_ERROR;
			}

	}

	}

#if DEBUG
			log_string ="[" +_device_address + "] Response Correct, Now get Calibration info";
			log(log_string.c_str())<<std::endl;
#endif

	_initialized = true;

#if DEBUG || CONSOLE
  	stringstream ss1,ss2,ss3,ss4;
  	ss1 << _device_gain_A;
  	ss2 << _device_gain_B;
  	ss3 << _device_offset_total;
  	ss4 << _device_offset_noise;
	log_string ="[" +_device_address + "] _device_gain_A:" + ss1.str();
	log(log_string.c_str())<<std::endl;
	log_string ="[" +_device_address + "] _device_gain_B:" + ss2.str();
	log(log_string.c_str())<<std::endl;
	log_string ="[" +_device_address + "] _device_offset_total:" + ss3.str();
	log(log_string.c_str())<<std::endl;
	log_string ="[" +_device_address + "] _device_offset_noise:" + ss4.str();
	log(log_string.c_str())<<std::endl;
#endif

	data_packet->dataVector.push_back(new UnsignedCharValue(_device_address));
	data_packet->dataVector.push_back(new FloatValue(_device_gain_A));
	data_packet->dataVector.push_back(new FloatValue(_device_gain_B));
	data_packet->dataVector.push_back(new FloatValue(_device_offset_total));
	data_packet->dataVector.push_back(new FloatValue(_device_offset_noise));

	return *data_packet;

}



DataPacket & PlugwiseCircle::get_instant_energy_consumption()
{
	std::string log_string = "";
	//create data packet
	DataPacket *data_packet(new DataPacket());
    float power = 0.0f;				// assume no power consumed
    int pulses = 0;

	std::ostringstream request_str;
	request_str << PlugwiseRequest::POWER_INFORMATION_REQUEST;
	request_str << MAC_ADDRESS_PREFFIX;
	request_str << _device_address;
	//const unsigned char* req_char = reinterpret_cast<const unsigned char*>(request_str.str().c_str());
	//PlugwiseRequest *request= new PlugwiseRequest(req_char);
	std::string message_string = request_str.str();
	PlugwiseRequest *request(new PlugwiseRequest(message_string));
	PlugwiseDataPacket *response;
	response = NULL;
	response = &_stick->send_message(request);

	if(!response->is_succesful())
		throw EMPTY_RESPONSE_ERROR;

	std::ostringstream stream_response;
	stream_response << response->get_data(0);
	std::string response_str = stream_response.str();
#if DEBUG || CONSOLE
			log_string ="[" +_device_address + "] Response:[" + response_str + "][/" +_device_address + "]";
			log(log_string.c_str())<<std::endl;
#endif
	int location_first_header; //pos 0 for firmware 2011
	int location_second_header; //pos 23 for firmware 2011

	std::string ack;
	std::string protocol_response;
	//std::cout <<  "Response:" << response_str << std::endl;

	location_first_header = response_str.find((char*)PlugwiseRequest::HEADER);

	// ACKNOWLEDGE in the second line, there are more headers
	while((response_str.substr(location_first_header+12, 4) != (char*)PlugwiseRequest::ACKNOWLEDGE)&&location_first_header>=0){
			location_first_header = response_str.find((char*)PlugwiseRequest::HEADER,(location_first_header+4));
		}

	ack = response_str.substr(location_first_header+12, 4);
	//CASE 1
	//  0000000800C15691
	// �00130008000D6F0000AF6325002300E6000030E4FFFFFFEE0005C515
	// 00C1 in position 12
	// 0013 in position 27 (or second header + 4)
	if(ack == (char*)PlugwiseRequest::ACKNOWLEDGE){
		 location_second_header = response_str.find((char*)PlugwiseRequest::HEADER,(location_first_header+4));
			//response code is originally in the 27th position

		 while((response_str.substr(location_second_header+4, 4) != (char*)PlugwiseRequest::POWER_INFORMATION_RESPONSE)&&location_second_header>=0){
			 location_second_header = response_str.find((char*)PlugwiseRequest::HEADER,(location_second_header+4));
			}

		 protocol_response = response_str.substr(location_second_header+4,4);
		 if(protocol_response ==(char*)PlugwiseRequest::POWER_INFORMATION_RESPONSE){
		//if((response_str.find((char*)PlugwiseRequest::POWER_INFORMATION_RESPONSE,1))==(location_second_header+4)){

			//pos 51
		//	int pulses =			// get 8-second pulse count
		//			numbconv->get_int_from_hex_char((response_str.substr((location_second_header+28),4)).c_str());
				 pulses =			// get 8-second pulse count
								numbconv->get_int_from_hex_char((response_str.substr((location_second_header+28),4)).c_str());

		}
		else{
#if DEBUG
			log_string ="[" +_device_address + "] Instant Power Response Wrong";
			log(log_string.c_str())<<std::endl;
#endif
			throw WRONG_RESPONSE_ERROR;
		} //older firmware-- no datapacket identifier, so everything shrinks 4 chars
	} else {

		// ACKNOWLEDGE in the second line, there are more headers
		while((response_str.substr(location_first_header+12-4, 4) != (char*)PlugwiseRequest::ACKNOWLEDGE)&&location_first_header>=0){
				location_first_header = response_str.find((char*)PlugwiseRequest::HEADER,(location_first_header+4));
			}

		ack = response_str.substr(location_first_header+12-4, 4);
		if (ack == (char*)PlugwiseRequest::ACKNOWLEDGE){

		 location_second_header = response_str.find((char*)PlugwiseRequest::HEADER,(location_first_header+4));
		 			//response code is originally in the 27th position

		 while((response_str.substr(location_second_header+4, 4) != (char*)PlugwiseRequest::POWER_INFORMATION_RESPONSE)&&location_second_header>=0){
			 location_second_header = response_str.find((char*)PlugwiseRequest::HEADER,(location_second_header+4));
			}

		 protocol_response = response_str.substr(location_second_header+4,4);

		 if(protocol_response ==(char*)PlugwiseRequest::POWER_INFORMATION_RESPONSE){
		//if((response_str.find((char*)PlugwiseRequest::POWER_INFORMATION_RESPONSE,1))==(location_second_header+4)){
			//pos 51
		//	int pulses =			// get 8-second pulse count
		//			numbconv->get_int_from_hex_char((response_str.substr((location_second_header+28),4)).c_str());
				 pulses =			// get 8-second pulse count
								numbconv->get_int_from_hex_char((response_str.substr((location_second_header+28-4),4)).c_str());
		}
		else{
#if DEBUG
			log_string ="[" +_device_address + "] Instant Power Response Wrong";
			log(log_string.c_str())<<std::endl;
#endif
			throw WRONG_RESPONSE_ERROR;
		}
	} else{
#if DEBUG
			log_string ="[" +_device_address +"] ACK Response Wrong";
			log(log_string.c_str())<<std::endl;
#endif
		throw ACK_RESPONSE_ERROR;
		}

	}

#if DEBUG
	log_string ="[" +_device_address + "] Response Correct, Now get Instant Power and Calibration Info";
	log(log_string.c_str())<<std::endl;
#endif
	if(!is_initialized()) {
		usleep(1000*1000*3);
		get_calibration_information();
	}

	int period = 1;			// non-zero period?
      if (pulses != 0 &&			// meaningful pulse count?
	  pulses != 65535 && pulses != -1) {
	float pulsesAverage =			// get pulses per second
	  ((float) pulses) / period;
	float pulsesOffsetNoise =		// correct pulses for noise
	  pulsesAverage + _device_offset_noise;

	// correct pulses for gains
	float pulsesCorrected = ((pulsesOffsetNoise * pulsesOffsetNoise) * _device_gain_B) + (pulsesOffsetNoise * _device_gain_A) + _device_offset_total;
			 power =  (pulsesCorrected/(POWER_PULSE_WATT_FACTOR))*1000; // get power in watts
			//power = POWER_PULSE_WATT_FACTOR * pulsesCorrected; // get power in watts

	//power = pulses;
      }


#if DEBUG || CONSOLE
  	stringstream ss;
  	ss << power;
      log_string ="[" +_device_address + "] EnergyConsumption: Power =" + ss.str();
      log(log_string.c_str())<<std::endl;
#endif

  	//assemble data packet
	data_packet->dataVector.push_back(new UnsignedCharValue(_device_address));
	//data_packet->dataVector.push_back(new UnsignedCharValue(_device_address));
	data_packet->dataVector.push_back(new FloatValue(power));

	return *data_packet;
}



DataPacket & PlugwiseCircle::get_device_information()
{
	std::string log_string = "";
	DataPacket *data_packet(new DataPacket());

	std::ostringstream request_str;
	request_str << PlugwiseRequest::DEVICE_INFORMATION_REQUEST;
	request_str << MAC_ADDRESS_PREFFIX;
	request_str << _device_address;
	//const unsigned char* req_char = reinterpret_cast<const unsigned char*>(request_str.str().c_str());
	//PlugwiseRequest *request= new PlugwiseRequest(req_char);
	std::string message_string = request_str.str();
	PlugwiseRequest *request(new PlugwiseRequest(message_string));
	PlugwiseDataPacket *response;
	response = NULL;
	response = &_stick->send_message(request);

	if(!response->is_succesful())
		throw EMPTY_RESPONSE_ERROR;

	std::ostringstream stream_response;
	stream_response << response->get_data(0);
	std::string response_str = stream_response.str();
#if DEBUG || CONSOLE
			log_string ="[" +_device_address + "] Response:[" + response_str + "][/" +_device_address + "]";
			log(log_string.c_str())<<std::endl;
#endif
	int location_first_header; //pos 0 for firmware 2011
	int location_second_header; //pos 23 for firmware 2011

	std::string ack;
	std::string protocol_response;

	location_first_header = response_str.find((char*)PlugwiseRequest::HEADER);

	// ACKNOWLEDGE in the second line, there are more headers
	while((response_str.substr(location_first_header+12, 4) != (char*)PlugwiseRequest::ACKNOWLEDGE)&&location_first_header>=0){
		location_first_header = response_str.find((char*)PlugwiseRequest::HEADER,(location_first_header+4));
	}
	ack = response_str.substr(location_first_header+12, 4);

	if(ack == (char*)PlugwiseRequest::ACKNOWLEDGE){
		 location_second_header = response_str.find((char*)PlugwiseRequest::HEADER,(location_first_header+4));
			//response code is originally in the 27th position

		 while((response_str.substr(location_second_header+4, 4) != (char*)PlugwiseRequest::DEVICE_INFORMATION_RESPONSE)&&location_second_header>=0){
			 location_second_header = response_str.find((char*)PlugwiseRequest::HEADER,(location_second_header+4));
			}

		 protocol_response = response_str.substr(location_second_header+4,4);
		//if((response_str.find((char*)PlugwiseRequest::DEVICE_INFORMATION_RESPONSE,1))==(location_second_header+4)){
		 if(protocol_response ==(char*)PlugwiseRequest::DEVICE_INFORMATION_RESPONSE){
			   //55 position
			std::string log_astr = (response_str.substr(location_second_header+32,8));
			_device_log_address = numbconv->get_int_from_hex_char(log_astr.c_str());

		}
		else{
#if DEBUG
			log_string ="[" +_device_address + "] Device Information Response Wrong";
			log(log_string.c_str())<<std::endl;
#endif
			throw WRONG_RESPONSE_ERROR;
		}
	} else {

		//old firmware without package id
		// ACKNOWLEDGE in the second line, there are more headers
		while((response_str.substr(location_first_header+12-4, 4) != (char*)PlugwiseRequest::ACKNOWLEDGE)&&location_first_header>=0){
			location_first_header = response_str.find((char*)PlugwiseRequest::HEADER,(location_first_header+4));
		}
		ack = response_str.substr(location_first_header+12-4, 4);

		if (ack == (char*)PlugwiseRequest::ACKNOWLEDGE){

		 location_second_header = response_str.find((char*)PlugwiseRequest::HEADER,(location_first_header+4));
		 			//response code is originally in the 27th position

		 while((response_str.substr(location_second_header+4, 4) != (char*)PlugwiseRequest::DEVICE_INFORMATION_RESPONSE)&&location_second_header>=0){
			 location_second_header = response_str.find((char*)PlugwiseRequest::HEADER,(location_second_header+4));
			}

		 protocol_response = response_str.substr(location_second_header+4,4);


		//if((response_str.find((char*)PlugwiseRequest::DEVICE_INFORMATION_RESPONSE,1))==(location_second_header+4)){
		 if(protocol_response ==(char*)PlugwiseRequest::DEVICE_INFORMATION_RESPONSE){
			   //55 position
			std::string log_astr = (response_str.substr(location_second_header+32-4,8));
			_device_log_address = numbconv->get_int_from_hex_char(log_astr.c_str());
		}
		else{
#if DEBUG
			log_string ="[" +_device_address + "] Device Information Response Wrong";
			log(log_string.c_str())<<std::endl;
#endif
			throw WRONG_RESPONSE_ERROR;
		}
	}else{
#if DEBUG
			log_string ="[" +_device_address + "] ACK Response Wrong";
			log(log_string.c_str())<<std::endl;
#endif
		throw ACK_RESPONSE_ERROR;
		}
	}

#if DEBUG
		log_string ="[" +_device_address +"] Response Correct, Now get Device info";
		log(log_string.c_str())<<std::endl;
#endif

	_log_address_string = numbconv->get_hex_char_from_int_no_size(_device_log_address);
#if DEBUG || CONSOLE
	log_string ="[" +_device_address + "] Log Address Hex:[" + _log_address_string + "]";
	log(log_string.c_str())<<std::endl;
	stringstream ss;
	ss << _device_log_address;
	log_string ="[" +_device_address + "] Device Log Address:" + ss.str();
	log(log_string.c_str())<<std::endl;
#endif



	data_packet->dataVector.push_back(new UnsignedCharValue(_device_address));
	data_packet->dataVector.push_back(new UnsignedCharValue(_device_log_address));
	data_packet->dataVector.push_back(new UnsignedCharValue(_log_address_string));

	return *data_packet;

}
