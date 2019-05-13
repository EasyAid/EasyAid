<?php

if ($_SERVER['REQUEST_METHOD']=='POST'|| $_SERVER['REQUEST_METHOD']=='GET') {
    
    $table = $_REQUEST['table'];
    $sql = null;
    
    require_once 'connect.php';

    switch ($table) {
    	//RICETTA
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

	    //VISITA
        case 4:

            $IdPaziente = $_REQUEST['idpaziente'];
            $IdMedico = $_REQUEST['idmedico'];
            $Data = $_REQUEST['data'];
            $Ora = $_REQUEST['ora'];
            $StatoRichiesta = $_REQUEST['statorichiesta'];
            
            $sql = "
            INSERT INTO Visita (IdPaziente, IdMedico, Data, Ora, StatoRichiesta) 
            VALUES ('$IdPaziente', '$IdMedico', '$Data', '$Ora', '$StatoRichiesta')";
        break;

        //ORDINE
    	case 5:

    		$IdFarmacia = $_REQUEST['idfarmacia'];
		    $IdRicetta = $_REQUEST['idricetta'];
		    $Pagato = $_REQUEST['pagato'];
		    $Totale = $_REQUEST['totale'];
		    $DataRitiro = $_REQUEST['dataritiro'];
		    
		    $sql = "
		    INSERT INTO Ordine (IdFarmacia, IdRicetta, Pagato, Totale) 
		    VALUES ('$IdFarmacia', '$IdRicetta', '$Pagato', '$Totale')";
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
