<?php

    $severname = "localhost";
    $username = "admin";
    $password = "1qaz@wsx";
    $database = "newdatabase";

    //create connection
    $conn = mysqli_connect($severname,$username,$password,$database);
    
    //check
    if(mysqli_connect_error()){
        print "Failed to connect to MySql:" . mysqli_connect_error();
    }else{
        //print "Successful connection" . "<br>";
    }

    mysqli_query($conn,"SET NAMES utf8"); //中文編碼問題
?>