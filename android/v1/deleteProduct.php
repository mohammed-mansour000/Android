
<?php
require_once '../includes/DbOperations.php';

$respnse = array();

if($_SERVER['REQUEST_METHOD']=='POST'){

 if (isset ($_POST['pid']))
 
    {

	$db = new dbOperations();
	$result = $db->deleteProduct ($_POST['pid']);

	if($result == 1){

		$response['error'] = false;
		$response['message'] = "Product Deleted";

	}else{

		$response['error'] = true;
		$response['message'] = "Delete Failed";
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
