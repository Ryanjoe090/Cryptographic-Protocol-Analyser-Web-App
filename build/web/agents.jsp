<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<!-- Template adapted from https://startbootstrap.com/template-overviews/sb-admin/ -->
    <head>

        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        
        

        <title>Cryptographic Protocol Verification Tool</title>

        <!-- Bootstrap Core CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom CSS -->
        <link href="css/sb-admin.css" rel="stylesheet">

        <!-- Custom Fonts -->
        <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">

        <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
            <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
        <![endif]-->

    </head>

    <body>

        <div id="wrapper">

            <!-- Navigation -->
            <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="index.html">Cryptographic Protocol Verification Tool Web App <i class="fa fa-key"></i><i class="fa fa-lock"></i></a>
                </div>
                <!-- Top Menu Items -->
                
                <!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
                <div class="collapse navbar-collapse navbar-ex1-collapse">
                    <ul class="nav navbar-nav side-nav">
                        <li class="active">
                            <a href="./index.jsp"><i class="fa fa-fw fa-home"></i> Home</a>
                        </li>
                        <li>
                            <a href="./agents"><i class="fa fa-fw fa-user"></i> Agents</a>
                        </li>
                        <li>
                            <a href="./roles.jsp"><i class="fa fa-fw fa-list"></i> Roles</a>
                        </li>
                        <li>
                            <a href="./networkbuffer"><i class="fa fa-fw fa-wifi"></i> Network Buffer</a>
                        </li>
                        <li>
                            <a href="./protocol"><i class="fa fa-fw fa-file-text"></i> Protocol Base File</a>
                        </li>                       
                    </ul>
                </div>
                <!-- /.navbar-collapse -->
            </nav>

            <div id="upModal" class="modal fade" role="dialog" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-content">
                        <div class="modal-header">
                            <legend> 
                                <i class="fa fa-check-square"></i>&nbsp;&nbsp;&nbsp;Correct Agent Variable
                            </legend>
                        </div>
                        <div id="entry_fieldsg" class="modal-body">	
                            <form method="POST" enctype="multipart/form-data" action="agents">
                                <input id="currentAgent" name="currentAgent" hidden="true" value="0">
                                <label class="control-label">Select Variable:</label>
                                <br>
                                <div style="position:relative;">
                                    <select id="selectVariable" name="selectedVariable" class="form-control">
                                    </select>
                                </div>
                                <br>
                                <label class="control-label">Select Type:</label>
                                <br>
                                <div style="position:relative;">
                                    <select id="selectTermType" name="selectedTermType" class="form-control">
                                        <option value="1">Public</option>
                                        <option value="2">Fresh</option>
                                    </select>
                                </div>
                                <br>
                                <label class="control-label">New Term String:</label>
                                <br>
                                <div style="position: relative;">
                                    <div class="form-group input-group">
                                        <span class="input-group-addon"><i class="fa fa-tag"></i></span>
                                        <input type="text" name="newTermString" class="form-control" placeholder="Enter String Here">
                                    </div>
                                </div>
                                <br>
                                <button type="submit" class="btn btn-primary" name="postAgent" value="CHANGE">Submit</button>	
                            </form>
                            <br>
                            <div id="proBar"  hidden class="progress">
                                <div id="proBar2" class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            
            <div id="stepModal" class="modal fade" role="dialog" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                    <div class="modal-content">
                        <div class="modal-header">
                            <legend> 
                                <i class="fa fa-check-square"></i>&nbsp;&nbsp;&nbsp;Take Step
                            </legend>
                        </div>
                        <div id="entry_fieldsg" class="modal-body">	
                            <form method="POST" enctype="multipart/form-data" action="agents">
                                <input id="currentStepAgent" name="currentStepAgent" hidden="true" value="0">
                                <input id="currentAction" name="currentAction" hidden="true" value="0">
                                <label id="selectlable" class="control-label">Select Term from Buffer:</label>
                                <br>
                                <div style="position:relative;">
                                    <select id="selectTerm" name="selectedTerm" class="form-control">
                                    </select>
                                </div>
                                <br>
                                <label id="currentStep" class="control-label">STEP</label>
                                <br>
                                <button type="submit" class="btn btn-primary" name="postAgent" value="STEP">Submit</button>	
                            </form>
                            <br>
                            <div id="proBar"  hidden class="progress">
                                <div id="proBar2" class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div id="page-wrapper">
                <div class="container-fluid">

                    <!-- Page Heading -->
                    <div class="row">
                        <div class="col-lg-12">
                            <h1 class="page-header">
                                Agents
                            </h1>
                            <ol class="breadcrumb">
                                <li class="active">
                                    <i class="fa fa-user"></i> Agents
                                </li>
                            </ol>
                        </div>
                    </div>

                    <%
                        //get agents 
                        security.Environment environment = (security.Environment) request.getSession().getAttribute("environment");
                        if (environment == null) {%>
                    <p>No Protocol Uploaded</p>
                    <%} else if (environment.getAgents().size() == 0) {%>
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <h3 class="panel-title">Add Agent</h3>
                                </div>
                                <div class="panel-body">
                                    <form method="POST" enctype="multipart/form-data" action="agents">
                                        <div class="form-group input-group">
                                            <span class="input-group-addon"><i class="fa fa-user"></i><i class="fa fa-plus"></i></span>
                                            <input type="text" name="name" class="form-control" placeholder="Enter Name Here">
                                        </div>
                                        <div class="form-group">
                                            <label for="selectRole">Select Role</label>
                                            <select id="selectRole" name="selectRoleChar" class="form-control">
                                                <%for (int i = 0; i < environment.getProtocol().getRole().size(); i++) {%>
                                                <option><%=environment.getProtocol().getRole().get(i).getAgent()%></option>
                                                <%}%>
                                            </select>
                                        </div>

                                        <button type="submit" class="btn btn-danger" name="postAgent" value="ADD">Add Agent</button>
                                    </form>

                                </div>
                            </div>

                        </div>
                    </div>
                    <p>No Agents Created</p>
                    <%} else {%>
                    <div class="col-sm-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h3 class="panel-title">Add Agent</h3>
                            </div>
                            <div class="panel-body">
                                <form method="POST" enctype="multipart/form-data" action="agents">
                                    <div class="form-group input-group">
                                        <span class="input-group-addon"><i class="fa fa-user"></i><i class="fa fa-plus"></i></span>
                                        <input type="text" name="name" class="form-control" placeholder="Enter Name Here">
                                    </div>
                                    <div class="form-group">
                                        <label for="selectRole">Select Role</label>
                                        <select id="selectRole" name="selectRoleChar" class="form-control">
                                            <%for (int i = 0; i < environment.getProtocol().getRole().size(); i++) {%>
                                            <option><%=environment.getProtocol().getRole().get(i).getAgent()%></option>
                                            <%}%>
                                        </select>
                                    </div>

                                    <button type="submit" class="btn btn-danger" name="postAgent" value="ADD">Add Agent</button>
                                </form>

                            </div>
                        </div>

                    </div>
                    <%
                        //if null then p
                        //is there then loop
                        for (int i = 0; i < environment.getAgents().size(); i++) {%>
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h3 class="panel-title"><%=environment.getAgents().get(i).getAgentDescription()%></h3>
                                </div>
                                <div class="panel-body">
                                    <div class="col-sm-3">
                                        <ul class="list-group">
                                            <%for (int j = 0; j < environment.getAgents().get(i).getKnowledge().size(); j++) {%>
                                            <li class="list-group-item"><%=environment.getAgents().get(i).getKnowledge().get(j).getTermString()%></li>
                                                <%}%>
                                        </ul>
                                    </div>
                                    <div class="col-sm-3">
                                        <ul class="list-group">
                                            <%for (int j = 0; j < environment.getAgents().get(i).getRole().getSteps().size(); j++) {%>
                                            <li class="list-group-item"><%=environment.getAgents().get(i).getRole().getSteps().get(j).getDescription()%></li>
                                                <%}%>
                                        </ul>
                                    </div>
                                    <div class="col-sm-6">
                                        <button type="button" class="btn btn-success btn-block" onclick="getVariables(event,<%=environment.getAgents().get(i).getRunIdentifier()%>)">Change Knowledge</button>
                                        <%if(environment.getAgents().get(i).getStepCounter() < environment.getAgents().get(i).getRole().getSteps().size()){ %>
                                        <button type="button" class="btn btn-info btn-block" onclick="getStep(event,<%=environment.getAgents().get(i).getRunIdentifier()%>,'<%=environment.getAgents().get(i).getRole().getSteps().get(environment.getAgents().get(i).getStepCounter()).getDescription()%>','<%=environment.getAgents().get(i).getRole().getSteps().get(environment.getAgents().get(i).getStepCounter()).getAction()%>')">Take Step</button>
                                        <%}%>
                                        <button type="button" class="btn btn-danger btn-block">Delete Agent</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%}
                        }%>
                </div>
            </div>
            <!-- /#page-wrapper -->

        </div>
        <!-- /#wrapper -->

        <!-- jQuery -->
        <script src="js/jquery.js"></script>
        <script src="js/ajax.js"></script>

        <!-- Bootstrap Core JavaScript -->
        <script src="js/bootstrap.min.js"></script>

        <!-- Morris Charts JavaScript -->
        <script src="js/plugins/morris/raphael.min.js"></script>
        <script src="js/plugins/morris/morris.min.js"></script>
        <script src="js/plugins/morris/morris-data.js"></script>

    </body>

</html>
