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

	$semail = $_SESSION['semail'];
	$errormessage = "";
	try {
	
		$servicefacade = new java("org.moxhu.esavegame.business.facade.JavaBridgeBusinessFacade");
		$transaction = $servicefacade->getProfile($semail);
		if(java_values($transaction->containsKey("exception"))){
			$errormessage =
			"<p><span class=\"errormessage\">".$transaction->get("exception.message")."</span></p><br/>";
			include("error.php");
		}else{
			$user = $transaction->get("user");
			if(java_values($transaction->containsKey("avatar"))){
	
	?>
<!DOCTYPE html>
<html>
 <head>
  <meta http-equiv="Content-type" content="text/html;charset=UTF-8"/>
  <meta name="generator" content="1.1.960.120"/>
  <title>Energy-Saver - Welcome</title>
  <link rel="shortcut icon" href="images/favicon.png" type="image/png" />
  <!-- CSS -->
  <link rel="stylesheet" type="text/css" href="css/site_global.css?102093719"/>
  <link rel="stylesheet" type="text/css" href="css/master_a-master.css?4277846012"/>
  <link rel="stylesheet" type="text/css" href="css/index.css?21114377"/>
	<link href="css/jquery/jquery.ui.theme.css" rel="stylesheet" type="text/css">

  <!-- Other scripts -->
  <script type="text/javascript">
   document.documentElement.className = 'js';
</script>
   </head>
 <body>	
  <div class="clearfix" id="page"><!-- column -->
   <div class="position_content" id="page_position_content">
    <div class="clearfix colelem" id="ppu104"><!-- group -->
     <div class="clearfix grpelem" id="pu104"><!-- column -->
      <div class="clearfix colelem" id="u103-a19"><!-- content -->
 		<br/>
       </div>
      <div class="colelem" id="u112"><!-- image -->
      <br/><br/><br/><br/><br/><br/>
       <img id="u112_img" src="images/water2.png" alt="" width="901" height="54"/>
      </div>
     </div>
     
      <div class="rounded-corners grpelem" id="welcome"><!-- content -->
      <p id="info_about"> 
		<b>Energy&#45;Saver Game</b> implements an innovative way to promote energy saving. It keeps
track of your energy consumption and provides you with feedback on a daily basis.
       <br/>
       <br/>Daily feedback is based on your consumption of the previous day. On every Monday, you will see the accumulated results from the previous week.
       <br/>
       <br/>We adjust the feedback based on your own consumption, according to your equipment, amount of hours you spent in your office and to your own variations in energy use.
       <br/><br/><br/>
	</p>
	<p id="info_about"> 
       You have been randomly assigned to a group. The other members of your group are some of your colleagues from the TU/e who also work in the Potentiaal Building.
       <br/><br/> They are all randomly assigned, but it is possible that you might know someone within your group. It could be someone from your same office, floor or even someone that you might not know at all.
       </p>
       <br/>
       			<span class="rounded-corners clearfix colelem" id="u119b"><!-- group -->
       		<a id="gonext" class="fltrt" href="example.php">Next&gt;&gt;</a>
      		</span>
		<!-- simple frame --></div>
     
      <div class="rounded-corners grpelem" id="contact_box"><!-- column -->
			<ul class="nav_title">
			<li><br/></li>
			<li class="contact_info">Questions or Help?</li>
			<li><br/></li>
			<li class="small_text">If you have any question during the study please contact: <b>Jesus Muñoz-Alcantara</b></li>
			<li><a id="box_mailto" class="small_text nonblock" href="mailto:j.munoz.alcantara@student.tue.nl" target="_blank">j.munoz.alcantara@student.tue.nl</a></li>
			<li><br/></li>
			<li><br/></li>
			</ul>
		<!-- simple frame --></div>
     <div class="clearfix grpelem" id="master-header"><!-- group -->
      <div class="rounded-corners clearfix grpelem" id="u85"><!-- group -->
       <a class="nonblock nontext grpelem" id="u79" href="index.php"><!-- rasterized frame --><img id="u79_img" src="images/Logo_Transparent_Web.png" alt="" width="160" height="77"/></a>
       <p class="fltrt"><span class="logout"><?php echo $semail?></span></p><br/><p class="fltrt"><a class="logout" href="logout.php" >Log out</a></p><br/><br/>
      </div>
     </div>
    </div>
    <div class="clearfix colelem" id="master-footer"><!-- group -->
     <div class="clearfix grpelem" id="u88"><!-- group -->
      <div class="grpelem" id="u91"></div>
      <div class="clearfix grpelem" id="u89-6"><!-- content -->
       <p>2012 &#45; Jesus Muñoz&#45;Alcantara &#45; <a id="mailto" class="nonblock" href="mailto:j.munoz.alcantara@student.tue.nl" target="_blank">j.munoz.alcantara@student.tue.nl</a></p>
	<!-- about -->
       <a id="about" href="about.php">About Energy&#45;Saver</a>
       	<!--[if IE]><br/><br/><span id="webexplorer">* This website works better on <b><i>Firefox</i></b>,<b><i> Chrome </i></b>or <b><i>Safari</i></b> Web Browsers.</span><![endif]-->
      </div>
      <div class="clearfix grpelem" id="tue_logos">
	<!-- image -->
     		<a  href="http://www.tue.nl"><img id="u91_img" src="images/tue_logo.png" alt="" width="87" height="44"/></a>&nbsp;&nbsp;&nbsp;
			<a  href="http://www.actlab.ele.tue.nl/"><img id="u91_img" src="images/actlab_logo_transparent_tiny.png" alt="ACTlab" width="100px" height="52px"/></a>&nbsp;&nbsp;&nbsp;
			<a  href="http://hti.ieis.tue.nl/"><img id="u91_img" src="images/HTIlogo_small.jpg" alt="HTI Group" width="100px" height="52px"/></a>&nbsp;&nbsp;&nbsp;
			<a  href="http://www.greenerbuildings.eu/"><img id="u91_img" src="images/GreenerBuildings_Logo_1.png" alt="GreenerBuildings" width="60px" height="62px"/></a>      </div>      
     </div>
    </div>
   </div>
  </div>
  <!-- JS includes -->
  <script src="http://musecdn.businesscatalyst.com/scripts/1.1/jquery-1.7.min.js" type="text/javascript"></script>
  <script type="text/javascript">
   window.jQuery || document.write('\x3Cscript src="scripts/1.1/jquery-1.7.min.js" type="text/javascript">\x3C/script>');
</script>
  <script src="scripts/1.1/sprydomutils.js?4230755498" type="text/javascript"></script>
  <script src="scripts/1.1/museutils.js?3762396489" type="text/javascript"></script>
  <!-- Other scripts -->
  <script type="text/javascript">
   Muse.Utils.addSelectorFn('body', Muse.Utils.transformMarkupToFixBrowserProblemsPreInit);/* body */
Muse.Utils.addSelectorFn('a.nonblock', Muse.Utils.addHyperlinkAnchor); /* a.nonblock */
Muse.Utils.addSelectorFn('body', Muse.Utils.showWidgetsWhenReady);/* body */
Muse.Utils.addSelectorFn('body', Muse.Utils.transformMarkupToFixBrowserProblems);/* body */

</script>

   </body>
</html> 
<?php 


			} else
				header("location:editavatar.php");

		}
	}catch (JavaException $je) {
		$errormessage =$errormessage."<p><span class=\"errormessage\">Exception occured: ".$je."</span></p><br/>";
		include("error.php");
	}
} //If session not registered
else header("location:login.php");

?>