var fldData;
var products = new Array();
var productsLeft = new Array();
var productsChosen = new Array();
var productsRemoved = new Array();
var headerData = new Array();
var preferences = new Array();
var weights = new Array();
var attributes = new Array();
var information = new Array();
var standardize = new Array();
var headerData = new Array();

var attribute = new Boolean();

var width = 1/3;

//-------------------------------------UITLEG INTRO---------------------------------------

var uitlegID;
function openUitleg(uitlegID){

switch(uitlegID){
	 case 'uitleg1':
		document.getElementById("uitleg1").style.display = "block";
		document.getElementById("uitleg2").style.display = "none";
		document.getElementById("uitleg3").style.display = "none";
		document.getElementById("uitleg4").style.display = "none";
		document.getElementById("uitleg5").style.display = "none";
		document.getElementById("menu_actief").style.display = "none";
		document.getElementById("menu_inactief").style.display = "block";
    break;
    case 'uitleg2':
		document.getElementById("uitleg2").style.display = "block";
		document.getElementById("uitleg1").style.display = "none";
		document.getElementById("uitleg3").style.display = "none";
		document.getElementById("uitleg4").style.display = "none";
		document.getElementById("uitleg5").style.display = "none";
		document.getElementById("menu_actief").style.display = "none";
		document.getElementById("menu_inactief").style.display = "block";
    break;
    case 'uitleg3':
		document.getElementById("uitleg3").style.display = "block";
		document.getElementById("uitleg1").style.display = "none";
		document.getElementById("uitleg2").style.display = "none";
		document.getElementById("uitleg4").style.display = "none";
		document.getElementById("uitleg5").style.display = "none";
		document.getElementById("menu_actief").style.display = "none";
		document.getElementById("menu_inactief").style.display = "block";
    break;
    case 'uitleg4':
		document.getElementById("uitleg4").style.display = "block";
		document.getElementById("uitleg1").style.display = "none";
		document.getElementById("uitleg2").style.display = "none";
		document.getElementById("uitleg3").style.display = "none";
		document.getElementById("uitleg5").style.display = "none";
		document.getElementById("menu_actief").style.display = "none";
		document.getElementById("menu_inactief").style.display = "block";
    break;
    case 'uitleg5':
		document.getElementById("uitleg5").style.display = "block";
		document.getElementById("uitleg1").style.display = "none";
		document.getElementById("uitleg2").style.display = "none";
		document.getElementById("uitleg3").style.display = "none";
		document.getElementById("uitleg4").style.display = "none";
		document.getElementById("menu_actief").style.display = "none";
		document.getElementById("menu_inactief").style.display = "block";
    break;
	 case 'uitlegtotaal':
		document.getElementById("uitlegtotaal").style.display = "block";
		document.getElementById("tab1").style.display = "none";
		document.getElementById("icon1").innerHTML="<img src=\"img/icon1.png\">";
		document.getElementById("stap1").className="menu_item";
		document.getElementById("menu_actief").style.display = "block";
		document.getElementById("menu_inactief").style.display = "none";
    break;
}
}
//-------------------------------------TABMENU---------------------------------------

