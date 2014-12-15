<?php
include("../b/conexao.php");

function json_encode_string($in_str)
  {
    
    mb_internal_encoding("UTF-8");
    $convmap = array(0x80, 0xFFFF, 0, 0xFFFF);
    $str = "";
    for($i=mb_strlen($in_str)-1; $i>=0; $i--)
    {
      $mb_char = mb_substr($in_str, $i, 1);
      if(mb_ereg("&#(\\d+);", mb_encode_numericentity($mb_char, $convmap, "UTF-8"), $match))
      {
        $str = sprintf("\\u%04x", $match[1]) . $str;
      }
      else 
      {
        $str = $mb_char . $str;
      }
    }
    return $str;
  }
  function php_json_encode($arr)
  {
    $json_str = "";
    if(is_array($arr))
    {
      $pure_array = true;
      $array_length = count($arr);
      for($i=0;$i<$array_length;$i++)
      {
        if(! isset($arr[$i]))
        {
          $pure_array = false;
          break;
        }
      }
      if($pure_array)
      {
        $json_str ="[";
        $temp = array();
        for($i=0;$i<$array_length;$i++)       
        {
          $temp[] = sprintf("%s", php_json_encode($arr[$i]));
        }
        $json_str .= implode(",",$temp);
        $json_str .="]";
      }
      else
      {
        $json_str ="{";
        $temp = array();
        foreach($arr as $key => $value)
        {
          $temp[] = sprintf("\"%s\":%s", $key, php_json_encode($value));
        }
        $json_str .= implode(",",$temp);
        $json_str .="}";
      }
    }
    else
    {
      if(is_string($arr))
      {
        $json_str = "\"". htmlspecialchars($arr) . "\"";
      }
      else if(is_numeric($arr))
      {
        $json_str = $arr;
      }
      else
      {
        $json_str = "\"". htmlspecialchars($arr) . "\"";
      }
    }
    return $json_str;
  }

//http://www.bign.com.br/nb/az.php?op=crianot&nottag="+nottag+"&email="+contas[0].name +"&titulo="+titulo+"&msg="+msg+"&opcoes="+opcoes

//http://www.bign.com.br/nb/az.php?responder=resp&idm="+idm+"&email="+contas[0].name

//http://www.bign.com.br/nb/az.php?contar="+nottag+"&email="+contas[0].name

if(isset($_GET["enviaNot"]))
{
	$up ="update metaresposta set publicado=1 where metarespostaId='".$_GET["enviaNot"]."'";
	$qup=mysql_query($up);
}

if(isset($_GET["contaresp"]))
{

	$s = "select metaRespostaDescricao from metaresposta where metaRespostaId='$_GET[contaresp]'";
	$q = mysql_query($s) or die(mysql_error());
	$r = mysql_fetch_assoc($q);
	$valores = explode("[v]",$r["metaRespostaDescricao"]);
		array_pop($valores);
		$array =array();

		foreach($valores as $i =>$v)
		{
			$linha = explode("[t]",$v); 

			$array[$linha[0]] = $linha[1];
		
		}
	
	
	$ops = explode(" ",$array["opcoes"]);


	$total = 0;
	foreach($ops as $id => $resp)
	{	
 
	$sseg = "select count(*) as TOTAL from metaresposta where metaId='67' and  metaRespostaDescricao like '%idnot[t]$_GET[contaresp][v]%' and  metaRespostaDescricao like '%resposta[t]".$resp."[v]%' and publicado=1";
	$qseg = mysql_query($sseg);
	$rseg = mysql_fetch_assoc($qseg);

	$tags["respostas"][]="".$rseg["TOTAL"]==""?"0":$rseg["TOTAL"] . "";
	$total+=$rseg["TOTAL"];

	
	}
	$tags["total"]=$total;
 	mb_http_output("UTF-8");
	ob_start("mb_output_handler");
	echo php_json_encode($tags); 
	exit();
} 

if(isset($_GET["contar"]))
{
 
	$sseg = "select count(*) as TOTAL from metaresposta where metaId='65' and  metaRespostaDescricao like '%nottag[t]$_GET[contar][v]%' and publicado=1";
	$qseg = mysql_query($sseg);
	$rseg = mysql_fetch_assoc($qseg);
	
	
 	mb_http_output("UTF-8");
	ob_start("mb_output_handler");
	echo php_json_encode($rseg);
	exit();
} 

