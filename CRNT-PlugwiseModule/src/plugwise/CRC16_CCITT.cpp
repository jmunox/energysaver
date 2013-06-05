/*
 * CRC16_CCITT.cpp
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
 *	@version beta.1.4: 17/05/2012
 */

#include "CRC16_CCITT.h"
#include <iostream>
#include <sstream>
#include <stdio.h>
#include <stdlib.h>
#include <iomanip>

using namespace std;

CRC16_CCITT::CRC16_CCITT() {
	init_crc();
}

CRC16_CCITT::~CRC16_CCITT() {
	delete _crc_table;
}

std::string CRC16_CCITT::calculate_CRC(const std::string data){
	int work = 0x0000;
	char* bytes = (char*)data.c_str();
	for (unsigned int a=0;a<=data.length()-1;a++)
	{
		work = (_crc_table[(bytes[a] ^ (work >> 8)) & 0xff] ^ (work << 8)) & 0xffff;
	}
	std::ostringstream crc;
	crc  << std::right<< setfill('0')<<setw(4) << std::hex << std::uppercase << work;
	return crc.str();
}

void CRC16_CCITT::init_crc() {

    for (int i = 0; i < 256; i++) {
      int fcs = 0;
      int d = i << 8;
      for (int k = 0; k < 8; k++) {
	if (((fcs ^ d) & 0x8000) != 0)
	  fcs = (fcs << 1) ^ CRC_POLYNOMIAL;
	else
	  fcs = (fcs << 1);
	d <<= 1;
	fcs &= 0xffff;
      }
      _crc_table[i] = fcs;
    }

}

