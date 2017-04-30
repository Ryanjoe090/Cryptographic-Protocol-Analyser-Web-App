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
                                <i class="fa fa-check-square"></i>&nbsp;&nbsp;&nbsp;Encrypt
                            </legend>
                        </div>
                        <div id="entry_fieldsg" class="modal-body">	
                            <form method="POST" enctype="multipart/form-data" action="networkbuffer">
                                <label class="control-label">Select Variable:</label>
                                <br>
                                <div style="position:relative;">
                                    <select id="selectTermsEncrypt" name="selectedTerm" class="form-control" multiple>
                                    </select>
                                </div>
                                <br>
                                <label class="control-label">Select Key:</label>
                                <br>
                                <div style="position:relative;">
                                    <select id="selectKey" name="selectedKey" class="form-control">
                                    </select>
                                </div>
                                <br>
                                <button id="postcommand" type="submit" class="btn btn-primary" name="postCommand" value="placehold">Submit</button>	
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
                                Network Buffer
                            </h1>
                            <ol class="breadcrumb">
                                <li class="active">
                                    <i class="fa fa-user"></i> Network Buffer
                                </li>
                            </ol>
                        </div>
                    </div>

                    <%
                        //get agents //

                        //String protocolRaw = (String)request.getAttribute("protocolR");
                        //java.util.LinkedList<security.Role> protocolRaw = (java.util.LinkedList<security.Role>) request.getSession().getAttribute("environment");
                        security.Environment environment = (security.Environment) request.getSession().getAttribute("environment");

                        if (environment == null) {%>
                    <p>No Protocol Loaded</p>
                    <%} else {

                        //if null then p
                        //is there then loop
                    %>
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="panel panel-primary">
                                <div class="panel-heading">
                                    <h3 class="panel-title">Network Buffer</h3>
                                </div>
                                <div class="panel-body">
                                    <div class="col-sm-4">
                                        <ul class="list-group">                                     
                                            <%for (int i = 0; i < environment.getNetworkBuffer().size(); i++) {%> 
                                            <li class="list-group-item">
                                                <%=environment.getNetworkBuffer().get(i).getTermString()%>
                                            </li> <%}%>
                                        </ul>
                                    </div>
                                    <div class="col-sm-4">
                                        <ul class="list-group">                                     
                                            <%for (int i = 0; i < environment.getProtocol().getNetworkKnowledge().size(); i++) {%> 
                                            <li class="list-group-item">
                                                <%=environment.getProtocol().getNetworkKnowledge().get(i).getTermString()%>
                                            </li> <%}%>
                                        </ul>
                                    </div>
                                    <div class="col-sm-4">
                                        <button type="button" class="btn btn-info btn-block" onclick="getEncrypt(event)">Encrypt</button>
                                        <button type="button" class="btn btn-danger btn-block" onclick="getDecrypt(event)">Decrypt</button>
                                        <button type="button" class="btn btn-success btn-block">Create Fresh</button>
                                        <button type="button" class="btn btn-warning btn-block">Hash Term</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%
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
