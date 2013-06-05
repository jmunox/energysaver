/*
 * PlugwiseDevice.h
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
 *	@version beta.1.0: 7/05/2012
 */

#ifndef PLUGWISEDEVICE_H_
#define PLUGWISEDEVICE_H_

#include "PlugwiseDataPacket.h"
#include "PlugwiseRequest.h"
#include "NumberConverter.h"

#include "../core/TBObject.h"
#include "../core/StreamTask.h"
#include "../core/DataPacket.h"
#include "../core/Thread.h"
//#include "Vector.h"

using namespace std;

class PlugwiseVersion {

public:

	PlugwiseVersion(char major, char minor, char release, std::string version_type, std::string date)
	{
		_my_major = major;
		_my_minor = minor;
		_my_release = release;
		_my_version_type = version_type;
		_my_date = date;
	}


	std::string get_firmware_version(){

		std::ostringstream firmware_version;
		firmware_version << (_my_major);
		firmware_version << (".");
		firmware_version << (_my_minor);
		firmware_version << (".");
		firmware_version << (_my_release);
		firmware_version << (".");
		firmware_version << (_my_version_type);
		firmware_version << ("-");
		firmware_version << (_my_date);

		return firmware_version.str();
	}

private:
	char _my_major;
	char _my_minor;
	char _my_release;
	std::string _my_version_type;
	std::string _my_date;
};


class PlugwiseDevice : public TBObject {

	//GIBN_MAKE_AVAILABLE_ABSTRACT(PlugwiseDevice, TBObject)

public:
	PlugwiseDevice();
	PlugwiseDevice(const std::string& device_address);
	virtual ~PlugwiseDevice();
	std::string get_firmware_version() { return _my_firmware->get_firmware_version();}
	void set_device_address(std::string device_address){_device_address = device_address;}
	std::string get_device_address(){return _device_address;}
	//std::string get_complete_network_address(){;}
	bool is_initialized(){return _initialized;}


	// Setup constants
	const static unsigned int DEFAULT_SLEEP_TIME 						= 0; // 0 seconds
	const static unsigned int DEFAULT_REPEAT_TIMES 						= 1; //	Just run it once
	const static unsigned int DEFAULT_RETRY_LIMIT 						= 3;

	//const static unsigned int POWER_PULSE_WATT_FACTOR = 2.1324759f;		// Pulse count is multiplied to this constant to get information in Watt
	const static unsigned int POWER_PULSE_WATT_FACTOR = 468.9385193;
	//Checksum CRC CCITT 16 bits polynomial: 0x1021 = x^16 + x^12 + x^5 + 1
	const static unsigned int CRC_POLYNOMIAL = 0x1021;

	const static unsigned char* const MAC_ADDRESS_PREFFIX;			// "000D6F0000";

protected:

	//Name of serial device
	//GIBN_REQUIRED std::string _device_address;
	//GIBN_OPTIONAL int _sleep_time GIBN_DEFAULT(DEFAULT_SLEEP_TIME);
	//GIBN_OPTIONAL int _repeat_times GIBN_DEFAULT(DEFAULT_REPEAT_TIMES);
	//GIBN_OPTIONAL int _retry_limit GIBN_DEFAULT(DEFAULT_RETRY_LIMIT);
	std::string _device_address;
	int _sleep_time; //= DEFAULT_SLEEP_TIME; //= 0;
	int _repeat_times; //= DEFAULT_REPEAT_TIMES; //= 0;
	int _retry_limit; //= DEFAULT_RETRY_LIMIT; //= 3;

	NumberConverter *numbconv;
	bool _initialized;


private:
	PlugwiseVersion *_my_firmware;

};

#endif /* PLUGWISEDEVICE_H_ */
