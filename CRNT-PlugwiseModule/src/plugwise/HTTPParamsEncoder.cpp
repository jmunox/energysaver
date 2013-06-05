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

// HTTPParamsEncoder.cpp

#include "HTTPParamsEncoder.h"
#include "../core/Value.h"
#include <iomanip>
#include <cstring>


using namespace std;

// CONSOLE switch, runs in Console out debugging mode when flag = 1
#define DEBUG 1
#define CONSOLE 1

HTTPParamsEncoder::HTTPParamsEncoder()
{
	initialized = false;
	numOfChannels = 0;
	separator = "&";
}

HTTPParamsEncoder::~HTTPParamsEncoder()
{
}


Encoder* HTTPParamsEncoder::clone() const
{
	return new HTTPParamsEncoder( *this );
}


void HTTPParamsEncoder::init( const DataPacket *p )
{
	if( p ) {
		numOfChannels = p->dataVector.size();
		initialized = true;
	}
	else {
		log( "init(): ERROR: initialization failed" );
		initialized = false;
	}
}


int HTTPParamsEncoder::get_header( unsigned char *buf, unsigned int size )
{
	unsigned int len = 0;
	
	if( !initialized ) {
		log( "get_header(): ERROR: not initialized" );
		return -1;
	}
	
	//there is no header
	return len;
}


int HTTPParamsEncoder::encode( const DataPacket *p, unsigned char *buf, unsigned int size )
{
	if( !initialized ) {
		log( "encode(): ERROR: not initialized" );
		return -1;
	}
	
	if( (p == NULL ) ) {
		log( "encode(): ERROR: NULL Packet" );
		return -1;
	}

	if( (p->dataVector.size() != numOfChannels) ) {
		if( warnNumOfChannels ) {
			log( "encode(): WARNING: data packet dateVector.size() does not match" );
		}
		
		//re-initialize number of channels
		init(p);
	}
	
	ostringstream outs;

	outs << params_label_list[0] << "=" <<p->timestamp.tv_sec;
	outs << setfill('0') << setw(6) << p->timestamp.tv_usec << separator;
	
	for( unsigned int i = 0; i < p->dataVector.size(); i++ ) {
#if DEBUG || CONSOLE
		log( "Encoder Value: ")<<p->dataVector[i]<<std::endl;
#endif
		if(i) {
			outs << separator;
		}
		if( markInvalidValues && !p->dataVector[i]->isValid() ) {
			outs << "~";
		}
		outs << params_label_list[i+1] << "=" << p->dataVector[i];
	}

	//outs << endl;

	string s = outs.str();
	char *str = (char *) (s.c_str());

	unsigned int len = strlen(str);
	
	if( len >= size ) {
		len = size;
		log( "encode(): ERROR: buffer too small for entire packet" );
	}
	
	//copy str to buffer
	strncpy( (char *) buf, str, len ); // may be unterminated. 
	
	return len;
}


int HTTPParamsEncoder::get_footer( unsigned char *buf, unsigned int size )
{
	if( !initialized ) {
		log( "get_footer(): ERROR: not initialized" );
		return -1;
	}
	
	//there is no footer
	return 0;
}



