async function Login() {
    localStorage.setItem("jwt", "");
    let username = document.getElementById("user").value;
    let password = document.getElementById("pass").value;
    fetch("/api/auth/login", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        body: JSON.stringify({
            username: username,
            password: password
        })
    }).then(res => res.json()).then(res => {
        let data = res;
        if (!data.error) {
            localStorage.setItem("jwt", data.token);
            document.location.href = "/";
        } else {
            console.log(data.error);
            document.getElementById("error").innerHTML = data.error;
        }
    });
}