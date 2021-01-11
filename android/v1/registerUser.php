
<?php
require_once '../includes/DbOperations.php';
$respnse = array();
if($_SERVER['REQUEST_METHOD']=='POST'){
	if (isset ($_POST['username']) and
	isset ($_POST['email']) and
	isset ($_POST['password'] ))
{
	$db = new dbOperations();
	$result = $db->createUser ($_POST['username'],$_POST['password'],$_POST['email']);

	if($result == 1){

		$response['error'] = false;
		$response['message'] = "User registered successfully";
		$response['user_id'] = $db->getUserID($_POST['username'],$_POST['password']);

	}else if($result == 2){

		$response['error'] = true;
		$response['message'] = "Error occurred during registeration";
	}else{

		$response['error'] = true;
		$response['message'] = "User Already Exists";
	}

}else{
	$response['error'] = true;
	$response['message'] = "Some error occurred ";
}

}else{
	$response ['error'] = true;
	$response ['message'] = "Required fields are missing";
}
echo json_encode($response);

 ?>
