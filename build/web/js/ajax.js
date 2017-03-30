/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function getVariables(event, runID) {
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

            });
    //}
}

function getVariables(event, runID) {
    var variableIndex = $("#selectVariable").val();
    var variableType =  $("#selectTermType").text();
    var newTermString = $("#newTermString").text(); 
    $.post("./ajax/changeVariable",
            {
                runID: runID,
                variableIndex: variableIndex,
                variableType: variableType,
                newTermString: newTermString
                
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

            });
    //}
}


