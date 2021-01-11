
<?php
require_once '../includes/DbOperations.php';

$respnse = array();

if($_SERVER['REQUEST_METHOD']=='POST'){
	if (isset ($_POST['pid']) and
			isset ($_POST['name']) and
				isset ($_POST['price']) and
					isset ($_POST['cid']))
{
	$db = new dbOperations();
	$result = $db->updateProduct ($_POST['pid'],$_POST['name'],$_POST['price'],$_POST['cid']);

	if($result == 1){

		$response['error'] = false;
		$response['message'] = "Product Updated Successfully";

	}else{

		$response['error'] = true;
		$response['message'] = "Update Failed";
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
