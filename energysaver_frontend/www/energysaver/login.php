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


session_start(); //Start the session
if(!isset( $_SESSION['auth'] )){//If session not registered
	
?>
<!DOCTYPE html>
<html>
 <head>

  <meta http-equiv="Content-type" content="text/html;charset=UTF-8"/>
  <meta name="generator" content="1.1.960.120"/>
  <title>Energy-Saver - Login</title>
  <link rel="shortcut icon" href="images/favicon.png" type="image/png" />
  <!-- CSS -->
  <link rel="stylesheet" type="text/css" href="css/site_global.css?102093719"/>
  <link rel="stylesheet" type="text/css" href="css/master_a-master.css?4277846012"/>
  <link rel="stylesheet" type="text/css" href="css/index.css?21114377"/>
  <style type="text/css">

</style>
  <!-- Other scripts -->
  <script type="text/javascript">
   document.documentElement.className = 'js';
</script>
   </head>
 <body>
  <?php echo $errormessage; ?>		
  <div class="clearfix" id="page"><!-- column -->
   <div class="position_content" id="page_position_content">
    <div class="clearfix colelem" id="ppu104"><!-- group -->
     <div class="clearfix grpelem" id="pu104"><!-- column -->
      <div class="rounded-corners clearfix colelem" id="u104"><!-- group -->
       <div class="clearfix grpelem" id="u99-6"><!-- content -->
        <p id="u99-2">Encouraging energy preservation </p>
        <p id="u99-4">in office environments.</p>
       </div>
      </div>
      <div class="clearfix colelem" id="u103-a19"><!-- content -->
       <p id="u103-2"><span id="info_about"> We invite you to participate in a study called <b>Energy&#45;Saver Game</b>.</span><br/><br/></p>
       <p id="u103-4"><span id="info_about">The aim of this study is to investigate an innovative form to promote energy saving.</span></p>
       <p id="u103-5">&nbsp;</p>
       <p id="u103-7"><span id="info_about"><b>Energy&#45;Saver Game</b> is an interactive online application, which keeps track of the energy consumption, and challenges you to adopt new behaviors.</span></p>
       <p id="u103-4"><br/>This website works better on <b><i>Firefox</i></b>,<b><i> Chrome </i></b>or <b><i>Safari</i></b> Web Browsers.</p>
      </div>
      <div class="colelem" id="u112"><!-- image -->
       <img id="u112_img" src="images/water2.png" alt="" width="901" height="54"/>
      </div>
     </div>
    <div class="rounded-corners grpelem" id="u87"><!-- column -->
      <form id="loginForm" method="POST" action="checklogin.php">
			<ul class="nav">
			<li><br/></li>
			<li class="nav_title">Login to Energy&#45;Saver</li>
			<li><br/></li>
			<li>Email:<br/><input type="text" name="email" class="required email" size="25"/></li>
			<li>Password:<br/><input type="password" name="pass" class="required" minlength="6" size="25" /></li>
			<li><br/></li>
			<li><b><input id="login" type="submit" value="Login" /></b></li>
			<li><br/></li>
			<li><br/></li>
			<li><br/></li>
			<li><br/></li>
			<li><br/></li>
			<li>New to Energy&#45;Saver?</li>
			<li><br/></li>
			<li><b><a id="register" href="register.php" >Register here</a></b></li>
			</ul>
		</form>
		<!-- simple frame --></div>
     <div class="clearfix grpelem" id="master-header"><!-- group -->
      <div class="rounded-corners clearfix grpelem" id="u85"><!-- group -->
       <a class="nonblock nontext grpelem" id="u79" href="index.php"><!-- rasterized frame --><img id="u79_img" src="images/Logo_Transparent_Web.png" alt="" width="160" height="77"/></a>
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
			<a  href="http://www.greenerbuildings.eu/"><img id="u91_img" src="images/GreenerBuildings_Logo_1.png" alt="GreenerBuildings" width="60px" height="62px"/></a>      
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

<!--[if IE]><script type="text/javascript" src="scripts/extra/excanvas.js"></script><![endif]-->
<script src="scripts/extra/jquery.jqcanvas.js">//</script>
<script type="text/javascript" src="http://jzaefferer.github.com/jquery-validation/jquery.validate.js"></script>
            
<script>
$.validator.addMethod("plugrequired", $.validator.methods.required,
"Last 6 digits of the ID of your Plugwise Circle.");
$.validator.addClassRules("plugwise", { plugrequired: true, minlength: 6 });
  $(document).ready(function(){
    $("#loginForm").validate();
    $("#register_form").validate();
  });


  </script>
   </body>
</html> 

<?php

}
else{
	header("location:welcome.php");
}
?>