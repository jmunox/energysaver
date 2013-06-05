/*
 * Copyright (C) 2007 David Bannach, Embedded Systems Lab
 * 
 * This file is part of the CRN Toolbox.
 * The CRN Toolbox is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 * The CRN Toolbox is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License
 * along with the CRN Toolbox; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * 
 *  @author Jesus Muñoz-Alcantara
 *	@version beta.1.3: 17/05/2012
 */

 
#ifndef HTTPCLIENTWRITER_H
#define HTTPCLIENTWRITER_H

#include "../core/StreamTask.h"
#include "../core/Encoder.h"

using namespace std;

/**
 * \ingroup writer
 * \brief Write data to a server via HTTP.
 *
 * This task acts as a HTTP client. It connects to the
 * specified server. An Encoder is used to serialize
 * DataPackets received on the single in-port. The
 * serialized packets are sent to the connected server
 * via HTTP.
 */
class HTTPClientWriter : public StreamTask
{

	//GIBN_MAKE_AVAILABLE(HTTPClientWriter,StreamTask)

	public:
	HTTPClientWriter( );
	virtual ~HTTPClientWriter();
	void sendMessage(std::string& message);
	void testMessage();


		/**
		 * Remote host where this client should connect to.
		 */
//		GIBN_REQUIRED std::string host;

		/**
		 * The port on the remote host.
		 */
//		GIBN_REQUIRED short port;
		
		/**
		 * Remote host end point where this client should make the request. The relative URL.
		 */
//		GIBN_REQUIRED std::string end_point;

		/**
		 * The Encoder to use for serializing toolbox DataPackets.
		 * (t.g. TimestampedLinesEncoder)
		 */
//		GIBN_REQUIRED_OBJ Encoder *encoder;
		
		/**
		 * Reduced delay on TCP connection if set to true.
		 */
//		GIBN_OPTIONAL bool tcpNoDelay GIBN_DEFAULT( false );

		/**
		 * The time in seconds before retrying a failed connection attempt.
		 * No reconnect if negative.
		 */
//		GIBN_OPTIONAL int retryInterval GIBN_DEFAULT(5);
		
		/**
		 * Size of string buffer. Must be large enough to provide
		 * space for the biggest possible line of output.
		 */
//		GIBN_OPTIONAL unsigned int lineBufferSize GIBN_DEFAULT(10240);


		std::string host;
		short port;
		std::string end_point;
		Encoder *encoder;
		bool tcpNoDelay; //Default false
		int retryInterval; //default 5
		unsigned int lineBufferSize; // default 10240

		unsigned char *buf;


	protected:
		void paramsChanged();

	private:
		void run();
};
	


#endif	//HTTPCLIENTWRITER_H
