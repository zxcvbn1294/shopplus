<?php

    require_once("connmysql.php");

    $sql = "SELECT `sell`.`pic` , `sell`.`productname` , `sell`.`price` FROM `sell`";

    $result = mysqli_query($conn,$sql);

    while ($row = mysqli_fetch_array($result,MYSQLI_ASSOC))
    {
        $output[] = $row; 
    }

    print(json_encode($output, JSON_UNESCAPED_UNICODE));
    
    mysqli_free_result($result);
    mysqli_close($conn);
?>