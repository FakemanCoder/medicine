//上一页
function lastPage() {
	var currentNum=document.getElementById('currentNum').innerText;
	if (parseInt(currentNum) == 1) {
		return;
	}
	$("#pageNum").val(parseInt(currentNum)-1);
	document.getElementById("medicine_form").submit();
}

//下一页
function nextPage() {
	
	var currentNum=document.getElementById('currentNum').innerText;
	var maxPage=document.getElementById('maxPage').value;
	if (parseInt(maxPage) <= parseInt(currentNum)) {
		return;
	}
	$("#pageNum").val(parseInt(currentNum)+1);
	document.getElementById("medicine_form").submit();
}


//停用或启用药品
function stopMedicine(medicineId,isRelease) {
	$.ajax({
		type : "POST",
		url : "http://127.0.0.1:8080/api/medicine/stopMedicine",
		data : {'medicineId':medicineId,'isRelease':isRelease},
		error : function(request) {
			parent.layer.alert("网络超时");
		},
		success : function(data) {
			console.log("data:"+data);
			if (data.code != 0) {
				alert("编辑失败");
			}else {
				alert("操作成功");
			}
			document.getElementById("medicine_form").submit();
		}
	});

}

//保存或编辑药品信息
function editMedicine() {
	
	var cnName=$("#cnName").val();
	var inPrice=$("#inPrice").val();
	var pkgPrice=$("#pkgPrice").val();
	var description=$("#description").val();
	var func=$("#func").val();
	var contentNum=$("#contentNum").val();
	var contentUnit=$("#contentUnit").val();
	var medicineId=$("#medicineId").val();
	if(cnName==''){
		alert("名称不合法");
		return;
	}else if(inPrice == '' || inPrice <= 0){
		alert("进货价不合法")
		return;
	}else if(pkgPrice == '' || pkgPrice <= 0){
		alert("售货价不合法")
		return;
	}else if(func == '') {
		alert("功能不能为空")
		return;
	}else if (contentNum == '' || contentNum <= 0){
		alert("请填写单位数量")
		return;
	}else if(contentUnit == '') {
		alert("请选择单位")
		return;
	}else {
		document.getElementById("edit_medicine_form").submit();
	}
}