if($_GET["op"]=="crianot")
{
 
	$pub="1";
	if($_GET["foto"]=="S")
	$pub="0";
	
//&certa="+certa+"&subtag="+subtag+"&dagenda="+dagenda qresp
$dagenda = $_GET["dagenda"];
	
	$in = "insert into metaresposta (metaRespostaId,metaRespostaDescricao,publicado,metaRespostaData,metaRespostaValor,metaRespostaOpcao,usuarioId,metaId) values
	('','email[t]".$_GET["email"]."[v]nottag[t]".$_GET["nottag"]."[v]titulo[t]".$_GET["titulo"]."[v]mensagem[t]".$_GET["msg"]."[v]opcoes[t]".$_GET["opcoes"]."[v]foto[t]".$_GET["foto"]."[v]certa[t]".$_GET["certa"]."[v]subtag[t]".$_GET["subtag"]."[v]qresp[t]".$_GET["qresp"]."[v]','$pub',NOW(),'1','MAIS','9','66')";
	$qin = mysql_query($in) or die(mysql_error());
	

	
	$sin = "select metaRespostaId as IDM, metaRespostaData as DATA from metaresposta where metaRespostaId='".mysql_insert_id()."'";
	
	
	$qin = mysql_query($sin);
	$rin = mysql_fetch_assoc($qin);
	
	
		$dagenda = implode("-",array_reverse(explode("/",substr($dagenda,0,10)))). substr($dagenda,10);
		$ia = "insert into magenda values('".$rin["IDM"]."','$dagenda')";
		$qia = mysql_query($ia);
	
 
	
	$rin["DATA"]=implode("/",array_reverse(explode("-",substr($rin["DATA"],0,10)))) . substr($rin["DATA"],10);
 	mb_http_output("UTF-8");
	ob_start("mb_output_handler");
	echo php_json_encode($rin);
	exit();
} 

if(isset($_GET["responder"]))
{
 
	
	
	$in = "insert into metaresposta (metaRespostaId,metaRespostaDescricao,publicado,metaRespostaData,metaRespostaValor,metaRespostaOpcao,usuarioId,metaId) values
	('','email[t]".$_GET["email"]."[v]idnot[t]".$_GET["idnot"]."[v]resposta[t]".$_GET["responder"]."[v]','1',NOW(),'1','MAIS','9','67')";
	$qin = mysql_query($in) or die(mysql_error());

	
	$sin = "select metaRespostaId as IDM, metaRespostaData as DATA from metaresposta where metaRespostaId='".mysql_insert_id()."'";
	$qin = mysql_query($sin);
	$rin = mysql_fetch_assoc($qin);
 
	
	$rin["DATA"]=implode("/",array_reverse(explode("-",substr($rin["DATA"],0,10)))) . substr($rin["DATA"],10);
 	mb_http_output("UTF-8");
	ob_start("mb_output_handler");
	echo php_json_encode($rin);
	exit();
} 

if($_GET["op"]=="crianot")
{
 
	
	
	$in = "insert into metaresposta (metaRespostaId,metaRespostaDescricao,publicado,metaRespostaData,metaRespostaValor,metaRespostaOpcao,usuarioId,metaId) values
	('','email[t]".$_GET["email"]."[v]nottag[t]".$_GET["nottag"]."[v]titulo[t]".$_GET["titulo"]."[v]mensagem[t]".$_GET["msg"]."[v]opcoes[t]".$_GET["opcoes"]."[v]','1',NOW(),'1','MAIS','9','66')";
	$qin = mysql_query($in) or die(mysql_error());

	
	$sin = "select metaRespostaId as IDM, metaRespostaData as DATA from metaresposta where metaRespostaId='".mysql_insert_id()."'";
	$qin = mysql_query($sin);
	$rin = mysql_fetch_assoc($qin);
 
	
	$rin["DATA"]=implode("/",array_reverse(explode("-",substr($rin["DATA"],0,10)))) . substr($rin["DATA"],10);
 	mb_http_output("UTF-8");
	ob_start("mb_output_handler");
	echo php_json_encode($rin);
	exit();
} 

