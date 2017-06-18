<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-1.7.1.min.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<table>
		<tbody>
			<tr id="player-one">
				<c:forEach items="${stones2}" var="stone" varStatus="count">
					<td ><a href="#" value="${stone}"
						id="pl2_${count.index}" onclick="action(this)">${stone}</a></td>
				</c:forEach>

			</tr>
			<tr id="player-two">
				<c:forEach items="${stones1}" var="stone" varStatus="count">
					<td ><a href="#" value="${stone}"
						id="pl1_${count.index}" onclick="action(this)">${stone}</a></td>
				</c:forEach>
			</tr>
		</tbody>
	</table>
</body>
<script type="text/javascript">
function action(e){
	var player1 = [];
	var player2 = [];
	
	var id = $(e).attr("id");
	var split = id.split("_");
	var position = split[1];
	var player = split[0];
	
	
	if(player =='pl2'){
		console.log(position);
		console.log(player);
		switch(position){
		case '0':
			position = 13;
			break;
		case '1':
			position = 12;
			break;
		case '2':
			position = 11;
			break;
		case '3':
			position = 10;
			break;
		case '4':
			position = 9;
			break;
		case '5':
			position = 8;
			break;
		case '6':
			position = 7;
			break;
		}
	}
	
	
	for(var i=0; i<=6; i++){
		var value1 = $('#pl1_'+i).text();
		var value2 = $('#pl2_'+i).text();
		player2[i] = value2;
		player1[i] = value1;
	}
	
	
	
	console.log(player1);
	console.log(player2);
	$.ajax({
		   type: "POST",
		   data: {
		   	   player1:player1 ,
			   player2:player2,
			   position : position,
			   player : player
		   },
		   url: "move",
		   success: function(data){
		     console.log(data);
		     $.each(data[0] , function(i, val) { 
		    	 $('#pl1_'+i).text(val);
		    	});
		   
		     $.each(data[1] , function(i, val) { 
		    	 $('#pl2_'+i).text(val);
		    	});
		   }
		});
	
	
}

</script>
</html>