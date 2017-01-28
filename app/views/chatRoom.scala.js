@(username: String)

$(function() {
    var WS = window['MozWebSocket'] ? MozWebSocket : WebSocket
 var chatSocket = new WS("@routes.Application.chat(username).webSocketURL(request)")

    var sendMessage = function() {
        chatSocket.send(JSON.stringify( {nr: $("#nr").val()} ))
        $("#nr").val('')

    }

	var mymove = false
	
    var receiveEvent = function(event) {
        var data = JSON.parse(event.data)

		if(data.type == "MOVE")
		{
			pawnarray = data.board.board
			drawBoard()
			mymove = true
		}
		else if(data.type == "GIVEUP")
		{
			pawnarray = data.board.board
			drawboward;
			mymove = false
		}
		else if(data.type == "W")
		{
			playercol = 2
		}
		else if(data.type == "B")
		{
			playercol = 1
			mymove = true
		}
    /*    // Handle errors
        if(data.error) {
            chatSocket.close()
            $("#onError span").text(data.error)
            $("#onError").show()
            return
        } 
        else {
            $("#onChat").show()
            }        
            // Create the message element       
	        var el = $('<div class="message"><p style="font-size:16px"></p></div>')
	        $("p", el).text(data.message)
	        $(el).addClass('me')
	        $('#messages').append(el) 
			*/
    }

    var handleReturnKey = function(e) {
        if(e.charCode == 13 || e.keyCode == 13) {
            e.preventDefault()
            sendMessage()
        }
    }

	var cursorx=-2
	var cursory=-2
	var a = 601
	var playercol = 2	//1-czarny, 2-bialy
	var field = a/9.0
	// 0-puste, 1-czarny, 2-bialy
	var pawnarray = new Array(9)
	for(var i=0; i<9; i++)
	{
		pawnarray[i] = new Array(9)
	}
	
	function drawBoard()	//rysuje plansze
	{
		
		var boardCode = '<svg style = "background: #ffccff" width="' + a + '" height="' + a + '">'
		for(var i=0; i<9; i++)
		{
			boardCode+= '<line x1="' + field/2.0 +'" y1="' + (field/2.0 + field*i) + '" x2="'+ (a-(field/2.0)) + '" y2="' + (field/2.0 + field*i) + '" style ="stroke:rgb(0,0,0);stroke-width:2" />' 
			boardCode+= '<line x1="' + (field/2.0 + field*i) +'" y1="' + field/2.0 + '" x2="'+ (field/2.0 + field*i) + '" y2="' + (a-(field/2.0)) + '" style ="stroke:rgb(0,0,0);stroke-width:2" />' 
		}
		for(var j=0; j<9; j++)
			for(var k=0; k<9; k++)
			{
				if(pawnarray[j][k] == 1)
				{
					boardCode += '<circle cx="' + (j*field + field/2) + '" cy="' + (k*field + field/2) + '" r="'+ field/4 + '" stroke="black" stroke-width="3" fill="#212121" />'
					
				}
				else if(pawnarray[j][k] == 2)
				{
					boardCode += '<circle cx="' + (j*field + field/2) + '" cy="' + (k*field + field/2) + '" r="'+ field/4 + '" stroke="white" stroke-width="3" fill="#F5F5F5" />'
				}
			}
		
		boardCode+= '<rect x="' + cursorx*field + '" y="' + cursory*field + '" width="'+ field +'" height="' + field + '" style="fill:#ff1a75;stroke:#ff0066;stroke-width:5;fill-opacity:0.4;stroke-opacity:0.5" />'		
		boardCode += '</svg>'
		
		document.getElementById("board").innerHTML = boardCode
	}
	
	drawBoard()
	
	document.getElementById("board").onmousemove = function(event)
	{
		if(mymove == true)
		{
			cursorx = parseInt((event.screenX - document.getElementById("board").getBoundingClientRect().left)/field)
			cursory = parseInt((event.screenY - document.getElementById("board").getBoundingClientRect().top - 85)/field)
			drawBoard()			
		}
	}
	
	document.getElementById("board").onmousedown = function(event)
	{
		if(mymove == true)
		{
			chatSocket.send(JSON.stringify({type: "MOVE", x:cursorx, y:cursory}))
			mymove = false
		}
	}
	//	pawnarray[cursorx][cursory] = playercol
		
	//	if(playercol == 1)
	//		playercol=2
	//	else if(playercol == 2)
	//		playercol=1

	
	document.getElementById("but1").onmousedown = function(event)
	{
		if(mymove == true)
		{
			chatSocket.send(JSON.stringify({type:"PASS", board:board}))
			mymove = false		
		}
	}
	document.getElementById("but2").onmousedown = function(event)
	{
		chatSocket.send(JSON.stringify({type:"GIVEUP", board:board}))
		mymove = false
	}
	
    $("#nr").keypress(handleReturnKey)


    chatSocket.onmessage = receiveEvent

})
