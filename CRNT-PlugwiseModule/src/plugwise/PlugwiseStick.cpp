/*
 * PlugwiseStick.cpp
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
 *	@version beta.2.2: 27/11/2012
 */

#include "PlugwiseStick.h"
#include <iostream>
#include <sstream>

#include <fcntl.h>
#include <termios.h>
#include <errno.h>
#include <cstdlib>
#include <unistd.h>
#include <cstring>
#include <string>

using namespace std;


// CONSOLE switch, runs in Console out debugging mode when flag = 1
#define DEBUG 0
#define CONSOLE 1



PlugwiseStick::PlugwiseStick() : PlugwiseDevice() {
#if DEBUG
	log("PlugwiseStick Constructor.");
#endif
	_tty_fd = -1;
	_read_timeout = DEFAULT_READ_TIMEOUT;
}

PlugwiseStick::PlugwiseStick(const std::string& serial_port) : PlugwiseDevice(), _serial_port_name(serial_port) {
#if DEBUG
	log("PlugwiseStick Constructor with Parameters.");
#endif
	_tty_fd = -1;
	_read_timeout = DEFAULT_READ_TIMEOUT;
}


PlugwiseStick::~PlugwiseStick() {
	close_conn();
}


// Create the connection
bool PlugwiseStick::open_conn() {
	if(_tty_fd==-1) {
		// initialize device
		struct termios tio;
		//clear options struct
		bzero(&tio, sizeof(tio));
		memset(&tio,0,sizeof(tio));

		log("") << "tio.c_iflag=" << tio.c_iflag << ", tio.c_oflag=" << tio.c_oflag << ", tio.c_cflag=" << tio.c_cflag << ", tio.c_lflag=" << tio.c_lflag << ", tio.c_cc[VMIN]=" << tio.c_cc[VMIN] << ", tio.c_cc[VTIME]=" << tio.c_cc[VTIME];

		tio.c_iflag=0;
		tio.c_oflag=0;
		tio.c_cflag=CS8|CREAD|CLOCAL; // 8n1, see termios.h for more information
		tio.c_lflag=0;
		tio.c_cc[VMIN]=1;
		tio.c_cc[VTIME]=5;

		_tty_fd=open(_serial_port_name.c_str(), O_RDWR ); // O_RDWR | O_NOCTTY
		//non blocking code
		int flags;
		flags = fcntl(_tty_fd, F_GETFL);
		flags |= O_NONBLOCK;
		fcntl(_tty_fd, F_SETFL, flags);
		//end of non blocking code
		cfsetospeed(&tio,BIT_RATE); // 9600 baud
		cfsetispeed(&tio,BIT_RATE); // 9600 baud
		tcsetattr(_tty_fd,TCSANOW,&tio);

#if DEBUG
		log("Opening port [" ) << _serial_port_name  << "] : "<< _tty_fd << std::endl;
#endif

		tcflush(_tty_fd,TCIFLUSH);
	}
	return (_tty_fd>-1);
}


// Closes the connection
void PlugwiseStick::close_conn()
{
	close(_tty_fd);
#if DEBUG
			log ("Connection closed [_tty_fd = ") << _tty_fd <<  "]:" << std::endl;
#endif

}


// send the command to the Circle
PlugwiseDataPacket& PlugwiseStick::send_message(PlugwiseRequest *request) {
	std::string log_string = "";
	if(!open_conn()){
		log_string = "Error while trying to connect to [" + _serial_port_name  + "] ";
		log(log_string.c_str())<<std::endl;
			throw PlugwiseIOError( _tty_fd, errno );
	}
	unsigned const char* ch_request = request->get_data(0);

#if DEBUG || CONSOLE
	log_string = "Request: '" + request->get_data_string(0)  + "'";
	log(log_string.c_str())<<std::endl;
#endif

	write(_tty_fd, ch_request , strlen((char*)ch_request));

	return read_response();

}


PlugwiseDataPacket& PlugwiseStick::read_response() {

	std::string log_string = "";

	bool successful;
	int max_buffer_size = 1024;
	unsigned char char_buffer [max_buffer_size/2];// {'\0'};
	unsigned char char_buffer2 [max_buffer_size/2];// {'\0'};
	unsigned char* temp_buffer_loc [max_buffer_size];

	PlugwiseDataPacket *response;
	int received_size_total ,received_size1, received_size2;

#if DEBUG
	log( "Waiting for response...");
#endif
	//wait for reply?
	usleep(_read_timeout*1000*1000); // usleep = microseconds
		try{
			received_size_total, received_size1, received_size2 = 0;
#if DEBUG
			log("READING");
#endif
			fcntl(_tty_fd, F_SETFL, FNDELAY);

			received_size1 = read(_tty_fd,&char_buffer,max_buffer_size-1);
			received_size2 = read(_tty_fd,&char_buffer2,max_buffer_size-1);

#if DEBUG || CONSOLE
		      stringstream ss1;
		      ss1 << received_size1;
		      log_string = "Response size: "+ ss1.str();
		      log(log_string.c_str())<<std::endl;
#endif
		      received_size_total = received_size1+received_size2;
		      if (received_size_total>0) {
				successful = true;

				memcpy(temp_buffer_loc, char_buffer, received_size1);

				//now put the other data at the end of the buffer
				memcpy(temp_buffer_loc + received_size1, char_buffer2, received_size2);

				char_buffer[received_size_total<max_buffer_size?received_size_total:max_buffer_size]='\0';
			} else {
				successful = false;
#if DEBUG || CONSOLE
					if (received_size_total==0)
						log( "[Empty Response]");
#endif

				if (errno == EAGAIN)
				    	{
					log("[ERROR : EAGAIN][No Response from the device]");
					}
					else
					{
						if(errno>0) {
						stringstream ss2;
						ss2 <<"Read error: [" <<  errno <<  "] : " <<  strerror(errno);
						log_string = ss2.str();
						log(log_string.c_str())<<std::endl;
						throw PlugwiseIOError( _tty_fd, errno );
						}
					}
								//close_conn();
			}
		} catch (int e)
		{
			stringstream ss3;
			ss3 <<"An exception occurred. Exception Nr. " << e;
			log_string = ss3.str();
			log(log_string.c_str())<<std::endl;
			throw PlugwiseIOError( _tty_fd, e );
		}

#if DEBUG || CONSOLE
log ("Response: ")<< char_buffer << std::endl;
#endif

response = new PlugwiseDataPacket(char_buffer);
response->set_status(successful);
	return *response;

}

bool PlugwiseStick::initialize_plugwise_stick()
{
	std::string message((char*)PlugwiseRequest::STICK_INITIAL_REQUEST);
	PlugwiseRequest *request(new PlugwiseRequest(message));
//	log("init prep req:")<< request->get_message() << std::endl;
//	log("init prep complete req:")<< request->get_data(0);
	PlugwiseDataPacket *response;
	response = NULL;
	response = &send_message(request);
	//clean_input_stream();
	std::ostringstream stream_response;
	stream_response << (response->get_data(0));
	std::string string_response = stream_response.str();
	if((string_response.find((char*)PlugwiseRequest::ACKNOWLEDGE,1))==12){
		if((string_response.find((char*)PlugwiseRequest::STICK_INITIAL_RESPONSE,1))==26){
			log( "Stick Initialized successfully.");
			return true;
		}
	}
	return false;
}
