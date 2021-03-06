<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<title>My To-Do</title>
	<meta charset="utf-8"> 
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<link rel="stylesheet" href="style.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body style="padding-bottom:10px;">
<p id="place">Id:</p>
	<div class="col-md-4 col-md-offset-4 main">
		<div class="col-md-12 section_one bord">
			<div class="col-md-12" style="margin-top:10px;">
				<div class="col-md-8 space">
					<input type="text" class="form-control top">
				</div>
				<div class="col-md-4">
					<input type="button" class="btn btn-primary" value="Add" onclick="setTopic();" >
				</div>
			</div>
		</div>
		<div id="section_two" class="col-xs-12 section_two" style="margin-top:10px">
			<c:catch var="MyException">
				<c:forEach var="topic" items="${requestScope['topics']}">
					<div class="col-xs-12 adder">
						<input type="hidden" class="track" value="${topic.id}">
						<div class="col-xs-12 block">
							<div class="col-xs-8">
								${topic.data}
							</div>
							<div class="col-xs-4">
								<span class='glyphicon glyphicon-plus topAdder' onclick='lowerPart(this,2);'>
								</span><span class='glyphicon glyphicon-remove topAdder' onclick='closex(this);'></span>
							</div>
						</div>
					<c:forEach var="subone" items="${topic.sub}">
						<div class="col-xs-12 adder">
							<input type="hidden" class="track" value="${subone.id}">
							<div class="col-xs-11 block marg-s">
								<div class="col-xs-8">
									${subone.data}
								</div>
								<div class="col-xs-4">
									<span class='glyphicon glyphicon-plus topAdder' onclick='lowerPart(this,3);'></span>
									<span class='glyphicon glyphicon-remove topAdder' onclick='closex(this);'></span>
								</div>
							</div>
						<c:forEach var="subtwo" items="${subone.sub}">
							<div class="col-xs-12 adder">
								<input type="hidden" class="track" value="${subtwo.id}">
								<div class="col-xs-9 block marg-s">
									<div class="col-xs-10">
										${subtwo.data}
									</div>
									<div class="col-xs-2">
										<span class='glyphicon glyphicon-remove topAdder' onclick='closex(this);'></span>
									</div>
								</div>
							</div>
						</c:forEach>
						</div>
					</c:forEach>
					</div>
				</c:forEach>
			</c:catch>	
		<c:if test="${MyException!=null}">
			Exception: ${MyException.message}
		</c:if>
		<c:if test="${requestScope.error!=null}">
			Exception: ${requestScope.error}
		</c:if>
		</div>
	</div>	
