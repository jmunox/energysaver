/*
 * SwitchOnOffPlugwiseDevice.h
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

#ifndef SWITCHONOFFPLUGWISEDEVICE_H_
#define SWITCHONOFFPLUGWISEDEVICE_H_

#include "../core/StreamTask.h"
#include "../core/DataPacket.h"
#include "../core/Thread.h"
//#include "Vector.h"

#include "PlugwiseStick.h"

using namespace std;

class SwitchOnOffPlugwiseDevice: public StreamTask {

	//GIBN_MAKE_AVAILABLE(SwitchOnOffPlugwiseDevice, StreamTask)

public:
	SwitchOnOffPlugwiseDevice();
	SwitchOnOffPlugwiseDevice(const std::string& serial_port, const std::string& device_address);
	SwitchOnOffPlugwiseDevice(const std::string& serial_port, const std::string& device_address, const bool switch_on);
	SwitchOnOffPlugwiseDevice(const std::string& serial_port, const std::string& device_address, const bool switch_on, int sleep_time, int repeat_times);
	virtual ~SwitchOnOffPlugwiseDevice();

	//methods
	virtual void run();							// Thread


private:
	//GIBN_OPTIONAL int _switch_on_int GIBN_DEFAULT(0);
	int _switch_on_int;
	//GIBN_REQUIRED std::string _serial_port;
	std::string _serial_port;
	//GIBN_REQUIRED std::string _device_address;
	std::string _device_address;
	PlugwiseStick *_stick;
};

#endif /* SWITCHONOFFPLUGWISEDEVICE_H_ */
