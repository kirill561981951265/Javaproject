function errorPage(mes){return `<div><h3>${mes}</h3><h4><a href="/login">Pls, sing in</a></h4></div>`;
}

function header(){return 'Bearer ' + localStorage.getItem("jwt");}


async function add() {
    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;
    let name = document.getElementById("name").value;
    let phoneNumber = document.getElementById("phoneNumber").value;
    let email = document.getElementById("email").value;
    fetch("api/admin/add", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization' : header()
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
        if(res.status == 500){
            $('#div').html(errorPage("Authorisation Error"));
        }else {
            if (!data.error) {
                $(':input', '#form')
                    .not(':button, :submit, :reset, :hidden')
                    .val('')
                    .removeAttr('checked')
                    .removeAttr('selected');
                getUsers();
            } else {
                document.getElementById("error").innerHTML = data.error;
            }
        }
    });
}

async function getUsers(){
    document.getElementById("users").innerHTML = "";
    fetch("api/admin/users", {
        method: 'Get',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization' : header()
        }
    }).then(res => res.json()).then(res => {
        let str = 'Not Found';
        if(res.status == 401)
            $('#div').html(errorPage("You are not authorized"));
        else {
            if (res.status == 403)
                $('#div').html(errorPage("Not enough access rights"));
            else {
        if(res.status == 500){
            $('#div').html(errorPage("Authorisation Error"));
        }else {
            if (!res.error) {
                str = '<table>' +
                    '    <thead>' +
                    '    <tr>' +
                    '        <th>Username</th>' +
                    '        <th>Name</th>' +
                    '        <th>Phone nuumber</th>' +
                    '        <th>Email</th>' +
                    '        <th>Roles</th>' +
                    '    </tr>' +
                    '    </thead>' +
                    '    <tbody>';
                res.forEach(obj => {
                    str += '<tr>' +
                        '<td>' + obj.username + '</td>' +
                        '<td>' + obj.name + '</td>' +
                        '<td>' + obj.phoneNumber + '</td>' +
                        '<td>' + obj.email + '</td>' +
                        '<td>' + obj.roles + '</td>' +
                        '</tr>';
                });
                str += '</tbody></table>';
            }
            document.getElementById("users").innerHTML = str;
        }
        }
        }
    });
}