if($_GET["tags"]=="msg")
{
	$sseg = "select m.metaRespostaDescricao,m.metaRespostaId,m.metaRespostaData,(select dagenda from magenda where idm=m.metaRespostaId) from metaresposta m where m.metaId='66' and  m.metaRespostaDescricao like '%email[t]$_GET[email][v]%' and m.metaRespostaDescricao like '%nottag[t]$_GET[nottag][v]%' and m.publicado=1";
 

	$qseg=mysql_query($sseg) or die(mysql_error());
	$array=array();
	while ($rseg = mysql_fetch_assoc($qseg))
	{
		$valores = explode("[v]",$rseg["metaRespostaDescricao"]);
		array_pop($valores);
		$array =array();
		foreach($valores as $i =>$v)
		{
			$linha = explode("[t]",$v); 

			$array[$linha[0]] = $linha[1];
		
		}
		$tag["nottags"][]=$array["nottag"];
		$tag["titulos"][]=$array["titulo"];
		$tag["mensagens"][]=$array["mensagem"];
		$tag["idms"][]=$rseg["metaRespostaId"];
		$tag["opcoes"][]=$array["opcoes"];
		$tag["datas"][]= implode("/",array_reverse(explode("-",substr($rseg["metaRespostaData"],0,10)))) . substr($rseg["metaRespostaData"],10);
		$tag["certas"][]=$array["certa"]?$array["certa"]:"";
		$tag["dagendas"][]=$rseg["dagenda"]?$rseg["dagenda"]:"";
		$tag["subtags"][]=$array["subtag"]?$array["subtag"]:"";
		$tag["qresps"][]=$array["qresp"]?intval($array["qresp"]):0;
		
		
	}

	mb_http_output("UTF-8");
	ob_start("mb_output_handler");
	
	echo php_json_encode($tag);
	exit();
}




if($_GET["op"]=="pode")
{
	$sseg = "select metaRespostaDescricao from metaresposta where metaId='64' and  metaRespostaDescricao like '%nottag[t]$_GET[nottag][v]%' and publicado=1";
 
	$qseg=mysql_query($sseg) or die(mysql_error());
	
	

	if(mysql_num_rows($qseg)>0)
	$tag["PODE"]="0";
	else
	{
	
	$in = "insert into metaresposta (metaRespostaId,metaRespostaDescricao,publicado,metaRespostaData,metaRespostaValor,metaRespostaOpcao,usuarioId,metaId) values
	('','dono[t]".$_GET["email"]."[v]nottag[t]".$_GET["nottag"]."[v]','1',NOW(),'1','MAIS','9','64')";
	$qin = mysql_query($in);
	
	$tag["PODE"]="1";
	}
	 
 	mb_http_output("UTF-8");
	ob_start("mb_output_handler");
	echo php_json_encode($tag);
	exit();
}

if($_GET["tags"]=="criei")
{
	$sseg = "select metaRespostaDescricao from metaresposta where metaId='64' and  metaRespostaDescricao like '%dono[t]$_GET[email][v]%' and publicado=1";
 

	$qseg=mysql_query($sseg) or die(mysql_error());
	$array=array();
	while ($rseg = mysql_fetch_assoc($qseg))
	{
		$valores = explode("[v]",$rseg["metaRespostaDescricao"]);
		array_pop($valores);
		$array =array();
		foreach($valores as $i =>$v)
		{
			$linha = explode("[t]",$v); 

			$array[$linha[0]] = $linha[1];
		
		}
		$tag["tags"][]=$array["nottag"];
	}
	mb_http_output("UTF-8");
	ob_start("mb_output_handler");
	
	echo php_json_encode($tag);
	exit();
}



if($_GET["segue"])
{
	
	$sseg = "select metaRespostaDescricao from metaresposta where metaId='64' and  metaRespostaDescricao like '%nottag[t]$_GET[nottag][v]%' and publicado=1";
	
		$sja = "select metaRespostaDescricao from metaresposta where metaId='65' and  metaRespostaDescricao like '%nottag[t]$_GET[nottag][v]%' and metaRespostaDescricao like '%seguidor[t]$_GET[segue][v]%' and publicado=1";


		$qja = mysql_query($sja);
		if(mysql_num_rows($qja)>0)
		{
		$tag["PODE"]="2";
		echo php_json_encode($tag);
		exit();
		}

 

	$qseg=mysql_query($sseg) or die(mysql_error());
	
	

	if(mysql_num_rows($qseg)>0)
	{
	$in = "insert into metaresposta (metaRespostaId,metaRespostaDescricao,publicado,metaRespostaData,metaRespostaValor,metaRespostaOpcao,usuarioId,metaId) values
	('','seguidor[t]".$_GET["segue"]."[v]nottag[t]".$_GET["nottag"]."[v]','1',NOW(),'1','MAIS','9','65')";
	$qin = mysql_query($in);
	$tag["PODE"]="1";
	}
	else
	{
		$tag["PODE"]="0";
	}
	mb_http_output("UTF-8");
	ob_start("mb_output_handler");
	echo php_json_encode($tag);
	exit();
	
	
}

