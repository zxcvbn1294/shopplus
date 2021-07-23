<?php

require_once("connmysql.php");

$json = file_get_contents('php://input');      
$data = json_decode($json,true);

$id = $data['id'];
$password = $data['password'];
$phone = $data['phone'];
$email = $data['email'];

$sql  = "INSERT INTO `create` (`id`,`password`,`phone`,`email`) 
         VALUES ('$id', '$password', '$phone', '$email')";

$check ="SELECT * FROM `create`
         WHERE `id`= '$id' ";

$result=mysqli_query($conn,$check);

if((mysqli_num_rows($result)==1)){

}else{
    print(json_encode($id, JSON_UNESCAPED_UNICODE));
    mysqli_query($conn,$sql);
    mysqli_close($conn);
}




?>
