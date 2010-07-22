<%-- 
    Document   : organization-creation
    Created on : Jul 17, 2010, 1:56:52 PM
    Author     : uzzal
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>create organization</title>

<%--           <script type="text/javascript" src="jquery-1.4.2.js"></script>
           <script type="text/javascript" src="json2.js"></script>
           <script type="text/javascript" src="jquery.form.js"></script>

             <script type="text/javascript">
        // wait for the DOM to be loaded
            $(document).ready(function() {
            // bind 'myForm' and provide a simple callback function
            $('#organizationform').ajaxForm(function() {
                alert("Thank you for your comment!");
            });
            });
            </script>--%>

           <LINK REL=StyleSheet href="../css/organization-style.css" TYPE="text/css" MEDIA=screen>

    </head>

    <body>

        <div id="maindivision">


            <div id="header_organization" align="center">
                <label id="header">Organization Entry</label>
            </div>


            <div id="form_organizationentry" align="center">

                    <form action="http://russel:9090/organizations" method="post" accept="application/json" enctype="" id="organizationform">

                        <div id="inner-left-1" align="right"><label>Organization Name:</label></div><div id="inner-right-1" align="left"><input type="text" name="name" size="40" value=""></div>

                        <div id="inner-left-2" align="right"><label>Unique short Name:</label></div><div id="inner-right-2" align="left"><input type="text" name="uniqueShortName" size="40" value=""></div>

                        <div id="inner-left-3" align="right"><label>Street Address:</label></div><div id="inner-right-3" align="left"><input type="text" name="streetAddress" size="40" value=""></div>

                        <div id="inner-left-4" align="right"><label>City:</label></div><div id="inner-right-4" align="left"><input type="text" name="city" size="40" value=""></div>

                        <div id="inner-left-5" align="right"><label>State:</label></div><div id="inner-right-5" align="left"><input type="text" name="state" size="40" value=""></div>

                        <div id="inner-left-6" align="right"><label>Country:</label></div><div id="inner-right-6" align="left"><input type="text" name="country" size="40" value=""></div>

                        <div id="inner-left-7" align="right"><label>Zip:</label></div><div id="inner-right-7" align="left"><input type="text" name="zip" size="40" value=""></div>

                        <div class="inner-right-8" align="center"><input type="submit" value="submit" name="submitBtn"></div>

                    </form>
                
            </div>
            

          </div>



    </body>
</html>