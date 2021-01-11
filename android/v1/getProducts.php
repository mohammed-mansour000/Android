<?php
header("Cache-Control: no-store, no-cache, must-revalidate, max-age=0");
header("Cache-Control: post-check=0, pre-check=0", false);
header("Pragma: no-cache");

$con=mysqli_connect("localhost","id15747809_api_database", "V~y}wE|LTO~*}7BH","id15747809_finaldatabasep");
// Check connection
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

$sql = "select products.pid, products.name, products.quantity, products.price, categories.name category
from products
INNER JOIN categories on products.cid = categories.cid";
if ($result = mysqli_query($con,$sql))
  {
   $emparray = array();
   while($row =mysqli_fetch_assoc($result))
       $emparray[] = $row;

  echo(json_encode($emparray));
  // Free result set
  mysqli_free_result($result);
  mysqli_close($con);
}

?> 	