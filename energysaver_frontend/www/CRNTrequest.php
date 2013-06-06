<?php require_once ("JavaBridge/java/Java.inc");


if ($_SERVER['REQUEST_METHOD'] == 'GET') {
    $time = $_GET['time'];
    $plugid = $_GET['plugid'];
    $power = $_GET['power'];
$my_req ='time='.$time.'&plugid='.$plugid.'&power='.$power;

header("X-esavergame-GET: $my_req");

	try {

		$my_req ='time='.$time.'&plugid='.$plugid.'&power='.$power;
		$ip_add = get_client_ip();
		$registerBean = new java("org.moxhu.esavegame.business.RegisterEnergyConsumptionBean");
  		$registerBean->registerInstantEnergyConsumption($plugid, $power, $time, $ip_add);
		echo $my_req;
		} catch (JavaException $e) {
			echo "Exception occured: "; echo $e; echo "<br>\n";
		}

} elseif ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $time = $_POST['time'];
    $plugid = $_POST['plugid'];
    $power = $_POST['power'];

$my_req ='time='.$time.'&plugid='.$plugid.'&power='.$power;

header("X-esavergame-POST: $my_req");
	try {

		$my_req ='time='.$time.'&plugid='.$plugid.'&power='.$power;

		$registerBean = new java("org.moxhu.esavegame.business.RegisterEnergyConsumptionBean");
		$registerBean->registerInstantEnergyConsumption($plugid, $power, $time);
		echo $my_req;
	} catch (JavaException $e) {
		echo "Exception occured: "; echo $e; echo "<br>\n";
}

} else
echo "No request";

// Function to get the client ip address
function get_client_ip() {
	$ipaddress = '';
	if (getenv('HTTP_CLIENT_IP'))
		$ipaddress = getenv('HTTP_CLIENT_IP');
	else if(getenv('HTTP_X_FORWARDED_FOR'))
		$ipaddress = getenv('HTTP_X_FORWARDED_FOR');
	else if(getenv('HTTP_X_FORWARDED'))
		$ipaddress = getenv('HTTP_X_FORWARDED');
	else if(getenv('HTTP_FORWARDED_FOR'))
		$ipaddress = getenv('HTTP_FORWARDED_FOR');
	else if(getenv('HTTP_FORWARDED'))
		$ipaddress = getenv('HTTP_FORWARDED');
	else if(getenv('REMOTE_ADDR'))
		$ipaddress = getenv('REMOTE_ADDR');
	else
		$ipaddress = 'UNKNOWN';

	return $ipaddress;
}
?>
