<?php

if ($_SERVER['REQUEST_METHOD']=='POST'|| $_SERVER['REQUEST_METHOD']=='GET') {
    
    $table = $_REQUEST['table'];
    $sql = null;
    
    require_once 'connect.php';

    switch ($table) {
    	case 3:

    		$IdMedico = $_REQUEST['idmedico'];
		    $IdPaziente = $_REQUEST['idpaziente'];
		    $IdFarmaco = $_REQUEST['idfarmaco'];
		    $NumeroScatole = $_REQUEST['numeroscatole'];
		    $Descrizione = $_REQUEST['descrizione'];
		    $EsenzionePatologia = $_REQUEST['esenzionepatologia'];
		    $EsenzioneReddito = $_REQUEST['esenzionereddito'];
		    $StatoRichiesta = $_REQUEST['statorichiesta'];
		    $Data = $_REQUEST['data'];
		    $Ora = $_REQUEST['ora'];
		    
		    $sql = "
		    INSERT INTO Ricetta (IdMedico, IdPaziente, IdFarmaco, NumeroScatole, Descrizione, EsenzionePatologia, EsenzioneReddito, StatoRichiesta, Data, Ora) 
		    VALUES ('$IdMedico', '$IdPaziente', '$IdFarmaco', '$NumeroScatole', '$Descrizione', '$EsenzionePatologia', '$EsenzioneReddito', '$StatoRichiesta', '$Data', '$Ora')";
	    break;
    }

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
