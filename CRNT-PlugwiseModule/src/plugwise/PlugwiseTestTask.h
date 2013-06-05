/*
 * PlugwiseTestTask.h
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
 *	@version beta.1.2: 17/05/2012
 */

#ifndef PLUGWISETESTTASK_H_
#define PLUGWISETESTTASK_H_

#include "../core/StreamTask.h"
#include "../core/DataPacket.h"
#include "../core/Thread.h"
//#include "Vector.h"

using namespace std;

class PlugwiseTestTask : public StreamTask {


	//GIBN_MAKE_AVAILABLE(PlugwiseTestTask, StreamTask)
public:
	PlugwiseTestTask();
	virtual ~PlugwiseTestTask();

		//methods
	virtual void run();							// Thread


	private:
	std::string _serial_port;
	std::string _device_address;
	int _test_case;
	std::string _message;
};

#endif /* PLUGWISETESTTASK_H_ */
