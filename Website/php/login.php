<?php	
	try{
		$host = "db.ist.utl.pt";
		$user = "ist178982";
		$password = "password2";
		$dbname = $user; 

		$db = new PDO("mysql:host=$host;dbname=$dbname", $user, $password);
		$db->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
		
		
		$inputEmail=$_POST['email'];
		$inputPass=$_POST['password'];
		
		//escape things
		
		//get user with pass
		
		
		$query = "SELECT * FROM User WHERE email='$inputEmail' and password='$inputPass';";
		$result = $db->query($query);
		
		
		if($inputEmail=="cenas@gmail.com" && $inputPass=="cenas"){
			header("Location: ../home.html");
		}else{
			header("Location: ../home.html");
		}
		
	}catch(PDOException $e){
		echo("<p>ERROR: {$e->getMessage()}</p>");
	}
	
?>