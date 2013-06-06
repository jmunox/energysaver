<?php require_once ("../JavaBridge/java/Java.inc");
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
  		$transaction = $servicefacade->createAgenda($user->getUserId(), $hours);
		
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
			header("location:createagenda.php");
		
		}
		else header("location:login.php");
		
		
		?>
