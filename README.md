energysaver
===========

Project belonging to my final Thesis for my master studies of Human Technology Interaction at the 
Eindhoven University of Technology


Brief Overview:
===============

As part of my graduation project I executed a field experiment aiming to test an innovative form of engagement 
to promote energy saving programs in office environments. 
This research proposed to turn the sustainable behavior into a playful activity as a way to effectively 
motivate people to save electrical energy. 

I develped an interactive online game which keept track of the energy consumption, with the aim to challenge 
the individual to adopt new behaviors. I included simple game factors of challenge, fantasy, cooperation and 
competition with others in order to improve the gaming experience and enhance participation.

For the purpose of this field experiment, I developed an energy measurement infrastructure using Plugwise devices.
The system consisted of 72 Plugwise devices, twelve computers acting as data gateways and a central database server, 
an application server as back-end and a web server in charge of the front-end channel. 


Feedback:
=========

The feedback was provided through a web interface and it consisted of a virtual animated island containing 4 
avatars which were associated to each participant of a specific team. 

*The idea of the animated island was inspired in the concept of the EcoIsland, a work done previously by 
Takayama et al. (2009). http://forum.pervasive2008.org/Papers/Workshop/w2-17.pdf

The way the feedback was presented differed depending on the experimental condition: 

 *   group condition, 
 *   individual comparison condition,
 *   control group

In the group condition, the feedback was represented by the water level around the island which could rise or fall 
according to the overall consumption of the group. 

In the individual comparison condition, a crown was assigned to the person who consumed the lowest amount of energy 
of the group and a pair of donkey ears was assigned to the person who consumed the highest amount of energy of the 
group. The crown represented a positive performance compared to the performance of other members from the group. 
The donkey ears indicated a bad performance in comparison to the performance of other group members. 

Participants in the control group could only see their avatars and the avatars of the other team members.



Deployment:
===========

