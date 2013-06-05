/*
 * PollDevicesTask.cpp
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
 *	@version beta.2.1: 26/11/2012
 */
#define DEBUG 0
#define CONSOLE 1

#include "PollDevicesTask.h"
#include "PlugwiseStick.h"
#include "PlugwiseErrorLogHelper.h"

using namespace std;

PollDevicesTask::PollDevicesTask() : StreamTask(1,2){
	_sleep_time = DEFAULT_SLEEP_TIME;
	_repeat_times = DEFAULT_REPEAT_TIMES;
}

PollDevicesTask::PollDevicesTask(const std::string& serial_port): StreamTask(1,2), _serial_port(serial_port)  {
	_sleep_time = DEFAULT_SLEEP_TIME;
	_repeat_times = DEFAULT_REPEAT_TIMES;
}

PollDevicesTask::PollDevicesTask(const std::string& serial_port,  int sleep_time, int repeat_times): StreamTask(1,2), _serial_port(serial_port)   {
	_sleep_time = sleep_time;
	_repeat_times = repeat_times;
}

PollDevicesTask::~PollDevicesTask() {
	_stick->close_conn();
	delete _stick;
}


void PollDevicesTask::run(){


		running = true;
		std::string log_string = "";
#if DEBUG
		log( "Running PollDevicesTask")<<std::endl;
#endif

		time_t seconds1, seconds2;
		PlugwiseErrorLogHelper *error_logger(new PlugwiseErrorLogHelper());

		PlugwiseStick *stick(new PlugwiseStick(_serial_port));
		_stick = stick;
		_stick->set_read_timeout(_read_timeout);

		int times_done = 1;

		if(_repeat_times ==-1)
			times_done=-2;

		if(_repeat_times ==0)
			running = false;

		bool init_stick = false;
		int retries = 0;
		while(!init_stick && retries < PlugwiseStick::DEFAULT_RETRY_LIMIT){
			init_stick = _stick->initialize_plugwise_stick();
			retries++;
		}

		for(unsigned char i=0; i<device_list.size() ; i++){
			log( "Device:")<<(device_list.at(i))<<std::endl;
			PlugwiseCircle *circle(new PlugwiseCircle(device_list.at(i)));
			circle->set_plugwise_stick(_stick);
			circles.push_back(circle);

		}
		int _frequency = 0;
		if(_sleep_time>0)
		_frequency = _sleep_time;
			//_frequency = _sleep_time / device_list.size();

		if (_frequency<1)
			_frequency = 0;

		while (running && (times_done < (_repeat_times+1))) {
			//listen...
			seconds1 = time (NULL);

			if(_repeat_times > 0)
				times_done++;
#if DEBUG
		  	stringstream ss1;
		  	ss1 << times_done;
			log_string ="Times Done:" + ss1.str();
			log(log_string.c_str())<<std::endl;
#endif
			for(unsigned char i=0; i<circles.size() ; i++){
#if DEBUG
				log_string = "Device Address:"+(circles.at(i)->get_device_address());
				log(log_string.c_str())<<std::endl;
#endif

				//_time_out_thread->stop();
				try{
					stream_data(&(circles.at(i))->get_instant_energy_consumption());
					//(circles.at(i))->get_instant_energy_consumption();
					usleep(1000*1000*2);//polling the devices takes around 6 seconds, and a command has a life span of 8 seconds. (so 8 seconds in total to be safe)
				} catch (int exc){
					if(exc==300){
						log_string = "Error in Response at Device Address:" + circles.at(i)->get_device_address();
						log(log_string.c_str())<<std::endl;
					}
					//log error
					stream_error_data(&error_logger->log_plugwise_error(circles.at(i)->get_device_address(),exc));
				}

			}

			if(times_done < (_repeat_times+1)){
								if(_frequency > 0 ){
									seconds2 = time (NULL);
									int diff = seconds2 - seconds1;
									diff = _frequency - diff;
#if DEBUG
										stringstream ss2;
										ss2 << times_done;
										log_string ="Time left for next cycle:" + ss2.str();
										log(log_string.c_str())<<std::endl;
#endif
										if(diff > 0 )
											usleep(1000*1000*diff);
								}
							}

			//log( "Sleeping for next cycle")<<std::endl;
			//usleep(1000*1000*60);

		}

}


int PollDevicesTask::stream_data(DataPacket *data_packet)
{
	/*	//create data packet
	DataPacket *packet = NULL;
	packet = new DataPacket();

	//assemble data packet
	for (int i = 0; i < buf_size; i++){
		packet->dataVector.push_back(new IntValue(buf[i]));
	}

	delete packet;
	packet = NULL;
	 */
	//send data packet
	//DataPacket *packet;
	//packet = data_packet->clone();
	outPorts[0]->send(data_packet);
	//outPorts[1]->send(packet);

#if DEBUG
	log( ".stream_data: stream data successful.");
#endif

	return 0;
}


int PollDevicesTask::stream_error_data(DataPacket *data_packet)
{

	outPorts[1]->send(data_packet);

#if DEBUG
	log( ".stream_error_data: stream error data successful.");
#endif

	return 0;
}
