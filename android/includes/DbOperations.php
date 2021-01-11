<?php 

	class DbOperations{

		private $con; 

		function __construct(){

			require_once dirname(__FILE__).'/DbConnect.php';

			$db = new DbConnect();

			$this->con = $db->connect();

		}

		/*CRUD -> C -> CREATE */

		public function createUser($username, $pass, $email){
			if($this->isUserExist($username,$email)){
				return 0; 
			}else{
				$password = md5($pass);
				$stmt = $this->con->prepare("INSERT INTO `users` (`id`, `username`, `password`, `email`) VALUES (NULL, ?, ?, ?);");
				$stmt->bind_param("sss",$username,$password,$email);

				if($stmt->execute()){
					return 1; 
				}else{
					return 2; 
				}
			}
		}

		public function userLogin($username, $pass){
			$password = md5($pass);
			$stmt = $this->con->prepare("SELECT userStatus FROM users WHERE username = ? AND password = ?");
			$stmt->bind_param("ss",$username,$password);
			$stmt->execute();
	
	        $check = $stmt->get_result(); 
	        $data = $check->fetch_assoc();
		

			if($check->num_rows > 0 and $data['userStatus'] == 1){

				return 1;

			}else if($check->num_rows > 0 and $data['userStatus'] == 0){

				return 2;

			}else{

				return 0; 
				
			}
			
		}

		public function updateProduct($pid, $name, $price, $cid){

			$stmt = $this->con->prepare("UPDATE products  set name = ?, price = ?, cid = ? WHERE pid = ?");
			$stmt->bind_param("sdii",$name,$price,$cid,$pid);

			if($stmt->execute()){

				return 1; 

			}else{

				return 2; 

			}

			// }
			// if($check->num_rows > 0 and $data['userStatus'] == 1){

			// 	return 1;

			// }else if($check->num_rows > 0 and $data['userStatus'] == 0){

			// 	return 2;

			// }else{

			// 	return 0; 
				
			// }
			
		}

		public function deleteProduct($pid){

			$stmt = $this->con->prepare("DELETE FROM products WHERE pid = ?");
			$stmt->bind_param("i",$pid);

			if($stmt->execute()){

				return 1; 

			}else{

				return 2; 

			}
			
		}

		public function deleteProductFromCart($order_id){

			$stmt = $this->con->prepare("DELETE FROM orders WHERE order_id = ?");
			$stmt->bind_param("i",$order_id);

			if($stmt->execute()){

				return 1; 

			}else{

				return 2; 

			}
			
		}

		public function addOrder($user_id,$product_id){

			$stmt = $this->con->prepare("INSERT INTO `orders` (`order_id`, `user_id`, `product_id`) VALUES (NULL, ?, ?);");
			$stmt->bind_param("ii",$user_id,$product_id);

			if($stmt->execute()){

				return 1; 

			}else{

				return 2; 

			}
			
		}


		public function getUserID($username, $pass){

			$password = md5($pass);
			$stmt = $this->con->prepare("SELECT id FROM users WHERE username = ? AND password = ?");
			$stmt->bind_param("ss",$username,$password);
			$stmt->execute();
			$check = $stmt->get_result(); 
			$data = $check->fetch_assoc();
			
			return $data['id'];
		}


		public function getUserByUsername($username){
			$stmt = $this->con->prepare("SELECT * FROM users WHERE username = ?");
			$stmt->bind_param("s",$username);
			$stmt->execute();
			return $stmt->get_result()->fetch_assoc();
		}
		

		private function isUserExist($username, $email){
			$stmt = $this->con->prepare("SELECT id FROM users WHERE username = ? OR email = ?");
			$stmt->bind_param("ss", $username, $email);
			$stmt->execute(); 
			$stmt->store_result(); 
			return $stmt->num_rows > 0; 
		}

	}