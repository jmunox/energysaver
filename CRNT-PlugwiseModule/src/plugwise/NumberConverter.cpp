/*
 * NumberConverter.cpp
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


#include <iostream>
#include <sstream>
#include <stdio.h>
#include <stdlib.h>
#include <iomanip>

#include "NumberConverter.h"

using namespace std;

NumberConverter::NumberConverter() {
	// TODO Auto-generated constructor stub

}

NumberConverter::~NumberConverter() {
	// TODO Auto-generated destructor stub
}


float NumberConverter::get_float_from_hex_char(const char* my_char){
	return int_bits_to_float(get_int_from_hex_char(my_char)); //convert the int back to a IEEE 754 single-precision float
}

int NumberConverter::get_int_from_hex_char(const char* my_char){

	unsigned int number_int;
	unsigned long int number_long;
	number_long = strtoul (my_char,NULL,16);
	number_int = (int)(number_long & 0XFFFFFFFF);
	//number_int = int_to_big_endianess(number_int); //change endianess
	return number_int;
}

const char* NumberConverter::get_hex_char_from_int(const unsigned int x, int expected_size){
	const char* my_int_char;
	std::ostringstream str_string;
	str_string << std::right<< setfill('0')<<setw(expected_size) << std::hex << std::uppercase  << x;
	my_int_char = str_string.str().c_str();
	return my_int_char;
}

const char* NumberConverter::get_hex_char_from_int_no_size(const unsigned int x){
	const char* my_int_char;
	std::ostringstream str_string;
	str_string << std::hex << std::uppercase  << x;
	my_int_char = str_string.str().c_str();
	return my_int_char;
}


// From http://www.cplusplus.com/forum/general/40690/
// Convert little endian int to big endian int
unsigned int NumberConverter::int_to_big_endianess(const unsigned int x)
{
    return  ( x >> 24 ) |  // Move first byte to the end,
            ( ( x << 8 ) & 0x00FF0000 ) | // move 2nd byte to 3rd,
            ( ( x >> 8 ) & 0x0000FF00 ) | // move 3rd byte to 2nd,
            ( x << 24 ); // move last byte to start.
}


// From http://www.cplusplus.com/forum/general/40690/
// Return the float value corresponding to a given bit represention of a scalar int value or vector of int values
float NumberConverter::int_bits_to_float(const int x)
{
    union {
       float f;  // assuming 32-bit IEEE 754 single-precision
       int i;    // assuming 32-bit 2's complement int
    } u;

    u.i = x;
    return u.f;
}

const char* NumberConverter::get_time(int minutes){
	const char* my_int_char;
	minutes = minutes % (24 * 60);		// get minutes from day start
	int hour = minutes / 60;			// get hours
	int minute = minutes % 60;			// get minutes from hour start
	std::ostringstream str_string;
	str_string << std::right<< setfill('0')<<setw(2) << hour << ":" << std::right<< setfill('0')<<setw(2) << minute;
	str_string << ":00";
	my_int_char = str_string.str().c_str();
	return my_int_char;
}
