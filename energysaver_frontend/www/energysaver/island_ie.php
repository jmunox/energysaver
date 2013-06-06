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
	//include("login.php");
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
					$transaction = $servicefacade->getCurrentAgenda($user->getUserId());
		
					if(java_values($transaction->containsKey("exception"))){
						$errormessage = "<p><span class=\"errormessage\">".$transaction->get("exception.message")."</span></p><br/>";
						include("error.php");
					}else{
						if(java_values($transaction->containsKey("agenda"))){
							$instructions = "We are sorry. You have not been assigned to a group yet.<br/><br/>You will be added to one soon so that you can start playing and saving energy.<br/>Please come back later.";
							
							$transaction = $servicefacade->getCurrentFeedBack($user->getUserId());
							
							$boy_normal = 'images/Small_Boy_Normal.png';
							$boy_king = 'images/Small_Boy_King.png';
							$boy_donkey = 'images/Small_Boy_Donkey.png';
							$girl_normal = 'images/Small_Girl_Normal.png';
							$girl_queen = 'images/Small_Girl_Queen.png';
							$girl_donkey = 'images/Small_Girl_Donkey.png';
								
							$avatarname = java_values($avatar->getAvatarName());
							
							$gender = java_values($avatar->getGender()->toString());
								
							$mrnobody_name = " ";
							$mrnobody_image = "images/Small_Mr_Nobody.png";
							$scaleImage_Path = "images/Small_Scale.png";
							
							$avatarname2 = $mrnobody_name;
							$avatarImage2 = $mrnobody_image;
							$avatarname3 = $mrnobody_name;
							$avatarImage3 = $mrnobody_image;
							$avatarnamge4 = $mrnobody_name;
							$avatarImage4 = $mrnobody_image;
							$scaleImage = $mrnobody_image;
							
							$king = "";
							$donkey = "";
							$top_water4_posy = 175;
							$bottom_water4_posy = 400;
							$normal_water4_posy = 325;
							$waterlevel = $normal_water4_posy;
							$goal = "";
							$feedback = "";
							if(java_values($transaction->containsKey("team"))){
								$team = $transaction->get("team");
								$goal = java_values($team->getGoal()->toString());
								$feedback = java_values($team->getFeedback()->toString());
							
								if($goal=="individual"){
									$instructions = "Your goal, as a player, is to make your personal energy consumption as low as possible.<br/><br/>";
								}else if($goal=="group"){
									$instructions = "Your goal, as member of your team, is to make the energy consumption of your group as low as possible.<br/><br/>";
								}else if($goal=="simple"){
									$instructions = "Your goal is to save as much energy as possible.<br/><br/>";
								}else if($goal=="none"){
									header("location:logout.php");
									die();
								}
								$instructions = "Your goal is to save as much energy as possible.<br/><br/>";
								if($feedback == "individual_comparison"){
									$instructions = $instructions."In the image, you can see your own consumption and the consumption of the other group members.<br/><br/>";
									$instructions = $instructions."The crown is given to the person who consumed the least energy of the group. The pair of donkey ears are given to the person who consumed the most energy of the group.<br/><br/>";
									if(java_values($transaction->containsKey("king"))){
										$king = java_values($transaction->get("king"));
									}
									if(java_values($transaction->containsKey("donkey"))){
										$donkey = java_values($transaction->get("donkey"));
									}
									$waterlevel = $normal_water4_posy;
								}else if ($feedback == "group"){
									$instructions = $instructions."In the image, you can see how much energy your group consumes and how this affects the water level around the island you are living on.<br/><br/>";
									$instructions = $instructions."The level of the water rises or falls according to the overall consumption of the group. The level of the water will rise if the whole group is consuming energy.<br/><br/>";
																		
									$waterlevel = 0;
									if(java_values($transaction->containsKey("water")))
										$waterlevel = java_values($transaction->get("water"));
									
									$scaleImage = $scaleImage_Path;
									
									$waterlevel = $bottom_water4_posy - ($waterlevel * 112.5);
									if($waterlevel>$bottom_water4_posy){
										$waterlevel = $bottom_water4_posy;
									}else if ($waterlevel<$top_water4_posy){
										$waterlevel = $top_water4_posy;
									}
								}else if($feedback == "mixed"){
									$instructions = $instructions."In the image, you can see how much energy you and your group consume and how this affects the water level around the island you are living on.<br/><br/>";
									$instructions = $instructions."The crown is given to the person who consumed the least energy of the group. The pair of donkey ears are given to the person who consumed the most energy of the group.<br/><br/>";
									if(java_values($transaction->containsKey("king"))){
										$king = java_values($transaction->get("king"));
									}
									if(java_values($transaction->containsKey("donkey"))){
										$donkey = java_values($transaction->get("donkey"));
									}
									$instructions = $instructions."The level of the water rises or falls according to the overall consumption of the group. The level of the water will rise if the whole group is consuming energy.<br/><br/>";
									
									$waterlevel = 0;
									if(java_values($transaction->containsKey("water")))
										$waterlevel = java_values($transaction->get("water"));
									
									$scaleImage = $scaleImage_Path;
									
									$waterlevel = $bottom_water4_posy - ($waterlevel * 112.5);
										if($waterlevel>$bottom_water4_posy){
											$waterlevel = $bottom_water4_posy;
										}else if ($waterlevel<$top_water4_posy){
											$waterlevel = $top_water4_posy;
										}
								}else if ($feedback == "none"){
									$instructions = $instructions."In the image, you can see all the members of your group.<br/><br/>";
								}
								
								
								if(java_values($transaction->containsKey("title"))){
									$title = java_values($transaction->get("title"));
									
									if ($feedback == "none"){
										$title = " ";
									}
									$instructions = $instructions."<br/><br/>".$title;
								}
							}
								
							
								
							if($gender=="male"){
								$avatarImage = $boy_normal;
								if($king=="avatar"){
									$avatarImage = $boy_king;
								}
								if($donkey=="avatar"){
									$avatarImage = $boy_donkey;
								}
							
							}else{
								$avatarImage = $girl_normal;
								if($king=="avatar"){
									$avatarImage = $girl_queen;
								}
								if($donkey=="avatar"){
									$avatarImage = $girl_donkey;
								}
							}
							
							
							if(java_values($transaction->containsKey("avatar2"))){
								$avatar2 = $transaction->get("avatar2");
								$avatarname2 = java_values($avatar2->getAvatarName());
								$gender2 = java_values($avatar2->getGender()->toString());
								if($gender2=="male"){
									$avatarImage2 = $boy_normal;
									if($king=="avatar2"){
										$avatarImage2 = $boy_king;
									}
									if($donkey=="avatar2"){
										$avatarImage2 = $boy_donkey;
									}
							
								}else{
									$avatarImage2 = $girl_normal;
									if($king=="avatar2"){
										$avatarImage2 = $girl_queen;
									}
									if($donkey=="avatar2"){
										$avatarImage2 = $girl_donkey;
									}
								}
							}
							if(java_values($transaction->containsKey("avatar3"))){
								$avatar3 = $transaction->get("avatar3");
								$avatarname3 = java_values($avatar3->getAvatarName());
								$gender3 = java_values($avatar3->getGender()->toString());
								if($gender3=="male"){
									$avatarImage3 = $boy_normal;
									if($king=="avatar3"){
										$avatarImage3 = $boy_king;
									}
									if($donkey=="avatar3"){
										$avatarImage3 = $boy_donkey;
									}
										
								}else{
									$avatarImage3 = $girl_normal;
									if($king=="avatar3"){
										$avatarImage3 = $girl_queen;
									}
									if($donkey=="avatar3"){
										$avatarImage3 = $girl_donkey;
									}
								}
							}
							if(java_values($transaction->containsKey("avatar4"))){
								$avatar4 = $transaction->get("avatar4");
								$avatarname4 = java_values($avatar4->getAvatarName());
								$gender4 = java_values($avatar4->getGender()->toString());
								if($gender4=="male"){
									$avatarImage4 = $boy_normal;
									if($king=="avatar4"){
										$avatarImage4 = $boy_king;
									}
									if($donkey=="avatar4"){
										$avatarImage4 = $boy_donkey;
									}
										
								}else{
									$avatarImage4 = $girl_normal;
									if($king=="avatar4"){
										$avatarImage4 = $girl_queen;
									}
									if($donkey=="avatar2"){
										$avatarImage4 = $girl_donkey;
									}
								}
							}
								
								
						
	
?>		
<!DOCTYPE html>
<html>
 <head>

  <meta http-equiv="Content-type" content="text/html;charset=UTF-8"/>
  <meta name="generator" content="1.1.960.120"/>
  <title>Energy-Saver - The Island</title>
  <link rel="shortcut icon" href="images/favicon.png" type="image/png" />
  <!-- CSS -->
  <link rel="stylesheet" type="text/css" href="css/site_global.css?102093719"/>
  <link rel="stylesheet" type="text/css" href="css/master_a-master.css?4277846012"/>
  <link rel="stylesheet" type="text/css" href="css/theisland.css?447132735"/>
  <!-- Other scripts -->
    <!-- Other scripts -->
  <!--[if IE]><script type="text/javascript" src="scripts/extra/excanvas.js"></script><![endif]-->
	
	<script src="scripts/extra/canvas.js">//</script>
	<script src="scripts/extra/optimer-normal-normal.js">//</script>
  <script type="text/javascript">
   document.documentElement.className = 'js';
</script>
   </head>
 <body onload="draw();">
  <?php echo $errormessage; ?>	
  <div class="clearfix" id="page"><!-- column -->
   <div class="clearfix colelem" id="master-header"><!-- group -->
    <div class="rounded-corners clearfix grpelem" id="u85"><!-- group -->
     <a class="nonblock nontext grpelem" id="u79" href="index.php"><!-- rasterized frame --><img id="u79_img" src="images/Logo_Transparent_Web.png" alt="" width="160" height="77"/></a>
     <p class="fltrt"><span class="logout"><?php echo $semail?></span></p><br/><p class="fltrt"><a class="logout" href="logout.php" >Log out</a></p><br/><br/>
    </div>
   </div>
   <div class="clearfix colelem" id="pu78"><!-- group -->
    <div class="grpelem" id="u78"><!-- simple frame -->
     <div class="_u78 f3s_top"><span class="nav_title"><?php echo date('D, d M Y');?></span></div>
     <div class="_u78 f3s_mid">
     <canvas id="canvas" width="577" height="430">  
	</canvas></div>
     <div class="_u78 f3s_bot"></div>
    </div>
    <div class="rounded-corners grpelem" id="u105">
        <ul class="nav">
			<li><br/></li>
			<li class="nav_title">Dear <?php echo $avatarname;?>:</li>
			<li><br/></li>
			<li class="nav_title"><?php echo $instructions;?></li>
			<li><br/></li>
		</ul>
	<span class="rounded-corners clearfix colelem" id="u119b"><!-- group -->
       <a id="gonext" class="fltrt" style="" href="instructions.php">&lt;&lt; Back to Instructions</a>
      </span>
    <div id="instructions"></div>
    <!-- simple frame --></div>
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
			<a  href="http://www.greenerbuildings.eu/"><img id="u91_img" src="images/GreenerBuildings_Logo_1.png" alt="GreenerBuildings" width="60px" height="62px"/></a>
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

     <script type="text/javascript">
						
		function draw() {  
        var ctx = document.getElementById('canvas').getContext('2d');  
		    ctx.mozImageSmoothingEnabled = false;  
		var img = new Image();
		
		//var scalefactorX=0.75;
		//var scalefactorY=0.7;
		//if(window.innerWidth<960) {
		//scalefactor = 0.6;
		//}
		var xd = 15;
		var dy = 0;
		var usr1 = new Image();
		usr1_posx = 130 -xd ;
		usr1_posy = 200 ;
		var usr1_name = "<?php echo $avatarname;?>";

		
		var usr2 = new Image();
		usr2_posx = 220 -xd;
		usr2_posy = 200 ;
		var usr2_name = "<?php echo $avatarname2;?>";
		
		var usr3 = new Image();
		usr3_posx = 310 -xd;
		usr3_posy = 200 ;
		var usr3_name = "<?php echo $avatarname3;?>";
		
		var usr4 = new Image();
		usr4_posx = 400 -xd;
		usr4_posy = 200 ;
		var usr4_name = "<?php echo $avatarname4;?>";

		var scaleImg = new Image();
		scaleImg_posx = 520;
		scaleImg_posy = 243;
		
		var water = new Image();
		water4_posx = 0;
		water4_posy = <?php echo $waterlevel;?>;
		
        img.onload = function(){  
		//ctx.scale(scalefactorX, scalefactorY);
            ctx.drawImage(img,0,0, 577, 430);
			ctx.drawImage(usr1,  
                0,0,70,120,usr1_posx,usr1_posy,70,120);
			ctx.drawImage(usr2,  
                0,0,70, 120,usr2_posx,usr2_posy,70,120);
			ctx.drawImage(usr3,  
                0,0,70,120,usr3_posx,usr3_posy,70,120);
			ctx.drawImage(usr4,  
                0,0,70,120,usr4_posx,usr4_posy,70,120);

		ctx.fillStyle = "#000";
        ctx.strokeStyle = "#f00";
        ctx.font = "13px Optimer";
		
		 
    	ctx.fillText(usr1_name, 120, 150);
		ctx.fillText(usr2_name, 215 , 150);
		ctx.fillText(usr3_name, 315 , 150);
		ctx.fillText(usr4_name, 420 , 150);


				//ctx.fillStyle = "rgb(66,65,60)";  
        //ctx.fillRect (0, 600, 1000, 1500); 
		//ctx.fillRect (750, 0, 1000, 1500);   
		
        			ctx.drawImage(water,  
                0,0,1000,300,water4_posx,water4_posy,1000,300);
				
        }; 
		
		
		//var boy_normal = 'images/Small_Boy_Normal.png';

		//var girl_normal = 'images/Small_Girl_Normal.png';
		
		var boy_normal = '';

		var girl_normal = '';
		
		var island ='images/Small-Island.png';
		
		
		water.src = 'images/Small_Water.png';
		
		img.src = 'images/Small-Island.png';
        usr1.src = '<?php echo $avatarImage;?>'; 
		usr2.src = '<?php echo $avatarImage2;?>';
		usr3.src = '<?php echo $avatarImage3;?>';
		usr4.src = '<?php echo $avatarImage4;?>';
		scaleImg.src = '<?php echo $scaleImage;?>';
      }  
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
