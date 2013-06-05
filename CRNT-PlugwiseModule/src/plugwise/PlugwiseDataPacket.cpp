/*
 * PlugwiseDataPacket.cpp
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

#include "PlugwiseDataPacket.h"

using namespace std;

PlugwiseDataPacket::PlugwiseDataPacket(){
	//_my_data_packet = new DataPacket();

}

PlugwiseDataPacket::PlugwiseDataPacket(const unsigned char* data){
	_my_data=(char*)data;
	//_my_data_packet = new DataPacket();
	//_my_data_packet->dataVector.push_back( new UnsignedCharValue( data ));
}

PlugwiseDataPacket::PlugwiseDataPacket(std::string data){
	_my_data=data;
	//_my_data_packet = new DataPacket();
	//_my_data_packet->dataVector.push_back( new UnsignedCharValue( data ));
}

PlugwiseDataPacket::~PlugwiseDataPacket() {
	//delete _my_data_packet;
	//_my_data_packet = NULL;
}


void PlugwiseDataPacket::set_data(const unsigned char* data) {
	_my_data=(char*)data;
	//_my_data_packet->dataVector.push_back( new UnsignedCharValue( data ));
}

const unsigned char* PlugwiseDataPacket::get_data(int index) {
/*	UnsignedCharValue *new_val((UnsignedCharValue*)(_my_data_packet->dataVector.at(index)));
	const unsigned char* new_char(new_val->getChar());
	return new_char;*/
	return reinterpret_cast<const unsigned char*>(_my_data.c_str());
}


std::string PlugwiseDataPacket::get_data_string(int index) {
	//UnsignedCharValue *new_val((UnsignedCharValue*)(_my_data_packet->dataVector.at(index)));
	//return new_val->getString();
	return _my_data;
}

