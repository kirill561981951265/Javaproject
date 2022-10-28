var id;
var petid;
var page = 0;

async function Add(){
    fetch("api/pet/add", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization' : header()
        },
        body: JSON.stringify({
            idUser: id,
            name: $('#nameP').val(),
            type: $('#typeP option:selected').text(),
            breed: $('#breedP').val(),
            gender: $('#genderP option:selected').text(),
            bday: $('#bdayP').val().toString()
        })
    }).then(res => res.json()).then(res => {
        if(res.status == 400){
            $('#mesPet').text("Enter correct data");
        }else {
            if(res.status == 500){
                $('#div').html(errorPage("Authorisation Error"));
            }else {
                if (res.error) {
                    $('#mesPet').text(res.error);
                } else {
                    $('#mesPet').text("Successful");
                    $(':input', '#form')
                        .not(':button, :submit, :reset, :hidden')
                        .val('')
                        .removeAttr('checked')
                        .removeAttr('selected');
                    getPets();
                }
            }
        }
    });
}

async function getPets(){
    document.getElementById("listP").innerHTML = "";
    document.getElementById("historyPet").innerHTML = "";
    fetch("api/pet/user/" + id, {
        method: 'Get',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization' : header()
        }
    }).then(res => res.json()).then(res => {
        let str = 'Not Found';
        if(res.status == 500){
            $('#div').html(errorPage("Authorisation Error"));
        }else {
            if (!res.error) {
                str = '<table>' +
                    '    <thead>' +
                    '    <tr>' +
                    '        <th>Name</th>' +
                    '        <th>Type</th>' +
                    '        <th>Breed</th>' +
                    '        <th>Gender</th>' +
                    '        <th>Bday</th>' +
                    '        <th>History</th>' +
                    '        <th>Delete</th>' +
                    '    </tr>' +
                    '    </thead>' +
                    '    <tbody>';
                res.forEach(obj => {
                    str += '<tr>' +
                        '<td>' + obj.name + '</td>' +
                        '<td>' + obj.type + '</td>' +
                        '<td>' + obj.breed + '</td>' +
                        '<td>' + obj.gender + '</td>' +
                        '<td>' + obj.bday + '</td>' +
                        '<td><button onclick="historyPet(' + obj.id + ')">Open</button> </td>' +
                        '<td><button onclick="deletePet(' + obj.id + ')">X</button> </td>' +
                        '</tr>';
                });
                str += '</tbody></table>' +
                    '<h4>Select sort to history:</h4>' +
                    '<select id="sort" onselect="historyPet();">\n' +
                    '<option>asc</option>\n' +
                    '<option>desc</option>\n' +
                    '</select><br/>';
            }
            document.getElementById("listP").innerHTML = str;
        }
    });
}

async function deletePet(id){
    var del = confirm("Delete?");
    if (del == false)
        return;
    fetch("/api/pet/" + id, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization' : header()
        }
    }).then(res => res.json()).then(res => {
        if(res.status == 500){
            $('#div').html(errorPage("Authorisation Error"));
        }else {
            getPets();
        }
    });
}

async function historyPet(id){
    petid = id;
    let sort;
    if($('#sort option:selected').text() == "asc")
        sort = 1;
    else sort = 2;
    document.getElementById("historyPet").innerHTML = "";
    fetch("api/pet/history/"+ id + "?sort=" + sort + "&page=" + page, {
        method: 'Get',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization' : header()
        }
    }).then(res => res.json()).then(res => {
        let str = 'Not Found';
        if(res.status == 500){
            $('#div').html(errorPage("Authorisation Error"));
        }else {
            if (!res.error) {
                str = '<div id="apps"><table>' +
                    '    <thead>' +
                    '    <tr>' +
                    '        <th>Date</th>' +
                    '        <th>Condition</th>' +
                    '        <th>Diagnosis</th>' +
                    '        <th>Open</th>' +
                    '    </tr>' +
                    '    </thead>' +
                    '    <tbody>';
                res.forEach(obj => {
                    str += '<tr>' +
                        '<td>' + obj.date + '</td>' +
                        '<td>' + obj.complaints + '</td>' +
                        '<td>' + obj.diagnosis + '</td>' +
                        '<td><a href="/api/appointment/' + obj.id + '">Open</a></td>' +
                        '</tr>';
                });
                str += '</tbody></table></div><button onclick="back()">Back</button></br><button onclick="next()">Next</button>';
            }
            document.getElementById("historyPet").innerHTML = str;
        }
    });
}

async function back(){
    if (page < 1)
        return;
    else --page;
    let sort;
    if($('#sort option:selected').text() == "asc")
        sort = 1;
    else sort = 2;
    document.getElementById("apps").innerHTML = "";
    fetch("api/pet/history/"+ petid + "?sort=" + sort + "&page=" + page, {
        method: 'Get',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization' : header()
        }
    }).then(res => res.json()).then(res => {
        let str = 'Not Found';
        if(res.status == 500){
            $('#div').html(errorPage("Authorisation Error"));
        }else {
            if (!res.error) {
                str = '<table>' +
                    '    <thead>' +
                    '    <tr>' +
                    '        <th>Date</th>' +
                    '        <th>Condition</th>' +
                    '        <th>Diagnosis</th>' +
                    '        <th>Open</th>' +
                    '    </tr>' +
                    '    </thead>' +
                    '    <tbody>';
                res.forEach(obj => {
                    str += '<tr>' +
                        '<td>' + obj.date + '</td>' +
                        '<td>' + obj.complaints + '</td>' +
                        '<td>' + obj.diagnosis + '</td>' +
                        '<td><a href="/api/appointment/' + obj.id + '">Open</a></td>' +
                        '</tr>';
                });
                str += '</tbody></table>';
            }
            document.getElementById("apps").innerHTML = str;
        }
    });
}

async function next(){
    ++page;
    let sort;
    if($('#sort option:selected').text() == "asc")
        sort = 1;
    else sort = 2;
    document.getElementById("apps").innerHTML = "";
    fetch("api/pet/history/"+ petid + "?sort=" + sort + "&page=" + page, {
        method: 'Get',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization' : header()
        }
    }).then(res => res.json()).then(res => {
        let str = 'Not Found';
        if(res.status == 500){
            $('#div').html(errorPage("Authorisation Error"));
        }else {
            if (!res.error) {
                str = '<table>' +
                    '    <thead>' +
                    '    <tr>' +
                    '        <th>Date</th>' +
                    '        <th>Condition</th>' +
                    '        <th>Diagnosis</th>' +
                    '        <th>Open</th>' +
                    '    </tr>' +
                    '    </thead>' +
                    '    <tbody>';
                res.forEach(obj => {
                    str += '<tr>' +
                        '<td>' + obj.date + '</td>' +
                        '<td>' + obj.complaints + '</td>' +
                        '<td>' + obj.diagnosis + '</td>' +
                        '<td><a href="/api/appointment/' + obj.id + '">Open</a></td>' +
                        '</tr>';
                });
                str += '</tbody></table>';
            }
            document.getElementById("apps").innerHTML = str;
        }
    });
}



