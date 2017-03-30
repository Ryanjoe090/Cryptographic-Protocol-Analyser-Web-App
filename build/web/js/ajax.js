/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function getVariables(event) {
	var email = document.getElementById("newEmail").value;
	var compaEmail = document.getElementById("reNewEmail").value;
	var pass = document.getElementById("newPass").value;
	var compaPass = document.getElementById("reNewPass").value;
	event.preventDefault();
	event.stopPropagation();
	if(email != compaEmail)
	{
		alert("EMAILS DO NOT MATCH!");
		document.getElementById("newEmail").value = '';
		document.getElementById("reNewEmail").value = '';
	}
	else if(pass != compaPass)
	{
		alert("PASSWORDS DO NOT MATCH!");
		document.getElementById("newPass").value = '';
		document.getElementById("reNewPass").value = '';
	}
	else{
		$.post("/ajax/register",
    {
        email: email,
        password: pass
    },
    function(data, status){
        //alert("Data: " + data.status + "\nStatus: " + status);	
		if(data.status == "OK") {
			alert("SUCCESS!\nThank you your registration.")
			$('#regModal').modal('hide');
		} else {
			alert("Not success! User already taken.");
		}
		//document.getElementById("entry_fields").innerHTML = "<div><p><font color='Black'>Registration Successful!\nYou can now login with your detials!</font></p></div>"
    });
	}
}


