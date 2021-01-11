
<?php
require_once '../includes/DbOperations.php';

$respnse = array();

if($_SERVER['REQUEST_METHOD']=='POST'){

 if (isset ($_POST['product_id']) and isset ($_POST['user_id']))
 
    {

	$db = new dbOperations();
	$result = $db->addOrder ($_POST['user_id'],$_POST['product_id']);

	if($result == 1){

		$response['error'] = false;
		$response['message'] = "Order Added";

	}else{

		$response['error'] = true;
		$response['message'] = "Order Failed";
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
