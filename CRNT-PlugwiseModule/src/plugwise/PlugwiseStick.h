/*
 * PlugwiseStick.h
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

#ifndef PLUGWISESTICK_H_
#define PLUGWISESTICK_H_

#include <iostream>
#include <sstream>
#include <termios.h>
//
#include <stropts.h>
#include <sys/ioctl.h>
#include <sys/types.h>
#include <sys/stat.h>

#include "PlugwiseDevice.h"
#include "PlugwiseDataPacket.h"
#include "PlugwiseRequest.h"

using namespace std;


class PlugwiseIOError
{
	public:
		PlugwiseIOError( int ret, int err ) { retVal = ret; errNo = err; }

		int retVal;
		int errNo;
};

class PlugwiseStick : public PlugwiseDevice {
	//GIBN_MAKE_AVAILABLE_ABSTRACT(PlugwiseStick, PlugwiseDevice)

public:

	PlugwiseStick();
	PlugwiseStick(const std::string& serial_port);
	virtual ~PlugwiseStick();

	bool is_connected(){return (_tty_fd>-1);}
	bool open_conn();						// Open the connection to serial port
	void close_conn();						// close the connection
	bool initialize_plugwise_stick();
	void set_read_timeout(int time_out){ _read_timeout = time_out;};

	PlugwiseDataPacket& send_message(PlugwiseRequest *request);					// Send the packets to the Circles


	// Setup constants
	const static unsigned int BIT_RATE 							= B115200; // B115200 //B9600
	const static unsigned int DEFAULT_READ_TIMEOUT 						= 1; // 1 second
	const static unsigned int DEFAULT_INPUT_BUFFER_SIZE 			= 4*1024;
	const static unsigned int MAX_RESPONSE_BUFFER_SIZE 				= 128*1024;
	const static unsigned int DEFAULT_IN_OUT_BUFFER_SIZE 			= 2*1024;

	const static unsigned int POWER_PULSE_WATT_FACTOR = 2.1324759f;		// Pulse count is multiplied to this constant to get information in Watt


protected:

	//Name of serial device
	//GIBN_REQUIRED std::string _serial_port_name;
	std::string _serial_port_name;

	//ID of the serial device
	int _tty_fd;

	int _read_timeout;

	unsigned char input_buffer[MAX_RESPONSE_BUFFER_SIZE];

	//Listen to the port. Implement in case of asynchronous connections
	//virtual void listen();				// Listen to incoming packets
	PlugwiseDataPacket& read_response();	// Read the incoming packets

};


#endif /* PLUGWISESTICK_H_ */
