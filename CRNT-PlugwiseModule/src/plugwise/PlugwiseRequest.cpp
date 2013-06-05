/*
 * PlugwiseRequest.cpp
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
 *	@version alpha.6.0: 17/02/2012
 */

#include "PlugwiseRequest.h"
#include "PlugwiseDataPacket.h"
#include "CRC16_CCITT.h"

#include <sstream>
#include <string>
#include <string.h>

using namespace std;


const unsigned char* const PlugwiseRequest::HEADER  						=(unsigned char*)"\x05\x05\x03\x03";
const unsigned char* const PlugwiseRequest::FOOTER 							=(unsigned char*)"\x0d\x0a";

const unsigned char* const PlugwiseRequest::JUMP_LINE						=(unsigned char*)"\x83";

const unsigned char* const PlugwiseRequest::ACKNOWLEDGE 					=(unsigned char*)"00C1";
const unsigned char* const PlugwiseRequest::OTHER_ACKNOWLEDGE 					=(unsigned char*)"00C2";
const unsigned char* const PlugwiseRequest::GENERAL_RESPONSE 				=(unsigned char*)"0000";

// Protocol Stick Initial Request
const unsigned char* const PlugwiseRequest::STICK_INITIAL_REQUEST 			=(unsigned char*)"000A";
const unsigned char* const PlugwiseRequest::STICK_INITIAL_RESPONSE 			=(unsigned char*)"0011"; //="\x11";

//Protocol Clock Commands
const unsigned char* const PlugwiseRequest::CLOCK_GET_REQUEST				=(unsigned char*)"003e";
const unsigned char* const PlugwiseRequest::CLOCK_GET_RESPONSE 				=(unsigned char*)"003f";
const unsigned char* const PlugwiseRequest::CLOCK_SET_REQUEST 				=(unsigned char*)"0016";
const unsigned char* const PlugwiseRequest::CLOCK_SET_RESPONSE 				=(unsigned char*)"003f";

//Protocol Circle Device Commands
const unsigned char* const PlugwiseRequest::DEVICE_CALIBRATION_REQUEST		=(unsigned char*)"0026";
const unsigned char* const PlugwiseRequest::DEVICE_CALIBRATION_RESPONSE		=(unsigned char*)"0027";
const unsigned char* const PlugwiseRequest::DEVICE_INFORMATION_REQUEST		=(unsigned char*)"0023";
const unsigned char* const PlugwiseRequest::DEVICE_INFORMATION_RESPONSE		=(unsigned char*)"0024";

//Protocol Power
const unsigned char* const PlugwiseRequest::POWER_BUFFER_REQUEST			=(unsigned char*)"0048";
const unsigned char* const PlugwiseRequest::POWER_BUFFER_RESPONSE			=(unsigned char*)"0049";

// Turn on/off devices
const unsigned char* const PlugwiseRequest::POWER_CHANGE_REQUEST			=(unsigned char*)"0017";	// Turn on/off
const unsigned char* const PlugwiseRequest::POWER_CHANGE_RESPONSE			=(unsigned char*)"0000";

// Power Information
const unsigned char* const PlugwiseRequest::POWER_INFORMATION_REQUEST		=(unsigned char*)"0012";
const unsigned char* const PlugwiseRequest::POWER_INFORMATION_RESPONSE		=(unsigned char*)"0013";


PlugwiseRequest::PlugwiseRequest() : PlugwiseDataPacket() {
}

PlugwiseRequest::PlugwiseRequest(std::string message): PlugwiseDataPacket() {
	//log( "String - Pre Message: ") << message << std::endl;

	_my_message = message;

	//log( "Pre My Message: " )<< _my_message << std::endl;

	CRC16_CCITT *crc_test(new CRC16_CCITT());
	_crc_generator = crc_test;

	//log( "Pos Message: ") << message << std::endl;
	//log( "My Message: ") << _my_message << std::endl;

	std::ostringstream request_str;
	std::string pos_crc(_crc_generator->calculate_CRC(_my_message));

	//log( "Pos CRC: ") << pos_crc << std::endl;

	request_str << HEADER << _my_message << pos_crc << FOOTER;
	_my_data=request_str.str();

	//log("Request Stream: ") << request_str.str() << std::endl;

	//_my_data_packet->dataVector.push_back(new UnsignedCharValue(request_str.str()));
}

/*

PlugwiseRequest::PlugwiseRequest(const unsigned char* message): PlugwiseDataPacket() {

	log("Char - Pre Message: " )<< message << std::endl;
	_my_data_packet = new DataPacket();
	_my_message = message;
	log("Pre My Message: ") << _my_message << std::endl;
	CRC16_CCITT *crc_test(new CRC16_CCITT());
	_crc_generator = crc_test;
	log( "Pos Message: " ) << message << std::endl;
	log( "My Message: " ) << _my_message << std::endl;
	std::ostringstream request_str;
	const unsigned char* pos_crc(_crc_generator->calculate_CRC(_my_message));
	log( "Pos CRC: " ) << pos_crc << std::endl;
	request_str << HEADER << _my_message << pos_crc << FOOTER;

	log( "Request Stream: " ) << request_str.str() << std::endl;
	_my_data_packet->dataVector.push_back(new UnsignedCharValue(request_str.str()));
}
*/


PlugwiseRequest::~PlugwiseRequest(){
	delete _crc_generator;
	_crc_generator = NULL;
	//delete _my_data_packet;
	//_my_data_packet = NULL;
}



