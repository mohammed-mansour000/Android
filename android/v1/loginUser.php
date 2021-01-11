
<?php
require_once '../includes/DbOperations.php';
$respnse = array();
if($_SERVER['REQUEST_METHOD']=='POST'){
	if (isset ($_POST['username']) and	isset ($_POST['password'] ))
    {

	$db = new dbOperations();
	$result = $db->userLogin ($_POST['username'],$_POST['password']);

	if($result == 1){

		$response['error'] = false;
        $response['message'] = "Admin Logined successfully";
        $response['userStatus'] = 1;

    }else if($result == 2){

		$response['error'] = false;
        $response['message'] = "User Logined successfully";
		$response['userStatus'] = 0;
		$response['user_id'] = $db->getUserID($_POST['username'],$_POST['password']);
        
	}else{

		$response['error'] = true;
        $response['message'] = "Invalid Username or Password";
        
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
