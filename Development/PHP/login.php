<?php

if ($_SERVER['REQUEST_METHOD']=='POST'|| $_SERVER['REQUEST_METHOD']=='GET') {

    $table = $_REQUEST['table'];
    $cf = $_REQUEST['cf'];
    $password = $_REQUEST['password'];

    require_once 'connect.php';

    switch ($table){
        case 0:{
            //PAZIENTE
            $sql = "SELECT * FROM Paziente WHERE CodiceFiscale = '$cf' ";
    	    $response = mysqli_query($conn, $sql);
            break;
        }
        case 1:{
            //MEDICO
                $sql = "SELECT * FROM Medico WHERE CodiceFiscale = '$cf' ";
                $response = mysqli_query($conn, $sql);
            break;
        }
        case 2:{
            //FARMACIA
                $sql = "SELECT * FROM Farmacia WHERE CodiceFiscale = '$cf' ";
                $response = mysqli_query($conn, $sql);
            break;
        }
    }
/*
    //PAZIENTE
    if($table == 0){
    	$sql = "SELECT * FROM Paziente WHERE CodiceFiscale = '$cf' ";
    	$response = mysqli_query($conn, $sql);
    }
    //MEDICO
    else if($table == 1){
		$sql = "SELECT * FROM Medico WHERE CodiceFiscale = '$cf' ";
    	$response = mysqli_query($conn, $sql);
    }

    //FARMACIA
    else if($table == 2){
		$sql = "SELECT * FROM Farmacia WHERE CodiceFiscale = '$cf' ";
    	$response = mysqli_query($conn, $sql);
    }
*/
    $result = array();
    $result['login'] = array();
    
    if ( mysqli_num_rows($response) === 1 ) {

        $row = mysqli_fetch_assoc($response);

        if ($password == $row['Password']) {
            
            $index['nome'] = $row['Nome'];
            $index['cognome'] = $row['Cognome'];

            array_push($result['login'], $index);

            $result['success'] = "1";
            $result['message'] = "success";
            echo json_encode($result);

            mysqli_close($conn);

        } else {
            $result['success'] = "0";
            $result['message'] = "ErrorPassword";
            echo json_encode($result);

            mysqli_close($conn);

        }

    }else{
		$result['success'] = "0";
        $result['message'] = "ErrorUsername";
        echo json_encode($result);

        mysqli_close($conn);
	}

}

?>