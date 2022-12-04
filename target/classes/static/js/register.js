async function Send() {
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;
    let name = document.getElementById("name").value;
    let phoneNumber = document.getElementById("phoneNumber").value;
    let email = document.getElementById("email").value;
        fetch("api/registration", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify({
                username: username,
                password: password,
                name: name,
                phoneNumber: phoneNumber,
                email: email
            })
        }).then(res => res.json()).then(res => {
            let data = res;
            if (!data.error) {
                document.location.href = "/login";
            } else {
                document.getElementById("error").innerHTML = data.error;
            }
        });
}