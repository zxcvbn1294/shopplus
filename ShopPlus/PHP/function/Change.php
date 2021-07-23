<?php

require_once("connmysql.php");

$json = file_get_contents('php://input');      
$data = json_decode($json,true);

$id = $data['id'];
$password = $data['password'];
$phone = $data['phone'];
$email = $data['email'];


$sql = "UPDATE `create` 
        SET    `password` = '$password' , `phone`= '$phone' , `email` = '$email' 
        WHERE  `id` = '$id' ";

mysqli_query($conn,$sql);
mysqli_close($conn);


?>
