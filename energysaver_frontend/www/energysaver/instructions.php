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
if(!isset( $_SESSION['auth'] )){//If session not registered
	header("location:login.php"); // Redirect to login page
	//include("login");
} else {
		
		//header("location:login"); // Redirect to login page
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
					$avatar = $transaction->get("avatar");
					$transaction2 = $servicefacade->getCurrentAgenda($user->getUserId());
					$avatarname = java_values($avatar->getAvatarName());
					
					if(java_values($transaction2->containsKey("exception"))){
						$errormessage =
							"<p><span class=\"errormessage\">".$transaction->get("exception.message")."</span></p><br/>";
						include("error.php");
					}else{
						if(java_values($transaction2->containsKey("agenda"))){
							$instructions = "We are sorry. You have not been assigned to a group yet.<br/><br/>You will be added to one soon so that you can start playing and saving energy.<br/>Please come back later.<br/><br/>";
							$instructions = $instructions."These are some screenshots of the game.<br/><br/>";
							$goal = "";
							$feedback = "";
							$example_image = 'images/Island_basic.png';
							if(java_values($transaction->containsKey("team"))){
								$team = $transaction->get("team");
								$goal = java_values($team->getGoal()->toString());
								$feedback = java_values($team->getFeedback()->toString());
									
								if($goal=="individual"){
									$instructions = "Your goal, as a player, is to make your personal energy consumption as low as possible.<br/><br/>";
								}else if($goal=="group"){
									$instructions = "Your goal, as member of your team, is to make the energy consumption of your group as low as possible.<br/><br/>";
								}else if($goal=="simple"){
									$instructions = "Your goal in this game is to save as much energy as possible.<br/><br/>";
								}else if($goal=="none"){
									header("location:logout.php");
									die();
								}
								
								$instructions = "Your goal in this game is to save as much energy as possible.<br/><br/>";
								$instructions = $instructions."You have been assigned to a group with other people from the TU/e.<br/><br/>";
								if($feedback == "individual_comparison"){
									$instructions = $instructions."In the image, you will see your own consumption and the consumption of the other group members.<br/><br/>";
									$instructions = $instructions."The crown is given to the person who consumed the least energy of the group. The pair of donkey ears are given to the person who consumed the most energy of the group.<br/><br/>";
									$example_image = 'images/Island_king_donkey.png';
								}else if ($feedback == "group"){
									$instructions = $instructions."In the image, you will see how much energy your group consumes and how this affects the water level around the island you are living on.<br/><br/>";
									$instructions = $instructions."The level of the water rises or falls according to the overall consumption of the group. The level of the water will rise if the whole group is consuming energy.<br/><br/><br/><br/>";
									$example_image = 'images/Island_animation.gif';
								}else if ($feedback == "mixed"){
									$instructions = $instructions."In the image, you will see how much energy you and your group consume and how this affects the water level around the island you are living on.<br/><br/>";
									$instructions = $instructions."The crown is given to the person who consumed the least energy of the group. The pair of donkey ears are given to the person who consumed the most energy of the group.<br/><br/>";
									$instructions = $instructions."The level of the water rises or falls according to the overall consumption of the group. The level of the water will rise if the whole group is consuming energy.<br/><br/>";
									$example_image = 'images/Island_animation.gif';
								}else if ($feedback == "none"){
									$instructions = $instructions."In the image, you will see all the members of your group.<br/><br/>";
									$example_image = 'images/Island_basic.png';
								}
							}
?>
<!DOCTYPE html>
<html>
 <head>
  <meta http-equiv="Content-type" content="text/html;charset=UTF-8"/>
  <meta name="generator" content="1.1.960.120"/>
  <title>Energy-Saver - Instructions</title>
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
 		<br/><br/><br/><br/><br/><br/>
 		<br/><br/><br/><br/><br/><br/>
 		<br/><br/><br/><br/>
       </div>
      <div class="colelem" id="u112"><!-- image -->
       <img id="u112_img" src="images/water2.png" alt="" width="901" height="54"/>
      </div>
     </div>
      <div class="rounded-corners grpelem" id="example"><!-- content -->
      <img id="example_img" class="fltlft" src="<?php echo $example_image;?>" alt="" width="300" height="250"/>
      <div class="rounded-corners clearfix colelem" id="u119b"><!-- group -->
      <br/><span class="nav_title" >&nbsp;* This is a screenshot of the actual game.</span></div>
		<!-- simple frame --></div>
     
      <div class="rounded-corners grpelem" id="instructions_box"><!-- column -->
       	<p id="info_about">
      	<b><span class="nav_title" >Dear <?php echo $avatarname;?>:</span></b><br/><br/>
		<?php echo $instructions;?>
		<span class="rounded-corners clearfix colelem" id="u119b"><!-- group -->
       		<a id="gonext" class="fltrt" style="" href="island.php">Play&gt;&gt;</a>
      		</span>
		</p>
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
			<a  href="http://www.greenerbuildings.eu/"><img id="u91_img" src="images/GreenerBuildings_Logo_1.png" alt="G/reenerBuildings" width="60px" height="62px"/></a>
      </div>      
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
						}else{
							include("createagenda.php");
						}
					}
				}else{
					include("editavatar.php");
				}
			}
		}catch (JavaException $je) {
			$errormessage =$errormessage."<p><span class=\"errormessage\">Exception occured: ".$je."</span></p><br/>";
			include("error.php");
		}
	}
?>