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
 */

// TCPClientWriter.cpp
// 
#include "HTTPClientWriter.h"

#include <netdb.h>
#include <iostream>
#include <string>
#include <stdlib.h>
#include <assert.h>
#include <sstream>
#include <cstring>
#include <iomanip>

using namespace std;

// CONSOLE switch, runs in Console out debugging mode when flag = 1
#define DEBUG 1
#define CONSOLE 1

HTTPClientWriter::HTTPClientWriter( ) : StreamTask(1,0), buf(NULL)
{

}

HTTPClientWriter::~HTTPClientWriter() {
	//delete encoder;
	if (buf != NULL) {
		delete[] buf;
	}
}

void HTTPClientWriter::paramsChanged() {
	if (buf != NULL) {
		delete[] buf;
	}
	buf = new (unsigned char[lineBufferSize]);
}

void HTTPClientWriter::run() {

	paramsChanged();

	DataPacket *p;
	InPort *inPort = inPorts[0];

	//unsigned char *parameters;
	//initialize encoder
	p = inPort->receive();
	//encoder->init(p);
	int sock;
	sockaddr_in sin;
	while (running) {

		/*
		 //write header
		 int len = encoder->get_header( buf, lineBufferSize );
		 try {
		 socket->send( buf, len );
		 }
		 catch( SocketException &e ) {
		 log( "ERROR writing header:" ) << e.what() << endl;
		 }
		 */

		while (running) {

			//get new packet if necessary
			if (p == NULL) {
				p = inPort->receive();
			}

			if (p != NULL) {

				//paramsChanged();
				try {
					while (running) {

						sock = socket(AF_INET, SOCK_STREAM, 0);
						if (sock == -1) {
							log("Unable to open stream") << std::endl;
							if (retryInterval < 0) {
								running = false;
								throw 1;
							}
#if DEBUG || CONSOLE
							log("retrying in...") << " " << retryInterval
									<< " seconds." << endl;
#endif
							sleep(retryInterval);
							continue;
						}
						sin.sin_family = AF_INET;
						sin.sin_port = htons((unsigned short) port);

						struct hostent * host_addr = gethostbyname(
								host.c_str());
						if (host_addr == NULL) {
							cout << "Unable to locate host" << endl;
							if (retryInterval < 0) {
								running = false;
								throw 1;
							}
#if DEBUG || CONSOLE
							log("retrying in...") << " " << retryInterval
									<< " seconds." << endl;
#endif
							sleep(retryInterval);
							continue;
						}
						sin.sin_addr.s_addr = *((int*) *host_addr->h_addr_list);
						std::ostringstream str_log;
						log("") << "Port :" << sin.sin_port << ", Address : "
								<< sin.sin_addr.s_addr << std::endl;

						if (connect(sock, (const struct sockaddr *) &sin,
								sizeof(sockaddr_in)) == -1) {
							log("ERROR: cannot connect to server") << std::endl;
							if (retryInterval < 0) {
								running = false;
								throw 1;
							}
#if DEBUG || CONSOLE
							log("retrying in...") << " " << retryInterval
									<< " seconds." << endl;
#endif
							sleep(retryInterval);
							continue;
						}
#if DEBUG || CONSOLE
						log("run() connected successfully.");
#endif
						break;

					}

				} catch (int exc) {
					if (exc == 1)
						log("Error Not able to connect to HTTP server:") << host
								<< std::endl;
					break;
				}

				//int len = encoder->encode(p, buf, lineBufferSize);
				std::ostringstream params_str;
				params_str.setf ( ios::fixed );
				params_str.precision(8);
				params_str << "time=" << p->timestamp.tv_sec;
				params_str << setfill('0') << setw(6) << p->timestamp.tv_usec;
				params_str <<  "&plugid=" << p->dataVector[0];
				params_str <<  "&power=" << fixed << p->dataVector[1]->getFloat();

				std::string params = params_str.str();
								char *str = (char *) (params.c_str());

				std::ostringstream request_str;
				request_str << "GET ";
				request_str << end_point << "?" << params;
				request_str << " HTTP/1.0\r\n";
				request_str << "Accept: */*\r\n";
				request_str << "User-Agent: Mozilla/4.0\r\n";
				request_str << "Content-Length: ";
				request_str << params.length(); //request_str << message.length();
				request_str << "\r\n";
				request_str << "Accept-Language: en-us\r\n";
				request_str << "Accept-Encoding: gzip, deflate\r\n";
				request_str << "Host: ";
				request_str << "CRNT";
				request_str << "\r\n";
				request_str << "Content-Type: application/x-www-form-urlencoded\r\n";
				request_str << ("\r\n");
				request_str << ("\r\n");
				request_str << (params);
				request_str << ("\r\n");

				std::string request_string = request_str.str();
				// unsigned char* buf2;
				unsigned char *buf2 = new (unsigned char[2048]);
				buf2 = (unsigned char*) request_string.c_str();

#if DEBUG
				log("") << buf2;
#endif

				//socket->send( buf, strlen((char*)buf) );
				send(sock, buf2, strlen((char*) buf2), 0);

				// cout<<"####HEADER####"<<endl;
				char c1[1];
				int l, line_length;
				bool loop = true;
				bool bHeader = false;
				std::string message = "";
				while (loop) {
					l = recv(sock, c1, 1, 0);
					if (l < 0)
						loop = false;
					if (c1[0] == '\n') {
						if (line_length == 0)
							loop = false;

						line_length = 0;
						if (message.find("200") != string::npos)
							bHeader = true;

					} else if (c1[0] != '\r')
						line_length++;

					message += c1[0];
				}

				if (bHeader) {
					log("Request successful") << std::endl;
				} else {
					log("ERROR in HTTP Response") << std::endl;
				}
#if DEBUG
				log("Response: ") << message;
#endif
				close(sock);
				usleep(1000*1000*3);
				delete p;
				p = NULL;
			} else {
				log("ERROR: NULL packet received.");
			}
		}
	}
}

