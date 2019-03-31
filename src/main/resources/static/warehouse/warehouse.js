//上一页
function lastPage() {
	var currentNum=document.getElementById('currentNum').innerText;
	if (parseInt(currentNum) == 1) {
		console.log("lastPage=")
		return;
	}
	$("#pageNum").val(parseInt(currentNum)-1);
	document.getElementById("warehouse_page_form").submit();
}

//下一页
function nextPage() {
	
	var currentNum=document.getElementById('currentNum').innerText;
	var maxPage=document.getElementById('maxPage').value;
	if (parseInt(maxPage) <= parseInt(currentNum)) {
		console.log("nextPage=")
		return;
	}
	console.log("11111:"+currentNum)
	$("#pageNum").val(parseInt(currentNum)+1);
	document.getElementById("warehouse_page_form").submit();

}

//进货
function editWarehouse() {

	var inPrice=$("#inPrice").val();
	var contentNum=$("#contentNum").val();
	var medicineId=$("#medicineId").val();
	if(inPrice=='' || inPrice<=0){
		alert("进货价不合法");
		return;
	}else if(contentNum == '' || contentNum<=0){
		alert("进货数量不合法")
		return;
	}else if(medicineId == '' || medicineId<=0){
		alert("进货失败")
		return;
	}else {
		document.getElementById("in_warehouse_form").submit();
	}
}


//损耗
function outWarehouse() {

	var outOrIn=$("#outOrIn").val();
	var inPrice=$("#inPrice").val();
	var contentNum=$("#contentNum").val();
	var medicineId=$("#medicineId").val();
	if(inPrice=='' || inPrice<=0){
		alert("进货价不合法");
		return;
	}else if(contentNum == '' || contentNum<=0){
		alert("进货数量不合法")
		return;
	}else if(medicineId == '' || medicineId<=0){
		alert("进货失败")
		return;
	}
	
	$.ajax({
		type : "POST",
		url : "http://127.0.0.1:8080/api/medicine/outWarehouse",
		data : {'medicineId':medicineId,'num':contentNum,'inPrice':inPrice,'outOrIn':outOrIn},
		error : function(request) {
			parent.layer.alert("网络超时");
		},
		success : function(data) {
			console.log("data:"+data);
			if (data.code != 0) {
				alert(data.msg);
			}else {
				window.location.href="http://127.0.0.1:8080/api/medicine/medicine_page";
			}
		}
	});
}
