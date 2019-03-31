function login(){
	
	var username = $("#username").val();
	var password = $("#password").val();
	
	if (username == '' || username.length != 11) {
		alert("账号不合法");
		return;
	}	
	if (password == '' || password.length < 6) {
		alert("密码不合法");
		return;
	}
	
	$.ajax({
		type : "POST",
		url : "http://127.0.0.1:8080/loginapi/login",
		data : {'username':username,'password':password},
		error : function(request) {
			parent.layer.alert("网络超时");
		},
		success : function(data) {
			console.log("data:"+data);
			if (data.code != 0) {
				alert("登录失败");
			}else {
				window.location.href="http://127.0.0.1:8080/api/order/get_add_order";
			}
		}
	});
	
}