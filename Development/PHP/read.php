<?php

if ($_SERVER['REQUEST_METHOD']=='POST'|| $_SERVER['REQUEST_METHOD']=='GET') {
    
    $table = $_REQUEST['table'];
    $cf = $_REQUEST['cf'];
    $sql = null;

    require_once 'connect.php';

    switch ($table) {
        case 0:
            $sql = "SELECT * FROM Paziente WHERE CodiceFiscale ='$cf' ";
            break;
        
        case 1:
            $sql = "SELECT * FROM Medico WHERE CodiceFiscale ='$cf' ";
            break;

        case 2:
            $sql = "SELECT * FROM Farmacia WHERE CodiceFiscale ='$cf' ";
            break;

        case 3:
        	$sql = "SELECT * FROM Farmaco";
        	break;
    }

    $response = mysqli_query($conn, $sql);

    $result = array();
    $result['read'] = array();

    if( mysqli_num_rows($response) > 0 ) {
        
        if ($row = mysqli_fetch_array($response)) {
 
             switch ($table) {
                
                case 0:
                    $h['id']                   = $row['IdPaziente'] ;
                    $h['nome']                 = $row['Nome'] ;
                    $h['cognome']              = $row['Cognome'] ;
                    $h['datanascita']          = $row['DataNascita'] ;
                    $h['codicefiscale']        = $row['CodiceFiscale'] ;
                    $h['provincianascita']     = $row['ProvinciaNascita'] ;
                    $h['cittanascita']         = $row['CittaNascita'] ;
                    $h['provinciaresidenza']   = $row['ProvinciaResidenza'] ;
                    $h['cittaresidenza']       = $row['CittaResidenza'] ;
                    $h['viaresidenza']         = $row['ViaResidenza'] ;
					$h['sesso'] 			   = $row['Sesso'];
                    array_push($result["read"], $h);
                break;
                
                case 1:
                    $h['id']                       = $row['IdMedico'] ;
                    $h['nome']                     = $row['Nome'] ;
                    $h['cognome']                  = $row['Cognome'] ;
                    $h['datanascita']              = $row['DataNascita'] ;
                    $h['codicefiscale']            = $row['CodiceFiscale'] ;
                    $h['provincianascita']         = $row['ProvinciaNascita'] ;
                    $h['cittanascita']             = $row['CittaNascita'] ;
                    $h['provinciastudio']          = $row['ProvinciaStudio'] ;
                    $h['cittastudio']              = $row['CittaStudio'] ;
                    $h['viastudio']                = $row['ViaStudio'] ;
                    $h['sesso']                    = $row['Sesso'] ;
                    $h['password']                 = $row['Password'] ;
                    $h['email']                    = $row['Email'] ;
                    $h['telefono']                 = $row['Telefono'] ;

                    array_push($result["read"], $h);
                break;

                case 2:

                break;

                case 3:

                    do{
                        $h['idfarmaco']           = $row['IdFarmaco'] ;
                        $h['nomefarmaco']         = $row['NomeFarmaco'] ;
                        $h['confezione']          = $row['Confezione'] ;
                        $h['prezzo']              = $row['Prezzo'] ;
                        
                        array_push($result["read"], $h);
                    }while ($row = mysqli_fetch_array($response));
                    
                break;
             }
 
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