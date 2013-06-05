/*
 * SwitchOnOffPlugwiseDevice.cpp
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

#include "SwitchOnOffPlugwiseDevice.h"
#include "PlugwiseCircle.h"
#include "../core/DataPacket.h"

using namespace std;

SwitchOnOffPlugwiseDevice::SwitchOnOffPlugwiseDevice(): StreamTask(1,1){

}

SwitchOnOffPlugwiseDevice::SwitchOnOffPlugwiseDevice(const std::string& serial_port, const std::string& device_address): _serial_port(serial_port), _device_address(device_address), StreamTask(1,1){

	_switch_on_int = 1;
}

SwitchOnOffPlugwiseDevice::SwitchOnOffPlugwiseDevice(const std::string& serial_port, const std::string& device_address, const bool switch_on):  _serial_port(serial_port), _device_address(device_address), StreamTask(1,1){

	_switch_on_int= 1;
	if(switch_on)
		_switch_on_int= 1;
	else
		_switch_on_int= 0;

}

SwitchOnOffPlugwiseDevice::SwitchOnOffPlugwiseDevice(const std::string& serial_port, const std::string& device_address, const bool switch_on, int sleep_time, int repeat_times) : _serial_port(serial_port), _device_address(device_address), StreamTask(1,1){

	_switch_on_int= 1;
	if(switch_on)
		_switch_on_int= 1;
	else
		_switch_on_int= 0;

}

SwitchOnOffPlugwiseDevice::~SwitchOnOffPlugwiseDevice() {
	_stick->close_conn();
	delete _stick;
}


void SwitchOnOffPlugwiseDevice::run() {

	PlugwiseStick *stick(new PlugwiseStick(_serial_port));
	_stick = stick;
	running = true;
#if CONSOLE
	log("Entering switching.");
#endif

	DataPacket *packet;
	PlugwiseCircle *circle(new PlugwiseCircle(_device_address));
	circle->set_plugwise_stick(_stick);

#if CONSOLE
	log("data packet defined.");
#endif

	InPort *inPort = inPorts[0];
	while (running) {
#if CONSOLE
	log("Did i get inport????????.");
#endif

	packet = inPort->receive();


	if( (packet != NULL) && (packet->dataVector.size() > 0) ) {
		if(packet->dataVector.at(0)->getInt() == 0){
			_switch_on_int= 0;
		}
		else if(packet->dataVector.at(0)->getInt() == 1){
			_switch_on_int= 1;
		}
	}

		if(_switch_on_int==0)
		{

#if CONSOLE
			log("Entering switching off.");
#endif
			circle->switch_off();
		} else if(_switch_on_int==1)
		{
#if CONSOLE
			log("Entering switching on.");
#endif
			circle->switch_on();
		}

	}


}


