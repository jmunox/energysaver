<?php

session_start(); //Start the session
define(AUTH,$_SESSION['semail']); //Get the user name from the previously registered super global variable
if(!session_is_registered("auth")){ //If session not registered
	
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
      <div class="clearfix colelem" id="u103-9"><!-- content -->
       <p id="u103-2">We invite you to participate in a study called Energy&#45;Saver.</p>
       <p id="u103-4">The aim of this study is to investigate an innovative form to promote energy saving.</p>
       <p id="u103-5">&nbsp;</p>
       <p id="u103-7">Energy&#45;Saver Game is an interactive online application, which keeps track of the energy consumption, and challenges you to adopt new behaviors.</p>
      </div>
      <div class="colelem" id="u112"><!-- image -->
       <img id="u112_img" src="images/water2.png" alt="" width="712" height="54"/>
      </div>
     </div>
     <div class="clearfix grpelem" id="pu86"><!-- column -->
      <div class="rounded-corners colelem" id="u86">		
      <form id="loginForm" method="POST" action="checklogin">
		<ul class="nav">
			<li>Email: <input type="text" name="email" class="required email" /></li>
			<li>Password: <input type="password" name="pass" class="required" minlength="6" /></li>
			<li><b><input id="login" type="submit" value="Login" /></b></li>
		</ul>
		</form>  <!-- simple frame --></div>
      <div class="colelem" id="u87">
      <form id="register_form" name="register_form" method="POST" action="createuser">
			<ul class="nav" style="color: rgb(198, 198, 198);">
			<li><br/></li>
			<li style="color: rgb(255, 255, 255);">Register to Energy-Saver</li>
			<li><br/></li>
			<li>Email:</li><li><input type="text" name="email" class="required email" /></li>
			<li><br/></li>
			<li>Password:</li><li><input type="password" id="password" name="password" class="required" minlength="6" /></li>
			<li><br/></li>
			<li>Name of your Avatar:</li><li><input size="8" type="text" name="avatar" maxlength="8" class="required"/></li>
			<li><br/></li>
			<li>Plugwise Device Id:</li><li><input id="plugwise" size="6" type="text" name="plugid" maxlength="6" class="plugwise" /></li>
			<li><br/></li>
			<li>Gender:</li>
			<li><input type="radio" name="gender" value="male" class="required"/> Male &nbsp;&nbsp;
			<input type="radio" name="gender" value="female" class="required"/> Female</li>
			<li><br/></li>
			<li>Age: <input size="2" type="number" name="age" maxlength="2" class="required digits" /></li>
			<li><br/></li>
			<li><b><input id="register" type="submit" value="Register Me!" /></b></li>
			</ul>
		</form>
		<!-- simple frame --></div>
     </div>
     <div class="clearfix grpelem" id="master-header"><!-- group -->
      <div class="rounded-corners clearfix grpelem" id="u85"><!-- group -->
       <a class="nonblock nontext grpelem" id="u79" href="index"><!-- rasterized frame --><img id="u79_img" src="images/Logo_Transparent_Web.png" alt="" width="160" height="77"/></a>
      </div>
     </div>
    </div>
    <div class="clearfix colelem" id="master-footer"><!-- group -->
     <div class="clearfix grpelem" id="u88"><!-- group -->
      <div class="grpelem" id="u91"><!-- image -->
     <img id="u91_img" src="images/tue_logo.png" alt="" width="86" height="25"/>
      </div>
      <div class="clearfix grpelem" id="u89-6"><!-- content -->
       <p>2012 &#45; Jesus Muñoz&#45;Alcantara &#45; <a class="nonblock" href="mailto:j.munoz.alcantara@student.tue.nl" target="_blank">j.munoz.alcantara@student.tue.nl</a></p>
      </div>
      <div class="clearfix grpelem" id="about"><!-- about -->
       <p><a  href="about">About Energy&#45;Saver</a></p>
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
	header("location:island");
}
?>
