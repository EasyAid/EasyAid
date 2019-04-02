<?php

if ($_SERVER['REQUEST_METHOD']=='POST'|| $_SERVER['REQUEST_METHOD']=='GET') {

    $cf = $_REQUEST['cf'];
    $password = $_REQUEST['password'];

    require_once 'connect.php';

    $sql = "SELECT * FROM Paziente WHERE CodiceFiscale = '$cf' ";

    $response = mysqli_query($conn, $sql);

    $result = array();
    $result['login'] = array();
    
    if ( mysqli_num_rows($response) === 1 ) {

        $row = mysqli_fetch_assoc($response);

        if ( password_verify($password, $row['Password']) == 0 ) {
            
            $index['nome'] = $row['Nome'];
            $index['cognome'] = $row['Cognome'];

            array_push($result['login'], $index);

            $result['success'] = "1";
            $result['message'] = "success";
            echo json_encode($result);

            mysqli_close($conn);

        } else {
            $result['success'] = "0";
            $result['message'] = "error";
            echo json_encode($result);

            mysqli_close($conn);

        }

    }

}

?>