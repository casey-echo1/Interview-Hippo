document.getElementById('registerForm').addEventListener('submit', function(e) {
	let valid = true;
	const username = document.getElementById('username').value;
	const email = document.getElementById('email').value;
	const password = document.getElementById('password').value;
	const confirmPassword = document.getElementById('confirmPassword').value;

	// Username validation
	if (username.length < 3) {
		document.getElementById('usernameError').textContent = 'Username must be at least 3 characters long';
		valid = false;
	} else {
		document.getElementById('usernameError').textContent = '';
	}

	// Email validation
	if (!email.match(/^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]{2,7}$/)) {
		document.getElementById('emailError').textContent = 'Please enter a valid email address';
		valid = false;
	} else {
		document.getElementById('emailError').textContent = '';
	}

	// Password validation
	if (password.length < 8) {
		document.getElementById('passwordError').textContent = 'Password must be at least 8 characters long';
		valid = false;
	} else if (!password.match(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,}$/)) {
		document.getElementById('passwordError').textContent = 'Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character';
		valid = false;
	} else {
		document.getElementById('passwordError').textContent = '';
	}

	// Confirm password validation
	if (password !== confirmPassword) {
		document.getElementById('confirmPasswordError').textContent = 'Passwords do not match';
		valid = false;
	} else {
		document.getElementById('confirmPasswordError').textContent = '';
	}

	if (!valid) {
		e.preventDefault();
	}
});
