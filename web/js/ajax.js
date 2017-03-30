/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function getVariables(event, runID) {
    //alert("Clicked on button");
    //var email = document.getElementById("newEmail").value;
    //var compaEmail = document.getElementById("reNewEmail").value;
    //var pass = document.getElementById("newPass").value;
    //var compaPass = document.getElementById("reNewPass").value;
    //event.preventDefault();
    //event.stopPropagation();
    /*if(email != compaEmail)
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
     else{*/
    $.get("./ajax/getVariables",
            {
                runID: runID
            },
            function (data, status) {
                //alert("Data: " + data + "\nStatus: " + status);
                var $select = $("#selectVariable");
                $select.find("option").remove();
                $("#upModal").modal();
                $.each(data, function (index, item) {               // Iterate over the JSON object.
                    $("<option>").val(index).text(item.termString).appendTo($select); // Create HTML <option> element, set its value with currently iterated key and its text content with currently iterated item and finally append it to the <select>.
                    //alert(item.termString);
                });
                $("#upModal").modal();
                //document.getElementById("entry_fields").innerHTML = "<div><p><font color='Black'>Registration Successful!\nYou can now login with your detials!</font></p></div>"
            });
    //}
}


