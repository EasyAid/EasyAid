<?php

if ($_SERVER['REQUEST_METHOD']=='POST') {

    $cf = $_POST['cf'];
    $password = $_POST['password'];

    require_once 'connect.php';

    $sql = "SELECT * FROM paziente WHERE codice_fiscale='$cf' ";

    $response = mysqli_query($conn, $sql);

    $result = array();
    $result['login'] = array();
    
    if ( mysqli_num_rows($response) === 1 ) {

        $row = mysqli_fetch_assoc($response);

        if ( password_verify($password, $row['password']) == 0 ) {
            
            $index['nome'] = $row['nome'];
            $index['cognome'] = $row['cognome'];

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