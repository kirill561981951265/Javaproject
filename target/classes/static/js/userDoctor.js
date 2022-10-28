async function singOut(){
    localStorage.setItem("jwt", "");
    document.location.href = "/login";
}

function header(){return 'Bearer ' + localStorage.getItem("jwt");}

function errorPage(mes){return `<div><h3>${mes}</h3><h4><a href="/login">Pls, sing in</a></h4></div>`;
}

async function visProfile(){
    $('#divProfile').show();
    $('#divPets').hide();
}

async function  visPets(){
    $('#divProfile').hide();
    $('#divPets').show();

}

async function Save(role){
    if (role == 1)
        role = 'user';
    else role = 'doctor';
    fetch("api/" + role + "/save", {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization' : header()
        },
        body: JSON.stringify({
            id: id,
            username: $('#username').text(),
            name: $('#name').val(),
            phoneNumber: $('#phoneNumber').val(),
            email: $('#email').val()
        })
    }).then(res => res.json()).then(res => {
        if(res.status == 400){
            $('#mesP').text("Enter correct data");
        }else {
            if(res.status == 500){
                $('#div').html(errorPage("Authorisation Error"));
            }else {
                if (res.error) {
                    $('#mesP').text(res.error);
                } else {
                    $('#mesP').text("Successful");
                }
            }
        }
    });
}

async function getUser(role){
    $('#divProfile').hide();
    $('#divPets').show();
    let r;
    if (role == 1)
        r = 'user';
    else r = 'doctor';
    fetch("/api/" + r + "/about", {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization' : header()
        }
    }).then(res => res.json()).then(res => {
        if(res.status == 401)
            $('#div').html(errorPage("You are not authorized"));
        else {
            if (res.status == 403)
                $('#div').html(errorPage("Not enough access rights"));
            else {
                    if(res.status == 500){
                        $('#div').html(errorPage("Authorisation Error"));
                    }else {
                        let user = res;
                        id = user.id;
                        $('#username').text(user.username);
                        $('#name').text(user.name);
                        $('#phoneNumber').val(user.phoneNumber);
                        $('#email').val(user.email);
                        if (role == 1)
                            getPets();
                        else getCharacter();
                    }
            }
        }
    });
}

