/*
 * PlugwiseDataPacket.h
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
 *	@version beta.1.3: 17/05/2012
 */

#ifndef PLUGWISEDATAPACKET_H_
#define PLUGWISEDATAPACKET_H_

#include <iostream>
#include "../core/TBObject.h"
//#include "../core/DataPacket.h"

#include <string.h>

using namespace std;

class PlugwiseDataPacket : public TBObject {

	//GIBN_MAKE_AVAILABLE_ABSTRACT(PlugwiseDataPacket, TBObject)

public:
	PlugwiseDataPacket();
	PlugwiseDataPacket(const unsigned char* data);
	PlugwiseDataPacket(std::string data);
	virtual ~PlugwiseDataPacket();

	virtual const unsigned char* get_data(int index);
	std::string get_data_string(int index);

	virtual void set_data(const unsigned char* data);

	// was the response successful
	bool is_succesful(){return _successful;}

	void set_status(bool successful){ _successful = successful;}

	 //return strlen((char*)_my_data);
	//DataPacket *_my_data_packet;
	std::string _my_data;

protected:

	bool _successful;

};


#endif /* PLUGWISEDATAPACKET_H_ */
