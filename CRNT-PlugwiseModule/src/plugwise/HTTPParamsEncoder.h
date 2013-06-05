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

// HTTPParamsEncoder.h

 
#ifndef HTTPPARAMSSENCODER_H
#define HTTPPARAMSSENCODER_H

#include "../core/Encoder.h"
//#include "Vector.h"
#include <sstream>
#include <vector>


/**
 * \ingroup codec
 * \brief HTTPParamsEncoder
 *
 * URL params values, timestamp first, separeated
 * Separator is "&".
 * Returns:
 * param1=val1&param2=val2
 */
using namespace std;

class HTTPParamsEncoder : public Encoder
{

//	GIBN_MAKE_AVAILABLE( HTTPParamsEncoder, Encoder )

	public:
		HTTPParamsEncoder();
		virtual ~HTTPParamsEncoder();
		
		virtual Encoder* clone() const;
		virtual void init( const DataPacket *p );
		virtual int get_header( unsigned char *buf, unsigned int size );
		virtual int encode( const DataPacket *p, unsigned char *buf, unsigned int size );
		virtual int get_footer( unsigned char *buf, unsigned int size );
	
	private:
		bool initialized;
		unsigned int numOfChannels;

		/**
		 * Separator string that is printed between fields.
		 */
		std::string separator; // "&" ;

		

//		GIBN_REQUIRED_VEC Vector<string> params_label_list;


		/**
		 * Mark invalid values with a '~' if set to true.
		 */
//		GIBN_OPTIONAL bool markInvalidValues GIBN_DEFAULT(false);

		/**
		 * Print warning message if number of channels changes.
		 */
//		GIBN_OPTIONAL bool warnNumOfChannels GIBN_DEFAULT(true);


		std::vector<string> params_label_list;

		/**
		 * Mark invalid values with a '~' if set to true.
		 */
		bool markInvalidValues;

		/**
		 * Print warning message if number of channels changes.
		 */
		bool warnNumOfChannels;

};
	


#endif	//HTTPPARAMSSENCODER_H
