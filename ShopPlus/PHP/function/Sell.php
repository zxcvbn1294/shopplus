<?php

require_once("connmysql.php");

$json = file_get_contents('php://input');      
$data = json_decode($json,true);

$pic = $data['pic'];
$productname = $data['productname'];
$price = $data['price'];

$sql  = "INSERT INTO `sell` (`pic`,`productname`,`price`) 
VALUES ('$pic', '$productname', '$price')";

mysqli_query($conn,$sql);
mysqli_close($conn);

?>