var namee;
function change(namee){
timefunction('tab','tab',namee);
//optional Javascript code below - To change color of clicked tab
var livetab, i;
switch(namee){
    case 'tab1':
        livetab='stap1';
		document.getElementById("tab1").style.display = "block";
		document.getElementById("tab2").style.display = "none";
		document.getElementById("tab3").style.display = "none";
		document.getElementById("icon1").innerHTML="<img src=\"img/icon1_active.png\">";
		document.getElementById("icon2").innerHTML="<img src=\"img/icon2.png\">";
		document.getElementById("icon3").innerHTML="<img src=\"img/icon3.png\">";
		for(i=1;i<=5;i++){
    		document.getElementById("uitleg"+i).style.display = "none";
		}
		document.getElementById("uitlegtotaal").style.display = "none";
		document.getElementById("logo").style.display = "none";
		document.getElementById("uitlegtotaal").style.display = "none";
		document.getElementById("menu_actief").style.display = "block";
		document.getElementById("menu_inactief").style.display = "none";
    break;
    case 'tab2':
        livetab='stap2';
		document.getElementById("tab2").style.display = "block";
		document.getElementById("tab1").style.display = "none";
		document.getElementById("tab3").style.display = "none";
		document.getElementById("icon2").innerHTML="<img src=\"img/icon2_active.png\">";
		document.getElementById("icon1").innerHTML="<img src=\"img/icon1.png\">";
		document.getElementById("icon3").innerHTML="<img src=\"img/icon3.png\">";
		for(i=1;i<=5;i++){
    		document.getElementById("uitleg"+i).style.display = "none";
		}
		document.getElementById("logo").style.display = "none";
		document.getElementById("uitlegtotaal").style.display = "none";
		document.getElementById("menu_actief").style.display = "block";
		document.getElementById("menu_inactief").style.display = "none";
    break;
    case 'tab3':
        livetab='stap3';
		document.getElementById("tab3").style.display = "block";
		document.getElementById("tab1").style.display = "none";
		document.getElementById("tab2").style.display = "none";
		document.getElementById("icon3").innerHTML="<img src=\"img/icon3_active.png\">";
		document.getElementById("icon1").innerHTML="<img src=\"img/icon1.png\">";
		document.getElementById("icon2").innerHTML="<img src=\"img/icon2.png\">";
		for(i=1;i<=5;i++){
    		document.getElementById("uitleg"+i).style.display = "none";
		}
		document.getElementById("logo").style.display = "none";
		document.getElementById("uitlegtotaal").style.display = "none";
		document.getElementById("menu_actief").style.display = "block";
		document.getElementById("menu_inactief").style.display = "none";
    break;
}
for(i=1;i<=3;i++){
    document.getElementById("stap"+i).className="menu_item";
}
document.getElementById(livetab).className="active"; 
}
//-------------------------------------CHOICES en REMOVED---------------------------------------
var choRem;
function switchTo(choRem){

//optional Javascript code below - To change color of clicked tab
var livetab;
switch(choRem){
    case 'choices':
		document.getElementById("choices").style.display = "block";
		document.getElementById("removed").style.display = "none";
		document.getElementById('btn_view_ch').className = "btn_view_active";
		document.getElementById('btn_view_re').className = "";
    break;
    case 'removed':
		document.getElementById("choices").style.display = "none";
		document.getElementById("removed").style.display = "block";
		document.getElementById('btn_view_re').className = "btn_view_active";
		document.getElementById('btn_view_ch').className = "";
    break;
}	
}
//------------------------------------ALLEEN VOOR HET OPSTARTEN-------------------------
//binnenhalen van de product informatie in products.csv
function init(system) {
	if(system=="attribute"){
		this.attribute = true;
	}else{
		this.attribute = false;
	}
	
	url="products.csv";
	target = "announceContent";
	if (window.XMLHttpRequest) {
		req = new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		req = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	if (req != undefined) {
		req.onreadystatechange = function() {
		if (req.readyState == 4) { // only if req is "loaded"
			if (req.status == 200) { // only if "OK"
				setAttributes();
				setStandardize();
				setInitialView();
				createArray(req.responseText);
				setPreferences();
				balkje();
				setWeights();
				
			}
		}
	};
    req.open("GET", url, true);
    req.send("");
  }
  
}  

//welke attributen worden gebruikt in het systeem
function setAttributes(){
	if(this.attribute){
		this.attributes.push('effortonce');
		this.attributes.push('effortcont');
		this.attributes.push('savingsKWH');
		this.attributes.push('comfort');
	}else{
		this.attributes.push('visible');
		this.attributes.push('savingall');		
	
	}
}

function setInitialView(){
	document.getElementById('btn_view_be').className = "btn_view_active";
	this.information.push('realsavingsE');
	this.information.push('savingsKWH');
	document.getElementById('title1').innerHTML = "&#8364;/Jaar";
	document.getElementById('title1').className = "title_euro";
	document.getElementById('title2').innerHTML = "kWu/Jaar";
	document.getElementById('title2').className = "title_kwu";	
} 

function setPreferences(){
	if(this.attribute){
		$value = 0.5;
	}else{
		$value = 0;
	}
	
	for(i=0;i<this.attributes.length;i++){
		this.preferences[this.attributes[i]]=$value;
	}
}

//het uitlezen van de informatie die is verzameld vanuit init()
function createArray(allText){
	//elke regel worden een aparte regel in een array
	allTextLines = allText.split('!');
	
	//regel 0 is de header data en deze apart opgeslagen
	this.headerData = allTextLines[0].split(';');
	// document.getElementById('debug').innerHTML = headerData;
	
	//elke regel tekst wordt doorlopen
	for (var i = 1; i < allTextLines.length; i++) {
		//voor elk products wordt een apart array aangemaakt
		this.products[i-1] = new Array();
		//elk veld van regel i word opgeslagen in een array
		var fldData = allTextLines[i].split(';');

		for (var j = 0; j < fldData.length; j++) {
			//per product i wordt aan headerblok j de bijbehorende data gehangen
			this.products[i-1][headerData[j]] = fldData[j];
		}
	
	}
	
	//zet elk product op keuzecategorie 0 (niks mee gedaan)
	for (var j=0;j<this.products.length;j++){
		this.products[j]['choice'] = 0;
	}
}

//------------------------------------TERUGKOMENDE FUNCTIES-------------------------
//bereken de weights aan de hand van de preferences
function setWeights(){
	if(this.attribute){
		var prefSum=0;
		//de som van alle preference wordt bepaald
		for(i=0;i<this.attributes.length;i++){
			prefSum+=this.preferences[this.attributes[i]];
		}
		//Als het nul is dan wordt elk attribute op gewicht 0.25 gezet
		if(prefSum==0){
			for(i=0;i<this.attributes.length;i++){
				this.weights[this.attributes[i]]=0.25;
			}
		//anders wordt de voorkeur gedeeld door de som om het gewich te bepalen
		}else{
			for(i=0;i<this.attributes.length;i++){
				this.weights[this.attributes[i]]= this.preferences[this.attributes[i]]/prefSum;
			}
		}
		
		//Output test of de waardes kloppen, kan uiteindelijk weg
		var str='';
		for(i=0;i<this.attributes.length;i++){
			str+= this.attributes[i] +' = '+ this.weights[this.attributes[i]];
			str+= '<br />';
		}
	}	
	calculateUtilities();
}
	
function calculateUtilities(){
	//per product wordt voor elk attribute dat er toe doet the utility berekend
	if(this.attribute){
		for(var i=0; i<this.products.length; i++){
			var sum=0;
			for (var j=0; j<this.attributes.length; j++){
				//de utility van elk attribuut wordt uitgerekend door de standardizatie * voorkeur * attribute waarde van het product
				var attrValue = parseFloat(this.products[i][this.attributes[j]]);
				sum += this.standardize[this.attributes[j]]*this.weights[this.attributes[j]]*attrValue;
							
			}

			this.products[i]['utility'] = sum;
		}	
		
	}else{
		for(var i=0; i<this.products.length; i++){
			var sum=0;
			for (var j=0; j<this.attributes.length; j++){
				//de afstand tot elk product wordt bepaald door verschil gekwadrateerd, optellen en uiteindelijk daarvan de wortel (Eucledian distance)
				var attrValue = parseFloat(this.products[i][this.attributes[j]]);
				//sum =difference;
				sum += Math.pow(this.preferences[this.attributes[j]]-attrValue,2);
				//this.products[i][this.attributes[j]],2);

			}

			this.products[i]['utility'] = Math.sqrt(sum);
		}	
	}
	
	classifyProducts();	
}

// this outputs the arrays into form fields and submits the page

function outputSubmit(){
chosenOut = "";
removedOut = "";
chosenArray = this.headerData.join(";")+"!\r\n";

for (var j=0; j<this.productsChosen.length; j++){
chosenOut += this.productsChosen[j]['id']+"|";
for (var i=0; i<this.headerData.length-1;i++) {
chosenArray += this.productsChosen[j][headerData[i]]+";";
}
chosenArray +="!\r\n";
}
//alert(chosenArray);

for (var j=0; j<this.productsRemoved.length; j++){
removedOut += this.productsRemoved[j]['id']+"|";
}

document.forms[mlweb_fname].chosenArr.value = chosenArray;
document.forms[mlweb_fname].chosenitems.value = chosenOut;
document.forms[mlweb_fname].removeditems.value = removedOut;
timefunction('submit','submit','succeeded');
document.forms[mlweb_fname].submit();
}
//Deel de producten in in de verschillende arrays voor de verschillende categorieen
function classifyProducts(){	
	this.productsLeft = new Array();
	this.productsChosen = new Array();
	this.productsRemoved = new Array();
	
	for (var j=0; j<this.products.length; j++){
		if(this.products[j]['choice']==0){
			this.productsLeft.push(this.products[j]);
		}else if(this.products[j]['choice']==1){
			this.productsChosen.push(this.products[j]);
		}else if(this.products[j]['choice']==-1){
			this.productsRemoved.push(this.products[j]);
		}
	}
	
	//Bij attribute gebaseerd geldt hogere waarde is beter
	if(this.attribute){
		this.productsLeft.sort(sortUtility);
	//Bij needs-based  geldt een hogere waarde is slechter
	}else{
		this.productsLeft.sort(sortDistance);
	}
		
	this.productsChosen.sort(sortName);
	this.productsRemoved.sort(sortName);

	showRecommendations();
	showChoices();
	showRemoved();	
}	
//toon de aanbevelingen door het opbouwen van de html code
function showRecommendations(){	
	htmlRec = "<table cellspacing=\"0\" cellpadding=\"0\">";
	for(var i=0;i<5;i++){
		var removeID = (this.productsLeft[i]['id'])/1;
		htmlRec += "<tr id=\"animatie"+removeID+"\"><td class=\"choose\"><div onClick=\"setToGreen("+this.productsLeft[i]['id']+");\" class=\"btn_choose\"><img src=\"img/choose.png\"></div></td>";
		htmlRec +=	"<td class=\"remove\"><div onClick=\"setToRed("+this.productsLeft[i]['id']+")\" class=\"btn_choose\"><img src=\"img/remove.png\"></div></td>";
		htmlRec += "<td class=\"product\" id=\"productnaam"+removeID+"\">" + this.productsLeft[i]['name'] + "</td>";
		//htmlRec += "<td>" + this.productsLeft[i]['utility'] + "</td>";
		for(var j=0;j<this.information.length;j++){
			//document.getElementById('debug').innerHTML = this.information[j];
			if (this.information[j]=="savingsKWH") {
				htmlRec+="<td class=\" "+ this.information[j] + "\">" + Math.round(this.productsLeft[i][this.information[j]]) + " <span class=\"small\">kWu</span></td>";
			}
			if (this.information[j]=="realsavingsE" || this.information[j]=="costonce") {
				htmlRec+="<td class=\""+ this.information[j] + "\">&#8364; " + Math.round(this.productsLeft[i][this.information[j]]) + "</td>";
			}
			if (this.information[j]=="effortonce" || this.information[j]=="effortcont") {
				var margin = 25-this.productsLeft[i][this.information[j]]/2;
				var codeEffort = "<div id=\"bg_effortBar\"><div class=\"effortBar\" style=\"height:"+this.productsLeft[i][this.information[j]]/2+"px; margin-top:"+margin+"px; \"></div></div>";
				htmlRec+="<td class=\""+ this.information[j] + "\">" + codeEffort + "</td>";
			}
			if (this.information[j]=="comfort" || this.information[j]=="ecoweight") {
				if (this.productsLeft[i][this.information[j]] > 0) {
					var codeComfeco = "<div id=\"bg_comfecoBarP\"><p>+</p><div class=\"comfecoBarP\" style=\"width:"+this.productsLeft[i][this.information[j]]*4+"%;\"><p>&nbsp;</p></div></div><div id=\"bg_comfecoBarN\"><p>-</p></div>";
				}
				if (this.productsLeft[i][this.information[j]] > 15) {
					var codeComfeco = "<div id=\"bg_comfecoBarP\"><div class=\"comfecoBarP\" style=\"width:"+this.productsLeft[i][this.information[j]]*4+"%;\"><p>&nbsp;</p></div></div><div id=\"bg_comfecoBarN\"><p>-</p></div>";
				}
				if (this.productsLeft[i][this.information[j]] < 0) {
					var widthN = this.productsLeft[i][this.information[j]] * -4;
					var codeComfeco = "<div id=\"bg_comfecoBarP\"><p>+</p></div><div id=\"bg_comfecoBarN\"><div class=\"comfecoBarN\" style=\"width:"+widthN+"%;\"><p>&nbsp;</p></div><p>-</p></div>";
				}
				if (this.productsLeft[i][this.information[j]] < -15) {
					var widthN = this.productsLeft[i][this.information[j]] * -4;
					var codeComfeco = "<div id=\"bg_comfecoBarP\"><p>+</p></div><div id=\"bg_comfecoBarN\"><div class=\"comfecoBarN\" style=\"width:"+widthN+"%;\"><p>&nbsp;</p></div></div>";
				}
				if (this.productsLeft[i][this.information[j]] == 0) {
					var codeComfeco = "<div id=\"bg_comfecoBarP\"><p>+</p></div><div id=\"bg_comfecoBarN\"><p>-</p></div>";
				}
				htmlRec+="<td class=\""+ this.information[j] + "\">" + codeComfeco + "</td>";
			}
		}
		
		htmlRec += "</tr>";
	}
	htmlRec +="</table>";
	document.getElementById('recommendations').innerHTML = htmlRec;
}

function showChoices(){
	var somKWH = 0;
	var somE = 0;
	htmlCho = "<table>";
	for(var i=0;i<this.productsChosen.length;i++){
		htmlCho += "<tr><td class=\"product\">" + this.productsChosen[i]['name'] + "</td><td class=\"savings\">" + Math.round(this.productsChosen[i]['savingsKWH']) + " kWu</td><td class=\"realsavings\">&#8364; " + Math.round(this.productsChosen[i]['realsavingsE']) + "</td><td class=\"restore\"><div onClick=\"restore("+this.productsChosen[i]['id']+");\" class=\"btn_restore\"><img src=\"img/restore.png\"></div></td><tr>";
		somKWH += Math.round(productsChosen[i]['savingsKWH']);
		somE += Math.round(productsChosen[i]['realsavingsE']);
	}
	htmlCho +="<tr class=\"bold\"><td>Totaal:</td><td>" + somKWH + " kWu</td><td>&#8364; " + somE + "</td></tr></table>";
	document.getElementById('choices').innerHTML = htmlCho;
}
function showRemoved(){
	htmlRem = "<table>";
	for(var i=0;i<this.productsRemoved.length;i++){
			htmlRem += "<tr><td>" + this.productsRemoved[i]['name'] + "</td>";
			htmlRem += "<td class=\"restore\"><div onClick=\"restore("+this.productsRemoved[i]['id']+");\" class=\"btn_restore\"><img src=\"img/restore.png\"></div></td>"
	}
	htmlRem +="</tr></table>";
	document.getElementById('removed').innerHTML = htmlRem;
}



//------------------------------------ACTIES OP KNOPPEN-------------------------
//Iemand drukt op een preference knop
function pref(type,attribute){
	
	timefunction('pref',attribute,type);
	var factor = 0;
	
	if(this.attribute){
		if(type=='min'){
			factor =-1;
		}else if(type=='plus'){
			factor = 1;
		}
		
		//stepsize van de kliks 
		this.preferences[attribute]=this.preferences[attribute]+0.2*factor;
		
		//de grenzen voor de waardes worden ingesteld
		if(this.preferences[attribute]<0){
			this.preferences[attribute]=0;
		}
		if(this.preferences[attribute]>1){
			this.preferences[attribute]=1;
		
		}
	}else{
		if(type=='min'){
			factor =-0.5;
		}else if(type=='plus'){
			factor = 0.5;
		}
		
		//stepsize van de kliks 
		this.preferences[attribute]=this.preferences[attribute]+factor;
		
		//de grenzen voor de waardes worden ingesteld
		if(this.preferences[attribute]<-1.5){
			this.preferences[attribute]=-1.5;
		}
		if(this.preferences[attribute]>1.5){
			this.preferences[attribute]=1.5;
		
		}
	
	}
	balkje();
	setWeights();
	
}

//balkje
function balkje(){
	for(var i=0;i<this.attributes.length;i++){
		//var code='<img ';
		// if(this.preferences[attribute[i]]==0.2){
			// code+='src=\" \"'
		// }else 
		// }
		if(this.attribute){
			var barlength = 100*this.preferences[this.attributes[i]];
		}else{
			var barlength = 50+33.333*this.preferences[this.attributes[i]];
		}
		var code = "<div id=\"bg_prefBar\"><div class=\"prefBar\"style=\"width:"+barlength+"%\"></div></div>";
		var divje = this.attributes[i]+"Balk"
		document.getElementById(divje).innerHTML = code;
		
	}
}


function setToGreen (i)
{
	for (var j=0;j<this.products.length;j++){
		if(products[j]['id']==i){
			document.getElementById("animatie"+i+"").style.color = "#FFFFFF";
			document.getElementById("productnaam"+i+"").style.color = "#FFFFFF";
			document.getElementById("animatie"+i+"").style.fontWeight = "bold";
			document.getElementById("animatie"+i+"").style.background = "#00821E"; 
			document.getElementById("animatie"+i+"").style.filter = "progid:DXImageTransform.Microsoft.gradient(startColorstr='#00B91E', endColorstr='#00461E')"; 
			document.getElementById("animatie"+i+"").style.background = "-webkit-gradient(linear, left top, left bottom, from(#00B91E), to(#00461E))"; 
			document.getElementById("animatie"+i+"").style.background = "-moz-linear-gradient(top,  #00B91E,  #00461E)"; 
			setTimeout ( "choose("+i+")", 800 );
		}
	}
}

//Iemand kiest een maatregel
function choose(i){
	timefunction('choose','pick',i);
	for (var j=0;j<this.products.length;j++){
		if(products[j]['id']==i){
			this.products[j]['choice'] = 1;
		}
	}
	classifyProducts();
}

function setToRed (i)
{
	
	for (var j=0;j<this.products.length;j++){
		if(products[j]['id']==i){
			document.getElementById("animatie"+i+"").style.color = "#FFFFFF";
			document.getElementById("productnaam"+i+"").style.color = "#FFFFFF";
			document.getElementById("animatie"+i+"").style.fontWeight = "bold";
			document.getElementById("animatie"+i+"").style.background = "#FF0000"; 
			document.getElementById("animatie"+i+"").style.filter = "progid:DXImageTransform.Microsoft.gradient(startColorstr='#F00000', endColorstr='#A00000')"; 
			document.getElementById("animatie"+i+"").style.background = "-webkit-gradient(linear, left top, left bottom, from(#F00000), to(#A00000))"; 
			document.getElementById("animatie"+i+"").style.background = "-moz-linear-gradient(top,  #F00000,  #A00000)"; 
			setTimeout ( "remove("+i+")", 800 );
		}
	}
}

//Iemand verwijderd een maatregel
function remove(i){
	timefunction('choose','remove',i);
	for (var j=0;j<this.products.length;j++){
		if(products[j]['id']==i){
			this.products[j]['choice'] = -1;
		}
	}
	classifyProducts();
}

//Iemand zet een maatregel terug
function restore(i){
	timefunction('choose','restore',i);
	for (var j=0;j<this.products.length;j++){
		if(products[j]['id']==i){
			this.products[j]['choice'] = 0;
		}
	}
	classifyProducts();
}

//Iemand veranderd wat moet worden getoond
function viewInfo(combinatie){
	timefunction('attribute','attribute',combinatie);
	this.information = new Array();
	switch(combinatie)
	{
	case 'savings':
		this.information.push('realsavingsE');
		this.information.push('savingsKWH');
		document.getElementById('btn_view_be').className = "btn_view_active";
		document.getElementById('btn_view_mo').className = "";
		document.getElementById('btn_view_co').className = "";
		document.getElementById('btn_view_ko').className = "";
		document.getElementById('btn_view_mi').className = "";
		document.getElementById('title1').innerHTML = "&#8364;/Jaar";
		document.getElementById('title1').className = "title_euro";
		document.getElementById('title2').innerHTML = "kWu/Jaar";
		document.getElementById('title2').className = "title_kwu";		
		break;
		
	case 'effort':
		this.information.push('effortonce');
		this.information.push('effortcont');
		document.getElementById('btn_view_mo').className = "btn_view_active";
		document.getElementById('btn_view_be').className = "";
		document.getElementById('btn_view_co').className = "";
		document.getElementById('btn_view_ko').className = "";
		document.getElementById('btn_view_mi').className = "";
		document.getElementById('title1').innerHTML = "Eenmalig";
		document.getElementById('title1').className = "title_once";
		document.getElementById('title2').innerHTML = "Continu";
		document.getElementById('title2').className = "title_cont";
		break;
	
	case 'comfort':
		this.information.push('comfort');
		document.getElementById('btn_view_co').className = "btn_view_active";
		document.getElementById('btn_view_be').className = "";
		document.getElementById('btn_view_mo').className = "";
		document.getElementById('btn_view_ko').className = "";
		document.getElementById('btn_view_mi').className = "";
		document.getElementById('title1').innerHTML = "";
		document.getElementById('title2').innerHTML = "";
		break;
		
	case 'costonce':
		this.information.push('costonce');
		document.getElementById('btn_view_ko').className = "btn_view_active";
		document.getElementById('btn_view_be').className = "";
		document.getElementById('btn_view_mo').className = "";
		document.getElementById('btn_view_co').className = "";
		document.getElementById('btn_view_mi').className = "";
		document.getElementById('title1').innerHTML = "";
		document.getElementById('title2').innerHTML = "";
		break;
		
	case 'ecoweight':
		this.information.push('ecoweight');
		document.getElementById('btn_view_mi').className = "btn_view_active";
		document.getElementById('btn_view_be').className = "";
		document.getElementById('btn_view_mo').className = "";
		document.getElementById('btn_view_co').className = "";
		document.getElementById('btn_view_ko').className = "";
		document.getElementById('title1').innerHTML = "";
		document.getElementById('title2').innerHTML = "";
		break;
	}
	showRecommendations();
	
}

//------------------------------------BASIS FUNCTIES EN WAARDES-------------------------
//Hieronder staat een sorteer functie en de waardes van de standardisatie, hier hoeft niks mee te gebeuren
function sortUtility(a,b)
{
    // this sorts the array using the second element    
    return ((a['utility'] < b['utility']) ? 1 : ((a['utility'] > b['utility']) ? -1 : 0));
}

function sortDistance(a,b)
{
    // this sorts the array using the second element    
    return ((a['utility'] < b['utility']) ? -1 : ((a['utility'] > b['utility']) ? 1 : 0));
}


function sortName(a,b)
{
    // this sorts the array using the second element    
    return ((a['name'] < b['name']) ? -2 : ((a['name'] > b['name']) ? 2 : 0));
}

function setStandardize(){
	this.standardize['effortonce']=-0.02162;
	this.standardize['effortcont']=-0.034914;
	this.standardize['costonce'] = -0.000958;
	this.standardize['realsavingsE']=0.014750;
	this.standardize['savingsKWH']=0.000053;
	this.standardize['returninv']=-0.000147;
	this.standardize['ecoweight']=0.060812;
	this.standardize['comfort']=0.020116;
}