void HTTPClientWriter::sendMessage(std::string& message) {

	retryInterval = 3;
	tcpNoDelay = true;
	//ClientSocket *socket = NULL;
	unsigned char *parameters;
	//initialize encoder

	running = true;
	parameters = (unsigned char*) message.c_str();
	/*	while( running ) {
	 try {
	 socket = new ClientSocket( host, port );
	 //socket->setTcpNoDelay( tcpNoDelay );
	 log( "run() connected successfully." );
	 }
	 catch( SocketException &e ) {
	 log( "ERROR: cannot connect to server:" ) << "  " << e.what() << endl;
	 if (retryInterval < 0) {
	 running = false;
	 break;
	 }
	 log( "retrying in...") << " " << retryInterval << " seconds." << endl;
	 sleep(retryInterval);
	 continue;
	 }*/

	sockaddr_in sin;
	int sock = socket(AF_INET, SOCK_STREAM, 0);
	if (sock == -1) {
		cout << "Unable to open stream" << endl;
		return;
	}
	sin.sin_family = AF_INET;
	sin.sin_port = htons((unsigned short) 80);

	struct hostent * host_addr = gethostbyname(host.c_str());
	if (host_addr == NULL) {
		cout << "Unable to locate host" << endl;
		return;
	}
	sin.sin_addr.s_addr = *((int*) *host_addr->h_addr_list);
	cout << "Port :" << sin.sin_port << ", Address : " << sin.sin_addr.s_addr
			<< endl;

	if (connect(sock, (const struct sockaddr *) &sin, sizeof(sockaddr_in))
			== -1) {
		cout << "connect failed" << endl;
	}

	/*
	 //write header
	 int len = encoder->get_header( buf, lineBufferSize );
	 try {
	 socket->send( buf, len );
	 }
	 catch( SocketException &e ) {
	 log( "ERROR writing header:" ) << e.what() << endl;
	 }
	 */



	//get new packet if necessary
	std::ostringstream request_str;
	request_str << "POST ";
	request_str << end_point << "?" << message;
	request_str << " HTTP/1.0\r\n";
	request_str << "Accept: */*\r\n";
	request_str << "User-Agent: Mozilla/4.0\r\n";

	request_str << "Content-Length: ";
	//request_str << strlen((char*)parameters);
	request_str << message.length();
	request_str << "\r\n";
	request_str << "Accept-Language: en-us\r\n";
	request_str << "Accept-Encoding: gzip, deflate\r\n";
	request_str << "Host: ";
	request_str << "CRNT";
	request_str << "\r\n";
	request_str << "Content-Type: application/x-www-form-urlencoded\r\n";

	//If you need to send a basic authorization
	//string Auth        = "username:password";
	//Figureout a way to encode test into base64 !
	//string AuthInfo    = base64_encode(reinterpret_cast<const unsigned char*>(Auth.c_str()),Auth.length());
	//string sPassReq    = "Authorization: Basic " + AuthInfo;
	//request_str <<(sPassReq.c_str());

	request_str << ("\r\n");
	request_str << ("\r\n");
	request_str << (parameters);
	request_str << ("\r\n");

	std::string message_string = request_str.str();
	// unsigned char* buf;
	buf = (unsigned char*) message_string.c_str();

	log("") << buf;
	//socket->send( buf, strlen((char*)buf) );
	send(sock, buf, strlen((char*) buf), 0);

	// cout<<"####HEADER####"<<endl;
	char c1[1];
	int l, line_length;
	bool loop = true;
	bool bHeader = false;

	while (loop) {
		l = recv(sock, c1, 1, 0);
		if (l < 0)
			loop = false;
		if (c1[0] == '\n') {
			if (line_length == 0)
				loop = false;

			line_length = 0;
			if (message.find("200") != string::npos)
				bHeader = true;

		} else if (c1[0] != '\r')
			line_length++;

		log("") << c1[0];

		message += c1[0];
	}

	message = "";
	if (bHeader) {
		log("Request successful") << std::endl;
		char p[1024];
		while ((l = recv(sock, p, 1023, 0)) > 0) {
			cout.write(p, l);
			p[l] = '\0';
			message += p;
		}
	} else {
		log("ERROR in HTTP Response") << std::endl;
	}

}

