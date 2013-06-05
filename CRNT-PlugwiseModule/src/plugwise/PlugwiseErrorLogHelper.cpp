/*
 * PlugwiseErrorLogHelper.cpp
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
 *	@version beta.1.9: 13/11/2012
 */

#include "PlugwiseErrorLogHelper.h"
#include "UnsignedCharValue.h"
#include "../core/IntValue.h"

using namespace std;

PlugwiseErrorLogHelper::PlugwiseErrorLogHelper(){


}


PlugwiseErrorLogHelper::~PlugwiseErrorLogHelper() {

}

DataPacket & PlugwiseErrorLogHelper::log_plugwise_error(std::string device_address, int error_id){

	//create data packet
	DataPacket *data_packet(new DataPacket());

	//assemble data packet
	data_packet->dataVector.push_back(new UnsignedCharValue(device_address));
	data_packet->dataVector.push_back(new IntValue(error_id));

	return *data_packet;


}
