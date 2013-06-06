<?php

/*
 *
*  This file is part of the "EnergySaver Game Application".
*
* 	"Energy-Saver Game" is free software: you can redistribute it and/or modify
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
*/

require_once ("../JavaBridge/java/Java.inc");

session_start(); //Start the session
if(isset( $_SESSION['auth'] )){//If session registered
	

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	$hours = $_POST['hours'];
	$semail = $_SESSION['semail'];
	$errormessage = "";

	try {
		
		$servicefacade = new java("org.moxhu.esavegame.business.facade.JavaBridgeBusinessFacade");
  		$transaction = $servicefacade->getProfile($semail);
  		$user = $transaction->get("user");
  		$transaction = $servicefacade->createWeeklyAgenda($user->getUserId(), $hours);
		
  		if(java_values($transaction->containsKey("exception"))){
  			$errormessage =
				"<p><span class=\"errormessage\">".$transaction->get("exception.message")."</span></p><br/>";
				include("error.php");
  		}else
  			header("location:instructions.php");
  		
		} catch (JavaException $e) {
			$errormessage =$errormessage."<p><span class=\"errormessage\">Exception occured: ".$je."</span></p><br/>";
			include("error.php");
		}
		} else
			header("location:createweeklyagenda.php");
		
		}
		else header("location:login.php");
		
		
		?>
