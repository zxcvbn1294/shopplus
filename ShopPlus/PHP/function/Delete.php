<?php

require_once("connmysql.php");

$json = file_get_contents('php://input');      
$data = json_decode($json,true);

$id = $data['id'];

$sql = "DELETE FROM `create` 
        WHERE  `id` = '$id' ";

mysqli_query($conn,$sql);
mysqli_close($conn);

?>
