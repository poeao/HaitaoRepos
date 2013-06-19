<html>

<body>
	<h1 id="top">Query Result Output:</h1>
	<?php
		//print_r($_POST);
		$keyword = $_POST['word'];
		$sphinx = new SphinxClient();
		$sphinx->SetServer("localhost",9312);
        $sphinx->SetMatchMode(SPH_MATCH_ANY);
		$result = $sphinx->query("$keyword", "*");
		//echo "<pre>";
		//print_r($result);
		//echo "</pre>";
		//get query ids 
		//echo join(",",array_keys($result["matches"]));
		$ids = join(",",array_keys($result["matches"]));
		mysql_connect("localhost", "root","123456");
		mysql_query("set names utf8");
		mysql_select_db("test");
		$sql="select * from post where id in ($ids)";
		$rs = mysql_query($sql);
		$opts=array(
			"before_match"=>"<font style='font-weight:bold;color:red'>",
			"after_match"=>"</font>"
		);
		while($row=mysql_fetch_assoc($rs)) {
			//echo "<pre>";
			//print_r($row);
			//echo "</pre>";
			$rst2 = $sphinx->buildExcerpts($row,"main",$keyword,$opts);
			echo "<pre>";
			print_r($rst2);
			echo "</pre>";
		}
	?>
</body>

</html>