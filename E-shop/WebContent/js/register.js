/**
 * 
 */
function jump() {
	alert("注册成功！");
	window.location.reload = "e_shop.jsp"
}

function beforeSubmit(form) {
	if (form.username.value == '') {
		alert('用户名不能为空！');
		form.username.focus();
		return false;
	}
	if (form.password.value == '') {
		alert('密码不能为空！');
		form.password.focus();
		return false;
	}
	if (form.password.value.length < 6) {
		alert('密码至少为6位，请重新输入！');
		form.password.focus();
		return false;
	}
	if (form.password.value != form.repassword.value) {
		alert('你两次输入的密码不一致，请重新输入！');
		form.repassword.focus();
		return false;
	}
	var encryptor = new JSEncrypt() // 创建加密对象实例
	var publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDstiYKiePxsEJOiLskFkxwcGPC"
		+ "SUIHE4cmO0WWzC60zRQYEkUxRauwQoVbe+6wiVgSyDn3yvezfdMja8wTPaUsPh3i"
		+ "GYJPWAxZlH5fjv5G02r73LbFJLgveUIjSgykN50smt6S4+fzXg41nrp5Vasg9t9S"
		+ "NzjjU7XTYSG+75EuJwIDAQAB";
	encryptor.setPublicKey(publicKey);//设置公钥
	form.enpassword.value = encryptor.encrypt(form.password.value);
	form.enusername.value = encryptor.encrypt(form.username.value);
	var a=form.password.value.length;
	form.username.value = '';
	form.password.value = '';
	for (var i = 0; i < a; i++) {
		form.password.value = form.password.value + '.';
	}
	return true;
}