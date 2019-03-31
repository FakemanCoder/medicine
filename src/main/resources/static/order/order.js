$(window).on('load', function () {  
	$('.selectpicker').selectpicker({  
		'selectedText': 'cat'
	});
	// $('.selectpicker').selectpicker('hide');  
});

//上一页
function lastPage() {
	var currentNum=document.getElementById('currentNum').innerText;
	if (parseInt(currentNum) == 1) {
		console.log("lastPage=")
		return;
	}
	$("#pageNum").val(parseInt(currentNum)-1);
	document.getElementById("user_form").submit();
	
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
	document.getElementById("user_form").submit();

}

//添加订单药品
function addOrderDetail(){
	var num = $("#num").val();
	var medicineId = $("#medicineId").val();
	
	if (num == '' || num <= 0) {
		alert("数量不合法");
		return;
	}
	
	$.ajax({
		type : "POST",
		url : "http://127.0.0.1:8080/api/order/add_order_detail",
		data : {'medicineId':medicineId,'num':num},
		error : function(request) {
			parent.layer.alert("网络超时");
		},
		success : function(data) {
			console.log("data:"+data);
			if (data.code != 0) {
				alert("添加失败");
			}else {
				window.location.href="http://127.0.0.1:8080/api/order/get_add_order";
			}
		}
	});
}


//删除所有订单药品
function delAllOrderDetail(){

	$.ajax({
		type : "POST",
		url : "http://127.0.0.1:8080/api/order/del_all_order_detail",
		error : function(request) {
			parent.layer.alert("网络超时");
		},
		success : function(data) {
			console.log("data:"+data);
			if (data.code != 0) {
				alert("删除失败");
			}else {
				window.location.href="http://127.0.0.1:8080/api/order/get_add_order";
			}
		}
	});
}

//删除订单药品
function delOrderDetail(orderDetailId){
	$.ajax({
		type : "POST",
		url : "http://127.0.0.1:8080/api/order/del_order_detail",
		data : {"orderDetailId":orderDetailId},
		error : function(request) {
			parent.layer.alert("网络超时");
		},
		success : function(data) {
			console.log("data:"+data);
			if (data.code != 0) {
				alert("删除失败");
			}else {
				window.location.href="http://127.0.0.1:8080/api/order/get_add_order";
			}
		}
	});
}

//添加总订单药品
function addOrder(){
	var sumPrice = $("#sumPrice").text();
	if (sumPrice=='' || sumPrice <= 0) {
		alert("请先添加药品");
		return;
	}
	console.log("sumPrice="+sumPrice);
	$.ajax({
		type : "POST",
		url : "http://127.0.0.1:8080/api/order/add_order",
		data : {'price':sumPrice},
		error : function(request) {
			alert("网络超时");
		},
		success : function(data) {
			console.log("data:"+data);
			if (data.code != 0) {
				alert("操作失败");
			}else {
				alert("药品发放成功");
				window.location.href="http://127.0.0.1:8080/api/order/get_add_order";
			}
		}
	});
}


//退货
function returnOrderDetail(orderDetailId){
	var orderSumId = $("#orderSumId").val();
	$.ajax({
		type : "POST",
		url : "http://127.0.0.1:8080/api/order/return_order_detail",
		data : {'orderDetailId':orderDetailId},
		error : function(request) {
			alert("网络超时");
		},
		success : function(data) {
			console.log("data:"+data);
			if (data.code != 0) {
				alert("操作失败");
			}else {
				alert("退货成功");
				window.location.href="http://127.0.0.1:8080/api/order/get_order_detail?orderSumId="+orderSumId;
			}
		}
	});
}


