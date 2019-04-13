<?php

if ($_SERVER['REQUEST_METHOD']=='POST'){

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

    require_once 'connect.php';

    $sql = "INSERT INTO Paziente (Nome, Cognome, CodiceFiscale, DataNascita, Sesso, ProvinciaNascita, CittaNascita, ProvinciaResidenza, CittaResidenza, ViaResidenza, IdMedicoBase, Password) VALUES ('$Nome', '$Cognome', '$CodiceFiscale', '$DataNascita', '$Sesso', '$ProvinciaNascita', '$CittaNascita', '$ProvinciaResidenza', '$CittaResidenza', '$ViaResidenza', '$IdMedicoBase', '$Password')";

    if ( mysqli_query($conn, $sql) ) {
        $result["success"] = "1";
        $result["message"] = "success";

        echo json_encode($result);
    } else {

        $result["success"] = "0";
        $result["message"] = "error";

        echo json_encode($result);
    }

    
    mysqli_close($conn);
}

?>