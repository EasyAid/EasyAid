<?php

if ($_SERVER['REQUEST_METHOD']=='POST'|| $_SERVER['REQUEST_METHOD']=='GET') {
    
    $sql = $_REQUEST['sql'];

    require_once 'connect.php';

    $response = mysqli_query($conn, $sql);

    $result = array();
    $result['read'] = array();
    $result['data'] = $response;

    $result["success"] = "1";
    echo json_encode($result);
 
 }else {
 
     $result["success"] = "0";
     $result["message"] = "Error!";
     echo json_encode($result);
 
     mysqli_close($conn);
 }
 
 ?>
