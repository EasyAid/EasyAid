<?php
    //Connessione al Database
    $dbhost = $_SERVER['RDS_HOSTNAME'];
    $dbport = $_SERVER['RDS_PORT'];
    $dbname = $_SERVER['RDS_DB_NAME'];
    $charset = 'utf8' ;

    $dsn = "mysql:host={$dbhost};port={$dbport};dbname={$dbname};charset={$charset}";
    $username = $_SERVER['RDS_USERNAME'];
    $password = $_SERVER['RDS_PASSWORD'];

    $mysqli = new mysqli($dbhost, $username, $password, $dbname);
    if ($mysqli->connect_error) {
        die('Errore di connessione (' . $mysqli->connect_errno . ') '
        . $mysqli->connect_error);
    } else {
        echo 'Connesso. ' . $mysqli->host_info . "\n";
    }

    //Utilizzo query per visualizzazione dati tabella
    $mysqli->query("USE easyaiddb");
    $query = $mysqli->query("SELECT * FROM Paziente");
    if($query->num_rows) {
        echo "<pre>";	// Il tag pre rende facilmente leggibile l'array
        print_r($query->fetch_all(MYSQLI_BOTH));
        echo "</pre>";
    } else {
        echo "Accesso rifiutato";
    }
    //$link = mysqli_connect($_SERVER['RDS_HOSTNAME'], $_SERVER['RDS_USERNAME'], $_SERVER['RDS_PASSWORD'], $_SERVER['RDS_DB_NAME'], $_SERVER['RDS_PORT']);
?>