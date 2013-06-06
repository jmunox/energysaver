<?php
require_once('mobile_device_detect.php');
mobile_device_detect(true,true,true,true,true,true,true,'www.actlab.ele.tue.nl/~jmunoz/island.php', false);

?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
		<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
		<title>EcoIsland</title>
		<script src="js/script.js"></script>
		<!--[if lt IE 9]>
		<script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script>
		<![endif]-->

	</head>
<body>
<div id="container">
<div id="logoT"><img src="img/tue.png"></div>

<div id="iframe">
<iframe src="www.actlab.ele.tue.nl/~jmunoz/island.php">
  <p>Your browser does not support iframes.</p>
</iframe>
</div>
<div id="footer">...</div>
</div>
</body>
</html>