<script>
	var prt;
	var cv;
	var fix=0;
	var result;
	function constructor(){
		var addNew = document.createElement("div");
		$(addNew).addClass("col-xs-12");
		$(addNew).addClass("bord");
		$(addNew).addClass("insertBox");
		var cone = document.createElement("div");
		$(cone).addClass("col-md-8");
		$(cone).html('<input type="text" class="form-control lower">');
		//var txt = document.createTextNode(val)
		//$(cone).append(txt);
		//$(cone).text(val);
		var ctwo = document.createElement("div");
		$(ctwo).addClass("col-md-4");
		$(ctwo).html("<input type='button' class='btn btn-primary' onclick='initial();'  value='ADD'>");
		addNew.appendChild(cone);
		addNew.appendChild(ctwo);
		return addNew;
	}
	function setTopic(){
		prt = document.getElementById("section_two");
		cv = 1;
		fix = 1001;
		//alert("setTopic");
		initial();
	}
	function db(el){
		/*var xhttp = new XMLHttpRequest();
		var str;
		alert("confirmed1");
		xhttp.onReadyStateChange = function(){
			if(this.readyState == 4 && this.status == 200){
				alert("confirmed3");
				document.getElementById("place").innerHTML = this.responseText;
			}
		};
		xhttp.open("POST","operate",true);
		alert("confirmed2");
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		*/
		if(cv==1){
			var t = $(".top").val();
			str = "id="+fix+"&val=1&txt="+t;
			alert(str);
			$.post("operate",
			{
			  id:fix,
			  val:1,
			  txt:t
			},
			function(data,status){
				//alert("Data: " + data + "\nStatus: " + status);
				if(status=="success"){
					alert(status);
					result = data;
					var ch = $(el).children(".track");
					$(ch).val(result);
					alert("Inside initial: "+result);
				}
			});
		}
		else{
			var t = $(".lower").val();
			str = "id="+fix+"&val="+cv+"&txt="+t;
			alert(str);
			$.post("operate",
			{
			  id:fix,
			  val:cv,
			  txt:t
			},
			function(data,status){
				//alert("Data: " + data + "\nStatus: " + status);
				if(status=="success"){
					alert(status);
					result = parseInt(data);
					var ch = $(el).children(".track");
					$(ch).val(result);
					alert("Inside initial: "+result);
				}
			});
		}
		//xhttp.send(str);
	}
	function initial(){
		var el;
		if(cv==1){
			el = create(1);
			//result = db();
			db(el);
		}
		else
		{
			if(cv==2){
				el = create(2);
				//result = db();
				db(el);
			}
			else{
				el = create(3);
				//result = db();
				db(el);
			}
			$(prt).find("div.insertBox").remove();
		}
		$(prt).append(el);
	}
	function lowerPart(v,val){
		prt = $(v).parent().parent().parent();
		var child = prt.children(".track");
		fix = $(child).val();
		alert(fix);
		cv = val;
		//prt.appendChild(addNew);
		$(prt).append(constructor());
	}
	function closex(v){
		var el = $(v).parent().parent().parent();
		el.remove();
	}
	function create(v){
		var element = document.createElement("div");
		$(element).addClass("adder");
		$(element).addClass("col-md-12");
		$(element).html("<input type='hidden' class='track'>");
		if(v==1){
			var cone = document.createElement("div");
			$(cone).addClass("col-md-8");
			var val = $(".top").val()+""; //document.getElementById("top").value;
			//var txt = document.createTextNode(val)
			//$(cone).append(txt);
			$(cone).text(val);
			var ctwo = document.createElement("div");
			$(ctwo).addClass("col-md-4");
			$(ctwo).html("<span class='glyphicon glyphicon-plus topAdder' onclick='lowerPart(this,2);'></span><span class='glyphicon glyphicon-remove topAdder' onclick='closex(this);'></span>");
			var box = document.createElement("div");
			$(box).addClass("block");
			$(box).addClass("col-md-12");
			$(box).addClass("col-md-offset-3");
			box.appendChild(cone);
			box.appendChild(ctwo);
			element.appendChild(box);
			return element;
		}
		else if(v==2){
			var cone = document.createElement("div");
			$(cone).addClass("col-md-8");
			var val = $(".lower").val()+""; //document.getElementById("top").value;
			//var txt = document.createTextNode(val)
			//$(cone).append(txt);
			$(cone).text(val);
			var ctwo = document.createElement("div");
			$(ctwo).addClass("col-md-4");
			$(ctwo).html("<span class='glyphicon glyphicon-plus topAdder' onclick='lowerPart(this,3)'></span><span class='glyphicon glyphicon-remove topAdder' onclick='closex(this);'></span>")
			var box = document.createElement("div");
			$(box).addClass("block");
			$(box).addClass("col-md-11");
			$(box).addClass("marg-s");
			box.appendChild(cone);
			box.appendChild(ctwo);
			element.appendChild(box);
			return element;
		}
		else if(v==3){
			var cone = document.createElement("div");
			$(cone).addClass("col-md-10");
			var val = $(".lower").val()+""; //document.getElementById("top").value;
			//var txt = document.createTextNode(val)
			//$(cone).append(txt);
			$(cone).text(val);
			var ctwo = document.createElement("div");
			$(ctwo).addClass("col-md-2");
			$(ctwo).html("<span class='glyphicon glyphicon-remove topAdder' onclick='closex(this);'></span>")
			var box = document.createElement("div");
			var box = document.createElement("div");
			$(box).addClass("block");
			$(box).addClass("col-md-9");
			$(box).addClass("marg-s");
			box.appendChild(cone);
			box.appendChild(ctwo);
			element.appendChild(box);
			return element;
		}
	}
</script>
</body>
</html>