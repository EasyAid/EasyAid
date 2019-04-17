<?php

if ($_SERVER['REQUEST_METHOD']=='POST'|| $_SERVER['REQUEST_METHOD']=='GET') {
    
    $table = $_REQUEST['table'];
    $cf = $_REQUEST['cf'];

    require_once 'connect.php';

    if($table == 0){
        $sql = "SELECT * FROM Paziente WHERE CodiceFiscale ='$cf' ";

    }else if($table == 1){
        $sql = "SELECT * FROM Medico WHERE CodiceFiscale ='$cf' ";

    }else if($table == 2){
        $sql = "SELECT * FROM Farmacia WHERE CodiceFiscale ='$cf' ";
        
    }

    $response = mysqli_query($conn, $sql);

    $result = array();
    $result['read'] = array();

    if( mysqli_num_rows($response) === 1 ) {
        
        if ($row = mysqli_fetch_assoc($response)) {
 
            switch ($table) {
                case 0:

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
                    break;
                
                case 1:

                    $h['id']                        = $row['IdMedico'] ;
                    $h['codicefiscale']             = $row['CodiceFiscale'] ;
                    $h['password']                  = $row['Password'] ;
                    $h['nome']                      = $row['Nome'] ;
                    $h['cognome']                   = $row['Cognome'] ;
                    $h['datanascita']               = $row['DataNascita'] ;
                    $h['sesso']                     = $row['Sesso'] ;
                    $h['provincianascita']          = $row['ProvinciaNascita'] ;
                    $h['cittanascita']              = $row['CittaNascita'] ;
                    $h['provinciastudio']           = $row['ProvinciaStudio'] ;
                    $h['cittastudio']               = $row['CittaStudio'] ;
                    $h['viastudio']                 = $row['ViaStudio'] ;
                    $h['email']                     = $row['Email'] ;
                    $h['telefono']                  = $row['Telefono'] ;

                    break;
            }
             
 
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