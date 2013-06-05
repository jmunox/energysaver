/*
 * PlugwiseRequest.h
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

#ifndef PLUGWISEREQUEST_H_
#define PLUGWISEREQUEST_H_

#include "CRC16_CCITT.h"
#include "PlugwiseDataPacket.h"

using namespace std;


class PlugwiseRequest : public PlugwiseDataPacket {

	//GIBN_MAKE_AVAILABLE_ABSTRACT(PlugwiseRequest, PlugwiseDataPacket)

public:

	PlugwiseRequest();
	PlugwiseRequest(std::string message);
	//PlugwiseRequest(const unsigned char* message);
	virtual ~PlugwiseRequest();

	std::string get_message(){return _my_message;}

	//Checksum CRC CCITT 16 bits polynomial: 0x1021 = x^16 + x^12 + x^5 + 1
	const static unsigned int CRC_POLYNOMIAL = 0x1021;

	//const static unsigned char* const MAC_ADDRESS_PREFFIX;			// "000D6F0000";

	// Plugwise Protocol Constants

	const static unsigned char* const HEADER;							// 	"\x05\x05\x03\x03";

	const static unsigned char* const FOOTER; 							//	"\x0d\x0a";

	const static unsigned char* const JUMP_LINE;							// "\x83";

	const static unsigned char* const ACKNOWLEDGE; 						//	"\xc1";
	const static unsigned char* const OTHER_ACKNOWLEDGE; 				//	"\xc2";
	const static unsigned char* const GENERAL_RESPONSE; 					//	"\x00";

	// Protocol Stick Initial Request
	const static unsigned char* const STICK_INITIAL_REQUEST; 			//	"\x0a";
	const static unsigned char* const STICK_INITIAL_RESPONSE; 			//	"\x11";

	//Protocol Clock Commands
	const static unsigned char* const CLOCK_GET_REQUEST;					//	"\x3e";
	const static unsigned char* const CLOCK_GET_RESPONSE; 				// "\x3f";
	const static unsigned char* const CLOCK_SET_REQUEST;					//	"\x16";
	const static unsigned char* const CLOCK_SET_RESPONSE; 				//	"\x3f";

	//Protocol Circle Device Commands
	const static unsigned char* const DEVICE_CALIBRATION_REQUEST;		// 	"\x26";
	const static unsigned char* const DEVICE_CALIBRATION_RESPONSE;		// 	"\x27";
	const static unsigned char* const DEVICE_INFORMATION_REQUEST;		// 	"\x23";
	const static unsigned char* const DEVICE_INFORMATION_RESPONSE;		// 	"\x24";

	//Protocol Power
	const static unsigned char* const POWER_BUFFER_REQUEST;				//	"\x48";
	const static unsigned char* const POWER_BUFFER_RESPONSE;				// 	"\x49";

	// Turn on/off devices
	const static unsigned char* const POWER_CHANGE_REQUEST;				// 	"\x17";	// Turn on/off
	const static unsigned char* const POWER_CHANGE_RESPONSE;				// 	"\x00";

	// Power Information
	const static unsigned char* const POWER_INFORMATION_REQUEST;			// 	"\x12";
	const static unsigned char* const POWER_INFORMATION_RESPONSE;		//  "\x13";

protected:

	CRC16_CCITT *_crc_generator; //(new CRC16_CCITT());
	std::string _my_message;

};


#endif /* PLUGWISEREQUEST_H_ */
