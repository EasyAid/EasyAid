<?php
if ($_SERVER['REQUEST_METHOD']=='POST'|| $_SERVER['REQUEST_METHOD']=='GET') {
	$table = $_REQUEST['table'];
	$sql = null;
    
	require_once 'connect.php';
	mysqli_begin_transaction($conn);
	
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
	if (!$result = mysqli_query($conn, $sql)) {
		//Error
		$response["success"] = "0";
		$response["message"] = $conn->errno . ": " . $conn->error;
		mysqli_rollback($conn);
		echo json_encode($response);
		mysqli_close($conn);
		exit();
	}else{
		mysqli_commit($conn);
		mysqli_close($conn);
		$response = array();
		$response['read'] = array();
		$response['data'] = $result;
		$response["success"] = "1";
		echo json_encode($response);
	}
	
}else{
	$result["success"] = "0";
    $result["message"] = "Error!";
    echo json_encode($result);
 
    mysqli_close($conn);
}
?>