void HTTPClientWriter::testMessage() {
	running = true;
	int sock;
	sockaddr_in sin;
	while (running) {

		/*
		 //write header
		 int len = encoder->get_header( buf, lineBufferSize );
		 try {
		 socket->send( buf, len );
		 }
		 catch( SocketException &e ) {
		 log( "ERROR writing header:" ) << e.what() << endl;
		 }
		 */
		int i = 0;



			//get new packet if necessary




				try {
					while (running) {

						sock = socket(AF_INET, SOCK_STREAM, 0);
						if (sock == -1) {
							log("Unable to open stream") << std::endl;
							if (retryInterval < 0) {
								running = false;
								throw 1;
							}
#if DEBUG || CONSOLE
							log("retrying in...") << " " << retryInterval
									<< " seconds." << endl;
#endif
							sleep(retryInterval);
							continue;
						}
						sin.sin_family = AF_INET;
						sin.sin_port = htons((unsigned short) port);

						struct hostent * host_addr = gethostbyname(
								host.c_str());
						if (host_addr == NULL) {
							cout << "Unable to locate host" << endl;
							if (retryInterval < 0) {
								running = false;
								throw 1;
							}
#if DEBUG || CONSOLE
							log("retrying in...") << " " << retryInterval
									<< " seconds." << endl;
#endif
							sleep(retryInterval);
							continue;
						}
						sin.sin_addr.s_addr = *((int*) *host_addr->h_addr_list);
						std::ostringstream str_log;
						log("") << "Port :" << sin.sin_port << ", Address : "
								<< sin.sin_addr.s_addr << std::endl;

						if (connect(sock, (const struct sockaddr *) &sin,
								sizeof(sockaddr_in)) == -1) {
							log("ERROR: cannot connect to server") << std::endl;
							if (retryInterval < 0) {
								running = false;
								throw 1;
							}
#if DEBUG || CONSOLE
							log("retrying in...") << " " << retryInterval
									<< " seconds." << endl;
#endif
							sleep(retryInterval);
							continue;
						}
#if DEBUG || CONSOLE
						log("run() connected successfully.");
#endif
						break;

					}

				} catch (int exc) {
					if (exc == 1)
						log("Error Not able to connect to HTTP server:") << host
								<< std::endl;
					break;
				}
				paramsChanged();

				std::ostringstream request_;


					request_ << "time" << "=" <<"1339058858332";
					request_ <<  "&";
					request_ << "plugid" << "=" << "12345";
					request_ <<  "&";
					request_ << "power" << "=" << "1.2345e01";

				//outs << endl;

					std::string message_ = request_.str();
				char *str = (char *) (message_.c_str());

				unsigned int len = strlen(str);

				if( len >= lineBufferSize ) {
					len = lineBufferSize;
					log( "encode(): ERROR: buffer too small for entire packet" );
				}

				//copy str to buffer
				strncpy( (char *) buf, str, len ); // may be unterminated.


				std::ostringstream request_str;
				request_str << "GET ";
				request_str << end_point << "?" << str;
				request_str << " HTTP/1.0\r\n";
				request_str << "Accept: */*\r\n";
				request_str << "User-Agent: Mozilla/4.0\r\n";

				request_str << "Content-Length: ";
				request_str << len;
				//					request_str << message.length();
				request_str << "\r\n";
				request_str << "Accept-Language: en-us\r\n";
				request_str << "Accept-Encoding: gzip, deflate\r\n";
				request_str << "Host: ";
				request_str << "CRNT";
				request_str << "\r\n";
				request_str
						<< "Content-Type: application/x-www-form-urlencoded\r\n";

				//If you need to send a basic authorization
				//string Auth        = "username:password";
				//Figureout a way to encode test into base64 !
				//string AuthInfo    = base64_encode(reinterpret_cast<const unsigned char*>(Auth.c_str()),Auth.length());
				//string sPassReq    = "Authorization: Basic " + AuthInfo;
				//request_str <<(sPassReq.c_str());

				request_str << ("\r\n");
				request_str << ("\r\n");
				request_str << (str);
				request_str << ("\r\n");

				std::string message_string = request_str.str();
				// unsigned char* buf2;
				unsigned char *buf2 = new (unsigned char[lineBufferSize]);
				buf2 = (unsigned char*) message_string.c_str();

#if DEBUG
				log("") << buf2;
#endif

				//socket->send( buf, strlen((char*)buf) );
				send(sock, buf2, strlen((char*) buf2), 0);

				// cout<<"####HEADER####"<<endl;
				char c1[1];
				int l, line_length;
				bool loop = true;
				bool bHeader = false;
				std::string message = "";
				while (loop) {
					l = recv(sock, c1, 1, 0);
					if (l < 0)
						loop = false;
					if (c1[0] == '\n') {
						if (line_length == 0)
							loop = false;

						line_length = 0;
						if (message.find("200") != string::npos)
							bHeader = true;

					} else if (c1[0] != '\r')
						line_length++;

					message += c1[0];
				}

				if (bHeader) {
					log("Request successful") << std::endl;

				     log("####BODY####")<<endl;
				     char p[1024];
				     while((l = recv(sock,p,1023,0)) > 0)  {
				          cout.write(p,l) ;
					     p[l] = '\0';
					     message += p;
				     }

				     cout << message.c_str();
				} else {
					log("ERROR in HTTP Response") << std::endl;
				}
#if DEBUG
				log("Response: ") << message;
#endif


				close(sock);
				usleep(1000*1000*5);
	}

}

