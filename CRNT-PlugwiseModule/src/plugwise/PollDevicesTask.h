/*
 * PollDevicesTask.h
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
 *	@version beta.2.1: 26/11/2012
 */

#ifndef POLLDEVICESTASK_H_
#define POLLDEVICESTASK_H_


#include "../core/StreamTask.h"
#include "../core/DataPacket.h"
#include "../core/Thread.h"
#include <vector>
//#include "Vector.h"

#include "PlugwiseStick.h"
#include "PlugwiseCircle.h"

using namespace std;

class PollDevicesTask : public StreamTask {

	//GIBN_MAKE_AVAILABLE(PollDevicesTask, StreamTask)

public:
	PollDevicesTask();
	PollDevicesTask(const std::string& serial_port);
	PollDevicesTask(const std::string& serial_port,  int sleep_time, int repeat_times);
	virtual ~PollDevicesTask();


	//GIBN_REQUIRED_VEC std::vector<string> device_list;
	std::vector<string> device_list;

	std::vector<PlugwiseCircle*> circles;

	void run();

protected:

	// Setup constants
	const static unsigned int DEFAULT_SLEEP_TIME 						= 1; // 0 seconds
	const static unsigned int DEFAULT_REPEAT_TIMES 						= 1; //	Just run it once
	const static unsigned int DEFAULT_READ_TIMEOUT							= 1; //timeout to poll a device

	//Name of serial device
	//GIBN_REQUIRED std::string _serial_port;
	//GIBN_OPTIONAL int _sleep_time GIBN_DEFAULT(DEFAULT_SLEEP_TIME);
	//GIBN_OPTIONAL int _repeat_times GIBN_DEFAULT(DEFAULT_REPEAT_TIMES);
	//GIBN_OPTIONAL int _read_timeout GIBN_DEFAULT(DEFAULT_READ_TIMEOUT);
	std::string _serial_port;
	int _sleep_time; //= DEFAULT_SLEEP_TIME; //= 0;
	int _repeat_times; //= DEFAULT_REPEAT_TIMES; //= 1;
	int _read_timeout; //= DEFAULT_READ_TIMEOUT;  // =1;

	PlugwiseStick *_stick;


	int stream_data(DataPacket* data_packet);		// stream data to logger task

	int stream_error_data(DataPacket* data_packet);		// stream data from errors to logger task

};


#endif /* POLLDEVICESTASK_H_ */
