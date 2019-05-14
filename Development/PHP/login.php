<?php

if ($_SERVER['REQUEST_METHOD']=='POST'|| $_SERVER['REQUEST_METHOD']=='GET') {

    $table = $_REQUEST['table'];
    $cf = $_REQUEST['cf'];
    $password = $_REQUEST['password'];
    $sql = null;

    require_once 'connect.php';

    switch ($table){
        case 0:{
            //PAZIENTE
            $sql = "SELECT * FROM Paziente WHERE CodiceFiscale = '$cf' LOCK IN SHARE MODE";
    	    $response = mysqli_query($conn, $sql);
            break;
        }
        case 1:{
            //MEDICO
                $sql = "SELECT * FROM Medico WHERE CodiceFiscale = '$cf' LOCK IN SHARE MODE";
                $response = mysqli_query($conn, $sql);
            break;
        }
        case 2:{
            //FARMACIA
                $sql = "SELECT * FROM Farmacia WHERE Email = '$cf' LOCK IN SHARE MODE";
                $response = mysqli_query($conn, $sql);
            break;
        }
    }

    $result = array();
    $result['login'] = array();
    
    if ( mysqli_num_rows($response) === 1 ) {

        $row = mysqli_fetch_assoc($response);

        if ($password == $row['Password']) {
            
            if($table == 0 || $table == 1){
                $index['nome'] = $row['Nome'];
                $index['cognome'] = $row['Cognome'];
            }else{
                $index['nomeFarmacia'] = $row['NomeFarmacia'];
            }

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