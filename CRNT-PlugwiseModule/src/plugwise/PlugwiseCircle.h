/*
 * PlugwiseCircle.h
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
 *	@version beta.1.3: 17/05/2012
 */

#ifndef PLUGWISECIRCLE_H_
#define PLUGWISECIRCLE_H_
#define EMPTY_RESPONSE_ERROR 500
#define ACK_RESPONSE_ERROR 300
#define WRONG_RESPONSE_ERROR 400


#include "PlugwiseDevice.h"
#include "PlugwiseStick.h"

using namespace std;


class PlugwiseCircle : public PlugwiseDevice {

	//GIBN_MAKE_AVAILABLE(PlugwiseCircle, PlugwiseDevice)

public:
	PlugwiseCircle();
	PlugwiseCircle(const std::string& device_address);
	virtual ~PlugwiseCircle();
	DataPacket& get_instant_energy_consumption();
	DataPacket& get_hour_energy_consumption();
	DataPacket& switch_on();
	DataPacket& switch_off();
	DataPacket& get_calibration_information();
	DataPacket& get_device_information();
	void set_plugwise_stick(PlugwiseStick *stick){_stick=stick;}
	PlugwiseStick& get_plugwise_stick(){return *_stick;}

private:
	PlugwiseStick *_stick;

	float _device_gain_A;
	float _device_gain_B;
	float _device_offset_noise;
	float _device_offset_total;

	int _device_log_address;
	std::string _log_address_string;


};


#endif /* PLUGWISECIRCLE_H_ */
