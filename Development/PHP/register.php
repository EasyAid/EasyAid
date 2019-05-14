<?php
require_once 'connect.php';

if ($_SERVER['REQUEST_METHOD']=='POST'){

    $table = $_REQUEST['table'];
	$sql = null;
	mysqli_begin_transaction($conn);

    if($table == 0){
    	$Nome = $_REQUEST['nome'];
	    $Cognome = $_REQUEST['cognome'];
	    $CodiceFiscale = $_REQUEST['codicefiscale'];
	    $DataNascita = $_REQUEST['datanascita'];
	    $Sesso = $_REQUEST['sesso'];
	    $ProvinciaNascita = $_REQUEST['provincianascita'];
	    $CittaNascita = $_REQUEST['cittanascita'];
	    $ProvinciaResidenza = $_REQUEST['provinciaresidenza'];
	    $CittaResidenza = $_REQUEST['cittaresidenza'];
	    $ViaResidenza = $_REQUEST['viaresidenza'];
	    $IdMedicoBase = $_REQUEST['idmedicobase'];
	    $Password = $_REQUEST['password'];
	    
	    $sql = "
	    INSERT INTO Paziente (Nome, Cognome, CodiceFiscale, DataNascita, Sesso, ProvinciaNascita, CittaNascita, ProvinciaResidenza, CittaResidenza, ViaResidenza, IdMedicoBase, Password) 
	    VALUES ('$Nome', '$Cognome', '$CodiceFiscale', '$DataNascita', '$Sesso', '$ProvinciaNascita', '$CittaNascita', '$ProvinciaResidenza', '$CittaResidenza', '$ViaResidenza', '$IdMedicoBase', '$Password')";
    }

    else if($table == 1){

		$CodiceFiscale = $_REQUEST['codicefiscale'];
	    $Password = $_REQUEST['password'];
	    $Nome = $_REQUEST['nome'];
	    $Cognome = $_REQUEST['cognome'];
	    $DataNascita = $_REQUEST['datanascita'];
	    $Sesso = $_REQUEST['sesso'];
	    $ProvinciaNascita = $_REQUEST['provincianascita'];
	    $CittaNascita = $_REQUEST['cittanascita'];
	    $ProvinciaStudio = $_REQUEST['provinciastudio'];
	    $CittaStudio = $_REQUEST['cittastudio'];
	    $ViaStudio = $_REQUEST['viastudio'];
	    $Email = $_REQUEST['email'];
	    $Telefono = $_REQUEST['telefono'];

	    //INSERIMENTO NUOVO MEDICO

		$sql = "
	    INSERT INTO Medico (CodiceFiscale, Password, Nome, Cognome, DataNascita, Sesso, ProvinciaNascita, CittaNascita, ProvinciaStudio, CittaStudio, ViaStudio, Email, Telefono)
	    VALUES ('$CodiceFiscale', '$Password', '$Nome', '$Cognome', '$DataNascita', '$Sesso', '$ProvinciaNascita', '$CittaNascita',
	    	'$ProvinciaStudio', '$CittaStudio', '$ViaStudio', '$Email', '$Telefono')";

	    //IMPORTANTE//
	    //LETTURA ID MEDICO
	    /*
	    $sql = "SELECT IdMedico FROM Medico WHERE CodiceFiscale = '$CodiceFiscale' ";
    	$response = mysqli_query($conn, $sql);
    	$id = mysqli_fetch_assoc($response);

		$settimanaOrari = $_REQUEST['settimanaorari'];

		for($i = 0; $i < 6; $i++){
			$sql = "
		    INSERT INTO MedicoCalendario (IdMedico, Giorno, OraInizioMattina, OraFineMattina, OraInizioPomeriggio, OraFinePomeriggio)
		    VALUES ('$id['IdMedico']', $i, $settimanaOrari['$i']['0']['0'], $settimanaOrari['$i']['0']['1'], $settimanaOrari['$i']['1']['0'], $settimanaOrari['$i']['1']['1'])";
		}*/
    }

    else if($table == 2){

		$NomeFarmacia = $_REQUEST['nomefarmacia'];
	    $Telefono = $_REQUEST['telefono'];
	    $Email = $_REQUEST['email'];
	    $Provincia = $_REQUEST['provincia'];
	    $Citta = $_REQUEST['citta'];
	    $Via = $_REQUEST['via'];
	    $Password = $_REQUEST['password'];

	    //INSERIMENTO NUOVA FARMACIA

		$sql = "
	    INSERT INTO Farmacia (NomeFarmacia, Telefono, Email, Provincia, Citta, Via, Password)
	    VALUES ('$NomeFarmacia', '$Telefono', '$Email', '$Provincia', '$Citta', '$Via', '$Password')";

	    //IMPORTANTE//
	    //LETTURA ID FARMACIA
	    /*
	    $sql = "SELECT IdMedico FROM Medico WHERE CodiceFiscale = '$CodiceFiscale' ";
    	$response = mysqli_query($conn, $sql);
    	$id = mysqli_fetch_assoc($response);

		$settimanaOrari = $_REQUEST['settimanaorari'];

		for($i = 0; $i < 6; $i++){
			$sql = "
		    INSERT INTO MedicoCalendario (IdMedico, Giorno, OraInizioMattina, OraFineMattina, OraInizioPomeriggio, OraFinePomeriggio)
		    VALUES ('$id['IdMedico']', $i, $settimanaOrari['$i']['0']['0'], $settimanaOrari['$i']['0']['1'], $settimanaOrari['$i']['1']['0'], $settimanaOrari['$i']['1']['1'])";
		}*/
    }

    if ( mysqli_query($conn, $sql) ) {
        $result["success"] = "1";
        $result["message"] = "success";
		mysqli_commit($conn);
        echo json_encode($result);
    } else {

        $result["success"] = "0";
        $result["message"] = "error";
		mysqli_rollback($conn);
        echo json_encode($result);
    }

    
    mysqli_close($conn);
}

?>