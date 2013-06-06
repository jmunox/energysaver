<?php require_once ("../JavaBridge/java/Java.inc");

session_start(); //Start the session
if(!isset( $_SESSION['auth'] )){//If session not registered
	header("location:login.php"); // Redirect to login.php page
	
}
else{
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
			$avatar = $transaction->get("avatar");
			$transaction = $servicefacade->getCurrentAgenda($user->getUserId());
			if(java_values($transaction->containsKey("exception"))){
				$errormessage =
			 "<p><span class=\"errormessage\">".$transaction->get("exception.message")."</span></p><br/>";
					include("error.php");
			}else{
				if(java_values($transaction->containsKey("agenda"))){
					header("location:instructions.php");
				}else{
					
		?>		
<!DOCTYPE html>
<html>
 <head>

  <meta http-equiv="Content-type" content="text/html;charset=UTF-8"/>
  <meta name="generator" content="1.1.960.120"/>
  <title>Energy-Saver - Create your Weekly Agenda</title>
  <link rel="shortcut icon" href="images/favicon.png" type="image/png" />
  <!-- CSS -->
  <link rel="stylesheet" type="text/css" href="css/site_global.css?102093719"/>
  <link rel="stylesheet" type="text/css" href="css/master_a-master.css?4277846012"/>
  <link rel="stylesheet" type="text/css" href="css/editprofile.css?470012253"/>
 <!--[if IE]>
 <link rel="stylesheet" type="text/css" href="css/editprofile_ie.css"/>
 <![endif]-->
 <link href="css/jquery/jquery.ui.slider.css" rel="stylesheet" type="text/css">
	<link href="css/jquery/jquery.ui.theme.css" rel="stylesheet" type="text/css">
  <!-- Other scripts -->
  <script type="text/javascript">
   document.documentElement.className = 'js';
</script>
   </head>
 <body>
  <?php echo $errormessage; ?>		
  <div class="clearfix" id="page"><!-- column -->
   <div class="position_content" id="page_position_content">
    <div class="clearfix colelem" id="pppu132"><!-- group -->
     <div class="clearfix grpelem" id="ppu132"><!-- column -->
<div class="clearfix colelem" id="u103-a19"><!-- content -->
      <p id="info_about">We calculate your energy consumption based on the amount of time that you spend at your office.<br/>
      Use the red scrolling bar to set the right amount of office hours spent.<br/><br/>
	</p>
</div>
      <div class="clearfix colelem" id="pu132"><!-- group -->
       <div class="rounded-corners grpelem" id="u132">
       <div class="agenda">
	<ul class="text_agenda">
	<li><b>WEEKLY AGENDA</b></li>
	<li>What is the total percentage of hours that you will be in the office during <b>all the current week?</b><br/></li>
	<li><br/></li>
	<li><br/></li>
	<li class="text_hours">100% corresponds to <b>40 hours a week</b> (8 hours a day)</li>
	</ul>
	</div>
	
       <!-- simple frame --></div>
       <div class="clearfix grpelem" id="pu124"><!-- column -->
        <div class="rounded-corners clearfix colelem" id="u124"><!-- group -->
         <div class="clearfix grpelem" id="u125-4"><!-- content -->
         <p><input type="text" id="amount" size="0" readonly="readonly" value="120"></p>
         </div>
        </div>
        <div class="rounded-corners clearfix colelem" id="u121"><!-- group -->
         <div class="clearfix grpelem" id="u122-4"><!-- content -->
          <p><input type="text" id="amounthours" size="0" readonly="readonly" value="122.4"></p>
         </div>
        </div>
       </div>
       <div class="clearfix grpelem" id="pu126-4"><!-- column -->
        <div class="clearfix colelem" id="u126-4"><!-- content -->
         <p>%</p>
        </div>
        <div class="clearfix colelem" id="u123-4"><!-- content -->
         <p>hours / (week)</p>
        </div>
       </div>
      </div>
      <div class="rounded-corners clearfix colelem" id="u119"><!-- group -->
       <div class="grpelem" id="u131"><div class="agendabar" id="slider-agenda" ></div>
       <!-- simple frame --></div>
       <form name="register_form" method="POST" action="saveagenda.php" onsubmit="return registerFormValidator()">
	<p>
	<input type="hidden" id="hours" name="hours" />
	<input id="saveagenda" class="fltrt" style="" type="submit" value="Save" />
	</p><br/>
	</form>
      </div>
      <div class="colelem" id="u128"><!-- image -->
      <br/>
       <img id="u128_img" src="images/water2.png" alt="" width="901" height="54"/>
      </div>
     </div>
     <div class="clearfix grpelem" id="master-header"><!-- group -->      
      <div class="rounded-corners clearfix grpelem" id="u85"><!-- group -->
      <a class="nonblock nontext MuseLinkActive grpelem" id="u79" href="index.php"><!-- rasterized frame --><img id="u79_img" src="images/Logo_Transparent_Web.png" alt="" width="160" height="77"/></a>
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
    <script src="scripts/extra/ui/jquery.ui.core.js"></script>
	<script src="scripts/extra/ui/jquery.ui.widget.js"></script>
	<script src="scripts/extra/ui/jquery.ui.mouse.js"></script>
	<script src="scripts/extra/ui/jquery.ui.slider.js"></script>

<script>
$(function() {
	$( "#slider-agenda" ).slider({
		range: "min",
		min: 0,
		max: 200,
		value: 100,
		slide: function( event, ui ) {
			$( "#amount" ).val( ui.value );
			$( "#amounthours" ).val( (ui.value*40)/100 );
			$( "#hours" ).val( ui.value );
		}
	});
	$( "#amount" ).val( $( "#slider-agenda" ).slider( "value" ) );
	$( "#amounthours" ).val( ( ( $( "#slider-agenda" ).slider( "value" ))*40)/100 );
	$( "#hours" ).val( $( "#slider-agenda" ).slider( "value" ) );
});	</script>
   </body>
</html>

<?php 
				}

			}
		}

	} catch (JavaException $je) {
	$errormessage =$errormessage."<p><span class=\"errormessage\">Exception occured: ".$je."</span></p><br/>";
	include("error.php");
	}

}

		?>
