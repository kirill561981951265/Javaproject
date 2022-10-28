var petid;
var id;
var page = 0;

async function search(){
    document.getElementById("searchRes").innerHTML = "";
    fetch("api/doctor/search/" + $('#fio').val(), {
        method: 'Get',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization' : header()
        }
    }).then(res => res.json()).then(res => {
        let str = 'Not Found';
        console.log(res.error);
        if(res.status == 500){
            $('#div').html(errorPage("Authorisation Error"));
        }else {
            if (!res.error) {
                str = '<table>' +
                    '    <thead>' +
                    '    <tr>' +
                    '        <th>FIO</th>' +
                    '        <th>Name</th>' +
                    '        <th>Type</th>' +
                    '        <th>Breed</th>' +
                    '        <th>Gender</th>' +
                    '        <th>Bday</th>' +
                    '        <th>Add</th>' +
                    '        <th>History</th>' +
                    '    </tr>' +
                    '    </thead>' +
                    '    <tbody>';
                res.forEach(obj => {
                    str += '<tr>' +
                        '<td>' + obj.fio + '</td>' +
                        '<td>' + obj.name + '</td>' +
                        '<td>' + obj.type + '</td>' +
                        '<td>' + obj.breed + '</td>' +
                        '<td>' + obj.gender + '</td>' +
                        '<td>' + obj.bday + '</td>' +
                        '<td><a href="api/appointment/add?idu=' + id + '&idp=' + obj.id + '">add</a> </td>' +
                        '<td><button onclick="historyPet(' + obj.id + ')">Open</button> </td>' +
                        '</tr>';
                });
                str += '</tbody></table></br>' +
                    '<h4>Select sort to history:</h4>' +
                    '<select id="sort" onselect="historyPet(); style="margin: 10px;">\n' +
                    '<option>asc</option>\n' +
                    '<option>desc</option>\n' +
                    '</select><br/>';
            }
            document.getElementById("searchRes").innerHTML = str;
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
    fetch("api/pet/history/" + id + "?sort=" + sort + "&page=" + page, {
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
                    '        <th>Delete</th>' +
                    '    </tr>' +
                    '    </thead>' +
                    '    <tbody>';
                res.forEach(obj => {
                    str += '<tr>' +
                        '<td>' + obj.date + '</td>' +
                        '<td>' + obj.complaints + '</td>' +
                        '<td>' + obj.diagnosis + '</td>' +
                        '<td><a href="/api/appointment/' + obj.id + '">Open</a></td>' +
                        '<td><button onclick="deleteAppointment(' + obj.id + ')">X</button> </td>' +
                        '</tr>';
                });
                str += '</tbody></table></div><button onclick="back()">Back</button></br><button onclick="next()">Next</button>';
            }
            document.getElementById("historyPet").innerHTML = str;
        }
    });
}

async function deleteAppointment(id){
    var del = confirm("Delete?");
    if (del == false)
        return;
    fetch("/api/appointment/doctor/delete/" + id, {
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
            historyPet(petid);
        }
    });
}

async function getCharacter(){
    fetch("/api/doctor/users", {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            'Authorization' : header()
        }
    }).then(res => res.json()).then(res => {
        if(res.status == 500){
            $('#div').html(errorPage("Authorisation Error"));
        }else {
            var list = document.getElementById('character');
            let users = res;
            users.forEach(obj => {
                console.log(obj);
                var option = document.createElement('option');
                option.value = obj;
                list.appendChild(option);
            });
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
    fetch("api/pet/history/" + petid + "?sort=" + sort + "&page=" + page, {
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
                    '        <th>Delete</th>' +
                    '    </tr>' +
                    '    </thead>' +
                    '    <tbody>';
                res.forEach(obj => {
                    str += '<tr>' +
                        '<td>' + obj.date + '</td>' +
                        '<td>' + obj.complaints + '</td>' +
                        '<td>' + obj.diagnosis + '</td>' +
                        '<td><a href="/api/appointment/' + obj.id + '">Open</a></td>' +
                        '<td><button onclick="deleteAppointment(' + obj.id + ')">X</button> </td>' +
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
    fetch("api/pet/history/" + petid + "?sort=" + sort + "&page=" + page, {
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
                    '        <th>Delete</th>' +
                    '    </tr>' +
                    '    </thead>' +
                    '    <tbody>';
                res.forEach(obj => {
                    str += '<tr>' +
                        '<td>' + obj.date + '</td>' +
                        '<td>' + obj.complaints + '</td>' +
                        '<td>' + obj.diagnosis + '</td>' +
                        '<td><a href="/api/appointment/' + obj.id + '">Open</a></td>' +
                        '<td><button onclick="deleteAppointment(' + obj.id + ')">X</button> </td>' +
                        '</tr>';
                });
                str += '</tbody></table>';
            }
            document.getElementById("apps").innerHTML = str;
        }
    });

}



