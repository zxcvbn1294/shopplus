<?php

require_once("connmysql.php");

$json = file_get_contents('php://input');
$data = json_decode($json,true);

$id = $data['id'];
$password = $data['password'];

$sql = "SELECT * FROM `create`
        WHERE `id`= '$id' 
        AND `password` = '$password' ";

$result=mysqli_query($conn,$sql);

    if((mysqli_num_rows($result)==1))
    {
    print json_encode($success, JSON_UNESCAPED_UNICODE);
    }
    else{}
    
mysqli_query($conn,$sql);
mysqli_close($conn);

?>
