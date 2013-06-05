/*
 * CRC16_CCITT.h
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

#ifndef CRC16_CCITT_H_
#define CRC16_CCITT_H_

#include <sstream>
#include "../core/TBObject.h"

using namespace std;

class CRC16_CCITT : public TBObject{

	//GIBN_MAKE_AVAILABLE(CRC16_CCITT, TBObject)

public:
	//Checksum CRC CCITT 16 bits polynomial: 0x1021 = x^16 + x^12 + x^5 + 1
	const static unsigned int CRC_POLYNOMIAL = 0x1021;
	CRC16_CCITT();
	virtual ~CRC16_CCITT();
	std::string calculate_CRC(const std::string data);

private:
	// CRC CCITT-16 Table
	unsigned int _crc_table[255];
	void init_crc();
};


#endif /* CRC16_CCITT_H_ */