The Plugwise device was connected to all the electrical devices from the participants and it was connected via it 
wireless communication protocol to the gateway computer where a CRNT (http://crnt.sourceforge.net) instance was running. 
The CRNT collected the data and sent it through post HTTP requests over the Internet to a central server where 
the data was stored in the database (MongoDB). 

The web application consisted of a back-end module developed in Java and it was deployed on an Apache Tomcat 
application server. 

The front-end module was developed in PHP, HTML and JavaScript and it was deployed on an Apache web server. 
The front-end module communicated to the backend using php-bridge.

The web application made the calculations and comparisons, and created the adequate feedback screens for the 
participants.



Note:
=====
I developed a C module for the CRN Toolbox (CRNT) in order to connect to the Plugwise devices. 
The Plugwise Module for CRN Toolbox is based and inspired on the protocol information provided by
 
 *   Maarten Damen: http://www.maartendamen.com/category/plugwise-unleashed/
 * 	Roheve: http://roheve.wordpress.com/2011/04/24/plugwise-protocol-analyse/
 
 and the code implementations from
 * 	Gonium's libplugwise: https://github.com/gonium/libplugwise
 *  Kenneth J. Turner's work: http://www.cs.stir.ac.uk/~kjt/software/comms/plugwise.html


License:
========
*  "Energy-Saver Game" is free software: you can redistribute it and/or modify
* 	it under the terms of the GNU General Public License as published by
* 	the Free Software Foundation, either version 3 of the License, or
* 	(at your option) any later version.
*
* 	"Energy-Saver Game" is distributed in the hope that it will be useful,
* 	but WITHOUT ANY WARRANTY; without even the implied warranty of
* 	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* 	GNU General Public License for more details.
*
* 	You should have received a copy of the GNU General Public License
* 	along with "EnergySaver Game Application". If not, see <http://www.gnu.org/licenses/>
*
*	@author Jesus Muñoz-Alcantara @ moxhu
*	http://agoagouanco.com
*	http://moxhu.com

##############################################################################################################

Research Summary:
=================
If you are interested in the theoretical rational, scientific results and conclusions behind this project, please 
continue reading.


Research Rationale:
===================

The main challenge that any successful behavioral intervention faces is the inherently social nature of sustainability 
(e.g., Biel and Thø gersen, 2007; Fielding et al., 2008; Van Vugt, 2009). 
The support and adoption of a new behavior of consumption depends directly on the individual's decisions and consequent 
actions. However, sustainable behavior is fundamentally a group phenomenon. This implication is essential, especially, 
because the impact of a single individual is rather small, and a true change depends on the aggregate actions of a 
significant amount of people (Stern, 2000). Therefore, energy conservation is determined by the joint performance of 
a group of individuals. 

Furthermore, their performance may be influenced by eachothers actions: whether an individual does or does not 
adopt a sustainable behavior is interdependent with the interactions and relations to other subjects within the 
context in which he or she is embedded.

The current research extends the previous study of Midden et al. (2011) into a field experiment over long term interactions.


Research Question:
==================

The current study tested whether strategies that display the effects of individual contributions are more effective 
than strategies that only display the effects of group contribution in a field experiment. 
In particular, the current research investigated the differences in effectiveness between providing individual 
comparison feedback and providing group feedback in order to reduce energy consumption. 

Hypotheses:
===========
Hypothesis 1. Individual comparison feedback will be more effective than no feedback condition. 

Individual comparison feedback should reduce energy consumption.

Hypothesis 2. Group feedback will be more effective than no feedback condition. 

Group feedback should reduce the energy consumption.

Hypothesis 3. Individual comparison feedback will be more effective than group feedback.

Individual comparison feedback should reduce energy consumption more effectively than group feedbac.k


Methodology:
============

The research focused on workspaces which were owned and shared by a specific group of individuals where everyday 
group interactions would support the social gaming experience. I had the specific interest on PhDs workspaces 
within the Electrical Engineering Department of the Technical University of Eindhoven 
due to the fact that they are the individuals with more similar and consistent working schedules and habits.

The experiment consisted of 5 weeks of data collection during November and December 2012. 


Implementation:
===============

The intervention consisted of a set of sensors (Plugwise devices) which measure the electrical consumption of the 
devices connected to the sensors.

The study focused on measuring only the energy consumption of the devices which were directly related to daily 
work activities, specifically personal computer and attached display. Each sensor was placed between the socket
and the electrical appliance. 

The online game application will provided social feedback about the consumption primarily based on a daily basis.
Participants were provided with a web link where they be able consult the online application.

Data analysis:
==============
In order to test the difference between type of feedback strategy, the consumption data was submitted into a 
repeated measures ANOVA (“days” as within subjects factor vs “feedback type”, as between subjects factor).

Planned contrast comparisons were used to understand what were the differences in energy consumption between 
the experimental conditions (no feedback, individual comparison and group feedback).

Results:
========

The first planned contrast comparisons revealed a marginal significant difference between subjects that received 
no feedback and subjects that received individual comparison feedback, t(21) = 1.48, p = .07 (1-tailed). 
Contrary to hypothesis 1, subjects that received individual comparison feedback consumed in average more energy 
compared to individuals who did not receive feedback. 

A marginal significance difference was found between subjects with no feedback and subjects with group feedback 
(t(21) = 1.327, p = .06 (1-tailed). This result provides support for hypothesis 2 in which it was expected that 
the group feedback condition would reduce the energy consumption compared to the no feedback condition. 


A significant difference - t(21) = 2.90, p < .01 - in the total energy consumed was found between subjects in 
the individual comparison condition compared to subjects in the group condition. This difference provides evidence 
against Hypothesis 3, suggesting that the group feedback condition reduced their consumption compared to the 
individual comparison feedback group.


Conclusion:
============

The individuals that received the group feedback reduced their energy consumption more effectively compared to 
individuals that did not receive feedback. This result supported our hypothesis and provided new evidence about 
the way individuals might react to different types of settings regarding to their sustainable behavior. 
According to the results of the previous study (Midden et al., 2011), group feedback was only effective for 
subjects that came from a collectivist culture (Japan), while subjects that came from an individualistic 
culture (The Netherlands) group feedback was not motivating them enough to save energy. 

In the case of this study, we found evidence that group feedback was effective in changing the behavior of the 
individuals, when consumption was monitored in a context that occurred through a longer period of time. 

Secondly, individuals that received individual comparison feedback also presented a tendency to reduce their 
energy consumption. However, they consumed more energy in average compared to individuals that did not receive 
feedback. 

This result might not support our first hypothesis, nevertheless, there is a tendency to reduce their energy which 
might suggest that the feedback had a positive effect on the energy conservation behavior. 
The feedback motivated the individual to save energy but it might not have reduced the uncertainty that surrounded 
the goal of saving energy completely. 

Our results give evidence that providing feedback to individuals within a group triggers their commitment to 
sustainable behaviors.
