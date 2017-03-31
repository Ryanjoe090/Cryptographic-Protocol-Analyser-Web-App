/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function getVariables(event, runID) {
    $("#currentAgent").val(runID)
    $.get("./ajax/getVariables",
            {
                runID: runID
            },
            function (data, status) {
                //alert("Data: " + data + "\nStatus: " + status);
                var $select = $("#selectVariable");
                $select.find("option").remove();
                //$("#upModal").modal();
                $.each(data, function (index, item) {               // Iterate over the JSON object.
                    $("<option>").val(index).text(item.termString).appendTo($select); // Create HTML <option> element, set its value with currently iterated key and its text content with currently iterated item and finally append it to the <select>.
                    //alert(item.termString);
                });
                $("#upModal").modal();

            });
    //}
}

function getStep(event, runID, stepDescription, action) {
    $("#currentStepAgent").val(runID);
    $("#currentAction").val(action);
    $("#currentStep").val(stepDescription).text(stepDescription);
    if (action == 'SEND') {
        alert(stepDescription);
        //$("#currentStepAgent").val(runID);
        //$("#currentAction").val(action);
        //$("#currentStep").val(stepDescription).text(stepDescription);
        $("#selectTerm").hide();
        $("#stepModal").modal();
    } else {
        //$("#currentStep").val(stepDescription).text(stepDescription);
        //$("#currentStepAgent").val(runID);
        $.get("./ajax/getNetworkBuffer",
                {
                    runID: runID
                },
                function (data, status) {
                    //alert("Data: " + data + "\nStatus: " + status);
                    var $select = $("#selectTerm");
                    $select.show();
                    $select.find("option").remove();
                    //$("#upModal").modal();
                    $.each(data, function (index, item) {               // Iterate over the JSON object.
                        $("<option>").val(index).text(item.termString).appendTo($select); // Create HTML <option> element, set its value with currently iterated key and its text content with currently iterated item and finally append it to the <select>.
                        alert(item.termString);
                    });
                    $("#stepModal").modal();

                });
    }
    //}
}

function correctVariables(event) {
    alert("ree?");
    var runID = $("#currentAgent").val();
    var variableIndex = $("#selectVariable").val();
    var variableType = $("#selectTermType").val();
    var newTermString = $("#newTermString").val();
    $.get("./ajax/changeVariable",
            {
                runID: runID,
                variableIndex: variableIndex,
                variableType: variableType,
                newTermString: newTermString

            },
            function (data, status) {
                alert("Data: " + data + "\nStatus: " + status);

            });
    //}
}


