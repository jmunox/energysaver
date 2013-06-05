/*
 * PlugwiseDevice.cpp
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
 *	@version beta.1.9: 12/11/2012
 */

#include "PlugwiseDevice.h"
#include <iomanip>

using namespace std;

//initialize variables
const unsigned char* const PlugwiseDevice::MAC_ADDRESS_PREFFIX = (unsigned char*)"000D6F00";


PlugwiseDevice::PlugwiseDevice() {
	PlugwiseVersion  *p_firmware (new PlugwiseVersion('2','5','0', "FINAL", "27-JUN-11"));
	_my_firmware = p_firmware;
	_initialized = false;


}

PlugwiseDevice::PlugwiseDevice(const std::string& device_address) {
	std::ostringstream _device_ost;
	_device_ost  << std::right<< setfill('0')<<setw(8) << std::hex << std::uppercase << device_address;
	_device_address = _device_ost.str();
	PlugwiseVersion  *p_firmware (new PlugwiseVersion('2','5','0', "FINAL", "27-JUN-11"));
	_my_firmware = p_firmware;
	_initialized = false;

}



PlugwiseDevice::~PlugwiseDevice() {
#if CONSOLE
	log("PlugwiseDevice Destructor");
#endif
	delete _my_firmware;
	delete numbconv;
}




