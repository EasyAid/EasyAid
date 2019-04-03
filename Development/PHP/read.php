<?php

if ($_SERVER['REQUEST_METHOD']=='POST'|| $_SERVER['REQUEST_METHOD']=='GET') {
    
    $table = $_REQUEST['table'];
    $cf = $_REQUEST['cf'];

    require_once 'connect.php';

    $sql = "SELECT * FROM $table WHERE CodiceFiscale ='$cf' ";

    $response = mysqli_query($conn, $sql);

    $result = array();
    $result['read'] = array();

    if( mysqli_num_rows($response) === 1 ) {
        
        if ($row = mysqli_fetch_assoc($response)) {
 
             $h['id']                   = $row['IdPaziente'] ;
             $h['nome']                 = $row['Nome'] ;
             $h['cognome']              = $row['Cognome'] ;
             $h['dataNascita']          = $row['DataNascita'] ;
             $h['codiceFiscale']        = $row['CodiceFiscale'] ;
             $h['provinciaNascita']     = $row['ProvinciaNascita'] ;
             $h['cittaNascita']         = $row['CittaNascita'] ;
             $h['provinciaResidenza']   = $row['ProvinciaResidenza'] ;
             $h['cittaResidenza']       = $row['CittaResidenza'] ;
             $h['viaResidenza']         = $row['ViaResidenza'] ;
 
             array_push($result["read"], $h);
 
             $result["success"] = "1";
             echo json_encode($result);
        }
 
   }
 
 }else {
 
     $result["success"] = "0";
     $result["message"] = "Error!";
     echo json_encode($result);
 
     mysqli_close($conn);
 }
 
 ?>