if($_GET["tags"]=="sigo")
{
	$sseg = "select metaRespostaDescricao from metaresposta where metaId='65' and  metaRespostaDescricao like '%seguidor[t]$_GET[email][v]%' and publicado=1";
 

	$qseg=mysql_query($sseg) or die(mysql_error());
	$array=array();
	while ($rseg = mysql_fetch_assoc($qseg))
	{
		$valores = explode("[v]",$rseg["metaRespostaDescricao"]);
		array_pop($valores);
		$array =array();
		foreach($valores as $i =>$v)
		{
			$linha = explode("[t]",$v); 

			$array[$linha[0]] = $linha[1];
		
		}
		$tag["tags"][]=$array["nottag"];
	}

	mb_http_output("UTF-8");
	ob_start("mb_output_handler");
	echo php_json_encode($tag);
	exit();
}



	if($_GET["idm"])
	{
	$s = "update m set mlida=1 where mid=$_GET[idm] and memail='$_GET[email]'";
	$q = mysql_query($s);
	exit();
	}
	$s = "select metarespostaId as CHAVE,metaRespostaDescricao,metaRespostaData,(select dagenda from magenda where idm=r.metarespostaId) as dagenda from metaresposta r join meta m on (m.metaId=r.metaId) where r.publicado=1 and r.metaId=66";
	
	$sseg = "select metaRespostaDescricao,metaRespostaData from metaresposta where metaId='65' and  metaRespostaDescricao like '%seguidor[t]$_GET[email][v]%' and publicado=1";
 
	$qseg=mysql_query($sseg) or die(mysql_error());
	$tSegue="";
	while ($rseg = mysql_fetch_assoc($qseg))
	{
		$valores = explode("[v]",$rseg["metaRespostaDescricao"]);
		array_pop($valores);
		$array =array();
		foreach($valores as $i =>$v)
		{
			$linha = explode("[t]",$v); 

			$array[$linha[0]] = $linha[1];
		
		}
		
		$tSegue.="r.metaRespostaDescricao like '%nottag[t]".$array["nottag"]."[v]%' or ";
		
	}
	$s.=" and (".substr($tSegue,0,-4).")";
	
	$s.=" and (DATE_ADD(NOW(), INTERVAL -1 HOUR)>(select dagenda from magenda where idm=r.metarespostaId) )
	and not exists (select 1 from m where mchave=r.metarespostaId and memail='$_GET[email]') ";
	
	$s.=" order by dagenda asc limit 0,1";


	$q = mysql_query($s);

	$r = mysql_fetch_assoc($q);


	if(mysql_num_rows($q)>0) 
	{
		$s = "select * from m where mchave='".$r["CHAVE"]."' and memail='$_GET[email]'";
		$q = mysql_query($s);

		if(mysql_num_rows($q)==0)
		{
		$array = array();
		$valores = explode("[v]",$r["metaRespostaDescricao"]);
		array_pop($valores);
		foreach($valores as $i =>$v)
		{
			$linha = explode("[t]",$v); 

			$array[$linha[0]] = $linha[1];
		
		}
		
		
		$in = "insert into m values('','".$array["titulo"]."','".$array["nottag"]."','".$array["mensagem"]."',0,". $r["CHAVE"] .",'$_GET[email]','".$r["metaRespostaData"]."','".$array["opcoes"]."','".$array["foto"]."')";
		$qin = mysql_query($in);
#		echo $in;
		}
		
	}	
	
	$s = "select mid as IDM,mcab as NOTTAG,mdesc as TITULO,mtexto as MSG,mdata as DATA,mopcoes as OPCOES,mchave as IDNOT,mfoto as TEMFOTO from m where mlida=0 and memail='$_GET[email]'";
	$q = mysql_query($s);
	$r = mysql_fetch_assoc($q);
	$r["DATA"]= implode("/",array_reverse(explode("-",substr($r["DATA"],0,10)))) . substr($r["DATA"],10);

	if(mysql_num_rows($q)>0)
	{
	
	
	mb_http_output("UTF-8");
	ob_start("mb_output_handler");
	echo php_json_encode($r);
	}
	else
	{
		$r["NOTTAG"]="";
		$r["TITULO"]="";
		$r["TEMFOTO"]="";
		$r["MSG"]=""; 
		$r["DATA"]=""; 
		$r["OPCOES"]=""; 
		$r["IDM"]="0"; 
		$r["IDNOT"]="0"; 
		echo php_json_encode($r);
	}
	
	


?>