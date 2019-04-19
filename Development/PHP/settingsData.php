<?php
if ($_SERVER['REQUEST_METHOD']=='POST'|| $_SERVER['REQUEST_METHOD']=='GET') {
	$table = $_REQUEST['table'];
	$sql = null;
    
    require_once 'connect.php';
	
	switch ($table){
		case 0:
			//Opzione 1: Cambio password - Opzione 2: Cambio Medico
			$option = $_REQUEST['option'];
			if($option == "1"){
				$idPaziente = $_REQUEST['idPaziente'];
				$newPwd = $_REQUEST['password'];
				$sql = "UPDATE Paziente SET Password = '$newPwd' WHERE (IdPaziente = '$idPaziente');";
			}else if($option == "2"){
				$idPaziente = $_REQUEST['idPaziente'];
				$idMedico = $_REQUEST['idMedico'];
				$sql = "UPDATE Paziente SET IdMedicoBase = '$idMedico' WHERE (IdPaziente = '$idPaziente');";
			}
			
		break;
		
		case 1:
			//Medico
		break;
		
		case 2:
			//Farmacia
		break;
	}
	$response = mysqli_query($conn, $sql);

    $result = array();
    $result['read'] = array();
    $result['data'] = $response;

    $result["success"] = "1";
    echo json_encode($result);
	
}else{
	$result["success"] = "0";
    $result["message"] = "Error!";
    echo json_encode($result);
 
    mysqli_close($conn);
}
?>