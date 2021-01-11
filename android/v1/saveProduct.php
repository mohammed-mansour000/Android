<?php
$pid = addslashes(strip_tags($_POST['pid']));
$name = addslashes(strip_tags($_POST['name']));
$quantity = addslashes(strip_tags($_POST['quantity']));
$price = addslashes(strip_tags($_POST['price']));
$cid = addslashes(strip_tags($_POST['cid']));
$key = addslashes(strip_tags($_POST['key']));

if ($key != "cuBubcDE" or trim($name) == "")
    die("access denied");

$con = mysqli_connect("localhost","id11880858_user2", "l00K@the****","id11880858_company");
// Check connection
if (mysqli_connect_errno())
  {
  echo "Failed to connect to MySQL: " . mysqli_connect_error();
  }

$sql = "insert into products values ($pid, '$name', $quantity, $price, $cid)";
mysqli_query($con,$sql) or
    die ("can't add record");

echo "Record Added";
   
mysqli_close($con);
?> 			