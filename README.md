energysaver
===========

Project belonging to my final Thesis for my master studies of Human Technology Interaction. 


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
Takayama et al. (2009).

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




##############################################################################################################
If you are interested in the theoretical rational  behind this project, continue reading.

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


Research Question:
==================

The current study tested whether strategies that display the effects of individual contributions are more effective 
than strategies that only display the effects of group contribution in a field experiment. 
In particular, the current research investigated the differences in effectiveness between providing individual 
comparison feedback and providing group feedback in order to reduce energy consumption. 


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


