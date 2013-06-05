/*
 * NumberConverter.h
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

#ifndef NUMBERCONVERTER_H_
#define NUMBERCONVERTER_H_

#include "../core/TBObject.h"

using namespace std;

class NumberConverter : public TBObject{

	//GIBN_MAKE_AVAILABLE(NumberConverter, TBObject)

public:
	NumberConverter();
	virtual ~NumberConverter();

	float get_float_from_hex_char(const char* my_char);
	int get_int_from_hex_char(const char* my_char);
	const char* get_hex_char_from_int(const unsigned int x, int expected_size);
	const char* get_hex_char_from_int_no_size(const unsigned int x);
	const char* get_time(int minutes);

private:
	unsigned int int_to_big_endianess(const unsigned int x);
	float int_bits_to_float(const int x);
};


#endif /* NUMBERCONVERTER_H_ */
